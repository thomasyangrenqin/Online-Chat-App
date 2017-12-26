package model;


import java.util.HashSet;
import javax.swing.JPanel;

import common.IChatRoom;
import util.Receiver;
import util.User;

/**
 * the adapter to connect main model and main GUI
 * @author Renqin Yang
 * @author Qizhen Guo
 */
public interface IViewAdapter {
	/**
	 * Append information to info panel
	 * @param s the message to be append
	 */
	public void append(String s);

	/**
	 * set remote host address
	 * @param hostAddress  remote host address
	 */
	public void setRemoteHost(String hostAddress);

	/**
	 * append warning message 
	 * @param s  warning message
	 */
	public void warning(String s);

	/**
	 * remove chatroom  list
	 */
	public void removeChatroomList();

	/**
	 * make a new mini mvc
	 * @param chatRoom the associated chat room
	 * @param user the associated user
	 * @param receiver  the associated receiver
	 * @return  mini mvc adapter
	 */
	public IMiniMVCAdapter makeMiniMVC(IChatRoom chatRoom, User user, Receiver receiver);
	

	/**
	 * close the chat room
	 * @param miniView associated mini view
	 */
	public void closeRoom(JPanel miniView);

	/**
	 * set chat room list 
	 * @param chatrooms the chat room list to be set
	 */
	@SuppressWarnings("rawtypes")
	public void setChatroomList(HashSet chatrooms);

	/**
	 * no-op object
	 */
	public static final IViewAdapter NULL_OBJECT = new IViewAdapter() {

		public void append(String s) {

		}

		public void setRemoteHost(String hostAddress) {

		}

		public void warning(String s) {

		}

		public void removeChatroomList() {

		}

		public IMiniMVCAdapter makeMiniMVC(IChatRoom chatRoom, User user, Receiver receiver) {
			return null;
		}

		public void closeRoom(JPanel miniView) {

		}

		@SuppressWarnings("rawtypes")
		public void setChatroomList(HashSet chatrooms) {

		}

	};
}