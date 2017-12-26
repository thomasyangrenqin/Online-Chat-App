package model;


import java.awt.Component;

/**
 * mini MVC Adapter
 * @author Renqin Yang
 * @author Qizhen Guo
 */
public interface IMiniMVCAdapter {
	/**
	 * refresh chat room
	 */
	public void refresh();

	
	/**
	 * append text to info panel
	 * @param text the text to be append
	 */
	public void append(String text);
	
	/**
	 * add a new component to mini view
	 * @param component the component to be add
	 */
	public void addComponent(Component component);

	/**
	 * No-op singleton implementation of IModelAdapter
	 */
	public static final IMiniMVCAdapter NULL_OBJECT = new IMiniMVCAdapter() {

		public void refresh() {

		}

		public void append(String text) {

		}
		
		public void addComponent(Component component){
			
		}

	};

}
