package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

protected static Connection cnx = null;

    public static Connection conectar() throws Exception {
 try {
	        Class.forName("oracle.jdbc.driver.OracleDriver");
                // Cambia el puerto de 1522(Docker) a 1521(Local)
            cnx = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "DEVELOPER", "DEVELOPER2020");
        } catch (ClassNotFoundException | SQLException e) {
            throw e;
        }
        return cnx;
    }

    public static void cerrarCnx() throws Exception {
        if (Conexion.cnx != null) {
            cnx.close();
        }
    }
}
