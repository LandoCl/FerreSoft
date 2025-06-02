package controles;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ControlVenta {
    private Configuracion config;

    public ControlVenta(Configuracion config) {
        this.config = config;
    }

    // Método privado para obtener una nueva conexión
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(config.getDatabaseUrl(), config.getUser(), config.getPassword());
    }

    // Registra la venta y sus detalles. Devuelve true si se registra correctamente.
    public boolean guardarVenta(DefaultTableModel model) {
        Connection conexion = null;
        try {
            conexion = getConnection();
            conexion.setAutoCommit(false);

            // Insertar en la tabla Venta  
            String sqlVenta = "INSERT INTO Venta (id_corte, fecha, total) VALUES (?, CURDATE(), ?)";
            PreparedStatement ventaStmt = conexion.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS);

            double total = calcularTotal(model);
            ventaStmt.setNull(1, java.sql.Types.INTEGER); // id_corte enviado como NULL
            ventaStmt.setDouble(2, total);
            ventaStmt.executeUpdate();

            ResultSet rs = ventaStmt.getGeneratedKeys();
            rs.next();
            int idVenta = rs.getInt(1);

            // Insertar cada producto en Detalle_Venta  
            String sqlDetalle = "INSERT INTO Detalle_Venta (id_venta, id_producto, cantidad, precio, total) VALUES (?, ?, ?, ?, ?)";
            for (int i = 0; i < model.getRowCount(); i++) {
                int cantidad = Integer.parseInt(model.getValueAt(i, 1).toString());
                double precio = Double.parseDouble(model.getValueAt(i, 2).toString());
                int idProducto = Integer.parseInt(model.getValueAt(i, 3).toString());

                PreparedStatement detalleStmt = conexion.prepareStatement(sqlDetalle);
                detalleStmt.setInt(1, idVenta);
                detalleStmt.setInt(2, idProducto);
                detalleStmt.setInt(3, cantidad);
                detalleStmt.setDouble(4, precio);
                detalleStmt.setDouble(5, cantidad * precio);
                detalleStmt.executeUpdate();
            }

            conexion.commit();
            return true;
        } catch (SQLException e) {
            if (conexion != null) {
                try {
                    conexion.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            JOptionPane.showMessageDialog(null, "Error al registrar venta: " + e.getMessage());
            return false;
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al cerrar conexión: " + ex.getMessage());
                }
            }
        }
    }

    // Calcula el total sumando cantidad*precio para cada fila
    public double calcularTotal(DefaultTableModel model) {
        double total = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            int cantidad = Integer.parseInt(model.getValueAt(i, 1).toString());
            double precio = Double.parseDouble(model.getValueAt(i, 2).toString());
            total += cantidad * precio;
        }
        return total;
    }

    // Clase interna para representar un producto
    public class Producto {
        public String descripcion;
        public double precio;
        public String codigo;
    }

    // Busca un producto por descripción (exacción: igual a lo ingresado)
    public Producto buscarProductoPorDescripcion(String descripcion) {
        Producto prod = null;
        Connection con = null;
        try {
            con = getConnection();
            String sql = "SELECT id_producto, descripcion, precio FROM Inventario WHERE descripcion = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, descripcion);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                prod = new Producto();
                prod.descripcion = rs.getString("descripcion");
                prod.precio = rs.getDouble("precio");
                prod.codigo = String.valueOf(rs.getInt("id_producto"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ignore) {}
            }
        }
        return prod;
    }

    // Busca un producto por código (usando id_producto)
    public Producto buscarProductoPorCodigo(String codigo) {
        Producto prod = null;
        Connection con = null;
        try {
            con = getConnection();
            String sql = "SELECT id_producto, descripcion, precio FROM Inventario WHERE id_producto = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(codigo));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                prod = new Producto();
                prod.descripcion = rs.getString("descripcion");
                prod.precio = rs.getDouble("precio");
                prod.codigo = String.valueOf(rs.getInt("id_producto"));
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ignore) {}
            }
        }
        return prod;
    }
}