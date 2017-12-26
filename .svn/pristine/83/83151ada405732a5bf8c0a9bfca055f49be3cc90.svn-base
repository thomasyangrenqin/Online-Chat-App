package view;

import common.IChatRoom;

/**
 * main GUI to model adapter
 * @param <TDropListItem> TDroplist item
 * @author Renqin Yang
 * @author Qizhen Guo
 */
public interface IModelAdapter<TDropListItem> {

	/**
	 * No-op singleton implementation of IModelAdapter
	 */
	@SuppressWarnings("rawtypes")
	public static final IModelAdapter NULL_OBJECT = new IModelAdapter() {

		public String connectTo(String remoteHost) {
			return ("no connect");
		}

		public void quit() {

		}

		public void setUsername(String username) {

		}

		public void joinRoom(Object chatroom) {

		}

		public void createRoom(String setname) {

		}
		
		public void removeChatRoom(IChatRoom chatroom) {
			
		}
		
		public String getUsername(){
			return null;
		}

	};

	/**
	 * Connect to a remot host
	 * @param remoteHost  remote host ip address
	 * @return  status string
	 */
	public String connectTo(String remoteHost);

	/**
	 * quit the app
	 */
	public void quit();

	/**
	 * set and update the user name
	 * @param username the user name to be updated
	 */
	public void setUsername(String username);

	/**
	 * join a remote chat room 
	 * @param chatroom the chat room to join
	 */
	public void joinRoom(Object chatroom);

	/**
	 * create a new chat room
	 * @param setname  the name of chat room
	 */
	public void createRoom(String setname);
	
	/**
	 * remove the chat room from app
	 * @param chatroom the chat room to be removed
	 */
	public void removeChatRoom(IChatRoom chatroom);
	
	/**
	 * get the user name
	 * @return the user name
	 */
	public String getUsername();

}
