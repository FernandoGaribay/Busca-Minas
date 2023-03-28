package main;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class TableroBuscaminas {

    private Casilla[][] casillas;
    private int numFilas;
    private int numColumnas;
    private int numMinas;
    
    private int numCasillasAbiertas;
    private boolean juegoTerminado;
    
    private Consumer<List<Casilla>> eventoPartidaPerdida;
    private Consumer<List<Casilla>> eventoPartidaGanada;
    private Consumer<Casilla> eventoCasillaAbierta; 
    private Consumer<Casilla> eventoMarcarBandera; 

    public TableroBuscaminas(int numFilas, int numColumnas, int numMinas) {
        this.numFilas = numFilas;
        this.numColumnas = numColumnas;
        this.numMinas = numMinas;
        this.inicializarCasillas();
    }

    private void inicializarCasillas() {
        this.casillas = new Casilla[this.numFilas][numColumnas];
        for (int i = 0; i < this.casillas.length; i++) {
            for (int j = 0; j < this.casillas[i].length; j++) {
                this.casillas[i][j] = new Casilla(i, j);
            }
        }
        this.generarMinas();
    }

    private void generarMinas() {
        int minasGeneradas = 0;
        while (minasGeneradas != this.numMinas) {
            int posTmpFila = (int) (Math.random() * this.casillas.length);
            int posTmpColumna = (int) (Math.random() * this.casillas[0].length);
            if (!this.casillas[posTmpFila][posTmpColumna].isMina()) {
                this.casillas[posTmpFila][posTmpColumna].setMina(true);
                minasGeneradas++;
            }
        }
        this.actualizarNumeroMinasAlrrededor();
    }

    private void actualizarNumeroMinasAlrrededor() {
        for (int i = 0; i < this.casillas.length; i++) {
            for (int j = 0; j < this.casillas[i].length; j++) {
                if (this.casillas[i][j].isMina()) {
                    List<Casilla> casillasAlrrededor = obtenerCasillasAlrrededor(i, j);
                    casillasAlrrededor.forEach((c) -> c.incrementarNumeroMinasAlrrededor());
                }
            }
        }
    }

    private List<Casilla> obtenerCasillasAlrrededor(int posFila, int posColumna) {
        List<Casilla> listaCasillas = new LinkedList<>();
        for (int i = 0; i < 8; i++) {
            int tmpPosFila = posFila;
            int tmpPosColumna = posColumna;
            switch (i) {
                case 0: tmpPosFila--; break;                    //Arriba
                case 1: tmpPosFila--; tmpPosColumna++; break;   //Arriba Derecha
                case 2: tmpPosColumna++;  break;                //Derecha
                case 3: tmpPosFila++; tmpPosColumna++; break;   //Abajo Derecha  
                case 4: tmpPosFila++; break;                    //Abajo 
                case 5: tmpPosFila++; tmpPosColumna--; break;   //Abajo Izquierda
                case 6: tmpPosColumna--; break;                 //Izquierda
                case 7: tmpPosFila--; tmpPosColumna--; break;   //Arriba Izquierda 
            }
            if (tmpPosFila >= 0 && tmpPosFila < this.casillas.length
                    && tmpPosColumna >= 0 && tmpPosColumna < this.casillas[0].length) {
                listaCasillas.add(this.casillas[tmpPosFila][tmpPosColumna]);
            }
        }
        return listaCasillas;
    }

    private List<Casilla> obtenerCasillasConMinas() {
        List<Casilla> casillasConMinas = new LinkedList<>();
        for (Casilla[] fila : this.casillas) {
            for (Casilla filaColumna : fila) {
                if (filaColumna.isMina()) {
                    casillasConMinas.add(filaColumna);
                }
            }
        }
        return casillasConMinas;
    }
    
    public void seleccionarCasilla(int posFila, int posColumna){

        if (this.casillas[posFila][posColumna].isMina()) {
            eventoPartidaPerdida.accept(obtenerCasillasConMinas());
        } else {
            this.eventoCasillaAbierta.accept(this.casillas[posFila][posColumna]);
            if (this.casillas[posFila][posColumna].getNumMinasAlrrededor() == 0) {
                this.marcarCasillaAbierta(posFila, posColumna);
                List<Casilla> casillasAlrrededor = obtenerCasillasAlrrededor(posFila, posColumna);
                for (Casilla casilla : casillasAlrrededor) {
                    if (!casilla.isAbierta()) {
                        seleccionarCasilla(casilla.getPosFila(), casilla.getPosColumna());
                    }
                }
            } else {
                this.marcarCasillaAbierta(posFila, posColumna);
            }
        }

        if (this.partidaGanada()) {
            eventoPartidaGanada.accept(obtenerCasillasConMinas());
        }
    }
    
    private void marcarCasillaAbierta(int posFila, int posColumna){
        if(!this.casillas[posFila][posColumna].isAbierta()){
            this.casillas[posFila][posColumna].setAbierta(true);
            this.numCasillasAbiertas++;
        }
    }
    
    public void marcarCasillaBandera(int posFila, int posColumna){
        this.eventoMarcarBandera.accept(this.casillas[posFila][posColumna]);
        if(!this.casillas[posFila][posColumna].isBandera()){
            this.casillas[posFila][posColumna].setBandera(true);
        } else {
            this.casillas[posFila][posColumna].setBandera(false);
        }
    }
    
    public boolean partidaGanada(){
        return this.numCasillasAbiertas>=(this.numFilas*this.numColumnas)-this.numMinas; 
    } 
     
    public void setEventoPartidaPerdida(Consumer<List<Casilla>> eventoPartidaPerdida) {
        this.eventoPartidaPerdida = eventoPartidaPerdida;
    }
    
    public void setEventoCasillaAbierta(Consumer<Casilla> eventoCasillaAbierta) {
        this.eventoCasillaAbierta = eventoCasillaAbierta;
    }
    
    public void setEventoMarcarBandera(Consumer<Casilla> eventoMarcarBandera) {
        this.eventoMarcarBandera = eventoMarcarBandera;
    }
    
    public void setEventoPartidaGanada(Consumer<List<Casilla>> eventoPartidaGanada) {
        this.eventoPartidaGanada = eventoPartidaGanada;
    }
    
    
    
    public static void main(String[] args) {
        TableroBuscaminas tablero = new TableroBuscaminas(6, 6, 5);
        tablero.imprimirTablero();
        System.out.println("---------");
        tablero.imprimirPistas();
    }

    public void imprimirTablero() {
        for (Casilla[] fila : this.casillas) {
            for (Casilla filaColumna : fila) {
                System.out.print(filaColumna.isMina() ? "*" : "0");
            }
            System.out.println("");
        }
    }

    private void imprimirPistas() {
        for (Casilla[] fila : this.casillas) {
            for (Casilla filaColumna : fila) {
                System.out.print(filaColumna.getNumMinasAlrrededor());
            }
            System.out.println("");
        }
    }
}
