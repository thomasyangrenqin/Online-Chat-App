package util;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

import common.DataPacketChatRoom;
import common.IChatRoom;
import common.IReceiver;

/**
 * A concrete chatroom class
 * @author Renqin Yang
 * @author Qizhen Guo
 */
public class ChatRoom implements IChatRoom{
	/**
	 * The generate serial version id
	 */
	private static final long serialVersionUID = -5207620380615489899L;
	/**
	 * the id of the chat room
	 */
	private UUID uuid;
	/**
	 * the name of the chat room
	 */
	private String name;
	/**
	 * receiver list
	 */
	private HashSet<IReceiver> receives = new HashSet<IReceiver>();

	/**
	 * Constuctor of the chatroom class
	 * @param name  chatroom name
	 */
	public ChatRoom(String name) {
		this.uuid = UUID.randomUUID();
		this.name = name;
	}
	
	@Override
	public UUID getUUID() {
		return uuid;
	}

	@Override
	public String getName() {
		return name;
	}


	@Override
	public Collection<IReceiver> getIReceiverStubs(){
		return receives;
	}


	@Override
	public <T> void sendPacket(DataPacketChatRoom<T> data) {
//		(new Thread() {
//			public void run() {
//				super.run();
				receives.iterator().forEachRemaining((receive) -> {
					try {
						System.out.println("success receive!!!!");
						receive.receive(data);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				});
//			}
////		}).start();
	}
	
	@Override
	public String toString() {
		return this.getName();
	}

	@Override
	public boolean addIReceiverStubLocally(IReceiver receiver) {
		return receives.add(receiver);
	}

	@Override
	public boolean removeIReceiverStubLocally(IReceiver receiver) {
		return receives.remove(receiver);
	}
	
	public boolean equals(Object other) { 
		if (other instanceof ChatRoom) {
			return uuid.equals(((IChatRoom)other).getUUID());
		}
		return false;
	}

}
