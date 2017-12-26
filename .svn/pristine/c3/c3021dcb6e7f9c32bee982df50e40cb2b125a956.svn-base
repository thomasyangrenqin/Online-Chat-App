package model;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Consumer;

import javax.swing.JPanel;

import common.DataPacketChatRoom;
import common.IAddReceiverType;
//import chatapp.impl.Receiver;
import common.IChatRoom;
import common.IComponentFactory;
import common.IReceiver;
import common.IRemoveReceiverType;
import common.IUser;
import controller.MiniController;
import provided.rmiUtils.IRMIUtils;
import provided.rmiUtils.IRMI_Defs;
import provided.rmiUtils.RMIUtils;
import util.AddReceiver;
import util.ChatRoom;
import util.Receiver;
import util.RemoveReceiver;
import util.User;

/**
 * The main model to view adapter
 * @author Renqin Yang
 * @author Qizhen Guo
 */
public class MainModel {
	private Consumer<String> outputCmd = new Consumer<String>(){
    	@Override
		public void accept(String t) {
			model2ViewAdapter.append(String.valueOf(t) + "\n");
		}
    };
	/**
	 * registry for searching machine
	 */
	private Registry registry;

	/**
	 * RMI util object
	 */
	private IRMIUtils rmiUtils = new RMIUtils(outputCmd);

	/**
	 * default user name of program
	 */
	private String username = "ry13";
	/**
	 * local chat receivers
	 */
	private HashSet<Receiver> chatReceivers = new HashSet<Receiver>();

	/**
	 *  joined chatrooms set
	 */
	private HashSet<IChatRoom> chatrooms = new HashSet<IChatRoom>();
	
	/**
	 * chatroom to mini controller mapper
	 */
	@SuppressWarnings("unused")
	private HashMap<IChatRoom, MiniController> chatroomToMiniController = new HashMap<IChatRoom, MiniController>();
	
	/**
	 * room count integer
	 */
	private int roomCount = 1;
	/**
	 * port shift integer
	 */
	private int portCount = 0;
	
	/**
	 * default port
	 */
	private int receivePort = 2101;
	/**
	 * maximum number of valid running program
	 */
	private final int MAX_APP_NUM = 8;
	/**
	 * model to GUI adapter
	 */
	private IViewAdapter model2ViewAdapter;
	/**
	 * private user object
	 */
	private User user;
	
	/**
	 * private user stub
	 */
	private IUser userStub;

	/**
	 * constructor of model
	 * @param model2ViewAdapter  model to view adapter
	 */
	public MainModel(IViewAdapter model2ViewAdapter) {
		this.model2ViewAdapter = model2ViewAdapter;
	}

	/**
	 * close chat room
	 * @param miniView the mini model to mini view adapter
	 */
	public void closeRoom(JPanel miniView) {
		model2ViewAdapter.closeRoom(miniView);

		roomCount--;
	}

	/**
	 * start the main model
	 * @throws SocketException exception
	 * @throws UnknownHostException exception
	 */
	public void start() throws SocketException, UnknownHostException {

		while (portCount <= MAX_APP_NUM) {
			rmiUtils.startRMI(IRMI_Defs.CLASS_SERVER_PORT_CLIENT + portCount);

			try {
				user = new User(username, rmiUtils.getLocalAddress(), model2ViewAdapter);
				System.out.println(user.getName());
				registry = rmiUtils.getLocalRegistry();
				userStub = (IUser) UnicastRemoteObject.exportObject(user, 2100 + portCount);
				registry.rebind(IUser.BOUND_NAME, userStub);

				break;
			} catch (Exception e) {

			}

			portCount++;
		}
	}

	/**
	 * Connect to remote host and get list of chatroom
	 * @param remoteHost  remote host
	 * @return  info message
	 */
	public String connectTo(String remoteHost) {
		IUser remoteStub = null;
		try {
			registry = rmiUtils.getRemoteRegistry(remoteHost);
			remoteStub = (IUser) registry.lookup(IUser.BOUND_NAME);
			remoteStub.connect(userStub);
			
			HashSet<IChatRoom> chatrooms = new HashSet<>();
			   for(IChatRoom temproom: remoteStub.getChatRooms()) {
			    chatrooms.add(temproom);
			   }
			
			model2ViewAdapter.setChatroomList(chatrooms);
			
		} catch (Exception e) {
			System.err.println("ConnectTo exception:" + "\n");
			e.printStackTrace();
		}
		return "SUCCESS: connected to remote host: " + remoteHost;
	}
	
	private class ChatRoomManager {
		Receiver createdConnection;
		@SuppressWarnings("unused")
		IChatRoom room;
		IMiniMVCAdapter miniMVCAdapter;
		
		ChatRoomManager(IChatRoom room) throws RemoteException {
			this.room = room;
			createdConnection = new Receiver(user, room, new Cmd2ViewAdapter(){

				@Override
				public void appendMsg(String name,String text) {
					miniMVCAdapter.append(name+ ":"+text);
				}

				@Override
				public void buildScrollableComponent(IComponentFactory fac, String label) {
					if (fac.makeComponent() != null)
						miniMVCAdapter.addComponent(fac.makeComponent());
				}

				@Override
				public void refresh() {
					miniMVCAdapter.refresh();
				}

				@Override
				public void buildNonScrollableComponent(IComponentFactory fac, String label) {
					
				}
				
			});
			miniMVCAdapter = model2ViewAdapter.makeMiniMVC(room, user, createdConnection);
			IReceiver chatstub = (IReceiver) UnicastRemoteObject.exportObject(createdConnection, receivePort + portCount);
			createdConnection.setReceiverStub(chatstub);
			chatReceivers.add(createdConnection);
			room.addIReceiverStubLocally(chatstub);
			room.sendPacket(new DataPacketChatRoom<IAddReceiverType>(IAddReceiverType.class, new AddReceiver(chatstub), chatstub));
			user.addRoom(room);
			chatrooms.add(room);
			roomCount++;
		}
		
	}

	/**
	 * Create new chatroom
	 * @param setname the name of the chatroom
	 * @throws RemoteException exception
	 * @throws NotBoundException exception
	 */
	public void createRoom(String setname) throws RemoteException, NotBoundException {
		IChatRoom room = null;
		if(setname != null) room = new ChatRoom(setname);
		else room = new ChatRoom("Room-" + roomCount);
		new ChatRoomManager(room);
	}


	/**
	 * set user name
	 * @param username  name
	 * @throws RemoteException exceptions
	 */
	public void setUsername(String username) throws RemoteException {
		if(username != null){
			this.username = username;
			user.setName(username);
		}
	}
	
	/**
	 * get the current user name
	 * @return the user name
	 * @throws RemoteException exceptions
	 */
	public String getUsername() throws RemoteException{
		return this.user.getName();
	}

	/**
	 * Join a remote chatroom
	 * @param remoteChatroom  remote chatroom object
	 * @throws RemoteException exception
	 */
	public void joinRoom(IChatRoom remoteChatroom) throws RemoteException {

		if (null == remoteChatroom) {
			System.out.println("ERROR: connect and select chatroom first");
			return;
		}

		Collection<IReceiver> remoteConnections = remoteChatroom.getIReceiverStubs();
		if (remoteConnections == null) {
			System.out.println("remote connection is null");
			return;
		}


		for (IChatRoom cr : chatrooms) {
			if(cr.getUUID().equals(remoteChatroom.getUUID())) {
				System.out.println("You're already in this chatroom");
				return;
			}
		}
		
		new ChatRoomManager(remoteChatroom);

	}


	/**
	 * Quit program and leave all chatroom
	 */
	public void quit() {
		System.out.println(" client is quitting.");
		try {
			for(IChatRoom chatroom : chatrooms){
				for (IReceiver receiverStub : chatroom.getIReceiverStubs())
					try {
						if (receiverStub.getUserStub().getUUID().equals(user.getUUID())) {
							chatroom.removeIReceiverStubLocally(receiverStub);
							break;
						}
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				for (IReceiver receiverStub : chatroom.getIReceiverStubs())
					try {
						if (!receiverStub.getUserStub().getUUID().equals(user.getUUID())) {
							chatroom.sendPacket(new DataPacketChatRoom<IRemoveReceiverType>(IRemoveReceiverType.class, new RemoveReceiver(receiverStub), receiverStub));
						}
					} catch (RemoteException e) {
						e.printStackTrace();
					}
			}
			for(IChatRoom chatroom : chatrooms){
				user.removeRoom(chatroom);
			}
			chatrooms.clear();
			
			rmiUtils.stopRMI();

		} catch (Exception e) {
			System.err.println(" Error stopping class server: " + e);
		}
		System.exit(0);
	}
	
	/**
	 * remove chatroom
	 * @param chatroom the chatroom to be removed
	 * @throws RemoteException exception
	 */
	public void removeChatRoom(IChatRoom chatroom) throws RemoteException{
		for(IChatRoom ichatroom : chatrooms){
			if(ichatroom.getUUID().equals(chatroom.getUUID())){
				chatrooms.remove(chatroom);
				break;
			}
		}
		user.removeRoom(chatroom);
	}

}
