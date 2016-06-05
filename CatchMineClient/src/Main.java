
public class Main {

	public static void main(String args[]) {
		RoomListView roomListView = RoomListView.getInstance();
		
		Runtime.getRuntime().addShutdownHook( new Thread(() -> { System.err.println("Program FIN."); }));
	}
}
