import java.io.IOException;



public class Main {
	public static void main(String args[]) {
		Server server = new Server();
		
		try {
			server.Run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}