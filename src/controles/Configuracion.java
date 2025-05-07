package controles;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import javax.swing.table.*;
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
        this.databaseUrl = "jdbc:mysql://localhost:3308/ferreteria_acosta";
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
    public void listaUsers(String actionSource, JFrame parent) {
        this.actionSource = actionSource;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(databaseUrl, user, password);

            String sql = "call seleccionaListaUsuarios()";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = new DefaultTableModel(new String[]{"Usuario"}, 0);
            while (rs.next()) {
                model.addRow(new Object[]{rs.getString("user")});
            }

            JDialog dialogo = new JDialog(parent, "Seleccionar Usuario", true); // Modal delante de 'parent'
            dialogo.setSize(500, 300);
            dialogo.setLocationRelativeTo(parent); // Centrar respecto a la ventana principal

            JTable table = new JTable(model);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            table.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        String nombreUsuario = (String) table.getValueAt(selectedRow, 0);

                        if (e.getKeyCode() == KeyEvent.VK_ENTER && "update".equals(actionSource)) {
                            updUser(nombreUsuario);
                            dialogo.dispose();
                        } else if (e.getKeyCode() == KeyEvent.VK_DELETE && "delete".equals(actionSource)) {
                            delUser(nombreUsuario);
                            dialogo.dispose();
                        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                            dialogo.dispose();
                        }
                    }
                }
            });

            dialogo.add(new JScrollPane(table), BorderLayout.CENTER);
            dialogo.setVisible(true);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener los usuarios: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + ex.getMessage());
                }
            }
        }
    }
    public void addUser(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Crear Usuario", true); // Ventana modal delante de 'parent'
        dialog.setSize(300, 200);
        dialog.setLayout(new GridLayout(3, 2));
        dialog.setLocationRelativeTo(parent); // Centrar respecto a la ventana principal

        JLabel lblUsuario = new JLabel("Usuario:");
        JTextField txtUsuario = new JTextField();

        JLabel lblPassword = new JLabel("Contraseña:");
        JPasswordField txtPassword = new JPasswordField();

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
            String nuevoUsuario = txtUsuario.getText();
            String nuevaPassword = new String(txtPassword.getPassword());

            if (nuevoUsuario.isEmpty() || nuevaPassword.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Todos los campos son obligatorios.");
                return;
            }

            Connection conn = null;
            try {
                conn = DriverManager.getConnection(databaseUrl, user, password);

                // Verificar si el usuario ya existe
                String checkUserSql = "SELECT COUNT(*) AS count FROM mysql.user WHERE user = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkUserSql);
                checkStmt.setString(1, nuevoUsuario);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt("count") > 0) {
                    JOptionPane.showMessageDialog(dialog, "El usuario ya existe. Por favor, elige otro nombre.");
                    return;
                }

                // Crear el usuario
                String createUserSql = "CREATE USER ?@'localhost' IDENTIFIED BY ?";
                PreparedStatement createUserStmt = conn.prepareStatement(createUserSql);
                createUserStmt.setString(1, nuevoUsuario);
                createUserStmt.setString(2, nuevaPassword);
                createUserStmt.executeUpdate();

                // Asignar privilegios
                String grant1 = "GRANT SELECT, UPDATE ON servicio_inventario.inventario TO ?@'localhost'";
                String grant2 = "GRANT INSERT ON servicio_inventario.ticket TO ?@'localhost'";
                String grant3 = "GRANT INSERT ON servicio_inventario.detalle_ticket TO ?@'localhost'";
                PreparedStatement grantStmt = conn.prepareStatement(grant1);
                grantStmt.setString(1, nuevoUsuario);
                grantStmt.executeUpdate();
                grantStmt = conn.prepareStatement(grant2);
                grantStmt.setString(1, nuevoUsuario);
                grantStmt.executeUpdate();
                grantStmt = conn.prepareStatement(grant3);
                grantStmt.setString(1, nuevoUsuario);
                grantStmt.executeUpdate();

                JOptionPane.showMessageDialog(dialog, "Usuario creado correctamente.");
                dialog.dispose();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dialog, "Error al crear usuario: " + ex.getMessage());
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(dialog, "Error al cerrar la conexión: " + ex.getMessage());
                    }
                }
            }
        });

        dialog.add(lblUsuario);
        dialog.add(txtUsuario);
        dialog.add(lblPassword);
        dialog.add(txtPassword);
        dialog.add(btnGuardar);
        dialog.setVisible(true);
    }
    public void updUser(String usuario) {
        abrirFormularioParaActualizar(usuario); // Reutiliza el formulario para editar
    }
    public void delUser(String usuario) {
        int confirm = JOptionPane.showConfirmDialog(null, 
        "¿Seguro que deseas eliminar al usuario " + usuario + "?", 
        "Confirmar Eliminación", 
        JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            Connection conn = null;
            try {
                conn = DriverManager.getConnection(databaseUrl, user, password);

                // Eliminar el usuario
                String sql = "DROP USER ?@'localhost'";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, usuario);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente.");

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al eliminar usuario: " + ex.getMessage());
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + ex.getMessage());
                    }
                }
            }
        }
    }
    private void abrirFormularioParaActualizar(String usuario) {
        JDialog dialog = new JDialog(new JFrame(), "Modificar Usuario", true); // Modal y centrado en la ventana principal
        dialog.setSize(300, 200);
        dialog.setLayout(new GridLayout(3, 2));
        dialog.setLocationRelativeTo(null); // Centrar respecto a la ventana principal

        JLabel lblUsuario = new JLabel("Usuario:");
        JTextField txtUsuario = new JTextField(usuario);
        txtUsuario.setEnabled(false); // El nombre del usuario no puede ser modificado

        JLabel lblPassword = new JLabel("Nueva Contraseña:");
        JPasswordField txtPassword = new JPasswordField();

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
            String nuevaPassword = new String(txtPassword.getPassword());

            if (nuevaPassword.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "El campo de contraseña no puede estar vacío.");
                return;
            }

            Connection conn = null;
            try {
                conn = DriverManager.getConnection(databaseUrl, user, password);

                // Actualizar la contraseña del usuario
                String sql = "ALTER USER ?@'localhost' IDENTIFIED BY ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, usuario);
                stmt.setString(2, nuevaPassword);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(dialog, "Usuario actualizado correctamente.");
                dialog.dispose();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dialog, "Error al actualizar usuario: " + ex.getMessage());
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(dialog, "Error al cerrar la conexión: " + ex.getMessage());
                    }
                }
            }
        });

        dialog.add(lblUsuario);
        dialog.add(txtUsuario);
        dialog.add(lblPassword);
        dialog.add(txtPassword);
        dialog.add(btnGuardar);
        dialog.setVisible(true);
    }
}