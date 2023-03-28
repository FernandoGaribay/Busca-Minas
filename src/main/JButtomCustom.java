package main;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;

public class JButtomCustom extends JButton {

    private Estado estado;
    
    public JButtomCustom() {
        this.setFocusable(false);
        this.estado = Estado.DEFAULT; // estado por defecto
        dibujar();
    }

    public void setNumber(String number) {
        switch (number) {
            case "1" -> setForeground(Color.BLUE);
            case "2" -> setForeground(Color.GREEN.darker());
            case "3" -> setForeground(Color.RED);
            case "4" -> setForeground(Color.MAGENTA.darker());
            case "5" -> setForeground(Color.ORANGE);
            case "6" -> setForeground(Color.CYAN.darker());
            case "7" -> setForeground(Color.BLACK);
            case "8" -> setForeground(Color.GRAY);
        }
        setText(number);
    }

    private void cambiarEstado(Estado nuevoEstado) {
        if (estado == Estado.REVEALED) {
            return; // No se puede cambiar el estado si ya ha sido revelado
        }
        estado = nuevoEstado;
        dibujar();
    }

    
    private void dibujar() {
        switch (estado) {
            case DEFAULT:
                setBackground(Color.LIGHT_GRAY);
                setForeground(Color.BLACK);
                setFont(new Font("Arial", Font.BOLD, 16));
                setText("");
                break;
            case FLAGGED:
                setBackground(Color.LIGHT_GRAY);
                setForeground(Color.BLACK);
                setText("F");
                break;
            case REVEALED:
                setBackground(Color.WHITE);
                setForeground(getNumberColor());
                setBorderPainted(false);
                break;
            case BOMBA:
                setBackground(Color.RED);
                setText("X");
        }
        repaint();
    }

    public void normal(){
        cambiarEstado(Estado.DEFAULT);
    }
    
    public void revelar() {
        cambiarEstado(Estado.REVEALED);
    }
    
    public void bandera(){
        cambiarEstado(Estado.FLAGGED);
    }
    
    public void bomba(){
        cambiarEstado(Estado.BOMBA);
    }
        
    private enum Estado {
        DEFAULT,
        FLAGGED,
        REVEALED,
        BOMBA
    }

    

//    public void setBomb() {
//        setBackground(Color.RED);
//        setText("X");
//    }
    
//    public void setRevealed() {
//        setBackground(Color.WHITE);
//        setForeground(getNumberColor());
//        setBorderPainted(false);
//    }

//    public void setBandera() {
//        setBackground(Color.LIGHT_GRAY);
//        setForeground(Color.BLACK);
//        setText("F");
//    }
    
//    public void removeBandera() {
//        setBackground(Color.LIGHT_GRAY);
//        setForeground(Color.BLACK);
//        setText("");
//    }

    private Color getNumberColor() {
        return getForeground();
    }
}
