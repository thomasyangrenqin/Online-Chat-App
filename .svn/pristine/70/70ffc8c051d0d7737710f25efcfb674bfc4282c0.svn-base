package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import common.IChatRoom;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.util.HashSet;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Main GUI of the chat app
 * @param <TDropListItem>  TDriolist item
 * @author Renqin Yang
 * @author Qizhen Guo
 */
public class MainGUI<TDropListItem> extends JFrame {

	/**
	 * The generate serial version id
	 */
	private static final long serialVersionUID = -8111121007712737005L;

	/**
	 * The adapter to the model.
	 */
	private IModelAdapter<TDropListItem> model;

	private JPanel contentPane;
	private final JPanel pnlOperation = new JPanel();
	private final JTextField txtIP = new JTextField("");
	private final JButton btnConnect = new JButton("Connect");
	private final JLabel lblRemoteHost = new JLabel("Remote Host:");
	private final JButton btnJoinChatroom = new JButton("Join Chatroom");
	private final JButton btnCreateRoom = new JButton("Create Room");
	private final JButton btnQuit = new JButton("Quit");
	private final JComboBox<TDropListItem> cbxChatRoomList = new JComboBox<TDropListItem>();
	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private final JTextArea textArea = new JTextArea();
	private final JScrollPane scrollPane = new JScrollPane(textArea);
	private final JLabel lblRoomName = new JLabel("Room name");
	private final JTextField txtInputRoomName = new JTextField();
	private final JPanel pnlControl = new JPanel();
	private final JPanel pnlUser = new JPanel();
	private final JLabel lblUserName = new JLabel("User Name:");
	private final JTextField txtInputUserName = new JTextField();
	private final JButton btnSetUserName = new JButton("Set User Name");

	/**
	 * Starts the already initialized frame.
	 */

	public void start() {

		setVisible(true);
		
		lblUserName.setText("User Name: " + model.getUsername());

	}

	/**
	 * 
	 * Create the frame.
	 * @param imodelAdapter view to model adapter
	 */
	public MainGUI(IModelAdapter<TDropListItem> imodelAdapter) {
		setTitle("ChatApp_Group15");
		txtInputUserName.setToolTipText("Input the user name you want to have");
		txtInputUserName.setColumns(10);
		txtInputRoomName.setToolTipText("Input the room name you want to set");
		txtInputRoomName.setColumns(10);
		textArea.setToolTipText("text displaying area");
		txtIP.setToolTipText("Input the IP address you want to connect with.");
		txtIP.setColumns(10);
		model = imodelAdapter;
		initGUI();
	}

	private void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		contentPane.add(pnlControl, BorderLayout.NORTH);
		pnlControl.setLayout(new BorderLayout(0, 0));
		
		pnlControl.add(pnlUser, BorderLayout.NORTH);
		
		pnlUser.add(lblUserName);
		
		pnlUser.add(txtInputUserName);
		btnSetUserName.setToolTipText("Set your user name");
		btnSetUserName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = txtInputUserName.getText();
				if(username != null){
					model.setUsername(username);
				}
				System.out.println(model.getUsername());
				lblUserName.setText("User Name: " + model.getUsername());
			}
		});
		
		pnlUser.add(btnSetUserName);
		pnlControl.add(pnlOperation);

		pnlOperation.add(lblRemoteHost);

		pnlOperation.add(txtIP);
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connect();
			}
		});
		btnConnect.setToolTipText("Click to connect the Remote Host.");

		pnlOperation.add(btnConnect);
		cbxChatRoomList.setToolTipText("The list of remot host's chat rooms.");

		pnlOperation.add(cbxChatRoomList);
		btnJoinChatroom.setToolTipText("Join the selected chat room.");

		pnlOperation.add(btnJoinChatroom);
		btnCreateRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.createRoom(txtInputRoomName.getText());
			}
		});
		btnCreateRoom.setToolTipText("Create a chat room with the input or default name.");

		btnJoinChatroom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.joinRoom(cbxChatRoomList.getSelectedItem());
			}
		});
		
		pnlOperation.add(lblRoomName);
		
		pnlOperation.add(txtInputRoomName);

		pnlOperation.add(btnCreateRoom);
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.quit();
			}
		});
		btnQuit.setToolTipText("Exit the app.");

		pnlOperation.add(btnQuit);
		tabbedPane.setToolTipText("Displaying area for info and chatrooms tab");

		contentPane.add(tabbedPane, BorderLayout.CENTER);

		tabbedPane.addTab("Info", null, scrollPane, null);

		scrollPane.setViewportView(textArea);
	}

	/**
	 * let the model connect to the remote host.
	 */
	private void connect() {
		append("Connecting...\n");
		append(model.connectTo(txtIP.getText()) + "\n");
	}

	/**
	 * Append the given string(s) to the view's output text area.  
	 * @param s the string to display.
	 */
	public void append(String s) {
		textArea.append(s);
		textArea.setCaretPosition(textArea.getText().length());
	}

	/**
	 * remove all the chatroom list
	 */
	public void removeChatroomList() {
		cbxChatRoomList.removeAllItems();
	}

	/**
	 * set a new chatroom list to drop down list
	 * @param chatrooms the chat room list to be set
	 */
	@SuppressWarnings("unchecked")
	public void setChatroomList(HashSet<IChatRoom> chatrooms) {
		removeChatroomList();
		System.out.println(chatrooms.size());
		chatrooms.iterator().forEachRemaining((room) -> {
			System.out.println(room.toString());
			System.out.println(room.getName());
			cbxChatRoomList.insertItemAt((TDropListItem) room, 0);
		});
	}

	/**
	 * add chat room into the main app
	 * @param name the room name of the chatroom to be added
	 * @param room the chatroom to be added
	 */
	public void addRoom(String name, JPanel room) {
		tabbedPane.addTab(name, null, room, null);
	}

	/**
	 * remove a chatroom from the main app
	 * @param room the chatroom to be removed.
	 */
	public void removeRoom(JPanel room) {
		tabbedPane.remove(room);
	}

}
