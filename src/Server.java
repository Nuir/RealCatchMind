import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	public static ArrayList<Handler> handlerList;
	
	private final int startPort = 5001;
	private final int maxConn = 2000;
	private int port;
	
	public void Run() throws IOException {
		this.port = startPort;
		
		Server.handlerList = new ArrayList<Handler>();
		ServerSocket serverSocket;
		
		serverSocket = new ServerSocket(7777);       
	
		while(true) {
			
			System.out.println("Server Is Runing, Current Port: " + this.port);
			
			Socket socket = serverSocket.accept();
			
			DataInputStream is = new DataInputStream(socket.getInputStream());
			DataOutputStream os = new DataOutputStream(socket.getOutputStream());

			System.out.println("Send Msg");
			os.writeUTF("Conn#" + this.port);
			
			is.close();
			os.close();
			socket.close();
			
			// Reconnect
			Handler handler = new Handler(this.port);
			Server.handlerList.add(handler);
			
			Thread thread = new Thread(handler);
			thread.start();
			
			this.port = (this.port - this.startPort + 1) % this.maxConn; 
			this.port += this.startPort;
		}
		
		//serverSocket.close();
	}
	
	private class Handler implements Runnable {
		private ServerSocket server;
		private int port;
		private ArrayList<String> MsgQueue;
		
		public Handler(int port) {
			try {
				this.server = new ServerSocket(port);
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.port = port;
			this.MsgQueue = new ArrayList<String>();
		}
		
		public int getPort() {
			return this.port;
		}
		
		@Override
		public void run() {
			
			try {			
				boolean quit = false;
				Socket socket = this.server.accept();
				DataInputStream is = new DataInputStream(socket.getInputStream());
				DataOutputStream os = new DataOutputStream(socket.getOutputStream());
				
				while(true) {
					String msg;
					
					// Write
					if(MsgQueue.size() > 0) {
						msg = MsgQueue.get(0);
						MsgQueue.remove(0);
						os.writeUTF(msg);
					} else {
						os.writeUTF("");
					}
					
					// Read
					msg = is.readUTF();
					String token[] = msg.split("#");
					if(!msg.isEmpty()) System.out.println(msg);
					
					if(token.length > 0) switch(token[0]) {
					case "test": test(token); break;
					case "exit": quit = true; break;
					default: break;
					}
					
					if(quit) break;
				}
				is.close();
				os.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// Remove From List
			for(int i=0; i<Server.handlerList.size(); i++) {
				if(Server.handlerList.get(i).getPort() == this.port) {
					Server.handlerList.remove(i);
					break;
				}
			}
		}
		
		private void boardCastMsg(String msg) {
			
			for(int i=0; i<Server.handlerList.size(); i++) {
				Server.handlerList.get(i).MsgQueue.add(msg);
				System.out.println(i + " " +msg);
			}
		}
		
		private void test(String[] token){
			String msg = "";
			for(int i=1; token.length > 1 && i<token.length; i++) {
				msg += token[i];
			}
			this.boardCastMsg(msg);
		}
	}
}
