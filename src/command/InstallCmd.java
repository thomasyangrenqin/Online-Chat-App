package command;

import common.DataPacketAlgoCmd;
import common.IInstallCmdType;

/**
 * A concrete IInstallCmdType which used to represent to install a cmd.
 * @author Renqin Yang
 * @author Qizhen Guo
 */
public class InstallCmd implements IInstallCmdType{
	
	/**
	 * The generate serial version id
	 */
	private static final long serialVersionUID = 2499086829960521431L;
	private DataPacketAlgoCmd<?> cmd;
	private Class<?> cmdId;
	
	/**
	 * Constructor of the installcmd.
	 * @param cmdId the class name of the cmd to be installed.
	 * @param cmd the cmd to be installed.
	 */
	public InstallCmd(Class<?> cmdId, DataPacketAlgoCmd<?> cmd) {
		this.cmd = cmd;
		this.cmdId = cmdId;
	}

	@Override
	public DataPacketAlgoCmd<?> getCmd() {
		return cmd;
	}

	@Override
	public Class<?> getCmdId() {
		return cmdId;
	}
}
