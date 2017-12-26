package util;

import common.IAddReceiverType;
import common.IReceiver;

/**
 * A concrete IAddReceiverType class
 * @author Renqin Yang
 * @author Qizhen Guo
 */
public class AddReceiver implements IAddReceiverType{
	/**
	 * The generate serial version id
	 */
	private static final long serialVersionUID = 4275904265804916399L;
	private IReceiver _receiver;
	
	/**
	 * constructor of the addreceiver class
	 * @param receiver the receiver to be add
	 */
	public AddReceiver(IReceiver receiver){
		this._receiver = receiver;
	}

	@Override
	public IReceiver getReceiverStub() {
		return _receiver;
	}
	
}
