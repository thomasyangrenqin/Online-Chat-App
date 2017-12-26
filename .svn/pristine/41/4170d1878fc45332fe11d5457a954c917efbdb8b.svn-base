package model;

import common.DataPacketChatRoom;
import common.IChatRoom;
import common.IReceiver;
import common.IRemoveReceiverType;
import util.ChatRoom;
import util.Receiver;
import util.RemoveReceiver;
import util.User;
import view.IModelAdapter;


import java.io.File;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashSet;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * The mini model to view adapter
 * @author Renqin Yang
 * @author Qizhen Guo
 */
public class MiniModel {
	/**
	 * model to view adapter
	 */
	IMiniViewAdapter miniModel2ViewAdapter;
	/**
	 * chat room object
	 */
	private IChatRoom chatRoom;
	/**
	 * chat receiver object
	 */
	private Receiver receiver;
	/**
	 * remote chat receiver stub
	 */
	@SuppressWarnings("unused")
	private Receiver receiverStub;
	/**
	 * local chat room list
	 */
	@SuppressWarnings("unused")
	private HashSet<ChatRoom> chatrooms;
	
	/**
	 * local user
	 */
	private User user;
	
	/**
	 * the model to view adapter
	 */
	private IModelAdapter<?> modelAdapter;
	
	

	/**
	 * Constructor of the minimodel
	 * @param miniModel2ViewAdapter the mini model to mini view adapter
	 * @param chatRoom the chat room of the mini mvc
	 * @param user  the user of the mini mvc
	 * @param receiver  the receiver of the mini mvc
	 */
	public MiniModel(IMiniViewAdapter miniModel2ViewAdapter, IChatRoom chatRoom, User user, Receiver receiver) {
		this.miniModel2ViewAdapter = miniModel2ViewAdapter;
		this.chatRoom = chatRoom;
		this.user = user;
		this.receiver = receiver;
	}

	/**
	 * get chat room
	 * @return chat room
	 */
	public IChatRoom getChatroom() {
		return chatRoom;
	}

	/**
	 * set chat rooms
	 * @param chatrooms   chatrooms that currently join
	 */
	public void setChatrooms(HashSet<ChatRoom> chatrooms) {
		this.chatrooms = chatrooms;
	}
	
	/**
	 * set the model to view adapter
	 * @param modelAdapter the adapter to be set
	 */
	public void setModelAdapter(IModelAdapter<?> modelAdapter){
		this.modelAdapter = modelAdapter;
	}

	/**
	 * set the chat room
	 * @param chatroom the chat room to be set
	 */
	public void setChatroom(IChatRoom chatroom) {
		this.chatRoom = chatroom;
	}

	/**
	 * get chat connection
	 * @return chat connection
	 */
	public IReceiver getReceiver() {
		return receiver;
	}

	/**
	 * send text message to view
	 * @param username the name of the sender
	 * @param text the message to be send
	 */
	public void sendMsg(String username, String text) {
		miniModel2ViewAdapter.append(username, text);
	}

	/**
	 * refresh chatroom
	 */
	public void refresh() {
		miniModel2ViewAdapter.refresh();
	}


	/**
	 * start running chatroom
	 */
	public void start() {

	}

	/**
	 * close chat room
	 * @param miniview  chat room view
	 */
	public void closeRoom(JPanel miniview) {
		System.out.println("model adapter: " + modelAdapter);
		System.out.println("chatroom: " + chatRoom);
		modelAdapter.removeChatRoom(chatRoom);
		for (IReceiver receiverStub : chatRoom.getIReceiverStubs())
			try {
				if (receiverStub.getUserStub().getUUID().equals(user.getUUID())) {
					chatRoom.removeIReceiverStubLocally(receiverStub);
					chatRoom.sendPacket(new DataPacketChatRoom<IRemoveReceiverType>(IRemoveReceiverType.class, new RemoveReceiver(receiverStub), receiverStub));
					return ;
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
	}


	/**
	 * get chat connection stub
	 * @return chat connection stub
	 * @throws RemoteException exception
	 */
	public Collection<IReceiver> getChatStubs() throws RemoteException {
		return this.receiver.getChatroom().getIReceiverStubs();
	}



	/**
	 * send text message
	 * @param text txt content
	 */
	public void sendMsg(String text) {
		DataPacketChatRoom<String> data = new DataPacketChatRoom<String>(String.class, text, receiver);
		receiver.sendPacket(data, chatRoom);
		System.out.println("[MiniModel.sendMsg() ] " + text);
	}

	/**
	 * send image
	 * @param img the image to be send
	 */
	public void sendFile(ImageIcon img) {
		DataPacketChatRoom<ImageIcon> data = new DataPacketChatRoom<ImageIcon>(ImageIcon.class, img, receiver);
		receiver.sendPacket(data, chatRoom);
		System.out.println("[MiniModel.sendMsg() ] " + img.toString());
	}
	
	/**
	 * send file
	 * @param file the file to be send
	 */
	public void sendFile(File file){
		DataPacketChatRoom<File> data = new DataPacketChatRoom<File>(File.class, file, receiver);
		receiver.sendPacket(data, chatRoom);
		System.out.println("[MiniModel.sendMsg() ] " + file.getName());
	}
}
