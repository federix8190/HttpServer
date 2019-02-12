package py.com.konecta.restclientapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class LoadDataSqlServer {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

    	List<String> lineas = getLineasMiMundo();
    	
    	int i = 1;
    	System.out.println("Starting...");
    	
    	//String res = getServiciosTasables("971111183", "");
		//System.out.println(res);
    	
    	for (String linea : lineas.subList(0, 1000)) {
    		
    		i++;
    		//String res = getSaldoMiMundo(linea);
    		//procesarRespuestaMiMundo(res, linea);
    		String res = getServiciosTasables(linea, "");
    		System.out.println("Linea : " + linea);
    		System.out.println(res);
    		if (i % 10 == 0) {
    			System.out.println("Procesados : " + i);
    		}
    		if (i > 100) {
    			break;
    		}
    	}
		
    	System.out.println("Finish !!!");
    	/*ApiRestAccess api = new ApiRestAccess("10.150.16.123", 8080);
        String url = "/mundo/v1/test/saldo/packs?numeroLinea=971365910&canal=ag";
		String res = api.getDatos(url);
        System.err.println(res);*/
    } 
    
    private static String getSaldoMiMundo(String linea) {
    	ApiRestAccess api = new ApiRestAccess("10.150.16.123", 8080);
        String url = "/mundo/v1/test/saldo/consulta?numeroLinea=" + linea + "&canal=ag";
		String res = api.getDatos(url);
		return res;
    }
    
    private static String getServiciosTasables(String linea, String token) {
    	ApiRestAccess api = new ApiRestAccess("10.150.44.100", 8080);
        String url = "/servicio-services/api/elementos-grupo-destino?identificador=" + linea;
		String res = api.getDatos(url, token);
		return res;
    }
    
    private static void procesarRespuestaMiMundo(String res, String linea) {
    	if (res != null && !res.isEmpty()) {
			JSONParser parser = new JSONParser();
			try {
				JSONArray array = (JSONArray) parser.parse(res);
				if (array.size() > 1) {
					System.out.println("---------------------------");
		    		System.out.println(linea);
		    		//System.out.println(linea);
				}
			} catch (ParseException e) {
				System.out.println("---------------------------");
	    		System.out.println(linea);
	            System.out.println("Error al parsear : " + e.getMessage());
			}
		}
    }
    
    
    public static List<String> getLineasMiMundo() {
    	
    	Connection conn = null;
        Statement stmt = null;
        String url = "jdbc:sqlserver://10.150.12.96:1433;databaseName=mimundo";
        Properties props = new Properties();
        props.setProperty("user", "mimundo");
        props.setProperty("password", "mimundo");
        List<String> lineas = new ArrayList<String>();
        //props.setProperty("ssl", "true");

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(url, props);

            stmt = conn.createStatement();
            //String sql = "select usuario from dbo.usuario where tipoUsuario = 'TMP' or tipoUsuario = 'RES'";
            String sql = "select usuario from dbo.usuario where tipoUsuario = 'TIT'";
            
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                String linea = rs.getString("usuario");
                lineas.add(linea);
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
        
        return lineas;
    }
    
    public static void getCTE() {
    	
    	Connection conn = null;
        Statement stmt = null;
        String url = "jdbc:sqlserver://192.168.11.32:1433;databaseName=CTE_Legajos";
        Properties props = new Properties();
        props.setProperty("user", "cte_user");
        props.setProperty("password", "konecta123");
        //props.setProperty("ssl", "true");

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
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
                    "and descrip_objeto_gasto in ('SUELDOS', 'JORNALES', 'HONORARIOS PROFESIONALES') "
                    + "and cedula_identidad in (SELECT cedula_identidad from dbo.cpt_ee_legajos where anho = 2016 and mes = 6) "
                    + ") t GROUP by t.cedula_identidad HAVING count(1) = 1";
            
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
            	
                int cedula = rs.getInt("cedula_identidad");
                String insert = "insert into dbo.cpt_ee_legajos(anho, mes, cedula_identidad, id_cpt_ee) " 
                        + "values(2018, 6, " + cedula 
                        + ", (select id_cpt_ee from dbo.cpt_ee_legajos where anho = 2016 and mes = 6 and "
                        + "cedula_identidad = " + cedula + "));";
                System.out.println(insert);
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
