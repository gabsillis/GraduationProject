package main;
import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.media.*;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.filechooser.*;

import main.MusicKey.Mode;
import main.MusicKey.Tonality;

import org.jtransforms.dct.*;
import org.jtransforms.fft.DoubleFFT_1D;
import org.jtransforms.fft.DoubleFFT_2D;

import static java.lang.Math.sqrt;


public class getSamples extends JPanel implements ActionListener{
//	static private final String newline = "\n";
//	static private final int FOURIER_ARRAY_LENGTH = 16;
	
	JButton openButton, selectStartButton;
	JTextArea progressLog;
	JFileChooser directory,startFile;
	MusicPiece [] allMusicPieces;
    int nrOfPieces = 0;
	
	public getSamples() {
	       super(new BorderLayout());

	        //Create the log first, because the action listener
	        //needs to refer to it.
	        progressLog = new JTextArea(5,20);
	        progressLog.setMargin(new Insets(5,5,5,5));
	        progressLog.setEditable(false);
	        JScrollPane logScrollPane = new JScrollPane(progressLog);
	
		
		directory = new JFileChooser();
		directory.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		openButton = new JButton("Select Music catalog");
		openButton.addActionListener(this);

		
		startFile = new JFileChooser();
		selectStartButton = new JButton("Select Seed Music Piece");
		selectStartButton.addActionListener(this);
		
		selectStartButton.setEnabled(false);
		
		add(openButton, BorderLayout.PAGE_START);
		add(selectStartButton,BorderLayout.CENTER);
	
        add(logScrollPane, BorderLayout.AFTER_LAST_LINE);
 
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
		          int []orderedList = Ordering.Order(allMusicPieces,nrOfPieces,seedFileNr);
		          System.out.println("PlayList: ");
		          for (int i=0;i<nrOfPieces;i++)
				    System.out.println("File: " + allMusicPieces[orderedList[i]].getFile().getName()+ "," + allMusicPieces[orderedList[i]].getTonality() + "," + allMusicPieces[orderedList[i]].getMode()+ "," + allMusicPieces[orderedList[i]].getbpm());
      			} else
      				System.out.println("File not in list");
      		}			
		}

    }
}
