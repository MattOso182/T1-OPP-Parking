package ec.edu.espe.parkinglotgui.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.*;

/**
 *
 * @author T.A.P. (The Art of Programming), @ESPE
 */
public class FrmParkingSplash extends javax.swing.JFrame {

    private javax.swing.Timer timer;
    private int progreso = 0;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmParkingSplash.class.getName());

    /**
     * Creates new form FrmParkingSplash
     */
    public FrmParkingSplash() {
        initComponents();
        setAppIcon();  
        this.setLocationRelativeTo(null);

        progressBar.setStringPainted(true);
        progressBarConfiguration();
        startAutomaticsProgress();

        try {
            java.net.URL imgURL = getClass().getResource("/images/LOGO.png");

            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);

                Image img = icon.getImage();
                Image scaledImg = img.getScaledInstance(jLabelLogo.getWidth(), jLabelLogo.getHeight(), Image.SCALE_SMOOTH);

                jLabelLogo.setIcon(new ImageIcon(scaledImg));
                jLabelLogo.setText("");
            } else {
                System.err.println("Error: No se encontró la imagen en /src/main/resources/images/LOGO.png");
            }
        } catch (Exception e) {
            System.err.println("Error al cargar imagen: " + e.getMessage());
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                progressBar.setUI(new javax.swing.plaf.basic.BasicProgressBarUI());

                progressBar.setForeground(new Color(0, 102, 204));

                progressBar.repaint();
            }
        });

    }

    private void progressBarConfiguration() {
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setValue(0);
        progressBar.setForeground(new java.awt.Color(0, 150, 0));
        progressBar.setBackground(new java.awt.Color(240, 240, 240));
        progressBar.setString("0%");
        progressBar.setVisible(true);

    }

    private void startAutomaticsProgress() {
        timer = new javax.swing.Timer(50, new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                progreso++;

                progressBar.setValue(progreso);
                progressBar.setString(progreso + "%");

                updateBarColor(progreso);

                updateProgressMessage(progreso);

                if (progreso >= 100) {
                    timer.stop();
                    goLoginScreen(); 
                }
            }
        });

        javax.swing.Timer delayTimer = new javax.swing.Timer(1000, new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timer.start();
            }
        });
        delayTimer.setRepeats(false);
        delayTimer.start();
    }

    private void updateBarColor(int percentage) {
        Color barColor;

        if (percentage < 30) {
            barColor = new Color(70, 150, 230);
        } else if (percentage < 70) {
            barColor = new Color(0, 100, 200);
        } else {
            barColor = new Color(0, 60, 150);
        }

        progressBar.setForeground(barColor);

        Color textColor = calculateContrastColor(barColor);

        applyTextColor(textColor);

        progressBar.repaint();
    }

    private Color calculateContrastColor(Color backgroundColor) {

        double brightness = (0.299 * backgroundColor.getRed()
                + 0.587 * backgroundColor.getGreen()
                + 0.114 * backgroundColor.getBlue()) / 255;

        if (brightness > 0.5) {
            return Color.BLACK;
        } else {
            return Color.WHITE;
        }
    }

    private void applyTextColor(Color textColor) {
        Color currentBarColor = progressBar.getForeground();

        progressBar.setUI(new javax.swing.plaf.basic.BasicProgressBarUI() {
            @Override
            protected Color getSelectionForeground() {
                return textColor;
            }

            @Override
            protected Color getSelectionBackground() {
                return currentBarColor;
            }

            @Override
            public void paintDeterminate(Graphics g, JComponent c) {
                Color oldColor = g.getColor();

                g.setColor(currentBarColor);

                super.paintDeterminate(g, c);

                g.setColor(oldColor);
            }
        });

        progressBar.setForeground(currentBarColor);

        progressBar.repaint();
    }

    private void updateProgressMessage(int percentage) {
        String message = "";

        if (percentage < 20) {
            message = "Iniciando sistema...";
        } else if (percentage < 40) {
            message = "Cargando módulos...";
        } else if (percentage < 60) {
            message = "Conectando a base de datos MongoDB...";
        } else if (percentage < 80) {
            message = "Configurando interfaz...";
        } else if (percentage < 100) {
            message = "Finalizando...";
        } else {
            message = "¡Listo!";
        }

        if (lblState != null) {
            lblState.setText(message);
        }
    }

    private void goLoginScreen() {
        this.dispose();

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                FrmLogin login = new FrmLogin();
                login.setVisible(true);
                login.setLocationRelativeTo(null);
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabelLogo = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        jLabel6 = new javax.swing.JLabel();
        lblState = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel1.setText("Sistema de Estacionamiento");
        jLabel1.setAutoscrolls(true);

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Multifamiliares Luluncoto ");

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel3.setText("Version 0.8.8 ");

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel5.setText("©2025");

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel4.setText("NetBeans 21");

        jLabel6.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Sistema de Parqueadero Automatizado ");

        lblState.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        lblState.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(180, 180, 180)
                        .addComponent(jLabel5)
                        .addGap(182, 182, 182)
                        .addComponent(jLabel4)
                        .addGap(58, 58, 58))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(127, 127, 127))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblState, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(220, 220, 220))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(172, 172, 172))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblState, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void setAppIcon() {
        java.net.URL iconURL = getClass().getResource("/images/logo.png");
        if (iconURL != null) {
            setIconImage(new javax.swing.ImageIcon(iconURL).getImage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new FrmParkingSplash().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabelLogo;
    private javax.swing.JLabel lblState;
    private javax.swing.JProgressBar progressBar;
    // End of variables declaration//GEN-END:variables
}
