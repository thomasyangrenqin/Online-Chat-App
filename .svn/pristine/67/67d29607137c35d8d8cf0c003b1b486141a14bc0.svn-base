package view;

import java.io.File;

import javax.swing.ImageIcon;

import common.IReceiver;

/**
 * Mini GUI to mini model adapter
 * @param <UserItem>  user
 * @author Renqin Yang
 * @author Qizhen Guo
 */
public interface IMiniModelAdapter<UserItem> {

	/**
	 * No-op singleton implementation of IModelAdapter
	 */
	@SuppressWarnings("rawtypes")
	public static final IMiniModelAdapter NULL_OBJECT = new IMiniModelAdapter() {

		public void closeRoom() {

		}

		public void sendMsg(String msg) {

		}

		public void sendFile(ImageIcon img){
			
		}
		
		public void sendFile(File file){
			
		}

		public Iterable<Object> getChatStubs() {
			return null;
		}
	};

	/**
	 * close chatroom
	 */
	public void closeRoom();

	/**
	 * send message to chat room
	 * @param msg  message content
	 */
	public void sendMsg(String msg);


	/**
	 * Send a image type file
	 * @param img the image to be send
	 */
	public void sendFile(ImageIcon img);
	
	/**
	 * send a common file
	 * @param file the file to be send
	 */
	public void sendFile(File file);

	/** 
	 * get current chat stubs
	 * @return  set of chat stubs
	 */
	public Iterable<IReceiver> getChatStubs();

}
