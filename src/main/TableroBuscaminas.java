package main;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class TableroBuscaminas {

    private Casilla[][] casillas;
    private int numFilas;
    private int numColumnas;
    private int numMinas;
    private int numBanderas;
    private int numCasillasAbiertas;
    
    private Consumer<List<Casilla>> eventoPartidaPerdida;
    private Consumer<List<Casilla>> eventoPartidaGanada;
    private Consumer<Casilla> eventoCasillaAbierta; 
    private Consumer<Casilla> eventoMarcarBandera; 

    public TableroBuscaminas(int numFilas, int numColumnas, int numMinas) {
        this.numFilas = numFilas;
        this.numColumnas = numColumnas;
        this.numMinas = numMinas;
        this.numBanderas = numMinas;
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
        int filas = casillas.length;
        int columnas = casillas[0].length;
        List<Casilla> adyacentes = new LinkedList<>();
        for (int i = Math.max(0, posFila - 1); i < Math.min(posFila + 2, filas); i++) {
            for (int j = Math.max(0, posColumna - 1); j < Math.min(posColumna + 2, columnas); j++) {
                if (i != posFila || j != posColumna) {
                    adyacentes.add(casillas[i][j]);
                }
            }
        }
        return adyacentes;
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
    
    private List<Casilla> obtenerCasillasSinMinas() {
        List<Casilla> casillasConMinas = new LinkedList<>();
        for (Casilla[] fila : this.casillas) {
            for (Casilla filaColumna : fila) {
                if (!filaColumna.isMina()) {
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
            eventoPartidaGanada.accept(obtenerCasillasSinMinas());
        }
    }
    
    private void marcarCasillaAbierta(int posFila, int posColumna){
        if(!this.casillas[posFila][posColumna].isAbierta()){
            this.casillas[posFila][posColumna].setAbierta(true);
            this.numCasillasAbiertas++;
        }
    }

    public void marcarCasillaBandera(int posFila, int posColumna) {
        if (!this.casillas[posFila][posColumna].isBandera()) {
            if (this.casillas[posFila][posColumna].isBandera()) {
                this.casillas[posFila][posColumna].setBandera(false);
                numBanderas += 1;
            } else {
                if (numBanderas > 0) {
                    this.eventoMarcarBandera.accept(this.casillas[posFila][posColumna]);
                    this.casillas[posFila][posColumna].setBandera(true);
                    numBanderas -= 1;
                }
            }
        } else {
            this.eventoMarcarBandera.accept(this.casillas[posFila][posColumna]);
            this.casillas[posFila][posColumna].setBandera(false);
            numBanderas += 1;
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

    public int getNumBanderas() {
        return numBanderas;
    }

    public void setNumBanderas(int numBanderas) {
        this.numBanderas = numBanderas;
    }
    
    public void imprimirTablero() {
        for (Casilla[] fila : this.casillas) {
            for (Casilla filaColumna : fila) {
                System.out.print(filaColumna.isMina() ? "*" : "0");
            }
            System.out.println("");
        }
    }
}
