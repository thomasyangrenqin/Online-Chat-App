package util;

import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.UUID;

import common.IChatRoom;
import common.IUser;
import model.IViewAdapter;

/**
 * A concrete IUser class
 * @author Renqin Yang
 * @author Qizhen Guo
 */
public class User implements IUser {
	/**
	 *  User name of the user
	 */
	public String name;
	/**
	 * chatrooms of the user
	 */
	public HashSet<IChatRoom> chatrooms = new HashSet<IChatRoom>();
	/**
	 * the id of the user
	 */
	private UUID uuid = UUID.randomUUID();
	/**
	 * the ip address of the user
	 */
	private String ip;
	/**
	 * list of connected user
	 */
	public HashSet<IUser> connectedUserList = new HashSet<IUser>();
	
	/**
	 * the model to view adapter
	 */
	private IViewAdapter iviewadapter;
	

	/**
	 * Constructor of user
	 * @param name  user name
	 * @param ip  ip address
	 * @param iviewAdapter the model to view adapter
	 */
	public User(String name, String ip, IViewAdapter iviewAdapter) {
		this.name = name;
		this.ip = ip;
		this.iviewadapter = iviewAdapter;
	}

	@Override
	public String getName() throws RemoteException {
		return this.name;
	}

	
	/**
	 * set the name of the user
	 * @param name the user name to be set
	 * @throws RemoteException exception
	 */
	public void setName(String name) throws RemoteException {
		this.name = name;
	}


	/**
	 * get the ip address of the user
	 * @return the ip address
	 * @throws RemoteException exception
	 */
	public String getIP() throws RemoteException {
		return ip;
	}



	/**
	 * add a chatroom to chatroom list
	 * @param chatroom the chatroom to be added
	 * @return the result of the adding
	 * @throws RemoteException exception
	 */
	public boolean addRoom(IChatRoom chatroom) throws RemoteException {
		for(IChatRoom ichatroom : chatrooms){
			if(ichatroom.getUUID().equals(chatroom.getUUID())) return false;
		}
		chatrooms.add(chatroom);
		return true;
	}

	
	/**
	 * remove a chatroom from chat room list
	 * @param chatroom the chat room to be remove
	 * @return the resulr of the removing
	 * @throws RemoteException exception
	 */
	public boolean removeRoom(IChatRoom chatroom) throws RemoteException {
		for(IChatRoom ichatroom : chatrooms){
			if(ichatroom.getUUID().equals(chatroom.getUUID())){
				chatrooms.remove(chatroom);
				return true;
			}
		}
		return false;

	}


	@Override
	public HashSet<IChatRoom> getChatRooms() throws RemoteException{
		return chatrooms;
	}
	
	
	@Override
	public void connect(IUser userStub) throws RemoteException{
		connectedUserList.add(userStub);
		
		HashSet<IChatRoom> chatrooms = new HashSet<>();
		   for(IChatRoom temproom: userStub.getChatRooms()) {
		    chatrooms.add(temproom);
		   }
		
		iviewadapter.setChatroomList(chatrooms);
	}

	@Override
	public UUID getUUID() throws RemoteException {
		return uuid;
	}
	
}
