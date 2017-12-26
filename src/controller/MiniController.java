package controller;

import java.awt.Container;
import java.awt.EventQueue;
import java.io.File;
import java.rmi.RemoteException;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import common.IChatRoom;
import common.IReceiver;
import util.Receiver;
import util.User;
import model.IMiniViewAdapter;
import model.MainModel;
import model.MiniModel;
import view.IMiniModelAdapter;
import view.MiniGUI;

/**
 * The mini controller
 * @author Renqin Yang
 * @author Qizhen Guo
 */
public class MiniController {
	
	/**
	 * This field represents the mini model.
	 */
	private MiniModel miniModel; 
	/**
	 * This field represents the mini view.
	 */
	@SuppressWarnings("rawtypes")
	private MiniGUI miniGUI; 

	/**
	 * Main model object
	 */
	private MainModel model;
	/**
	 * chat receiver
	 */
	@SuppressWarnings("unused")
	private Receiver receiver;

	/**
	 * set main model
	 * @param model the main model to be set
	 */
	public void setMainModel(MainModel model) {
		this.model = model;
	}

	/**
	 * set chat receiver
	 * @param receiver  the receiver to be set
	 */
	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

	/**
	 * Controller constructor
	 * @param view   chatroom UI
	 * @param model   chatroom model
	 */
	public MiniController(MiniGUI<?> view, MiniModel model) {
		this.miniGUI = view;
		this.miniModel = model;
	}

	/**
	 * An other Constructor of the mini controller
	 * @param chatRoom the associated chat room
	 * @param user the associated user
	 * @param receiver  the associated receiver
	 */
	public MiniController(IChatRoom chatRoom, User user, Receiver receiver) {

		miniGUI = new MiniGUI<Object>(new IMiniModelAdapter<Object>() {
			@Override
			public void closeRoom() {
				miniModel.closeRoom(miniGUI);
				model.closeRoom(miniGUI);
			}

			@Override
			public void sendMsg(String msg) {
				miniModel.sendMsg(msg);
			}

			
			public void sendFile(ImageIcon img){
				miniModel.sendFile(img);
			}
			
			public void sendFile(File file){
				miniModel.sendFile(file);
			}

			@SuppressWarnings({ })
			@Override
			public Iterable<IReceiver> getChatStubs() {
				try {
					return miniModel.getChatStubs();
				} catch (RemoteException e) {
					e.printStackTrace();
					return null;
				}
			}

		});

		miniModel = new MiniModel(new IMiniViewAdapter() {

			@Override
			public void refresh() {
				try {
					miniGUI.refresh();
				} catch (RemoteException e) {
					e.printStackTrace();
				}

			}

			@Override
			public void append(String name, String text) {
				miniGUI.append(name, text);
			}

			@Override
			public Container createImageView() {
				return miniGUI.createImageView();
			}

			@Override
			public void closeRoom(JPanel miniview) {
				model.closeRoom(miniview);

			}

			@Override
			public void append(String name, ImageIcon data) {
				miniGUI.append(name, data);
			}

		}, chatRoom, user, receiver);
	}

	/**
	 * get the mini model
	 * @return the mini model
	 */
	public MiniModel getMiniModel() {
		return this.miniModel;
	}

	/**
	 * get mini GUI
	 * @return mini GUI
	 */
	public MiniGUI<?> getMiniView() {
		return this.miniGUI;
	}

	/**
	 * Start the system
	 * @throws RemoteException exception
	 */
	public void start() throws RemoteException {
		miniGUI.start();
		miniModel.start();
	}

	/**
	 * Launch the application.
	 * @param args Arguments given by the system or command line.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() { 
			public void run() {
				try {
					MainController controller = new MainController();
					controller.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
