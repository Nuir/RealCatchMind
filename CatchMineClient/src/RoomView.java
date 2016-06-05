import java.awt.Canvas;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.ScrollPaneConstants;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.JRadioButton;
import net.miginfocom.swing.MigLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import java.awt.Font;


public class RoomView extends JFrame {
	private static RoomView instance;
	private JTextField Question;
	private JTextField Answer;
	private JTextField Roomchat_Input;
	private JTextField Roomchat_Output;
	private JTextField User4;
	private JTextField User1;
	private JTextField User2;
	private JTextField User3;
	/**
	 * Create the panel.
	 */
	private RoomView() {
		setBackground(new Color(0, 0, 0));
		getContentPane().setBackground(new Color(255, 255, 255));
		getContentPane().setLayout(null);
		this.setSize(1209, 609);
		Question = new JTextField();
		Question.setFont(new Font("Arial", Font.PLAIN, 20));
		Question.setHorizontalAlignment(SwingConstants.CENTER);
		Question.setText("Qestion");
		Question.setBackground(new Color(255, 255, 255));
		Question.setEditable(false);
		Question.setBounds(14, 12, 154, 50);
		getContentPane().add(Question);
		Question.setColumns(10);
		
		Answer = new JTextField();
		Answer.setHorizontalAlignment(SwingConstants.CENTER);
		Answer.setText("Answer");
		Answer.setColumns(10);
		Answer.setBounds(14, 76, 154, 50);
		getContentPane().add(Answer);
		
		JPanel Canvas = new JPanel();
		Canvas.setBorder(new LineBorder(new Color(0, 0, 0)));
		Canvas.setBackground(Color.WHITE);
		Canvas.setBounds(190, 12, 674, 541);
		getContentPane().add(Canvas);
		Canvas.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(0, 464, 674, 77);
		Canvas.add(panel_1);
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton White = new JButton("White");
		White.setBackground(Color.WHITE);
		panel_1.add(White);
		
		JButton Red = new JButton("Red");
		Red.setBackground(Color.RED);
		panel_1.add(Red);
		
		JButton Orange = new JButton("Orange");
		Orange.setBackground(Color.ORANGE);
		panel_1.add(Orange);
		
		JButton Yellow = new JButton("Yellow");
		Yellow.setBackground(Color.YELLOW);
		panel_1.add(Yellow);
		
		JButton Green = new JButton("Green");
		Green.setBackground(Color.GREEN);
		panel_1.add(Green);
		
		JButton Blue = new JButton("Blue");
		Blue.setBackground(new Color(0, 0, 255));
		panel_1.add(Blue);
		
		JButton Darkblue = new JButton("Darkblue");
		Darkblue.setBackground(new Color(0, 0, 128));
		panel_1.add(Darkblue);
		
		JButton Violet = new JButton("Violet");
		Violet.setBackground(new Color(148, 0, 211));
		panel_1.add(Violet);
		
		JButton Black = new JButton("Black");
		Black.setBackground(new Color(0, 0, 0));
		panel_1.add(Black);
		
		JButton Eraser = new JButton("Eraser");
		Eraser.setBackground(new Color(255, 255, 224));
		panel_1.add(Eraser);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(878, 12, 299, 518);
		getContentPane().add(scrollPane);
		
		Roomchat_Output = new JTextField();
		Roomchat_Output.setBackground(new Color(255, 255, 255));
		scrollPane.setViewportView(Roomchat_Output);
		Roomchat_Output.setEditable(false);
		Roomchat_Output.setColumns(10);
		
		Roomchat_Input = new JTextField();
		Roomchat_Input.setBounds(878, 529, 299, 24);
		getContentPane().add(Roomchat_Input);
		Roomchat_Input.setColumns(10);
		
		JButton Start = new JButton("\uC2DC\uC791");
		Start.setFont(new Font("±¼¸²", Font.PLAIN, 35));
		Start.setBackground(Color.WHITE);
		Start.setBounds(14, 421, 154, 59);
		getContentPane().add(Start);
		
		JButton Exit = new JButton("\uB098\uAC00\uAE30");
		Exit.setFont(new Font("±¼¸²", Font.PLAIN, 35));
		Exit.setBackground(Color.WHITE);
		Exit.setBounds(14, 492, 154, 59);
		getContentPane().add(Exit);
		
		JPanel panel = new JPanel();
		panel.setBounds(14, 138, 154, 271);
		getContentPane().add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		User1 = new JTextField();
		User1.setFont(new Font("Arial", Font.PLAIN, 20));
		User1.setHorizontalAlignment(SwingConstants.CENTER);
		User1.setText("User1");
		panel.add(User1);
		User1.setColumns(10);
		
		User2 = new JTextField();
		User2.setHorizontalAlignment(SwingConstants.CENTER);
		User2.setFont(new Font("Arial", Font.PLAIN, 20));
		User2.setText("User2");
		panel.add(User2);
		User2.setColumns(10);
		
		User3 = new JTextField();
		User3.setFont(new Font("Arial", Font.PLAIN, 20));
		User3.setHorizontalAlignment(SwingConstants.CENTER);
		User3.setText("User3");
		panel.add(User3);
		User3.setColumns(10);
		
		User4 = new JTextField();
		User4.setFont(new Font("Arial", Font.PLAIN, 20));
		User4.setHorizontalAlignment(SwingConstants.CENTER);
		User4.setText("User4");
		panel.add(User4);
		User4.setColumns(10);
		this.Init();
	}
	
	public static RoomView getInstance() {
		if(RoomView.instance == null) 
			RoomView.instance = new RoomView();
		return RoomView.instance;
	}

	private void Init() {
		
	}
	
	private class ExitRoomEventListener implements WindowListener {

		@Override
		public void windowOpened(WindowEvent e) {
		}

		@Override
		public void windowClosing(WindowEvent e) {
			RoomListView roomListView = RoomListView.getInstance();
			roomListView.setVisible(true);
		}

		@Override
		public void windowClosed(WindowEvent e) {
		}

		@Override
		public void windowIconified(WindowEvent e) {
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
		}

		@Override
		public void windowActivated(WindowEvent e) {
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
		}
	}
}
