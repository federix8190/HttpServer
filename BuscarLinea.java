package py.com.konecta.restclientapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONException;
import org.json.simple.JSONObject;

public class BuscarLinea {
	
	public static void main(String[] args) throws IOException, JSONException {
        
        /*ApiRestAccess api = new ApiRestAccess("10.150.16.123", 8080);
        String url = "/mundo/v1/test/saldo/packs?numeroLinea=971365910&canal=ag";
		//ApiRestAccess api = new ApiRestAccess("192.168.12.36", 8080);
        //String url = "/TestRest/api/listar/ofertas";
        String res = api.getDatos(url);
        System.err.println(res);*/
		
		String s;
        Process p;
        try {
            p = Runtime.getRuntime().exec("ls -aF");
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null)
                System.out.println("line: " + s);
            p.waitFor();
            System.out.println ("exit: " + p.exitValue());
            p.destroy();
        } catch (Exception e) {}
        
	}

}
