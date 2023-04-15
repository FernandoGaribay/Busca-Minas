package main;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public final class JButtomCustom extends JButton {

    private final Color colorHover = new Color(193, 239, 105);
    private final Color bgDefault;
    private final Color bgRevelado;
    private Estado estado;

    public JButtomCustom(boolean esOscuro) {
        if (esOscuro) {
            bgDefault = new Color(205, 235, 145);
            bgRevelado = new Color(228, 193, 158);
            setBackground(new Color(198, 228, 139));
        } else {
            bgDefault = new Color(161, 208, 73);
            bgRevelado = new Color(214, 183, 152);
            setBackground(new Color(161, 208, 73));
        }

        this.setFocusable(false);
        this.setBorder(null);
        this.estado = Estado.DEFAULT; // estado por defecto
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        inicializarEventos();
        dibujar();
    }

    public void inicializarEventos() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (estado == Estado.DEFAULT) {
                    setBackground(colorHover);
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (estado == Estado.DEFAULT) {
                    setBackground(bgDefault);
                }
            }
        });
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
        if (estado == Estado.REVELAR) {
            return; 
        }
        estado = nuevoEstado;
        dibujar();
    }

    
    private void dibujar() {
        switch (estado) {
            case DEFAULT:
                setIcon(null);
                setForeground(Color.BLACK);
                setFont(new Font("Arial", Font.BOLD, 20));
                setText("");
                break;
            case BANDERA:
                try { 
                    Image imagen = ImageIO.read(getClass().getResource("/imagenes/bandera.png"));
                    ImageIcon icono = new ImageIcon(imagen.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH));
                    setIcon(icono);
                } catch (IOException ex) {}
                break;
            case REVELAR:
                setIcon(null);
                setBackground(bgRevelado);
                setForeground(getNumberColor());
                setBorderPainted(false);
                break;
            case BOMBA:
                setBackground(Color.RED);
                try { 
                    Image imagen = ImageIO.read(getClass().getResource("/imagenes/bomba.png"));
                    ImageIcon icono = new ImageIcon(imagen.getScaledInstance(getWidth()-15, getHeight()-15, Image.SCALE_SMOOTH));
                    setIcon(icono);
                } catch (IOException ex) {}
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
