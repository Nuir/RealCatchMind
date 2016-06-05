import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Network implements Runnable {
	private static Network instance;
	public ArrayList<String> MsgQueue;
	
	private Network() {
		MsgQueue = new ArrayList<String>();
	}
	
	public static Network getInstance() {
		if(Network.instance == null) 
			Network.instance = new Network();
		return Network.instance;
	}
	
	@Override
	public void run() {
		String serverIP = "127.0.0.1";
		try {
			Socket socket = new Socket(serverIP, 7777);
			
			DataInputStream in = new DataInputStream(socket.getInputStream());
			
			String port[] = in.readUTF().split("#");
			int portNo = Integer.parseInt(port[4]);
			this.setGeust(port);
			socket.close();
			
			System.out.println("Network.java : portnumber : " + portNo);
			
			socket = new Socket(serverIP, portNo);
			DataInputStream is = new DataInputStream(socket.getInputStream());
			DataOutputStream os = new DataOutputStream(socket.getOutputStream());
			
			
			boolean quit = false;
			while(true) {
				// Read
				String msg = is.readUTF();
				if(!msg.isEmpty()) System.out.println(msg);
				
				String token[] = msg.split("#");
				if(token.length > 0) switch(token[0]) {
				case "exit": quit = true; break;
				case "changenick": setGeust(token); break;
				case "nameing": setGeust(token); break;
				case "chatinput": chatinput(token); break;
				default: break;
				}
				
				if(quit) break;
				
				// Write
				if(MsgQueue.size() > 0) {
					msg = MsgQueue.get(0);
					MsgQueue.remove(0);
					os.writeUTF(msg);
				} else {
					os.writeUTF("");
				}
			}
			
			is.close();
			os.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	private void setGeust(String[] token) {
		RoomListView rlv = RoomListView.getInstance();
		System.out.println(" " + token[1] +" 닉네임은 " + token[2]);
		rlv.setNick(token[2]);
		rlv.setUserNumber(Integer.parseInt(token[1]));
	}

	private void chatinput(String[] token) {
		RoomListView rlv = RoomListView.getInstance();
		rlv.Outputtestfield(token[2] + " : " + token[4] + "\r\n");
		rlv.SetScrollBarBottom();
	}	
}
