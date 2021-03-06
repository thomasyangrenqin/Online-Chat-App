package controller;

import java.awt.Component;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashSet;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import common.IChatRoom;
import util.Receiver;
import util.User;
import model.IMiniMVCAdapter;
import model.IViewAdapter;
import model.MainModel;
import model.MiniModel;
import view.IModelAdapter;
import view.MainGUI;
import view.MiniGUI;

/**
 * The main model to view adapter
 * @author Renqin Yang
 * @author Qizhen Guo
 */
public class MainController {
	/**
	 * Main model
	 */
	private MainModel model;
	/**
	 * Main GUI
	 */
	private MainGUI<IChatRoom> mainGUI;
	/**
	 * mini model
	 */
	MiniModel miniModel = null;
	/**
	 * model to view adapter
	 */
	IViewAdapter model2ViewAdapter;

	/**
	 * main function of the program
	 * @param args args
	 * @throws SocketException exception
	 * @throws UnknownHostException exception
	 */
	public static void main(String[] args) throws SocketException, UnknownHostException {
		(new MainController()).start();
	}

	/**
	 * constructor of controller
	 * @throws SocketException exception
	 * @throws UnknownHostException exception
	 */
	public MainController() throws SocketException, UnknownHostException {
		IModelAdapter<IChatRoom> view2ModelAdapter = new IModelAdapter<IChatRoom>() {
			@Override
			public String connectTo(String remoteHost) {
				return model.connectTo(remoteHost);
			}

			@Override
			public void quit() {
				model.quit();
			}

			@Override
			public void setUsername(String username) {
				try {
					model.setUsername(username);
				} catch (RemoteException e) {
					System.out.println("ERROR: set username fail");
					e.printStackTrace();
				}
			}

			@Override
			public void joinRoom(Object chatroom) {
				try {
					model.joinRoom((IChatRoom) chatroom);
				} catch (RemoteException e) {
					System.out.println("ERROR: join chatroom fail");
					e.printStackTrace();
				}
			}

			@Override
			public void createRoom(String setname) {
				try {
					model.createRoom(setname);
				} catch (RemoteException e) {
					System.out.println("ERROR: fail to create room");
					e.printStackTrace();
				} catch (NotBoundException e) {
					System.out.println("ERROR: fail to create room");
					e.printStackTrace();
				}
			}

			@Override
			public void removeChatRoom(IChatRoom chatroom) {
				try {
					model.removeChatRoom(chatroom);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}

			@Override
			public String getUsername() {
				try {
					return model.getUsername();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				return null;
			}
		};

		model2ViewAdapter = new IViewAdapter() {
			@Override
			public void append(String s) {
				mainGUI.append(s);
			}

			@Override
			public void setRemoteHost(String hostAddress) {
			}

			@Override
			public void warning(String s) {
			}

			@Override
			public void removeChatroomList() {
				mainGUI.removeChatroomList();
			}

			@Override
			public IMiniMVCAdapter makeMiniMVC(IChatRoom chatRoom, User user, Receiver receiver) {

				MiniController miniController = new MiniController(chatRoom, user, receiver);
				miniController.setReceiver(receiver);
				miniController.setMainModel(model);
				miniController.getMiniModel().setModelAdapter(view2ModelAdapter);
				
				try {
					miniController.start();
				} catch (RemoteException e) {
					e.printStackTrace();
				}

				MiniGUI<?> miniview = miniController.getMiniView();
				mainGUI.addRoom(chatRoom.getName(), miniview);
				return new IMiniMVCAdapter(){

					@Override
					public void refresh() {
						try {
							miniview.refresh();
						} catch (RemoteException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void append(String text) {
						miniview.append("DataPacket:", text);
					}

					@Override
					public void addComponent(Component component) {
						SwingUtilities.invokeLater(new Runnable(){
							@Override
							public void run() {
								miniview.addComponent(component);
							}
						 });
					}
					
				};
			}

			@Override
			public void closeRoom(JPanel miniView) {
				mainGUI.removeRoom(miniView);
			}

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public void setChatroomList(HashSet chatrooms) {
				mainGUI.setChatroomList(chatrooms);
			}

		};

		mainGUI = new MainGUI<IChatRoom>(view2ModelAdapter);
		model = new MainModel(model2ViewAdapter);

	}

	/**
	 * Start running program
	 * @throws SocketException exception
	 * @throws UnknownHostException exception
	 */
	public void start() throws SocketException, UnknownHostException {
		model.start();
		mainGUI.start();
	}

}
