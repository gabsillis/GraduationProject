package main;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


public class Sequencing {

 	public static void main(String[] args) {
		running();

	}
	private static  void selectGUI(){
		JFrame frame = new JFrame ("music selection");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(300,250); 

		//This will center the JFrame in the middle of the screen 
		frame.setLocationRelativeTo(null);
			
		
		frame.add(new getSamples());

//		frame.pack();
		frame.setVisible(true);
	}
	
	public static void running(){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				selectGUI();
			}
		});
	}

}
