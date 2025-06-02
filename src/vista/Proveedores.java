package vista;

import controles.Configuracion;
import controles.ControlProveedores;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;


public class Proveedores extends javax.swing.JFrame {
    private Configuracion config;
    private ControlProveedores cProv;
    
    public Proveedores(Configuracion config) {
        initComponents();
        this.config = config;
        this.cProv = new ControlProveedores(config);
        cargarTablaProveedores();
    }

    private void cargarTablaProveedores() {
        jTable1.setModel(cProv.cargarProveedores());
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnAgregarProv = new javax.swing.JButton();
        btnModificarProv = new javax.swing.JButton();
        btnEliminarProv = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Proveedores.this.keyPressed(evt);
            }
        });

        btnAgregarProv.setText("Agregar");
        btnAgregarProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Proveedores.this.actionPerformed(evt);
            }
        });
        btnAgregarProv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Proveedores.this.keyPressed(evt);
            }
        });

        btnModificarProv.setText("Modificar");
        btnModificarProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Proveedores.this.actionPerformed(evt);
            }
        });
        btnModificarProv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Proveedores.this.keyPressed(evt);
            }
        });

        btnEliminarProv.setText("Eliminar");
        btnEliminarProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Proveedores.this.actionPerformed(evt);
            }
        });
        btnEliminarProv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Proveedores.this.keyPressed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Contacto"
            }
        ));
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Proveedores.this.keyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 36)); // NOI18N
        jLabel1.setText("Lista de Proveedores");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAgregarProv, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificarProv, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminarProv, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(85, 85, 85))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAgregarProv, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnModificarProv, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(btnEliminarProv, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void keyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyPressed
        if (evt.getKeyCode()==KeyEvent.VK_ESCAPE) {
            new Menu(config).setVisible(true);
            dispose();
        }
    }//GEN-LAST:event_keyPressed

    private void actionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actionPerformed
        // Agregar proveedor
        if (evt.getSource() == btnAgregarProv) {
            new AgregarProv(config).setVisible(true);
            dispose();
        }
        // Modificar proveedor
        if (evt.getSource() == btnModificarProv) {
            int filaSeleccionada = jTable1.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un proveedor para modificar", 
                                              "Atención", JOptionPane.WARNING_MESSAGE);
            } else {
                // Se espera que el modelo provisto por ControlProveedores tenga 4 columnas:
                // 0: ID, 1: Nombre, 2: Código, 3: Contacto.
                int id = Integer.parseInt(jTable1.getValueAt(filaSeleccionada, 0).toString());
                String nombre = jTable1.getValueAt(filaSeleccionada, 1).toString();
                String codigo = jTable1.getValueAt(filaSeleccionada, 2).toString();
                String contacto = jTable1.getValueAt(filaSeleccionada, 3).toString();
                
                // Invoca AgregarProv en modo MODIFICAR pasando toda la información
                new AgregarProv(config, AgregarProv.Mode.MODIFICAR, id, codigo, nombre, contacto).setVisible(true);
                dispose();
            }
        }
        // Eliminar proveedor
        if (evt.getSource() == btnEliminarProv) {
            int filaSeleccionada = jTable1.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un proveedor para eliminar", 
                                              "Atención", JOptionPane.WARNING_MESSAGE);
            } else {
                int id = Integer.parseInt(jTable1.getValueAt(filaSeleccionada, 0).toString());
                // El método eliminarProveedor de ControlProveedores ya verifica productos asociados y confirma la operación.
                boolean eliminado = cProv.eliminarProveedor(id);
                if (eliminado) {
                    JOptionPane.showMessageDialog(this, "Proveedor eliminado correctamente");
                    cargarTablaProveedores();
                }
            }
        }


    }//GEN-LAST:event_actionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarProv;
    private javax.swing.JButton btnEliminarProv;
    private javax.swing.JButton btnModificarProv;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
