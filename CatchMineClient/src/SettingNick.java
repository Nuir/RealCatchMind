import javax.swing.JFrame;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Window.Type;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.Color;

public class SettingNick extends JFrame {
	private JTextField textField;
	private static SettingNick instance;
	
	public SettingNick() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				SettingNick nick = SettingNick.getInstance();
				nick.setVisible(false);
			}
		});
		this.setSize(334, 93);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setType(Type.POPUP);
		setTitle("\uB2C9\uB124\uC784 \uC124\uC815");
		getContentPane().setLayout(null);
		
		
		//닉네임 입력 하는 부분
		textField = new JTextField();
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String nick;
				nick = textField.getText();
				if(nick.equals("닉네임을 입력하세요.")){
					textField.setText("");
				}
			}
		});
		textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				//textField.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				String nick;
				nick = textField.getText();
				if(nick.equals("")){
					textField.setText("닉네임을 입력하세요.");
				}
			}
		});
		textField.setBounds(14, 12, 171, 24);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		
		// 확인 버튼
		JButton btnNewButton = new JButton("\uD655\uC778");
		btnNewButton.setBackground(new Color(255, 255, 255));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!textField.getText().equals("")){
					System.out.println("nick");
					RoomListView room = RoomListView.getInstance();
					//room.setNick(textField.getText());
					Network.getInstance().MsgQueue.add("changenick#" + room.getUserNumber() + "#" + textField.getText() + "#" + room.getRoomNumber() + "#");
					SettingNick nick = SettingNick.getInstance();
					nick.setVisible(false);
				}
			}
		});
		btnNewButton.setBounds(199, 11, 105, 27);
		getContentPane().add(btnNewButton);

	}
	public static SettingNick getInstance() {
		if(SettingNick.instance == null) 
			SettingNick.instance = new SettingNick();
		return SettingNick.instance;
	}
}
