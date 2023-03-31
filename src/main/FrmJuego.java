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
    private JButtomCustom[][] botonesTablero;
    private TableroBuscaminas tableroBuscaminas;

    public FrmJuego() {
        initComponents();
    }

    private void descargarControles() {
        if (this.botonesTablero != null) {
            for (JButtomCustom[] filas : botonesTablero) {
                for (JButtomCustom filaColumna : filas) {
                    this.getContentPane().remove(filaColumna);
                }
            }
        }
    }

    private void nuevoJuego() {
        descargarControles();
        cargarControles();
        crearTableroBuscaminas();
        repaint();
    }

    private void crearTableroBuscaminas() {
        this.tableroBuscaminas = new TableroBuscaminas(numFilas, numColumnas, numMinas);
        
        this.tableroBuscaminas.setEventoPartidaPerdida((List<Casilla> t) -> {
            for (Casilla casillaConMina : t) {
                botonesTablero[casillaConMina.getPosFila()][casillaConMina.getPosColumna()].bomba();
            }
        });
        
        tableroBuscaminas.setEventoCasillaAbierta((Casilla t) -> {
            botonesTablero[t.getPosFila()][t.getPosColumna()].revelar();
            botonesTablero[t.getPosFila()][t.getPosColumna()].setNumber(t.getNumMinasAlrrededor() == 0 ? "" : t.getNumMinasAlrrededor() + "");
        });
        
        tableroBuscaminas.setEventoPartidaGanada((List<Casilla> t) -> {
            for (Casilla casillaConMina : t) {
                botonesTablero[casillaConMina.getPosFila()][casillaConMina.getPosColumna()].setText(":)");
            }
        });
        
        tableroBuscaminas.setEventoMarcarBandera((Casilla t) -> {
            if(!t.isBandera()){
                botonesTablero[t.getPosFila()][t.getPosColumna()].bandera();
            } else {
                botonesTablero[t.getPosFila()][t.getPosColumna()].normal();
            }
        });
        
        this.tableroBuscaminas.imprimirTablero();
    }

    private void cargarControles() {
        int posXReferencia = 25;
        int posYReferencia = 25;
        int anchoControl = 30;
        int altoControl = 30;

        this.botonesTablero = new JButtomCustom[this.numFilas][this.numColumnas];
        for (int i = 0; i < botonesTablero.length; i++) {
            for (int j = 0; j < botonesTablero[i].length; j++) {
                botonesTablero[i][j] = new JButtomCustom();
                botonesTablero[i][j].setName(i + "," + j);
                if (i == 0 && j == 0) {
                    botonesTablero[i][j].setBounds(posXReferencia, posYReferencia, anchoControl, altoControl);
                } else if (i == 0 && j != 0) {
                    botonesTablero[i][j].setBounds(botonesTablero[i][j - 1].getX() + botonesTablero[i][j - 1].getWidth() + 5, 
                            posYReferencia, anchoControl, altoControl);
                } else {
                    botonesTablero[i][j].setBounds(botonesTablero[i - 1][j].getX(), 
                            botonesTablero[i - 1][j].getY() + botonesTablero[i - 1][j].getHeight() + 5, 
                            anchoControl, altoControl);
                }
                
                botonesTablero[i][j].addActionListener((ActionEvent e) -> {
                    btnClickIzquierdo((JButtomCustom) e.getSource());
                });
                botonesTablero[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            btnClickDerecho((JButtomCustom) e.getSource());
                        }
                    }
                });
                getContentPane().add(botonesTablero[i][j]);
            }
        }
        this.setSize(botonesTablero[numFilas - 1][numColumnas - 1].getX()
                + botonesTablero[numFilas - 1][numColumnas - 1].getWidth() + 40,
                botonesTablero[numFilas - 1][numColumnas - 1].getY()
                + botonesTablero[numFilas - 1][numColumnas - 1].getHeight() + 80
        );

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
        tableroBuscaminas.seleccionarCasilla(coordenadas[0], coordenadas[1]);
    }
    
    private void btnClickDerecho(JButtomCustom e) {
        int coordenadas[] = obtenerCoordenadas(e);
        tableroBuscaminas.marcarCasillaBandera(coordenadas[0], coordenadas[1]);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        btnNuevoJuego = new javax.swing.JMenu();
        btnPrincipiante = new javax.swing.JMenuItem();
        btnIntermedio = new javax.swing.JMenuItem();
        btnExperto = new javax.swing.JMenuItem();
        btnPersonalizado = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        btnNuevoJuego.setText("Nuevo Juego");

        btnPrincipiante.setText("Principiante");
        btnPrincipiante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrincipianteActionPerformed(evt);
            }
        });
        btnNuevoJuego.add(btnPrincipiante);

        btnIntermedio.setText("Intermedio");
        btnIntermedio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIntermedioActionPerformed(evt);
            }
        });
        btnNuevoJuego.add(btnIntermedio);

        btnExperto.setText("Experto");
        btnExperto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExpertoActionPerformed(evt);
            }
        });
        btnNuevoJuego.add(btnExperto);

        btnPersonalizado.setText("Personalizado");
        btnPersonalizado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPersonalizadoActionPerformed(evt);
            }
        });
        btnNuevoJuego.add(btnPersonalizado);

        jMenuBar1.add(btnNuevoJuego);

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
    private javax.swing.JMenu btnNuevoJuego;
    private javax.swing.JMenuItem btnPersonalizado;
    private javax.swing.JMenuItem btnPrincipiante;
    private javax.swing.JMenuBar jMenuBar1;
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
}
