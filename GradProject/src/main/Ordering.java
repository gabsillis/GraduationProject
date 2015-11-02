package main;


public class Ordering {
	
	private void arraySwapToFront(MusicPiece[] array, int index){
		MusicPiece temp = array[0];
		array[0] = array[index];
		array[index] = temp;
	}
	
	private static MusicPiece[] ordered;
	
	
	
	Ordering(MusicPiece[] musicPieces){
		ordered = new MusicPiece[musicPieces.length];
		ordered[0] = musicPieces[0];
		int length = musicPieces.length;
		for(int i=musicPieces.length-1;i>0;i--){
			
			MusicKey key1 = new MusicKey();
			key1.setMode(musicPieces[0].getMode());
			MusicKey key2 = new MusicKey();
			key2.setMode(musicPieces[1].getMode());
			
			MusicPiece greatest = musicPieces[1];
			double GreatestCorrelation = bpmCorrelation.getCorrelation(musicPieces[0].getbpm(), musicPieces[1].getbpm())*KeyCorrelation.getCorrelation(key1, musicPieces[0].getTonality(), key2, musicPieces[1].getTonality());
			for(int j=2; j<=i;j++){
				key2.setMode(musicPieces[j].getMode());
				
				double Correlation = bpmCorrelation.getCorrelation(musicPieces[0].getbpm(), musicPieces[j].getbpm())*KeyCorrelation.getCorrelation(key1, musicPieces[0].getTonality(), key2, musicPieces[j].getTonality());
				if(Correlation > GreatestCorrelation){
					greatest = musicPieces[j];
					GreatestCorrelation = Correlation;
				}
			}
		}
	}

}
