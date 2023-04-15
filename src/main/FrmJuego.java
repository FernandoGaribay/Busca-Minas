package main;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;

public class FrmJuego extends javax.swing.JFrame {

    private int numFilas = 10;
    private int numColumnas = 10;
    private int numMinas = 2;
    private int tamanoCasilla = 40;
    private int margenCasillas = 5;
    private JButtomCustom[][] casillas;
    private TableroBuscaminas tablero;

    public FrmJuego() {
        initComponents();
    }

    private void descargarControles() {
        if (this.casillas != null) {
            for (JButtomCustom[] filas : casillas) {
                for (JButtomCustom filaColumna : filas) {
                    this.getContentPane().remove(filaColumna);
                }
            }
        }
    }

    private void nuevoJuego() {
        descargarControles();
        cargarControles();
        crearTablero();
        repaint();
    }

    private void crearTablero() {
        this.tablero = new TableroBuscaminas(numFilas, numColumnas, numMinas);
        
        this.tablero.setEventoPartidaPerdida((List<Casilla> t) -> {
            for (Casilla casillaConMina : t) {
                casillas[casillaConMina.getPosFila()][casillaConMina.getPosColumna()].bomba();
            }
        });
        
        tablero.setEventoCasillaAbierta((Casilla t) -> {
            casillas[t.getPosFila()][t.getPosColumna()].revelar();
            casillas[t.getPosFila()][t.getPosColumna()].setNumber(t.getNumMinasAlrrededor() == 0 ? "" : t.getNumMinasAlrrededor() + "");
        });
        
        tablero.setEventoPartidaGanada((List<Casilla> t) -> {
            for (Casilla casillaConMina : t) {
                casillas[casillaConMina.getPosFila()][casillaConMina.getPosColumna()].setText(":)");
            }
        });
        
        tablero.setEventoMarcarBandera((Casilla t) -> {
            if(!t.isBandera()){
                casillas[t.getPosFila()][t.getPosColumna()].bandera();
            } else {
                casillas[t.getPosFila()][t.getPosColumna()].normal();
            }
        });
        
        this.tablero.imprimirTablero();
    }

    private void cargarControles() {
        this.casillas = new JButtomCustom[this.numFilas][this.numColumnas];
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                boolean esOscuro = (i + j) % 2 == 0;
                casillas[i][j] = new JButtomCustom(esOscuro);
                casillas[i][j].setName(i + "," + j);
                casillas[i][j].setBounds(10 + j * tamanoCasilla, 10 + i * tamanoCasilla, tamanoCasilla, tamanoCasilla);

                casillas[i][j].addActionListener((ActionEvent e) -> {
                    btnClickIzquierdo((JButtomCustom) e.getSource());
                });
                casillas[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            btnClickDerecho((JButtomCustom) e.getSource());
                        }
                    }
                });
                getContentPane().add(casillas[i][j]);
            }
        }
        this.tamanoVentana();
    }
    
    private void tamanoVentana(){
        int ancho = numColumnas * tamanoCasilla + 37;
        int alto = numFilas * tamanoCasilla + 82;
        setSize(ancho, alto);
    }
    
    private int[] obtenerCoordenadas(JButtomCustom boton){
        int coordenadas[] = new int[2];
        for (int i = 0; i < 2; i++) {
            coordenadas[i] = Integer.parseInt(boton.getName().split(",")[i]);
        }
        return coordenadas;
    }

    private void btnClickIzquierdo(JButtomCustom e) {
        int coordenadas[] = obtenerCoordenadas(e);
        tablero.seleccionarCasilla(coordenadas[0], coordenadas[1]);
    }
    
    private void btnClickDerecho(JButtomCustom e) {
        int coordenadas[] = obtenerCoordenadas(e);
        tablero.marcarCasillaBandera(coordenadas[0], coordenadas[1]);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        menuNuevoJuego = new javax.swing.JMenu();
        btnPrincipiante = new javax.swing.JMenuItem();
        btnIntermedio = new javax.swing.JMenuItem();
        btnExperto = new javax.swing.JMenuItem();
        btnPersonalizado = new javax.swing.JMenuItem();
        menuConfiguracion = new javax.swing.JMenu();
        btnTamano = new javax.swing.JMenuItem();
        btnMargen = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        menuNuevoJuego.setText("Nuevo Juego");

        btnPrincipiante.setText("Principiante");
        btnPrincipiante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrincipianteActionPerformed(evt);
            }
        });
        menuNuevoJuego.add(btnPrincipiante);

        btnIntermedio.setText("Intermedio");
        btnIntermedio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIntermedioActionPerformed(evt);
            }
        });
        menuNuevoJuego.add(btnIntermedio);

        btnExperto.setText("Experto");
        btnExperto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExpertoActionPerformed(evt);
            }
        });
        menuNuevoJuego.add(btnExperto);

        btnPersonalizado.setText("Personalizado");
        btnPersonalizado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPersonalizadoActionPerformed(evt);
            }
        });
        menuNuevoJuego.add(btnPersonalizado);

        jMenuBar1.add(menuNuevoJuego);

        menuConfiguracion.setText("Configuracion");

        btnTamano.setText("Tamaño de las casillas");
        btnTamano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTamanoActionPerformed(evt);
            }
        });
        menuConfiguracion.add(btnTamano);

        btnMargen.setText("Marden de las casillas");
        btnMargen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMargenActionPerformed(evt);
            }
        });
        menuConfiguracion.add(btnMargen);

        jMenuBar1.add(menuConfiguracion);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 472, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 497, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnPrincipianteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrincipianteActionPerformed
        this.setNumFilas(8);
        this.setNumColumnas(8);
        this.setNumMinas(10);
        this.nuevoJuego();
    }//GEN-LAST:event_btnPrincipianteActionPerformed

    private void btnIntermedioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIntermedioActionPerformed
        this.setNumFilas(16);
        this.setNumColumnas(16);
        this.setNumMinas(40);
        this.nuevoJuego();
    }//GEN-LAST:event_btnIntermedioActionPerformed

    private void btnExpertoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExpertoActionPerformed
        this.setNumFilas(16);
        this.setNumColumnas(30);
        this.setNumMinas(99);
        this.nuevoJuego();
    }//GEN-LAST:event_btnExpertoActionPerformed

    private void btnPersonalizadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPersonalizadoActionPerformed
        int filas = Integer.parseInt(JOptionPane.showInputDialog("Dijite el numero de filas: "));
        int columnas = Integer.parseInt(JOptionPane.showInputDialog("Dijite el numero de columnas: "));
        int minas = Integer.parseInt(JOptionPane.showInputDialog("Dijite el numero de minas: "));

        this.setNumFilas(filas);
        this.setNumColumnas(columnas);
        this.setNumMinas(minas);
        this.nuevoJuego();
    }//GEN-LAST:event_btnPersonalizadoActionPerformed

    private void btnTamanoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTamanoActionPerformed
        int tamano = Integer.parseInt(JOptionPane.showInputDialog("Dijite el tamaño de las casillas: "));
        this.setTamanoCasilla(tamano);
    }//GEN-LAST:event_btnTamanoActionPerformed

    private void btnMargenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMargenActionPerformed
        int margen = Integer.parseInt(JOptionPane.showInputDialog("Dijite el tamaño de las casillas: "));
        this.setMargenCasillas(margen);
    }//GEN-LAST:event_btnMargenActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmJuego().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem btnExperto;
    private javax.swing.JMenuItem btnIntermedio;
    private javax.swing.JMenuItem btnMargen;
    private javax.swing.JMenuItem btnPersonalizado;
    private javax.swing.JMenuItem btnPrincipiante;
    private javax.swing.JMenuItem btnTamano;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu menuConfiguracion;
    private javax.swing.JMenu menuNuevoJuego;
    // End of variables declaration//GEN-END:variables

    private void setNumFilas(int numFilas) {
        this.numFilas = numFilas;
    }

    private void setNumColumnas(int numColumnas) {
        this.numColumnas = numColumnas;
    }

    private void setNumMinas(int numMinas) {
        this.numMinas = numMinas;
    }

    public void setTamanoCasilla(int tamanoCasilla) {
        this.tamanoCasilla = tamanoCasilla;
    }

    public void setMargenCasillas(int margenCasillas) {
        this.margenCasillas = margenCasillas;
    }
}
