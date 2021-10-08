package manejoJDBC;

import manejoJDBC.datos.PersonasJDBC;
import manejoJDBC.domain.Persona;
import java.util.List;
public class EjercicioManejoJDBC {

	public static void main(String[] args) {
        PersonasJDBC personasJDBC = new PersonasJDBC();
	//Prueba del metodo insert
        personasJDBC.insert("Alberto", "Juarez");
        
        //Prueba del metodo update
        //personasJDBC.update(2, "Nombre3", "Apellido3");
		
        //Prueba del metodo delete
        //personasJDBC.delete(1);
      
        //Prueba del metodo select
        //Uso de un objeto persona para encapsular la informacion
        //de un registro de base de datos
        List<Persona> personas = personasJDBC.select();
        for (Persona persona : personas) {
            System.out.print(persona);
            System.out.println("");
        }
	}

}
