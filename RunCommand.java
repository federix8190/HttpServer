package py.com.konecta.restclientapp;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.json.JSONException;

public class RunCommand {
	
	private static final String LOG4j_FILE_NAME = "log4j.properties";
	protected static final Logger logger = LoggerFactory.getLogger(RunCommand.class);

	public static void main(String[] args) throws IOException, JSONException {
		
		String s;
        Process p;
        InputStream isLog4j = null;
        
        try {
        	
        	isLog4j = RunCommand.class.getClassLoader().getResourceAsStream(LOG4j_FILE_NAME);
        	
        	/*try {
        		String str = "";
        	    //StringBuffer buf = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(isLog4j));
                if (isLog4j != null) {
                    while ((str = reader.readLine()) != null) {
                        //buf.append(str + "\n" );
                    	System.out.println(str);
                    }
                }
            } finally {
                try {
                	isLog4j.close();
                } catch (Throwable ignore) {}
            }*/
        	
        	PropertyConfigurator.configure(isLog4j);
        	
        	isLog4j.close();
        	
            /*p = Runtime.getRuntime().exec("ls -l");
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null)
                System.out.println("line: " + s);
        	//p = Runtime.getRuntime().exec("cd /home/konecta/Mi-Mundo-Personal/log/");
        	//p = Runtime.getRuntime().exec("javac Test.java");
        	//p = Runtime.getRuntime().exec("java Test");
        	p.waitFor();
            System.out.println ("exit: " + p.exitValue());
            p.destroy();*/
            
        	//boolean reachable = InetAddress.getByName(hostname).isReachable();
            System.out.println (pingHost("www.personal.com.py", 80, 10000));
            //logger.info("logger configurado exitosamente");
            
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            try {
            	if (isLog4j != null) isLog4j.close();
            } catch (Throwable ignore) {}
        }
        
	}
	
	public static boolean pingHost(String host, int port, int timeout) {
	    try (Socket socket = new Socket()) {
	        socket.connect(new InetSocketAddress(host, port), timeout);
	        return true;
	    } catch (IOException e) {
	        return false; // Either timeout or unreachable or failed DNS lookup.
	    }
	}

}
