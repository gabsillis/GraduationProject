package main;
<<<<<<< HEAD

=======
>>>>>>> origin/master

public class bpm {
	final static double BEATTHRESHOLD = 1.35;
		
	public static double getBPM(int[][] samples,int channels,int frameLength,int frameSize,float sampleRate){

		double Average = 0.0;
		double bpm = 0.0;
		for(int k=0; k< channels; k++){
			Average = 0.0;
			for(int i=0;i<frameLength; i++){
				Average = Average + samples[k][i];
			}
			Average = Average/frameLength;
			double absoluteAverage = Average;

			for(int j=0;j<60*sampleRate/1024;j = j+1024){// for 1 min of music
				if(samples[k][j] > absoluteAverage*BEATTHRESHOLD){
					bpm = bpm+1;
				}
			}
		}
		return bpm/channels; // because in the channels for loop i keep adding to the bpm of previous channel
	}
}
