package manejoTransaccionesJDBC;

import manejoTransaccionesJDBC.datos.Conexion;
import manejoTransaccionesJDBC.datos.PersonasJDBC;
import java.sql.*;

public class EjercicioManejoTransaccionesJDBC {

	public static void main(String[] args) {
        @SuppressWarnings("unused")
		PersonasJDBC personasJDBC = new PersonasJDBC();
    	
        //Creamos un objeto conexion, se va a compartir
        //para todos los queries que ejecutemos
        Connection conn = null;

        try {
            conn = Conexion.getConnection();
			//Revisamos si la conexion esta en modo autocommit
            //por default es autocommit == true
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
			//Creamos el objeto PersonasJDBC
            //proporcionamos la conexion creada
            PersonasJDBC personas = new PersonasJDBC(conn);

			//empezamos a ejecutar sentencias
            //recordar que una transaccion agrupa varias
            //sentencias SQL
            //si algo falla no se realizan los cambios en 
            //la BD
            //cambio correcto
            personas.update(2, "Regreso2", "Regreso");

            //Provocamos un error superando los 45 caracteres
            //del campo de apellido
            personas.insert("Miguel2",
                    "Ayala12341234123412341234123412341234123412341234123412341234123412341234123412341234");
                    //"Ayala2");
            //guardamos los cambios
            conn.commit();
        } catch (SQLException e) {
            //Hacemos rollback en caso de error
            try {
                System.out.println("Entramos al rollback");
                //Imprimimos la excepcion a la consola
                e.printStackTrace(System.out);
                //Hacemos rollback
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace(System.out);
            }
        }
	}

}
