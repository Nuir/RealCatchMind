import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.*;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ListSelectionModel;


public class RoomListView extends JFrame {

	private String Nickname = "USER";
	private int RoomNumber = 0;
	private int UserNumber = 0;
	
	
	
	private static RoomListView instance;
	
	private ArrayList<RoomInfo> roomInfoList;
	private JList RoomList;
	private JTextField inputChatField;
	private JTextArea outputChatField;
	private JButton button_1;
	private JScrollPane scrollPane;
	/**
	 * Create the panel.
	 */
	private RoomListView() {
		this.InitUI();
		this.Init();
		
		this.setVisible(true);
		
		Network conn = Network.getInstance();
		
		Thread thread = new Thread(conn);
		thread.start();
	}
	
	public static RoomListView getInstance() {
		if(RoomListView.instance == null) {
			RoomListView.instance = new RoomListView();
		}
		return RoomListView.instance;
	}
	
	private void InitUI() {
		getContentPane().setLayout(null);
		this.setSize(754, 350);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		RoomList = new JList(new RoomListModel());
		RoomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		RoomList.setBounds(14, 10, 244, 247);
		RoomList.addMouseListener(new RoomDblClickListener(RoomList));
		getContentPane().add(RoomList);
		
		inputChatField = new JTextField();
		inputChatField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String chat;
				chat = inputChatField.getText();
				System.out.println(chat + getNick());
				Network.getInstance().MsgQueue.add("chatinput#" + getUserNumber() + "#" + getNick() + "#" +getRoomNumber() + "#" + chat);
				inputChatField.setText("");
				SetScrollBarBottom();
			}
		});
		inputChatField.setBounds(408, 270, 314, 24);
		getContentPane().add(inputChatField);
		inputChatField.setColumns(10);
		
		//방만들기 버튼
		JButton CreateRoom = new JButton("\uBC29\uB9CC\uB4E4\uAE30");
		CreateRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Network.getInstance().MsgQueue.add("createroom#" + getUserNumber() + "#" + getNick() + "#" +getRoomNumber() + "#");
				
				RoomListView room = RoomListView.getInstance();

				room.roomInfoList.add(new RoomInfo(room.getRoomNumber(), room.getNick()));
				room.addRoomNumber();
				
				RoomListModel model = new RoomListModel();
				for(int i=0; i<room.roomInfoList.size(); i++) {
					model.addElement(room.roomInfoList.get(i));
				}
				room.RoomList.setModel(model);
				
			}
		});
		CreateRoom.setForeground(Color.BLACK);
		CreateRoom.setBackground(Color.WHITE);
		CreateRoom.setBounds(14, 269, 115, 27);
		getContentPane().add(CreateRoom);
		
		JButton button = new JButton("\uC785\uC7A5\uD558\uAE30");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button.setBackground(Color.WHITE);
		button.setBounds(143, 269, 115, 27);
		getContentPane().add(button);
		
		//닉네임 설정 버튼
		button_1 = new JButton("\uB2C9\uB124\uC784 \uC124\uC815");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SettingNick settingnick = SettingNick.getInstance();
				settingnick.setVisible(true);
			}
		});
		button_1.setBackground(Color.WHITE);
		button_1.setBounds(272, 269, 122, 27);
		getContentPane().add(button_1);
		
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(272, 10, 450, 245);
		getContentPane().add(scrollPane);
		
		outputChatField = new JTextArea();
		scrollPane.setViewportView(outputChatField);
		outputChatField.setColumns(10);
		outputChatField.setLineWrap(true);
		outputChatField.setEditable(false);
		
		
	}
	
	private void Init() {
		this.roomInfoList = new ArrayList<RoomInfo>();
		
		/*
		this.roomInfoList.add(new RoomInfo(1, "2"));
		this.roomInfoList.add(new RoomInfo(2, "2"));
		this.roomInfoList.add(new RoomInfo(3, "2"));
		
		for(int i=0; i<this.roomInfoList.size(); i++){ 
			model.addElement(this.roomInfoList.get(i));
		}
		
		RoomList.setModel(model);
		*/
	}	
	
	public void Outputtestfield(String msg){
		outputChatField.append(msg);
	}
	public void SetScrollBarBottom(){
		scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
	}
	public void setUserNumber(int number){
		UserNumber = number;
	}
	public int getUserNumber(){
		return UserNumber;
	}
	public void setNick(String nick){
		Nickname = nick;
	}	
	public String getNick(){
		return Nickname;
	}
	public void setRoomNumber(int num){
		RoomNumber = num;
	}
	public int getRoomNumber(){
	
		return RoomNumber;
	}
	public void addRoomNumber(){
		RoomNumber++;
	}
	
//	---		RoomInfo		---		//	
	class RoomInfo {
		public int no;
		public String name;
		
		public RoomInfo(int no, String name) {
			this.no = no;
			this.name = name;
		}
		
		public String toString() {
			return "No. " + this.no + " Name: " + this.name;
		}
	}
	
	/*
	 * 	List Model
	 */
	class RoomListModel extends AbstractListModel {
		private List<RoomInfo> RoomList;
		
		public RoomListModel() {
			this.RoomList = new ArrayList<RoomInfo>();
		}
		
		public void addElement(RoomInfo elem) {
			RoomList.add(elem);
		}
		
		@Override
		public int getSize() {
			return RoomList.size();
		}

		@Override
		public RoomInfo getElementAt(int index) {
			return RoomList.get(index);
		}
		
		public void clear() {
			for(int i=0; i<this.RoomList.size(); i++) {
				this.RoomList.remove(0);
			}
		}
	}
	
	/*
	 *	Enter Listener 
	 */
	class RoomDblClickListener implements MouseListener {

		private JList RoomList;
		
		public RoomDblClickListener(JList list) {
			this.RoomList = list;
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount() == 2) {	// doubleClick
				int index = this.RoomList.locationToIndex(e.getPoint());
				
				RoomInfo info = (RoomInfo) this.RoomList.getModel().getElementAt(index);
				
				// todo
				//System.out.println("No. " + info.no);
				
				RoomView room = RoomView.getInstance();
				
				room.setVisible(true);
				room.setTitle(info.toString());
				
				RoomListView roomList = RoomListView.getInstance();
				roomList.setVisible(false);
				
				Network.getInstance().MsgQueue.add("test#" + "No. " + info.no);
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}
}