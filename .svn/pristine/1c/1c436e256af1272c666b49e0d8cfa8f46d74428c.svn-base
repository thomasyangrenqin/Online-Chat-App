package command;

import java.io.File;

import common.DataPacketAlgoCmd;
import common.DataPacketChatRoom;
import common.ICmd2ModelAdapter;

/**
 * A pre-installed command used to deal with file type message which is belong to unknown data type.
 * @author Renqin Yang
 * @author Qizhen Guo
 */
public class FileCmd extends DataPacketAlgoCmd<File>{
	/**
	 * The generate serial version id.
	 */
	private static final long serialVersionUID = 125664661014383464L;
	@SuppressWarnings("unused")
	private transient ICmd2ModelAdapter cmd2ModelAdpt;
	
	@Override
	public String apply(Class<?> index, DataPacketChatRoom<File> host, String... params) {
		File file = host.getData();
		System.out.println("Get a File data. data = "+ file.getName());
		return null;
	}

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		this.cmd2ModelAdpt = cmd2ModelAdpt;
		
	}
}
