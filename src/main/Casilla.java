package main;

public class Casilla {
    
    private int posFila;
    private int posColumna;
    private boolean mina;
    private boolean bandera;
    private int numMinasAlrrededor;
    private boolean abierta;
    
    public Casilla(int posFila, int posColumna) {
        this.posFila = posFila;
        this.posColumna = posColumna;
    }

    public int getPosFila() {
        return posFila;
    }

    public void setPosFila(int posFila) {
        this.posFila = posFila;
    }

    public int getPosColumna() {
        return posColumna;
    }

    public void setPosColumna(int posColumna) {
        this.posColumna = posColumna;
    }

    public boolean isMina() {
        return mina;
    }

    public void setMina(boolean mina) {
        this.mina = mina;
    }

    public int getNumMinasAlrrededor() {
        return numMinasAlrrededor;
    }

    public void setNumMinasAlrrededor(int numMinasAlrrededor) {
        this.numMinasAlrrededor = numMinasAlrrededor;
    }
    
    public void incrementarNumeroMinasAlrrededor(){
        this.numMinasAlrrededor++;
    }

    public boolean isAbierta() {
        return abierta;
    }

    public void setAbierta(boolean abierta) {
        this.abierta = abierta;
    }

    public boolean isBandera() {
        return bandera;
    }

    public void setBandera(boolean bandera) {
        this.bandera = bandera;
    }
}
