package vista;

import java.sql.Connection;
import controles.Configuracion;
import controles.ControlInventario;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;


public class Inventario extends javax.swing.JFrame {
    private Configuracion config;
    private Connection conn;
    private ControlInventario cInv;
    public Inventario(Configuracion config) {
        this.config = config;
        initComponents();
        cInv = new ControlInventario(config);
        cargarTablaProductos();
    }

    private void cargarTablaProductos() {
        jTable1.setModel(cInv.cargarProductos());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnAgregarI = new javax.swing.JButton();
        txtBuscarI = new javax.swing.JTextField();
        btnBuscarI = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnEliminarI = new javax.swing.JButton();
        btnModificarI = new javax.swing.JButton();
        btnRestock = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(1500, 722));
        jPanel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Inventario.this.keyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 36)); // NOI18N
        jLabel1.setText("Lista de Productos");

        btnAgregarI.setText("Agregar");
        btnAgregarI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Inventario.this.actionPerformed(evt);
            }
        });
        btnAgregarI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Inventario.this.keyPressed(evt);
            }
        });

        txtBuscarI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Inventario.this.actionPerformed(evt);
            }
        });
        txtBuscarI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Inventario.this.keyPressed(evt);
            }
        });

        btnBuscarI.setText("Buscar");
        btnBuscarI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Inventario.this.actionPerformed(evt);
            }
        });
        btnBuscarI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Inventario.this.keyPressed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Producto", "Codigo", "Stock", "Precio", "Proveedor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Inventario.this.keyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        btnEliminarI.setText("Eliminar");
        btnEliminarI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Inventario.this.actionPerformed(evt);
            }
        });
        btnEliminarI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Inventario.this.keyPressed(evt);
            }
        });

        btnModificarI.setText("Modificar");
        btnModificarI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Inventario.this.actionPerformed(evt);
            }
        });
        btnModificarI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Inventario.this.keyPressed(evt);
            }
        });

        btnRestock.setText("Surtir");
        btnRestock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Inventario.this.actionPerformed(evt);
            }
        });
        btnRestock.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Inventario.this.keyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAgregarI, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminarI, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificarI, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRestock, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtBuscarI, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscarI, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 693, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtBuscarI)
                    .addComponent(btnBuscarI, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(btnAgregarI, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(btnModificarI, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(btnEliminarI, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btnRestock, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 899, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void keyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyPressed
        if (evt.getKeyCode()==KeyEvent.VK_S&&evt.isControlDown()) {
            txtBuscarI.requestFocusInWindow();
        }
        if (evt.getKeyCode()==KeyEvent.VK_ESCAPE) {
            new Menu(config).setVisible(true);
            dispose();
        }
        
    }//GEN-LAST:event_keyPressed

    private void actionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actionPerformed
        Object fuente = evt.getSource();

        // Agregar (el modo tradicional para ingresar un nuevo producto)
        if (fuente == btnAgregarI) {
            new AgregarItem(config).setVisible(true);
            dispose();
        }
        // Actualizar: se requiere que haya una fila seleccionada en jTable1
        else if (fuente == btnModificarI) {
            int filaSeleccionada = jTable1.getSelectedRow();
            if (filaSeleccionada != -1) {
                String descripcion = jTable1.getValueAt(filaSeleccionada, 0).toString();
                int codigo = Integer.parseInt(jTable1.getValueAt(filaSeleccionada, 1).toString());
                int stock = Integer.parseInt(jTable1.getValueAt(filaSeleccionada, 2).toString());
                double precio = Double.parseDouble(jTable1.getValueAt(filaSeleccionada, 3).toString());
                String proveedor = jTable1.getValueAt(filaSeleccionada, 4).toString();
                
                // Abrir AgregarItem en modo actualización (los campos de nombre, código, precio y proveedor son editables,
                // mientras que la cantidad se deja fija y deshabilitada)
                AgregarItem actualizarForm = new AgregarItem(config, AgregarItem.Mode.ACTUALIZAR, 
                        descripcion, codigo, precio, proveedor, stock);
                actualizarForm.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un producto para actualizar", 
                                              "Atención", JOptionPane.WARNING_MESSAGE);
            }
        }
        // Surtir: requiere que se seleccione la fila; en este modo se actualiza únicamente la cantidad
        else if (fuente == btnRestock) {
            int filaSeleccionada = jTable1.getSelectedRow();
            if (filaSeleccionada != -1) {
                String descripcion = jTable1.getValueAt(filaSeleccionada, 0).toString();
                int codigo = Integer.parseInt(jTable1.getValueAt(filaSeleccionada, 1).toString());
                int stock = Integer.parseInt(jTable1.getValueAt(filaSeleccionada, 2).toString());
                double precio = Double.parseDouble(jTable1.getValueAt(filaSeleccionada, 3).toString());
                String proveedor = jTable1.getValueAt(filaSeleccionada, 4).toString();
                
                // Abrir AgregarItem en modo surtir (sólo se activa la cantidad; el resto de los campos se muestran de solo lectura)
                AgregarItem surtirForm = new AgregarItem(config, AgregarItem.Mode.SURTIR, 
                        descripcion, codigo, precio, proveedor, stock);
                surtirForm.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un producto para surtir", 
                                              "Atención", JOptionPane.WARNING_MESSAGE);
            }
        }
        // Eliminar: se verificará que haya una fila seleccionada y se invocará el método eliminarProducto
        else if (fuente == btnEliminarI) {
            int filaSeleccionada = jTable1.getSelectedRow();
            if (filaSeleccionada != -1) {
                int codigo = Integer.parseInt(jTable1.getValueAt(filaSeleccionada, 1).toString());
                int confirmacion = JOptionPane.showConfirmDialog(this, "¿Desea eliminar este producto?");
                if (confirmacion == JOptionPane.YES_OPTION) {
                    if (cInv.eliminarProducto(codigo)) {
                        JOptionPane.showMessageDialog(this, "Producto eliminado exitosamente");
                        cargarTablaProductos();
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al eliminar el producto");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un producto para eliminar",
                                              "Atención", JOptionPane.WARNING_MESSAGE);
            }
        }

    }//GEN-LAST:event_actionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarI;
    private javax.swing.JButton btnBuscarI;
    private javax.swing.JButton btnEliminarI;
    private javax.swing.JButton btnModificarI;
    private javax.swing.JButton btnRestock;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtBuscarI;
    // End of variables declaration//GEN-END:variables
}