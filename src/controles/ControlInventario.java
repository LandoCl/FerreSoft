package controles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ControlInventario {
    private Configuracion config;
    private Connection conn;

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

    // Método auxiliar para obtener el ID de un proveedor por nombre
    private int obtenerIdProveedor(Connection conn, String nombreProveedor) throws SQLException {
        String sql = "SELECT id_proveedor FROM Proveedor WHERE nombre = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombreProveedor);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt("id_proveedor") : -1;
        }
    }
}