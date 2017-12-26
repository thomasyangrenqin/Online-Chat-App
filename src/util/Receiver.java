package util;


import java.io.File;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.ImageIcon;

import command.FileCmd;
import command.ImageCmd;
import command.InstallCmd;
import command.StringCmd;
import command.UnknownCmd;
import common.DataPacketAlgoCmd;
import common.DataPacketChatRoom;
import common.IAddReceiverType;
import common.IChatRoom;
import common.ICmd2ModelAdapter;
import common.IInstallCmdType;
import common.IReceiver;
import common.IRemoveReceiverType;
import common.IRequestCmdType;
import common.IUser;
import model.Cmd2ViewAdapter;
import provided.datapacket.*;

/**
 * A concrete IReceiver class
 * @author Renqin Yang
 * @author Qizhen Guo
 */
public class Receiver implements IReceiver{
	/**
	 * chat room owner
	 */
	private IUser user;
	/**
	 * the chatroom
	 */
	private IChatRoom chatRoom;
	
	/**
	 * the chatroom's id
	 */
	private UUID uuid = UUID.randomUUID();
	
	/**
	 * a map of pre-defined unknown type commands
	 */
	private Map<Class<?>, DataPacketAlgoCmd<?>> cmdMap = new HashMap<>();

	/**
	 * a map used to cache unknown type messages
	 */
	private Map<Class<?>, List<DataPacketChatRoom<?>>> messageCache = new HashMap<>();
	
	/**
	 * the command to model adapter
	 */
	private Cmd2ViewAdapter cmd2ModelAdapter;
	
	/**
	 * the receiver stub
	 */
	private IReceiver receiverStub;
	
	
	/**
	 * data parsing algorithm
	 */
	private DataPacketAlgo<String, String> dataPacketAlgo = new DataPacketAlgo<String, String>(new DataPacketAlgoCmd<Object>(){

		/**
		 * The generate serial version id
		 */
		private static final long serialVersionUID = -480202025451052938L;

		@Override
		public String apply(Class<?> id, DataPacketChatRoom<Object> host, String... params) {
			Object x = host.getData();
			try {
				cmd2ModelAdapter.appendMsg(host.getSender().getUserStub().getName(),"Default case. successfully process data " + x);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			List<DataPacketChatRoom<?>> cache = messageCache.getOrDefault(id, new LinkedList<DataPacketChatRoom<?>>());
			cache.add(host);
			messageCache.put(id, cache);
			
			try {
				host.getSender().receive(new DataPacketChatRoom<IRequestCmdType>(IRequestCmdType.class, new UnknownCmd(id), receiverStub));
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			return "Default case. data = "+ x;
		}	

		@Override
		public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		}
	});


	/**
	 * Constructor of the Receiver object
	 * @param user the associated user
	 * @param chatroom the associated chatroom
	 * @param cmd2ModelAdapter the associated command to model adpapter
	 */
	public Receiver(IUser user, IChatRoom chatroom, Cmd2ViewAdapter cmd2ModelAdapter) {
		this.user = user;
		this.chatRoom = chatroom;
		this.cmd2ModelAdapter = cmd2ModelAdapter;
		cmdMap.put(String.class, new StringCmd());
		cmdMap.put(ImageIcon.class, new ImageCmd());
		cmdMap.put(File.class, new FileCmd());
		
		this.dataPacketAlgo.setCmd(IAddReceiverType.class, new DataPacketAlgoCmd<IAddReceiverType>() {
			
			/**
			 * The generate serial version id
			 */
			private static final long serialVersionUID = 7743660382686579881L;
			
			@Override
			public String apply(Class<?> index, DataPacketChatRoom<IAddReceiverType> host, String... params) {
				try {
					cmd2ModelAdapter.appendMsg(host.getSender().getUserStub().getName(),"Case IAddReceiver " + host.getData());
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			
				chatRoom.addIReceiverStubLocally(host.getData().getReceiverStub());
				cmd2ModelAdapter.refresh();
				return null;
			}

			@Override
			public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
			}
		});
		
		this.dataPacketAlgo.setCmd(IRemoveReceiverType.class, new DataPacketAlgoCmd<IRemoveReceiverType>() {

			/**
			 * The generate serial version id
			 */
			private static final long serialVersionUID = 8024193794687140973L;

			@Override
			public String apply(Class<?> index, DataPacketChatRoom<IRemoveReceiverType> host, String... params) {
				try {
					cmd2ModelAdapter.appendMsg(host.getSender().getUserStub().getName(),"Case IRemoveReceiver " + host.getData());
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				
				chatRoom.removeIReceiverStubLocally(host.getData().getReceiverStub());
				cmd2ModelAdapter.refresh();
				return null;
			}

			@Override
			public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
			}
		});
		
		this.dataPacketAlgo.setCmd(IRequestCmdType.class, new DataPacketAlgoCmd<IRequestCmdType>() {
			
			/**
			 * The generate serial version id
			 */
			private static final long serialVersionUID = 7420417196382121871L;

			@Override
			public String apply(Class<?> index, DataPacketChatRoom<IRequestCmdType> host, String... params) {
				Class<?> cmdId = host.getData().getCmdId();
				System.out.println(cmdId);
				try {
					cmd2ModelAdapter.appendMsg(host.getSender().getUserStub().getName() ,"IUnknownCmd " + cmdId);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				try {
					cmd2ModelAdapter.appendMsg(host.getSender().getUserStub().getName(),"request command from me " + cmdMap.get(cmdId));
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				try {
					System.out.println(cmdMap.containsKey(cmdId));
					host.getSender().receive(new DataPacketChatRoom<IInstallCmdType>(IInstallCmdType.class, new InstallCmd(cmdId, cmdMap.get(cmdId)), receiverStub));
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
			}
		});
		
		this.dataPacketAlgo.setCmd(IInstallCmdType.class,  new DataPacketAlgoCmd<IInstallCmdType>(){
			private static final long serialVersionUID = 2853224131272935836L;
			@Override
			public String apply(Class<?> index, DataPacketChatRoom<IInstallCmdType> host, String... params) {
				Class<?> cmdId = host.getData().getCmdId();
				DataPacketAlgoCmd<?> cmd = host.getData().getCmd();
				cmd.setCmd2ModelAdpt(cmd2ModelAdapter);
				dataPacketAlgo.setCmd(cmdId, cmd);
				System.out.println("Successfully installed command for " + host.getData().getCmdId());
				for (DataPacketChatRoom<?> packet : messageCache.get(cmdId))
					packet.execute(dataPacketAlgo);

				return null;
			}
			@Override
			public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
				
			}
		});
	}


	@Override
	public <T> void receive(DataPacketChatRoom<T> data) throws RemoteException {
		data.execute(dataPacketAlgo);
	}



	@Override
	public IUser getUserStub() throws RemoteException {
		return this.user;
	}
	
	/**
	 * get the associated chatroom
	 * @return the chat room
	 * @throws RemoteException exception
	 */
	public IChatRoom getChatroom() throws RemoteException{
		return chatRoom;
	}
	
	/**
	 * send out the data packet
	 * @param data the data packet to be send
	 * @param room the chat room send data packet
	 * @return null
	 */
	public <T> Collection<DataPacketChatRoom<T>> sendPacket(DataPacketChatRoom<T> data, IChatRoom room) {
		System.out.println("[User.sendPacket() ] " + data);
		room.sendPacket(data);
		return null;
	}
	
	/**
	 * get the command of the data packet algo
	 * @param id the class name of the data type
	 * @return a command of the data packet
	 * @throws RemoteException exception
	 */
	public DataPacketAlgoCmd<?> getCmd(Class<?> id) throws RemoteException {
		return cmdMap.get(id);
	}

	@Override
	public UUID getUUID() throws RemoteException {
		return uuid;
	}
	
	/**
	 * get the receiver stub
	 * @return the receive stub
	 */
	public IReceiver getReceiverStub() {
		return receiverStub;
	}

	/**
	 * set the receive stub
	 * @param receiverStub the receiver stub to be set
	 */
	public void setReceiverStub(IReceiver receiverStub) {
		this.receiverStub = receiverStub;
	}
	
	public boolean equals(Object other){
		if (other instanceof IReceiver) {
			try {
				return uuid.equals(((IReceiver)other).getUUID());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	

}
