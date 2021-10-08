package capaDatos.personas.test;

import java.sql.SQLException;
import java.util.List;
import capaDatos.personas.dto.PersonaDTO;
import capaDatos.personas.jdbc.PersonasDAO;
import capaDatos.personas.jdbc.PersonaDaojdbc;

public class TestPersonas {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		//Utilizamos el tipo interface como referencia
        //a una clase concreta
        PersonasDAO personaDao = new PersonaDaojdbc();

        //Creamos un nuevo registro
        //Hacemos uso de la clase persona DTO la cual se usa
        //para transferiri la informacion entre las capas
        //no es necesario especificar la llave primaria
        //ya que en este caso es de tipo autonumerico y la BD se encarga
        //de asignarle un nuevo valor
        PersonaDTO persona = new PersonaDTO();
        persona.setNombre("mario");
        persona.setApellido("lopez01");
        //Utilizamos la capa DAO para persistir el objeto DTO
        try {
            //personaDao.insert(persona);

            //eliminamos un registro, el id 3
            //personaDao.delete( new PersonaDTO(3));
            
            //actualizamos un registro
            // PersonaDTO personaTmp= new PersonaDTO();
           //personaTmp.setId_persona(2);//actualizamos el registro 2
           //personaTmp.setNombre("Mario");
           //personaTmp.setApellido("lopez02");
           //personaDao.update(personaTmp);
            
            //Seleccionamos todos los registros
            List<PersonaDTO> personas = personaDao.select();
            for (PersonaDTO personaDTO : personas) {
                System.out.print( personaDTO );
                System.out.println();
            }

        } catch (SQLException e) {
            System.out.println("Excepcion en la capa de prueba");
            e.printStackTrace();
        }
    }
}
