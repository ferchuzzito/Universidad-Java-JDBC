package capaDatos.personas.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import capaDatos.personas.dto.PersonaDTO;

/**
 * Esta clase implementa la clase PersonaDao es una implementacion con la
 * tecnologia JDBC podrĂ­a haber otro tipo de implementaciones con tecnologias
 * como Hibernate, iBatis, SpringJDBC, etc.
 *
 * @author Ubaldo
 *
 */
public class PersonaDaojdbc  implements PersonasDAO {

    private Connection userConn;

    private final String SQL_INSERT = "INSERT INTO persona(nombre, apellido) VALUES(?,?)";

    private final String SQL_UPDATE = "UPDATE persona SET nombre=?, apellido=? WHERE id_persona=?";

    private final String SQL_DELETE = "DELETE FROM persona WHERE id_persona = ?";

    private final String SQL_SELECT = "SELECT id_persona, nombre, apellido FROM persona";

    /**
     * Constructor vacio
     */
    public PersonaDaojdbc() {

    }

    /**
     * Constructor con que recibe una conexion
     *
     * @param conn
     */
    public PersonaDaojdbc(Connection conn) {
        this.userConn = conn;
    }

    /**
     * El metodo insert recibe como argumento un objeto DTO el cual viene de
     * otra capa, y se extraen sus valores para crear un nuevo registro
     */
    @Override
    public int insert(PersonaDTO persona) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            int index = 1;
            stmt.setString(index++, persona.getNombre());
            stmt.setString(index, persona.getApellido());
            System.out.println("Ejecutando query:" + SQL_INSERT);
            rows = stmt.executeUpdate();
            System.out.println("Registros afectados:" + rows);
        } finally {
            Conexion.close(stmt);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }

        return rows;
    }

    /**
     * El metodo update recibe un objeto personaDTO el cual encapsula la
     * informacion en un solo objeto y evitamos pasar los 3 parametros de manera
     * aislada Despues extraemos la informacion del objeto y actualizamos el
     * registro seleccionado
     */
    @Override
    public int update(PersonaDTO persona)
            throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            System.out.println("Ejecutando query:" + SQL_UPDATE);
            stmt = conn.prepareStatement(SQL_UPDATE);
            int index = 1;
            stmt.setString(index++, persona.getNombre());
            stmt.setString(index++, persona.getApellido());
            stmt.setInt(index, persona.getId_persona());
            rows = stmt.executeUpdate();
            System.out.println("Registros actualizados:" + rows);
        } finally {
            Conexion.close(stmt);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return rows;
    }

    /**
     * Recibimos un objeto PersonaDTO no necesariamente debe venir lleno, sino
     * solo nos importa el atributo id_persona
     */
    @Override
    public int delete(PersonaDTO persona) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            System.out.println("Ejecutando query:" + SQL_DELETE);
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, persona.getId_persona());
            rows = stmt.executeUpdate();
            System.out.println("Registros eliminados:" + rows);
        } finally {
            Conexion.close(stmt);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return rows;
    }

    /**
     * En este metodo utilizamos el objeto PersonaDTO para llenar una lista y
     * regresarla
     */
    @Override
    public List<PersonaDTO> select() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        PersonaDTO personaDTO = null;
        List<PersonaDTO> personas = new ArrayList<PersonaDTO>();
        try {
            conn = (this.userConn != null) ? this.userConn : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while (rs.next()) {
                //Por cada registro se recuperan los valores
                //de las columnas y se crea un objeto DTO
                int idPersonaTemp = rs.getInt(1);
                String nombreTemp = rs.getString(2);
                String apellidoTemp = rs.getString(3);

                //Llenamos el DTO y lo agregamos a la lista
                personaDTO = new PersonaDTO();
                personaDTO.setId_persona(idPersonaTemp);
                personaDTO.setNombre(nombreTemp);
                personaDTO.setApellido(apellidoTemp);
                personas.add(personaDTO);
            }
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            if (this.userConn == null) {
                Conexion.close(conn);
            }
        }
        return personas;
    }
}
