package server;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import client.SocetData;
import main.Const;


public class Server {

	
	private List<Connection> connections = 
			Collections.synchronizedList(new ArrayList<Connection>());
	private ServerSocket server;
 int number=0;
	
	public Server() {
               
            
		try {
                    System.out.println("1");
			server = new ServerSocket(Const.Port);
			while (true) {
				Socket socket = server.accept();
				Connection con = new Connection(socket);
                                 System.out.println("2");  
				connections.add(con);
				con.start();
                                 
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
	}

	
	private void closeAll() {
		try {
			server.close();
			
			
			synchronized(connections) {
				Iterator<Connection> iter = connections.iterator();
				while(iter.hasNext()) {
					((Connection) iter.next()).close();
				}
			}
		} catch (Exception e) {
			System.err.println("Ошибка сети!");
		}
	}

	
	private class Connection extends Thread {
		//private BufferedReader in;
            private ObjectInputStream in;
                
		private ObjectOutputStream out;
		private Socket socket;
	
		private String name = "";
	
		
		public Connection(Socket socket) {
			this.socket = socket;
	
			try {
                            System.out.println("3");
                            InputStream inps=(socket.getInputStream());
                              System.out.println("4");
				 in = new ObjectInputStream(inps);
                               // ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                                 System.out.println("5");
                               // ObjectInputStream oin = new ObjectInputStream(fis);
                               // in=new BufferedReader(new ObjectInputStream(socket.getInputStream()));
				out = new ObjectOutputStream(socket.getOutputStream());
	
			} catch (Exception e) {
				 System.out.println(e.getMessage());;
				close();
			}
		}
	
		
		@Override
		public void run() {
			try {   
                            System.out.println("6");
                                String str = "";
                                 SocetData sd=new SocetData();
                                 sd=(SocetData)in.readObject();
                                  out.writeObject(sd);
                                 System.out.print(sd.getValue());
                                 if(!sd.getValue().equals("test"))
                                    {
                                    
				while (true) {
                                   
                                    System.out.println("6");
                                        sd=(SocetData)in.readObject();
                                        number++;
                                        sd.setValue(":number of" +number);
                                        out.writeObject(sd);
                                        System.out.println("7");
                                        System.out.println("terminal "+sd.getValue()+ ":number of" +number);
                                       
					}}
                                        
                              
			} catch (IOException e) {
				System.out.println(e.getMessage());
			} catch (ClassNotFoundException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
				//close();
			}
		}
	
		
		public void close() {
			try {
				in.close();
				out.close();
				socket.close();
	
				
				connections.remove(this);
				if (connections.size() == 0) {
					Server.this.closeAll();
					System.exit(0);
				}
			} catch (Exception e) {
				System.err.println("Ошибка сети");
			}
		}
	}
}
