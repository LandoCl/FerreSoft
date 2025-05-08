package controles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ControlProveedores {
    private Configuracion config;
    private Connection conn;

    public ControlProveedores(Configuracion config) {
        this.config = config;
    }

    // Método para obtener la conexión a la base de datos
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            config.getDatabaseUrl(), 
            config.getUser(), 
            config.getPassword());
    }

    // Método para cargar los proveedores en la tabla
    public DefaultTableModel cargarProveedores() {
        DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Código", "Contacto"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que la tabla no sea editable directamente
            }
        };
        
        String sql = "SELECT id_proveedor, codigo_prov, nombre, contacto FROM Proveedor ORDER BY nombre";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_proveedor"),
                    rs.getString("nombre"),
                    rs.getString("codigo_prov"),
                    rs.getString("contacto")
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar proveedores: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        return model;
    }

    // Método para agregar un nuevo proveedor
    public boolean agregarProveedor(String codigo, String nombre, String contacto) {
        // Validar código único
        if (!validarCodigoUnico(codigo)) {
            JOptionPane.showMessageDialog(null, 
                "El código de proveedor ya existe", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String sql = "INSERT INTO Proveedor (codigo_prov, nombre, contacto) VALUES (?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, codigo);
            stmt.setString(2, nombre);
            stmt.setString(3, contacto);
            int affectedRows = stmt.executeUpdate();
            
            return affectedRows > 0;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, 
                "Error al agregar proveedor: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private boolean validarCodigoUnico(String codigo) {
        String sql = "SELECT COUNT(*) AS count FROM Proveedor WHERE codigo_prov = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, codigo);
            ResultSet rs = stmt.executeQuery();
            
            return rs.next() && rs.getInt("count") == 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Error al agregar proveedor: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Método para modificar un proveedor existente
    public boolean modificarProveedor(int id, String codigo, String nombre, String contacto) {
        if (codigo.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Código y nombre son campos obligatorios",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String sql = "UPDATE Proveedor SET codigo_prov = ?, nombre = ?, contacto = ? WHERE id_proveedor = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, codigo);
            stmt.setString(2, nombre);
            stmt.setString(3, contacto);
            stmt.setInt(4, id);
            int affectedRows = stmt.executeUpdate();
            
            return affectedRows > 0;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al modificar proveedor: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Método para eliminar un proveedor
    public boolean eliminarProveedor(int id) {
        // Verificar si el proveedor tiene productos asociados
        if (tieneProductosAsociados(id)) {
            JOptionPane.showMessageDialog(null, 
                "No se puede eliminar el proveedor porque tiene productos asociados",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        int confirm = JOptionPane.showConfirmDialog(null, 
            "¿Está seguro que desea eliminar este proveedor?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm != JOptionPane.YES_OPTION) {
            return false;
        }

        String sql = "DELETE FROM Proveedor WHERE id_proveedor = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            
            return affectedRows > 0;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar proveedor: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Método auxiliar para verificar si un proveedor tiene productos asociados
    private boolean tieneProductosAsociados(int idProveedor) {
        String sql = "SELECT COUNT(*) AS count FROM Stock WHERE id_proveedor = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idProveedor);
            ResultSet rs = stmt.executeQuery();
            
            return rs.next() && rs.getInt("count") > 0;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al verificar productos asociados: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            return true; // Por precaución, asumimos que sí tiene productos
        }
    }

    // Método para obtener los datos de un proveedor por su ID
    public Object[] obtenerDatosProveedor(int id) {
        String sql = "SELECT codigo_prov, nombre, contacto FROM Proveedor WHERE id_proveedor = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Object[]{
                    rs.getString("codigo_prov"),
                    rs.getString("nombre"),
                    rs.getString("contacto")
                };
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener datos del proveedor: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
}