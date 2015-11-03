package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class NoteHistogram {
	private static int [] noteHistogram;
    private static double [] noteArrayFrequencies = NoteHistogram.builtNoteArrayFrequencies((double)440);
	
	public NoteHistogram(double A4Frequency){

		noteHistogram = new int [108];
        noteArrayFrequencies = builtNoteArrayFrequencies(A4Frequency);
	}

	private static double[] builtNoteArrayFrequencies(double a4F) {
		/* build noteArray from c0 to B8 starting from a4F (typical 440Hz) */
		
		double [] noteArray = new double [108];
	    double halfStepMultiplier = 1.059463094359;
	
		noteArray [57] = a4F;
		for (int i=56;i>=0;i--){
			noteArray[i]=noteArray[i+1]/halfStepMultiplier;
		}
		for (int i=58;i<108;i++){
			noteArray[i]=noteArray[i-1]*halfStepMultiplier;
		}
		return noteArray;
	}

	public void addBinToNoteArrayIndex (float binSize,int binNumber) {
		/* does not yet take into account that bin could span several notes, defaults to the lowest, can be mitigated by reducing bin size */
		
		int noteIndex;
		double halfPoint;

		  if (binNumber == 0) {
		    noteIndex=0;
		  } else {
			noteIndex=1;  
	        halfPoint = noteArrayFrequencies[noteIndex]+(noteArrayFrequencies[noteIndex+1]-noteArrayFrequencies[noteIndex])/2;
		    while ((halfPoint < binSize*binNumber) && (noteIndex <106)){
			  noteIndex=noteIndex+1;
			  halfPoint = noteArrayFrequencies[noteIndex]+(noteArrayFrequencies[noteIndex+1]-noteArrayFrequencies[noteIndex])/2;  
		    }
		    if ((noteIndex == 106)&& (noteArrayFrequencies[107] < binSize*binNumber))
		    	noteIndex= 107;
	     }  
		 noteHistogram[noteIndex]++;
	}
	
	public double[] get12NoteHistogram(){
		int j = 4;
		double[] NoteArray = new double[12];
		for(int i = 0; i<107; i++){
			NoteArray[j] = NoteArray[j] + noteHistogram[i];
			j = (j+1) % 12;
		}
		return NoteArray;
	}
	public void debugPrintHistogram (){
		File plotFile = new File("c:/GianniProject/NoteHistogram.csv");
    	FileWriter myWriter;
		try {
				myWriter = new FileWriter(plotFile);
				PrintWriter myPW = new PrintWriter(myWriter);
				for ( int i=0;i<108;i++)
						myPW.println(i+","+noteHistogram[i]);	
				myWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}	
}
