package util;

import common.IReceiver;
import common.IRemoveReceiverType;

/**
 * A concrete IRemoveReceiverType class
 * @author Renqin Yang
 * @author Qizhen Guo
 */
public class RemoveReceiver implements IRemoveReceiverType{
	
	/**
	 * The generate serial version id
	 */
	private static final long serialVersionUID = -3428827602093854689L;
	private IReceiver _receiver;
	
	/**
	 * Constructor of the removereceiver class
	 * @param receiver the receiver to be removed
	 */
	public RemoveReceiver(IReceiver receiver){
		this._receiver = receiver;
	}

	@Override
	public IReceiver getReceiverStub() {
		return _receiver;
	}

}
