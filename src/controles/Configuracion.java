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
        this.databaseUrl = "jdbc:mysql://localhost:3307/ferreteria_acosta";
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
            String sql = "select user from mysql.db where db ='ferreteria_acosta' and select_priv = 'Y';";
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
        if (password == null || password.isEmpty())
            return false;

        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection(databaseUrl, user, this.password);
            stmt = conn.createStatement();

            // Verificar si el usuario ya existe para host 'localhost'
            String checkSql = "SELECT COUNT(*) AS count FROM mysql.user WHERE user = '" + username + "' AND host = 'localhost'";
            ResultSet rs = stmt.executeQuery(checkSql);
            if (rs.next() && rs.getInt("count") > 0) {
                JOptionPane.showMessageDialog(null, "El usuario ya existe", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Crear el usuario
            String createSql = "CREATE USER '" + username + "'@'localhost' IDENTIFIED BY '" + password + "'";
            stmt.executeUpdate(createSql);

            // Asignar privilegios utilizando la forma:
            // GRANT SELECT, UPDATE ON ferreteria_acosta.* TO 'username'@'localhost';
            // Se pueden asignar varios grupos de privilegios; aquí se muestran ejemplos.
            String[] grants = {
                "GRANT SELECT, UPDATE ON ferreteria_acosta.* TO '" + username + "'@'localhost'",
                "GRANT SELECT, INSERT ON ferreteria_acosta.* TO '" + username + "'@'localhost'",
                "GRANT INSERT ON ferreteria_acosta.* TO '" + username + "'@'localhost'",
                "GRANT EXECUTE ON ferreteria_acosta.* TO '" + username + "'@'localhost'"
            };

            for (String grantSql : grants) {
                stmt.executeUpdate(grantSql);
            }

            // Forzar que MySQL refresque sus privilegios
            stmt.executeUpdate("FLUSH PRIVILEGES");

            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al crear usuario: " + ex.getMessage(),
                                          "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            if (stmt != null) {
                try { stmt.close(); } catch (SQLException ex) { /* Ignorar */ }
            }
            if (conn != null) {
                try { conn.close(); } catch (SQLException ex) { /* Ignorar */ }
            }
        }
    }

    public boolean modifyUser(String oldUsername, String newUsername) {
        // Aquí asumiremos que solo se modifica la contraseña
        String newPassword = showPasswordDialog("Modificar Usuario", "Ingrese la nueva contraseña para el usuario " + oldUsername + ":");
        if (newPassword == null || newPassword.isEmpty())
            return false;

        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection(databaseUrl, user, this.password);
            stmt = conn.createStatement();

            // ALTER USER debe formarse sin parámetros
            String sql = "ALTER USER '" + oldUsername + "'@'localhost' IDENTIFIED BY '" + newPassword + "'";
            stmt.executeUpdate(sql);

            stmt.executeUpdate("FLUSH PRIVILEGES");

            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al modificar usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            if (stmt != null) {
                try { stmt.close(); } catch (SQLException ex) { /* Ignorar */ }
            }
            if (conn != null) {
                try { conn.close(); } catch (SQLException ex) { /* Ignorar */ }
            }
        }
    }

    public boolean deleteUser(String username) {
        int confirm = JOptionPane.showConfirmDialog(null,
                "¿Está seguro que desea eliminar al usuario " + username + "?",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION)
            return false;

        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection(databaseUrl, user, this.password);
            stmt = conn.createStatement();

            String sql = "DROP USER '" + username + "'@'localhost'";
            stmt.executeUpdate(sql);

            stmt.executeUpdate("FLUSH PRIVILEGES");

            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            if (stmt != null) {
                try { stmt.close(); } catch (SQLException ex) { /* Ignorar */ }
            }
            if (conn != null) {
                try { conn.close(); } catch (SQLException ex) { /* Ignorar */ }
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