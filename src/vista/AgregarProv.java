package vista;

import controles.Configuracion;
import controles.ControlProveedores;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author jabs0
 */
public class AgregarProv extends javax.swing.JFrame {
    public enum Mode {
        AGREGAR, MODIFICAR
    }
    
    private Configuracion config;
    private ControlProveedores cProv;
    private Mode mode = Mode.AGREGAR;  // modo por defecto: agregar
    private int idProveedor;           // se utiliza únicamente en modo MODIFICAR

    // Constructor por defecto: modo AGREGAR
    public AgregarProv(Configuracion config) {
        this.config = config;
        initComponents();
        cProv = new ControlProveedores(config);
        mode = Mode.AGREGAR;
    }
    
    // Constructor sobrecargado para el modo MODIFICAR.
    // Recibe los datos del proveedor seleccionado para prellenar los campos.
    public AgregarProv(Configuracion config, Mode mode, int id, String codigo, String nombre, String contacto) {
        this.config = config;
        initComponents();
        cProv = new ControlProveedores(config);
        this.mode = mode;
        this.idProveedor = id;
        
        // Prellenar los campos con los datos actuales
        txtCodigoProv.setText(codigo);
        txtNombreProv.setText(nombre);
        txtContacto.setText(contacto);
        
        // Actualizar el texto del botón de guardar para reflejar que se trata de una modificación
        btnGuardarProv.setText("Modificar");
    }



    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtNombreP = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        lblNomProv = new javax.swing.JLabel();
        txtCodigoProv = new javax.swing.JTextField();
        lblIdProv = new javax.swing.JLabel();
        txtNombreProv = new javax.swing.JTextField();
        btnGuardarProv = new javax.swing.JButton();
        btnRegresar = new javax.swing.JButton();
        lblContProv = new javax.swing.JLabel();
        txtContacto = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AgregarProv.this.keyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 36)); // NOI18N
        jLabel1.setText("Agregar Proveedor");

        lblNomProv.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblNomProv.setText("Nombre");

        txtCodigoProv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AgregarProv.this.keyPressed(evt);
            }
        });

        lblIdProv.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblIdProv.setText("ID");

        txtNombreProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreProvActionPerformed(evt);
            }
        });
        txtNombreProv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AgregarProv.this.keyPressed(evt);
            }
        });

        btnGuardarProv.setText("Guardar");
        btnGuardarProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProvActionPerformed(evt);
            }
        });
        btnGuardarProv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AgregarProv.this.keyPressed(evt);
            }
        });

        btnRegresar.setText("Regresar");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });
        btnRegresar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AgregarProv.this.keyPressed(evt);
            }
        });

        lblContProv.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblContProv.setText("Contacto");

        txtContacto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtContactokeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblIdProv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblNomProv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblContProv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCodigoProv, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombreProv, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtContacto, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(129, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(224, 224, 224)
                .addComponent(btnGuardarProv)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRegresar)
                .addGap(15, 15, 15))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblNomProv)
                    .addComponent(txtNombreProv, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigoProv, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIdProv))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblContProv)
                    .addComponent(txtContacto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardarProv)
                    .addComponent(btnRegresar))
                .addGap(15, 15, 15))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProvActionPerformed
        String codigo = txtCodigoProv.getText().trim();
        String nombre = txtNombreProv.getText().trim();
        String contacto = txtContacto.getText().trim();
        
        if (codigo.isEmpty() || nombre.isEmpty() || contacto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios");
            return;
        }
        
        boolean operacionExitosa = false;
        if (mode == Mode.AGREGAR) {
            operacionExitosa = cProv.agregarProveedor(codigo, nombre, contacto);
            if (operacionExitosa) {
                JOptionPane.showMessageDialog(this, "Proveedor agregado correctamente");
                // Limpiar campos para agregar otro, o cerrar la ventana.
                txtCodigoProv.setText("");
                txtNombreProv.setText("");
                txtContacto.setText("");
            }
        } else if (mode == Mode.MODIFICAR) {
            operacionExitosa = cProv.modificarProveedor(idProveedor, codigo, nombre, contacto);
            if (operacionExitosa) {
                JOptionPane.showMessageDialog(this, "Proveedor modificado correctamente");
                dispose();
                new Proveedores(config).setVisible(true);
            }
        }

    }//GEN-LAST:event_btnGuardarProvActionPerformed

    private void txtNombreProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreProvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreProvActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        dispose();
        new Proveedores(config).setVisible(true);
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void keyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyPressed
        if (evt.getKeyCode()==KeyEvent.VK_ESCAPE) {
            dispose();
            new Proveedores(config).setVisible(true);
        }
    }//GEN-LAST:event_keyPressed

    private void txtContactokeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContactokeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtContactokeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardarProv;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblContProv;
    private javax.swing.JLabel lblIdProv;
    private javax.swing.JLabel lblNomProv;
    private javax.swing.JTextField txtCodigoProv;
    private javax.swing.JTextField txtContacto;
    private javax.swing.JTextField txtNombreP;
    private javax.swing.JTextField txtNombreProv;
    // End of variables declaration//GEN-END:variables
}
