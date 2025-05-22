package controles;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author Lando
 */
public class Configuracion {
    private String databaseUrl;
    private String user;
    private String password;
    private String actionSource;

    public Configuracion() {
        this.databaseUrl = "jdbc:mysql://localhost:3306/ferreteria_acosta";
        this.user = "administrador";
        this.password="admin1234";
    }
    
    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }
    
    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    /**
     *
     * @return
     */
    public List<String> getUserList() {
        List<String> users = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(databaseUrl, user, password);
            String sql = "select user from mysql.db where db ='ferreteria_acosta';";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                users.add(rs.getString("user"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener usuarios: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al cerrar conexión: " + ex.getMessage());
                }
            }
        }
        return users;
    }
    
    /**
     * Agrega un nuevo usuario
     */
    public boolean addUser(String username) {
        String password = showPasswordDialog("Crear Usuario", "Ingrese la contraseña para el nuevo usuario:");
        if (password == null || password.isEmpty()) return false;
        
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(databaseUrl, user, this.password);
            
            // Verificar si el usuario ya existe
            String checkSql = "SELECT COUNT(*) AS count FROM mysql.user WHERE user = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next() && rs.getInt("count") > 0) {
                JOptionPane.showMessageDialog(null, "El usuario ya existe", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            // Crear el usuario
            String createSql = "CREATE USER ?@'localhost' IDENTIFIED BY ?";
            PreparedStatement createStmt = conn.prepareStatement(createSql);
            createStmt.setString(1, username);
            createStmt.setString(2, password);
            createStmt.executeUpdate();
            
            // Asignar privilegios específicos según la estructura de la base de datos
            String[] grants = {
                // Permisos para Inventario (SELECT y UPDATE)
                "GRANT SELECT, UPDATE ON ferreteria_acosta.Inventario TO ?@'localhost'",
                
                // Permisos para Venta (SELECT e INSERT)
                "GRANT SELECT, INSERT ON ferreteria_acosta.Venta TO ?@'localhost'",
                
                // Permisos para Detalle_Venta (INSERT)
                "GRANT INSERT ON ferreteria_acosta.Detalle_Venta TO ?@'localhost'",
                
                // Permiso para ver las tablas relacionadas (solo SELECT)
                "GRANT SELECT ON ferreteria_acosta.Proveedor TO ?@'localhost'",
                "GRANT SELECT ON ferreteria_acosta.Stock TO ?@'localhost'",
                "GRANT SELECT ON ferreteria_acosta.Corte TO ?@'localhost'"
            };
            
            for (String grant : grants) {
                PreparedStatement grantStmt = conn.prepareStatement(grant);
                grantStmt.setString(1, username);
                grantStmt.executeUpdate();
            }
            
            // Otorgar permiso para usar procedimientos almacenados si existen
            try {
                String procGrant = "GRANT EXECUTE ON PROCEDURE ferreteria_acosta.* TO ?@'localhost'";
                PreparedStatement procStmt = conn.prepareStatement(procGrant);
                procStmt.setString(1, username);
                procStmt.executeUpdate();
            } catch (SQLException e) {
                // Ignorar si no hay procedimientos almacenados
            }
            
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al crear usuario: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al cerrar conexión: " + ex.getMessage());
                }
            }
        }
    }
    
    /**
     * Modifica un usuario existente (solo la contraseña)
     */
    public boolean modifyUser(String oldUsername, String newUsername) {
        // En MySQL no podemos cambiar el nombre de usuario directamente, 
        // solo podemos cambiar la contraseña o privilegios
        // Por eso aquí solo manejaremos el cambio de contraseña
        
        String newPassword = showPasswordDialog("Modificar Usuario", 
            "Ingrese la nueva contraseña para el usuario " + oldUsername + ":");
        if (newPassword == null) return false;
        
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(databaseUrl, user, this.password);
            
            String sql = "ALTER USER ?@'localhost' IDENTIFIED BY ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, oldUsername);
            stmt.setString(2, newPassword);
            stmt.executeUpdate();
            
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al modificar usuario: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al cerrar conexión: " + ex.getMessage());
                }
            }
        }
    }
    
    /**
     * Elimina un usuario
     */
    public boolean deleteUser(String username) {
        int confirm = JOptionPane.showConfirmDialog(null, 
            "¿Está seguro que desea eliminar al usuario " + username + "?", 
            "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        
        if (confirm != JOptionPane.YES_OPTION) return false;
        
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(databaseUrl, user, this.password);
            
            String sql = "DROP USER ?@'localhost'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.executeUpdate();
            
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar usuario: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al cerrar conexión: " + ex.getMessage());
                }
            }
        }
    }
    
    /**
     * Muestra un diálogo para ingresar contraseña
     */
    private String showPasswordDialog(String title, String message) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(message);
        JPasswordField passField = new JPasswordField(10);
        
        panel.add(label, BorderLayout.NORTH);
        panel.add(passField, BorderLayout.CENTER);
        
        int option = JOptionPane.showConfirmDialog(null, panel, title, 
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (option == JOptionPane.OK_OPTION) {
            return new String(passField.getPassword());
        }
        return null;
    }
}