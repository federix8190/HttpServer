package py.com.konecta.servers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

	public static void main(String args[]) throws Exception {
		ServerSocket ssock = new ServerSocket(1234);
		try {
			while (true) {
				Socket sock = ssock.accept();
				new SocketThread(sock).start();
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

class SocketThread extends Thread {
	
	Socket csocket;

	public SocketThread(Socket csocket) {
		this.csocket = csocket;
	}

	public void run() {
		
		try {
			//PrintStream pstream = new PrintStream(csocket.getOutputStream());
			InputStream inputToServer = csocket.getInputStream();
            OutputStream outputFromServer = csocket.getOutputStream();
            Scanner scanner = new Scanner(inputToServer, "UTF-8");
            PrintWriter serverPrintOut = new PrintWriter(new OutputStreamWriter(outputFromServer, "UTF-8"), true);
            serverPrintOut.println("Hello World! Enter Peace to exit.");
            boolean done = false;
            
            while(!done && scanner.hasNextLine()) {
            	System.err.println("leyendo datos");
                String line = scanner.nextLine();
                serverPrintOut.println("Echo from <Your Name Here> Server: " + line);	
                if(line.toLowerCase().trim().equals("finish")) {
                    done = true;
                }
            }
            
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
            if (scanner != null) scanner.close();
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
