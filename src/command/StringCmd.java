package command;

import java.rmi.RemoteException;

import common.DataPacketAlgoCmd;
import common.DataPacketChatRoom;
import common.ICmd2ModelAdapter;

/**
 * A pre-installed command used to deal with string type message which is belong to unknown data type.
 * @author Renqin Yang
 * @author Qizhen Guo
 */
public class StringCmd extends DataPacketAlgoCmd<String>{
	/**
	 * The generate serial version id.
	 */
	private static final long serialVersionUID = -8189620385100794733L;
	private transient ICmd2ModelAdapter cmd2ModelAdpt;
	
	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		this.cmd2ModelAdpt = cmd2ModelAdpt;
	}

	@Override
	public String apply(Class<?> index, DataPacketChatRoom<String> host, String... params) {
		String x = host.getData();
		try {
			cmd2ModelAdpt.appendMsg(host.getSender().getUserStub().getName(),x);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		System.out.println("Got a String data. data = "+ x);
		return null;
	}
}
