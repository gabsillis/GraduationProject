package main;

public class NoteHistogram {

	
	public static double[] builtNoteArrayFrequencies(double a4Frequency) {
		/* build noteArray from c0 to B8 starting from a4Frequency (typical 440Hz) */
		
		double [] noteArray = new double [108];
	    double halfStepMultiplier = 1.059463094359;
	
		noteArray [57] = a4Frequency;
		for (int i=56;i>=0;i--){
			noteArray[i]=noteArray[i+1]/halfStepMultiplier;
		}
		for (int i=58;i<108;i++){
			noteArray[i]=noteArray[i-1]*halfStepMultiplier;
		}
		return noteArray;
		
	}
	public static int [] initNoteHistogram(){
		int [] noteHistogram = new int [108];
		for (int i=0;i<108;i++)
          noteHistogram[i]=0;
		return noteHistogram;
	}
	public static int binToNoteArrayIndex (float binSize,int binNumber, double [] noteArrayFrequencies) {
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
		    if ((noteIndex == 106)&& (noteArrayFrequencies[107]<binSize*binNumber))
		    	noteIndex= 107;
	     }  
		return noteIndex;
	}
}
