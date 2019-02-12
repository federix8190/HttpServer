package py.com.konecta.servers;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class HttpServer {
	
	public static void main(String args[]) throws Exception {
		ServerSocket ssock = new ServerSocket(3456);
		try {
			while (true) {
				System.out.println("Crear conexion");
				Socket sock = ssock.accept();
				new HttpSocketThread(sock).start();
			}
		} finally {
        	if (ssock != null) {
				try {
					ssock.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
	}
}

class HttpSocketThread extends Thread {
	
	Socket csocket;

	public HttpSocketThread(Socket csocket) {
		this.csocket = csocket;
	}

	public void run() {
		
		try {
			//PrintStream pstream = new PrintStream(csocket.getOutputStream());
			
			InputStream inputToServer = csocket.getInputStream();
            OutputStream outputFromServer = csocket.getOutputStream();
            Scanner scanner = new Scanner(inputToServer, "UTF-8");
            System.err.println(csocket.getSendBufferSize());
            
            String line = scanner.nextLine();
            int i = 0;
            while (line != null && !line.isEmpty()) {
            	System.err.println("Linea : " + line);
            	line = scanner.nextLine();
            	//System.err.println(i);
            	i++;
            }
            
            /*BufferedInputStream bis = new BufferedInputStream(inputToServer, 40);
            byte[] datos = new byte[40]; 
            bis.read(datos);*/
            
            /*while (scanner.) {
            	line = scanner.nextLine();
            	System.err.println("Linea : " + line);
            	done = true;
            	//scanner.close();
            }*/
            
            line = scanner.nextLine();
        	System.err.println("Linea : " + line);
        	scanner.close();
            
            //if (bis != null) bis.close();
            /*while (line != null && !line.isEmpty()) {
            	System.err.println("Linea : " + line);
            	line = scanner.nextLine();
            }*/
            
            //String[] tokens = line.split(" ");
            
            String json = "{\"exitoso\":true,\"mensaje\":\"Espero que Funcione !!!\"}";
            int length = json.length();
            
            PrintWriter serverPrintOut = new PrintWriter(new OutputStreamWriter(outputFromServer, "UTF-8"), true);
            serverPrintOut.println("HTTP/1.1 200 OK\n" 
            		+ "Connection: keep-alive\n"
            		+ "X-Powered-By: Undertow/1\n"
					+ "Server: FederixServer\n"
					+ "Content-Type: application/json\n"
					+ "Content-Length: " + length + "\n"
					+ "Date: Domingo, 10 de Febrero 2019 Asuncion\n\n"
					+ json);
            
            /*while(!done && scanner.hasNextLine()) {
            	System.err.println("leyendo datos");
                String line = scanner.nextLine();
                serverPrintOut.println("Echo from <Your Name Here> Server: " + line);	
                if(line.toLowerCase().trim().equals("finish")) {
                    done = true;
                }
            }*/
            
			/*for (int i = 10000; i >= 0; i--) {
				pstream.println(i);
				try {
					sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}*/
			//pstream.close();
			//csocket.close();
            //if (scanner != null) scanner.close();
            if (serverPrintOut != null) serverPrintOut.close();
            
		} catch (IOException e) {
			System.out.println(e);
		} finally {
        	if (csocket != null) {
				try {
					csocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
	}

}
