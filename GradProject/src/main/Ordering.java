package main;


public class Ordering {
	
	
	
	public static int [] Order(MusicPiece [] allMP,int nrOfPieces,int seedPiece){
		
		int [] orderedList= new int[nrOfPieces]; //index 0 is pieceNr, index 1 is correlation Value


		double greatestCorrelationValue =0,currentCorrelation=0;
		int greatestCorrelationIndex;
		for (int i=0;i<nrOfPieces;i++){
		    orderedList[i]=i;
		}    
		orderedList[0]=seedPiece;
		orderedList[seedPiece]=0;
        int currentSeed = 0; 
		while (currentSeed < nrOfPieces -1 ){
			greatestCorrelationValue = 0;
			greatestCorrelationIndex = 0;
			for(int i=currentSeed+1;i<nrOfPieces;i++){
				MusicKey key1 = new MusicKey();
				key1.setMode(allMP[orderedList[currentSeed]].getMode());
				MusicKey key2 = new MusicKey();
				key2.setMode(allMP[orderedList[i]].getMode());
				currentCorrelation=bpmCorrelation.getCorrelation(allMP[orderedList[currentSeed]].getbpm(), allMP[i].getbpm())*
		    		                KeyCorrelation.getCorrelation(key1, allMP[orderedList[currentSeed]].getTonality(), key2, allMP[orderedList[i]].getTonality());
                if (currentCorrelation > greatestCorrelationValue){ 
                   greatestCorrelationValue = currentCorrelation;
                   greatestCorrelationIndex = i;
                }   
			}
			int swap;
			swap = orderedList[currentSeed +1];
			orderedList[currentSeed+1]=orderedList[greatestCorrelationIndex];
			orderedList[greatestCorrelationIndex]=swap;
			currentSeed++;
		}	
		return orderedList;    
	}

}
