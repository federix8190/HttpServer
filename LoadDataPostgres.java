package py.com.konecta.restclientapp;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author konecta
 */
public class LoadDataPostgres {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Connection conn = null;
        Statement stmt = null;
        String url = "jdbc:postgresql://192.168.11.32:5432/db_sicrh";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "postgres");
        //props.setProperty("ssl", "true");

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, props);

            stmt = conn.createStatement();
            //String sql = "select id_ceo, cedula_identidad from cte.ceo_legajos where anho = 2018 and mes = 4";
            String sql = "select t.cedula_identidad, count(1) from(\n" +
                   "select DISTINCT cedula_identidad, descrip_nombre_cargo, presupuestado, categoria_personal "
                    + "from dbo.planilla_sueldo \n" +
                    "where cedula_identidad in (select cedula_identidad from dbo.planilla_sueldo "
                    + "where ejercicio = 2018  and mes = 6)\n" +
                    "and cedula_identidad in (select cedula_identidad from dbo.planilla_sueldo "
                    + "where ejercicio = 2016  and mes = 6)\n" +
                    "and (ejercicio = 2018 or ejercicio = 2016) and mes = 6\n" +
                    "and descrip_objeto_gasto in ('SUELDOS', 'JORNALES', 'HONORARIOS PROFESIONALES')) t "
                    + "GROUP by t.cedula_identidad HAVING count(1) = 1";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int cedula = rs.getInt("cedula_identidad");
                System.out.println(cedula);
                /*int idCeo = rs.getInt("id_ceo");
                System.out.println("UPDATE cte.puesto_trabajo SET id_ceo = " + idCeo 
                        + " WHERE anho = 2018 AND mes = 4 AND cedula_identidad = " + cedula + ";");*/
            }
            rs.close();
        } catch (SQLException se) {            
            se.printStackTrace();
        } catch (Exception e) {            
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) conn.close();
            } catch (SQLException se) {}
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
