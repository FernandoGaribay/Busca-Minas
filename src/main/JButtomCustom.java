package main;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;

public class JButtomCustom extends JButton {

    private Estado estado;
    
    public JButtomCustom() {
        this.setFocusable(false);
        this.setBorder(null);
        this.estado = Estado.DEFAULT; // estado por defecto
        dibujar();
    }

    public void setNumber(String number) { // Cambiar el color del nombre del boton dependiendo el numero
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
        if (estado == Estado.REVELAR) {
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
            case BANDERA:
                setBackground(Color.LIGHT_GRAY);
                setForeground(Color.BLACK);
                setText("F");
                break;
            case REVELAR:
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
        cambiarEstado(Estado.REVELAR);
    }
    
    public void bandera(){
        cambiarEstado(Estado.BANDERA);
    }
    
    public void bomba(){
        cambiarEstado(Estado.BOMBA);
    }
        
    private enum Estado {
        DEFAULT,
        BANDERA,
        REVELAR,
        BOMBA
    }

    private Color getNumberColor() {
        return getForeground();
    }
}
