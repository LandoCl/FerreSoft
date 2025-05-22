CREATE DATABASE ferreteria_acosta;
USE ferreteria_acosta;

-- Tabla Proveedor
CREATE TABLE Proveedor (
    id_proveedor INT PRIMARY KEY AUTO_INCREMENT,
    codigo_prov VARCHAR(50) UNIQUE NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    contacto VARCHAR(100) NOT NULL
)engine InnoDB;

-- Tabla Inventario (Productos)
CREATE TABLE Inventario (
    id_producto INT PRIMARY KEY NOT NULL,
    descripcion VARCHAR (40) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    cantidad INT NOT NULL
)engine InnoDB;

-- Tabla Stock (Relación Proveedor - Inventario)
CREATE TABLE Stock (
    id_stock INT PRIMARY KEY AUTO_INCREMENT,
    id_producto INT NOT NULL,
    id_proveedor INT NOT NULL,
    cantidad INT NOT NULL
)engine InnoDB;

-- Tabla Venta
CREATE TABLE Venta (
    id_venta INT PRIMARY KEY AUTO_INCREMENT,
    id_corte INT NOT NULL,
    fecha DATE NOT NULL,
    total DECIMAL(10,2) NOT NULL
)engine InnoDB;

-- Tabla Detalle_Venta (Relación Venta - Inventario)
CREATE TABLE Detalle_Venta (
    id_venta INT NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
	total DECIMAL(10,2) NOT NULL
)engine InnoDB;

-- Tabla Corte (Opcional, para agrupar ventas)
CREATE TABLE Corte (
    id_corte INT PRIMARY KEY AUTO_INCREMENT,
    fecha DATE NOT NULL,
    total DECIMAL(10,2) NOT NULL
)engine InnoDB;

-- CONEXIONES (FOREIGN KEYS)
ALTER TABLE Stock ADD CONSTRAINT fk_stock_producto FOREIGN KEY (id_producto)
REFERENCES Inventario (id_producto) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Stock ADD CONSTRAINT fk_stock_proveedor FOREIGN KEY (id_proveedor)
REFERENCES Proveedor (id_proveedor) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Detalle_Venta ADD CONSTRAINT fk_detalle_venta FOREIGN KEY (id_venta)
REFERENCES Venta (id_venta) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Detalle_Venta ADD CONSTRAINT fk_detalle_producto FOREIGN KEY (id_producto)
REFERENCES Inventario (id_producto) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE Venta ADD CONSTRAINT fk_venta_corte FOREIGN KEY (id_corte)
REFERENCES Corte (id_corte);

-- creasion de usuarios
drop user 'cajero'@'localhost';
drop user 'administrador'@'localhost';
CREATE USER 'administrador'@'localhost' IDENTIFIED BY 'admin1234';
GRANT ALL PRIVILEGES ON ferreteria_acosta.* TO 'administrador'@'localhost' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON *.* TO 'administrador'@'localhost' WITH GRANT OPTION;
select * from mysql.user;
CREATE USER 'cajero'@'localhost' IDENTIFIED BY 'cajero1234';
GRANT SELECT, UPDATE ON ferreteria_acosta.* TO 'cajero'@'localhost';





