import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	public static ArrayList<UserInfo> userlist;
	
	private final int startPort = 5001;
	private final int maxConn = 2000;
	private int port;
	
	int usernumber = 0;
	
	@SuppressWarnings("resource")
	public void Run() throws IOException {
		this.port = startPort;
		int num = 0;
		Server.userlist = new ArrayList<UserInfo>();
		ServerSocket serverSocket;
		
		serverSocket = new ServerSocket(7777);       
	
	
		while(true) {
			
			System.out.println("Server Is Runing, Current Port: " + this.port);
			
			Socket socket = serverSocket.accept();
			DataInputStream is = new DataInputStream(socket.getInputStream());
			DataOutputStream os = new DataOutputStream(socket.getOutputStream());

			System.out.println("Send Msg");
			os.writeUTF("Conn#" + "0#" + num + "#" + "guest" + num + "#" +this.port);
			num++;
			is.close();
			os.close();
			socket.close();
			
			// Reconnect
			UserInfo userinfo = new UserInfo(this.port);
			Server.userlist.add(userinfo);
			Thread thread = new Thread(userinfo);
			thread.start();
			
			this.port = (this.port - this.startPort + 1) % this.maxConn; 
			this.port += this.startPort;
		}
		
		//serverSocket.close();
	}
	
	private class UserInfo implements Runnable {
		private ServerSocket server;
		private int port;
		private int roomnumber = 0;
		private int usernumber;
		private String nick;
		private ArrayList<String> MsgQueue;
		
		public UserInfo(int port) {
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
		public void setusernumber(int number){
			usernumber = number;
		}
		public int getusernumber(){
			return usernumber;
		}
		public void setroomnumber(int number){
			roomnumber = number;
		}
		public int getroomnumber(){
			return roomnumber;
		}
		public void setnick(String name){
			nick = name;
		}
		public String getnick(){
			return nick;
		}
		@Override
		public void run() {
			
			try {			
				int running = 0;
				boolean quit = false;
				Socket socket = this.server.accept();
				DataInputStream is = new DataInputStream(socket.getInputStream());
				DataOutputStream os = new DataOutputStream(socket.getOutputStream());
				while(true) {
					String msg;
					running ++;
					if(running % 200000 == 0){
						//System.out.println("server running...");
					}
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
					case "changenick": changenick(token); break;
					case "chatinput" : chatinput(msg);break;
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
			for(int i=0; i<Server.userlist.size(); i++) {
				if(Server.userlist.get(i).getPort() == this.port) {
					Server.userlist.remove(i);
					break;
				}
			}
		}
		
		private void changenick(String[] token) {
			int samenick = 0;
			for(int i=0; i<Server.userlist.size(); i++) {
				if(token[2].equals(Server.userlist.get(i).getnick())) {
					samenick = 1;
					break;
				}
			}
			if(samenick == 0){
				this.setnick(token[2]);
				String msg = token[0] +"#"+ token[1] +"#"+ token [2] +"#"+ token [3];
				this.MsgQueue.add(msg);
			}
			System.out.println(this.getnick());
		}

		private void chatinput(String msg) {
			this.broadCastMsg(msg);
		}

		private void broadCastMsg(String msg) {
			String token[] = msg.split("#");
			for(int i=0; i<Server.userlist.size(); i++) {
				if(Server.userlist.get(i).getroomnumber() == Integer.parseInt(token[3])){
					System.out.println("" + i + " "+ Server.userlist.get(i).getroomnumber());
					Server.userlist.get(i).MsgQueue.add(msg);
				}
				System.out.println(i + " " +msg);
			}
		}
		
		private void test(String[] token){
			String msg = "";
			for(int i=1; token.length > 1 && i<token.length; i++) {
				msg += token[i];
			}
			this.broadCastMsg(msg);
		}
	}
	public int getusernum(){
		return usernumber;
	}
	public void addusernum(){
		usernumber++;
	}
}
