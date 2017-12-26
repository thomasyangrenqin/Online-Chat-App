package command;

import common.IRequestCmdType;

/**
 * A concrete IRequestCmdType which used to represent unknown command.
 * @author Renqin Yang
 * @author Qizhen Guo
 */
public class UnknownCmd implements IRequestCmdType{
	/**
	 * The generate serial version id
	 */
	private static final long serialVersionUID = 2350385134648196636L;
	private Class<?> cmdId;
	
	/**
	 * The constructor of the unknowncmd
	 * @param cmdId the class name of the unknown cmd.
	 */
	public UnknownCmd(Class<?> cmdId) {
		this.cmdId = cmdId;
	}

	@Override
	public Class<?> getCmdId() {
		return cmdId;
	}
}
