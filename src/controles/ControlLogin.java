package controles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ControlLogin {
    private Configuracion config;
    
    public ControlLogin(Configuracion config) {
        this.config = config;
    }
    
    public Connection connectToDatabase(String user, String password) throws SQLException {
        String url = config.getDatabaseUrl();
        return DriverManager.getConnection(url, user, password);
    }

    public boolean verificaCredenciales(String user, String password) {
        String url = config.getDatabaseUrl();
        try (Connection testConn = DriverManager.getConnection(url, user, password)) {
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    public void configurarCredenciales(String user, String password) {
        config.setUser(user);
        config.setPassword(password);
    }
}