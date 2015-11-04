package main;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


import javax.swing.*;



public class getSamples extends JPanel implements ActionListener{
	
	JButton openButton, selectStartButton, exitButton;
	JTextArea progressLog;
	JFileChooser directory,startFile;
	MusicPiece [] allMusicPieces;
    int nrOfPieces = 0;
	
	public getSamples() {
	  
        progressLog = new JTextArea(10,40);
        progressLog.setMargin(new Insets(5,5,5,5));
        progressLog.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(progressLog);
	
		
		directory = new JFileChooser();
		directory.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		openButton = new JButton("   Select Music catalog    ");
		openButton.addActionListener(this); 

		
		startFile = new JFileChooser();
		selectStartButton = new JButton("Select Seed Music Piece");
		selectStartButton.addActionListener(this);
		selectStartButton.setEnabled(false);
		exitButton = new JButton("EXIT");
		exitButton.addActionListener(this);
		    
        add(openButton);
		add(selectStartButton);
        add(logScrollPane);
        add(exitButton);
	}
	

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == openButton) {
			int returnVal = directory.showOpenDialog(getSamples.this);
			if(returnVal == JFileChooser.APPROVE_OPTION){
				openButton.setEnabled(false);
				File folder = directory.getSelectedFile();
				if (folder.isDirectory()) {
					File[] listOfFiles = folder.listFiles();
					allMusicPieces = new MusicPiece[listOfFiles.length];  
					for (File file:listOfFiles){
						if (file.isFile()) {
							System.out.println("processing File: " + file.getName());
							progressLog.append("processed File: " + file.getName()+ "\n");
						   allMusicPieces[nrOfPieces]= new MusicPiece(file);
						   if (allMusicPieces[nrOfPieces].isValid()) {
							   nrOfPieces++;
						   }
						}   
					}
				}
				startFile.setCurrentDirectory(folder);
				selectStartButton.setEnabled(true);
			}
		}
		if(e.getSource() == selectStartButton) {
			int returnVal = startFile.showOpenDialog(getSamples.this);
      		if(returnVal == JFileChooser.APPROVE_OPTION){
      			File seedFile = startFile.getSelectedFile();
      			int seedFileNr = 0;
      			while ((seedFileNr < nrOfPieces)&&!(seedFile.getName().equals(allMusicPieces[seedFileNr].getFile().getName()))){
      				 System.out.println("seedfile " + seedFile.getName()+ " MP name " + allMusicPieces[seedFileNr].getFile().getName());
      			  seedFileNr++;	
      			}
      			if (seedFileNr < nrOfPieces) {
      				String playlistName = new String(seedFile.getParentFile().getParent() + "\\" +"OrderedPlaylist.txt");
      				File playListFile = new File(playlistName);
      		    	FileWriter myWriter;
      				try {
      					myWriter = new FileWriter(playListFile);
      					PrintWriter myPW = new PrintWriter(myWriter);   				     				
          	            int []orderedList = Ordering.Order(allMusicPieces,nrOfPieces,seedFileNr);
		                System.out.println("PlayList: ");
		                myPW.println("PlayList" + "\n");
		                for (int i=0;i<nrOfPieces;i++){
				          System.out.println("File: " + allMusicPieces[orderedList[i]].getFile().getName()+ "," + allMusicPieces[orderedList[i]].getTonality()
				        		             + "," + allMusicPieces[orderedList[i]].getMode()+ "," + allMusicPieces[orderedList[i]].getbpm());
		                  myPW.println(allMusicPieces[orderedList[i]].getFile().getName()+ "," + allMusicPieces[orderedList[i]].getTonality() 
		                		        + "," + allMusicPieces[orderedList[i]].getMode()+ "," + allMusicPieces[orderedList[i]].getbpm()+"\n");
		                } 
		                progressLog.append("Done Ordering, results saved in :" + playlistName + "\n");
		                myPW.close();
		             } catch (IOException e1) {
  						// TODO Auto-generated catch block
  						e1.printStackTrace();
					}
      			} else
      			   System.out.println("File not in list");

  			}
      	}	
		if(e.getSource() == exitButton) {
			System.exit(0);
		}
	}
}
