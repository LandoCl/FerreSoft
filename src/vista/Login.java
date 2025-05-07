package vista;

import controles.Configuracion;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Login extends javax.swing.JFrame {
    private Configuracion config;
    private Connection conn;
    private Menu menu;
    public Login() {
        config = new Configuracion();
        initComponents();
    }
    public void connectToDatabase(String user, String password) {
        try {
            String url = "jdbc:mysql://localhost:3308/ferreteria_acosta";
            conn = DriverManager.getConnection(url, user, password);
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error al conectar a la base de datos: " + ex.getMessage());
        }
    }

    public boolean verificaCredenciales(String user, String password) {
        String url = config.getDatabaseUrl(); // URL desde Configuracion
        try (Connection testConn = DriverManager.getConnection(url, user, password)) {
            return true; // Si conecta, credenciales válidas
        } catch (SQLException ex) {
            return false; // Credenciales inválidas
        }
    }
    private void salir(int opc) {
        switch (opc) {
            case JOptionPane.YES_OPTION -> System.exit(0);
            case JOptionPane.NO_OPTION -> {
            }
            case JOptionPane.CANCEL_OPTION -> {
            }
        }
    }
    private void limpiarCampos() {
        userLog.setText("");
        passLog.setText("");
        userLog.requestFocusInWindow();
    }
    private void iniciarSesion() {
        String user = userLog.getText();
        String password = new String(passLog.getPassword());

        if (verificaCredenciales(user, password)) {
            try {
                // Configurar credenciales en la clase Configuracion
                

                // Intentar conectar con las credenciales proporcionadas
                Connection conn = DriverManager.getConnection(config.getDatabaseUrl(), user, password);
                JOptionPane.showMessageDialog(this, "Conexión exitosa. Bienvenido " + user + "!", "Login", JOptionPane.INFORMATION_MESSAGE);

                // Inicia la aplicación principal
                config.setUser(user);
                config.setPassword(password);
                new Menu(config).setVisible(true);
                dispose(); // Cierra la ventana de login

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
            limpiarCampos();
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        passLog = new javax.swing.JPasswordField();
        userLog = new javax.swing.JTextField();
        userLbl = new javax.swing.JLabel();
        passLbl = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnIniciar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Inicio de Sesión");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));

        passLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Login.this.actionPerformed(evt);
            }
        });
        passLog.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Login.this.keyPressed(evt);
            }
        });

        userLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Login.this.actionPerformed(evt);
            }
        });
        userLog.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Login.this.keyPressed(evt);
            }
        });

        userLbl.setFont(new java.awt.Font("Garamond", 1, 18)); // NOI18N
        userLbl.setText("Usuario");

        passLbl.setFont(new java.awt.Font("Garamond", 0, 18)); // NOI18N
        passLbl.setText("Contraseña");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/usuario (2).png"))); // NOI18N

        btnIniciar.setText("Iniciar");
        btnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Login.this.actionPerformed(evt);
            }
        });
        btnIniciar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Login.this.keyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(passLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(userLbl)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(passLog)
                                .addComponent(userLog, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addComponent(btnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(139, 139, 139)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(userLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userLog, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(passLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passLog, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(btnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void actionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actionPerformed
        if (evt.getSource() == btnIniciar) {
            iniciarSesion();
        }
    }//GEN-LAST:event_actionPerformed

    private void keyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            salir(JOptionPane.showConfirmDialog(null, "¿Salir del sistema?"));
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            iniciarSesion();
        }
    }//GEN-LAST:event_keyPressed

    
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIniciar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel passLbl;
    private javax.swing.JPasswordField passLog;
    private javax.swing.JLabel userLbl;
    private javax.swing.JTextField userLog;
    // End of variables declaration//GEN-END:variables
}
