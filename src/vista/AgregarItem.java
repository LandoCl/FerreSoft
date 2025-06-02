package vista;

import controles.Configuracion;
import controles.ControlInventario;
import java.awt.event.KeyEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

public class AgregarItem extends javax.swing.JFrame {
    public enum Mode {
        AGREGAR, ACTUALIZAR, SURTIR
    }

    private Mode mode = Mode.AGREGAR;
    private Configuracion config;
    private ControlInventario controlInventario;
    private int originalCodigo; // Se usa para actualización o surtido.

    // Constructor por defecto (modo AGREGAR)
    public AgregarItem(Configuracion config) {
        initComponents();
        this.config = config;
        this.controlInventario = new ControlInventario(config);
        cargarProveedores();
        // En este modo, todos los campos están habilitados para ingresar un nuevo producto.
    }

    // Constructor sobrecargado para los modos ACTUALIZAR y SURTIR.
    // Se pasan los datos del producto seleccionado en Inventario para prellenar los campos.
    public AgregarItem(Configuracion config, Mode mode, String descripcion, int codigo, double precio, String proveedor, int stock) {
        initComponents();
        this.config = config;
        this.controlInventario = new ControlInventario(config);
        cargarProveedores();
        this.mode = mode;
        this.originalCodigo = codigo;
        
        // Prellenar campos
        txtNombreP.setText(descripcion);
        txtTipoProducto.setText(String.valueOf(codigo));
        txtPrecioP.setText(String.valueOf(precio));
        txtCantidadP.setText(String.valueOf(stock));
        
        // Mostrar el proveedor en el ComboBox
        DefaultComboBoxModel<String> modeloProv = (DefaultComboBoxModel<String>) comboProv.getModel();
        modeloProv.setSelectedItem(proveedor);
        
        // Configurar habilitación de campos según el modo
        if (mode == Mode.ACTUALIZAR) {
            // Permitir modificar: nombre, código, precio y proveedor.
            // La cantidad se muestra pero no se debe modificar.
            txtNombreP.setEnabled(true);
            txtTipoProducto.setEnabled(true);
            txtPrecioP.setEnabled(true);
            comboProv.setEnabled(true);
            txtCantidadP.setEnabled(false);
        } else if (mode == Mode.SURTIR) {
            // Sólo se permite modificar la cantidad y el resto se muestra en solo lectura.
            txtNombreP.setEnabled(false);
            txtTipoProducto.setEnabled(false);
            txtPrecioP.setEnabled(false);
            comboProv.setEnabled(false);
            txtCantidadP.setEnabled(true);
        }
    }

    private void cargarProveedores() {
        comboProv.setModel(controlInventario.cargarProveedores());
    }

    // Método unificado de "guardar" que distingue según modo.
    private void guardarProducto() {
        switch (mode) {
            case AGREGAR:
                guardarNuevoProducto();
                break;
            case ACTUALIZAR:
                actualizarProducto();
                break;
            case SURTIR:
                surtirProducto();
                break;
        }
    }

    // Método para insertar un nuevo producto (modo AGREGAR)
    private void guardarNuevoProducto() {
        String nombre = txtNombreP.getText();
        String codigoStr = txtTipoProducto.getText();
        String cantidadStr = txtCantidadP.getText();
        String precioStr = txtPrecioP.getText();
        String proveedor = (String) comboProv.getSelectedItem();

        try {
            int cantidad = Integer.parseInt(cantidadStr);
            double precio = Double.parseDouble(precioStr);
            
            if (controlInventario.agregarProducto(nombre, codigoStr, cantidad, precio, proveedor)) {
                JOptionPane.showMessageDialog(this, "Producto agregado exitosamente",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                new Inventario(config).setVisible(true);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Cantidad y precio deben ser números válidos",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para actualizar producto (modo ACTUALIZAR)
    private void actualizarProducto() {
        String nuevoNombre = txtNombreP.getText();
        String codigoStr = txtTipoProducto.getText();
        String precioStr = txtPrecioP.getText();
        String nuevoProveedor = (String) comboProv.getSelectedItem();

        if (nuevoNombre.isEmpty() || codigoStr.isEmpty() || nuevoProveedor.equals("-- Selecciona proveedor")) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios en la actualización",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int nuevoCodigo = Integer.parseInt(codigoStr);
            double nuevoPrecio = Double.parseDouble(precioStr);
            if (controlInventario.actualizarProducto(originalCodigo, nuevoCodigo, nuevoNombre, nuevoPrecio, nuevoProveedor)) {
                JOptionPane.showMessageDialog(this, "Producto actualizado exitosamente",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                new Inventario(config).setVisible(true);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El código y precio deben ser números válidos",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para surtir (modo SURTIR)
    private void surtirProducto() {
        String cantidadStr = txtCantidadP.getText();
        try {
            int cantidadASurtir = Integer.parseInt(cantidadStr);
            if (controlInventario.surtirProducto(originalCodigo, cantidadASurtir)) {
                JOptionPane.showMessageDialog(this, "Producto surtido exitosamente",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                new Inventario(config).setVisible(true);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un número válido",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNombreP = new javax.swing.JTextField();
        txtTipoProducto = new javax.swing.JTextField();
        txtCantidadP = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtPrecioP = new javax.swing.JTextField();
        comboProv = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AgregarItem.this.keyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Franklin Gothic Medium", 0, 36)); // NOI18N
        jLabel1.setText("Agregar Producto");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Nombre");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Codigo");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Cantidad");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Precio");

        txtNombreP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AgregarItem.this.keyPressed(evt);
            }
        });

        txtTipoProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AgregarItem.this.keyPressed(evt);
            }
        });

        txtCantidadP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AgregarItem.this.keyPressed(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarItem.this.actionPerformed(evt);
            }
        });
        btnGuardar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AgregarItem.this.keyPressed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Proveedor");

        txtPrecioP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AgregarItem.this.keyPressed(evt);
            }
        });

        comboProv.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Selecciona proveedor" }));
        comboProv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AgregarItem.this.keyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGuardar)
                .addGap(59, 59, 59))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(292, 292, 292)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6))
                        .addGap(65, 65, 65)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTipoProducto)
                                    .addComponent(txtCantidadP)
                                    .addComponent(txtNombreP, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(txtPrecioP, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(comboProv, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(208, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel1)
                .addGap(63, 63, 63)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNombreP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtTipoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(txtCantidadP, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPrecioP, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(comboProv, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(btnGuardar)
                .addGap(42, 42, 42))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void actionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actionPerformed
        if (evt.getSource()==btnGuardar) {
            guardarProducto();
        }
    }//GEN-LAST:event_actionPerformed

    private void keyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyPressed
        if (evt.getKeyCode()==KeyEvent.VK_ENTER) {
            guardarProducto();
        }
        if (evt.getKeyCode()==KeyEvent.VK_ESCAPE) {
            dispose();
        }
    }//GEN-LAST:event_keyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> comboProv;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtCantidadP;
    private javax.swing.JTextField txtNombreP;
    private javax.swing.JTextField txtPrecioP;
    private javax.swing.JTextField txtTipoProducto;
    // End of variables declaration//GEN-END:variables
}
