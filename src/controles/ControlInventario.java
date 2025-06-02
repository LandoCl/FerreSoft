package controles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ControlInventario {
    private Configuracion config;

    public ControlInventario(Configuracion config) {
        this.config = config;
    }

    // Método para obtener la conexión a la base de datos
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            config.getDatabaseUrl(), 
            config.getUser(), 
            config.getPassword());
    }

    // Método para cargar los productos en la tabla
    public DefaultTableModel cargarProductos() {
        DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Producto", "Codigo", "Stock", "Precio", "Proveedor"}, 0);
        
        String sql = "SELECT i.id_producto, i.descripcion, i.precio, i.cantidad, p.nombre AS proveedor "
                   + "FROM Inventario i "
                   + "LEFT JOIN Stock s ON i.id_producto = s.id_producto "
                   + "LEFT JOIN Proveedor p ON s.id_proveedor = p.id_proveedor";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("descripcion"),
                    rs.getInt("id_producto"),
                    rs.getInt("cantidad"),
                    rs.getDouble("precio"),
                    rs.getString("proveedor")
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar productos: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        return model;
    }

    // Método para cargar proveedores en el JComboBox
    public DefaultComboBoxModel<String> cargarProveedores() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("-- Selecciona proveedor");
        
        String sql = "SELECT id_proveedor, nombre FROM Proveedor ORDER BY nombre";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                model.addElement(rs.getString("nombre"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar proveedores: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        return model;
    }

    // Método para agregar un nuevo producto
    public boolean agregarProducto(String nombre, String codigo, int cantidad, 
                                 double precio, String proveedor) {
        if (nombre.isEmpty() || codigo.isEmpty() || proveedor.equals("-- Selecciona proveedor")) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            
            // 1. Obtener ID del proveedor seleccionado
            int idProveedor = obtenerIdProveedor(conn, proveedor);
            if (idProveedor == -1) {
                return false;
            }
            
            // 2. Insertar el producto en Inventario
            String sqlInventario = "INSERT INTO Inventario (id_producto, descripcion, precio, cantidad) "
                                + "VALUES (?, ?, ?, ?)";
            
            try (PreparedStatement stmt = conn.prepareStatement(sqlInventario)) {
                stmt.setInt(1, Integer.parseInt(codigo));
                stmt.setString(2, nombre);
                stmt.setDouble(3, precio);
                stmt.setInt(4, cantidad);
                stmt.executeUpdate();
            }
            
            // 3. Insertar relación en Stock
            String sqlStock = "INSERT INTO Stock (id_producto, id_proveedor, cantidad) "
                           + "VALUES (?, ?, ?)";
            
            try (PreparedStatement stmt = conn.prepareStatement(sqlStock)) {
                stmt.setInt(1, Integer.parseInt(codigo));
                stmt.setInt(2, idProveedor);
                stmt.setInt(3, cantidad);
                stmt.executeUpdate();
            }
            
            conn.commit();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al agregar producto: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "El código debe ser un número válido",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    // Método para actualizar un producto (nombre, código, precio y proveedor)
    // La cantidad no se modifica en este método.
    // 'oldCodigo': código actual del producto.
    // 'newCodigo', 'newDescripcion', 'newPrecio' y 'newProveedor': nuevos valores.
    public boolean actualizarProducto(int oldCodigo, int newCodigo, String newDescripcion, 
                                      double newPrecio, String newProveedor) {
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            
            // Obtener el ID del nuevo proveedor
            int idProveedor = obtenerIdProveedor(conn, newProveedor);
            if (idProveedor == -1) {
                JOptionPane.showMessageDialog(null, "Proveedor no encontrado.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            // Actualizar la tabla Inventario (id_producto, descripcion y precio)
            String sqlInventario = "UPDATE Inventario SET id_producto = ?, descripcion = ?, precio = ? WHERE id_producto = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlInventario)) {
                stmt.setInt(1, newCodigo);
                stmt.setString(2, newDescripcion);
                stmt.setDouble(3, newPrecio);
                stmt.setInt(4, oldCodigo);
                stmt.executeUpdate();
            }
            
            // Actualizar la tabla Stock con el nuevo id_producto y el proveedor actualizado
            String sqlStock = "UPDATE Stock SET id_producto = ?, id_proveedor = ? WHERE id_producto = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlStock)) {
                stmt.setInt(1, newCodigo);
                stmt.setInt(2, idProveedor);
                stmt.setInt(3, oldCodigo);
                stmt.executeUpdate();
            }
            
            conn.commit();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar producto: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    // Método para surtir (abastecer) un producto
    // Se incrementa la cantidad actual con 'cantidadASurtir'. Se actualiza tanto en Inventario como en Stock.
    public boolean surtirProducto(int idProducto, int cantidadASurtir) {
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            
            // Obtener la cantidad actual del producto
            String query = "SELECT cantidad FROM Inventario WHERE id_producto = ?";
            int cantidadActual = 0;
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, idProducto);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        cantidadActual = rs.getInt("cantidad");
                    } else {
                        JOptionPane.showMessageDialog(null, "Producto no encontrado.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                }
            }
            
            int nuevaCantidad = cantidadActual + cantidadASurtir;
            
            // Actualizar la cantidad en Inventario
            String updateInventario = "UPDATE Inventario SET cantidad = ? WHERE id_producto = ?";
            try (PreparedStatement stmt = conn.prepareStatement(updateInventario)) {
                stmt.setInt(1, nuevaCantidad);
                stmt.setInt(2, idProducto);
                stmt.executeUpdate();
            }
            
            // Actualizar la cantidad en Stock
            String updateStock = "UPDATE Stock SET cantidad = ? WHERE id_producto = ?";
            try (PreparedStatement stmt = conn.prepareStatement(updateStock)) {
                stmt.setInt(1, nuevaCantidad);
                stmt.setInt(2, idProducto);
                stmt.executeUpdate();
            }
            
            conn.commit();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al surtir producto: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    // Método para eliminar un producto de la base de datos
    public boolean eliminarProducto(int idProducto) {
        try (Connection conn = getConnection()) {
            String sql = "DELETE FROM Inventario WHERE id_producto = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idProducto);
                int filasAfectadas = stmt.executeUpdate();
                if (filasAfectadas > 0) {
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "Producto no encontrado.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar producto: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    // Método auxiliar para obtener el ID de un proveedor por nombre
    private int obtenerIdProveedor(Connection conn, String nombreProveedor) throws SQLException {
        String sql = "SELECT id_proveedor FROM Proveedor WHERE nombre = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombreProveedor);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt("id_proveedor") : -1;
        }
    }
    // Método para buscar producto por Descripción en la tabla Inventario
public Object[] buscarProductoPorDescripcion(String descripcion) {
    Object[] datos = null;
    try (Connection conn = getConnection()) {
        String sql = "SELECT id_producto, descripcion, precio FROM Inventario WHERE descripcion = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, descripcion);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            datos = new Object[3];
            datos[0] = rs.getString("descripcion");    // Descripción
            datos[1] = rs.getDouble("precio");           // Precio
            datos[2] = rs.getInt("id_producto");         // Código o ID del producto
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return datos;
}

    // Método para buscar producto por Código (id_producto) en la tabla Inventario
    public Object[] buscarProductoPorCodigo(String codigo) {
        Object[] datos = null;
        try (Connection conn = getConnection()) {
            String sql = "SELECT id_producto, descripcion, precio FROM Inventario WHERE id_producto = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(codigo));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                datos = new Object[3];
                datos[0] = rs.getString("descripcion");
                datos[1] = rs.getDouble("precio");
                datos[2] = rs.getInt("id_producto");
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
        return datos;
    }
}