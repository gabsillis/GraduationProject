package main;

public class bpm {
	final static double BEATTHRESHOLD = 1.35;
		
	public static double getBPM(int[][] samples,int channels,int frameLength,int frameSize,float sampleRate){

		double Average = 0.0;
		double bpm = 0.0;
		for(int k=0; k< channels; k++){
			Average = 0.0;
			for(int i=0;i<samples[k].length; i++){
				Average = Average + samples[k][i];
			}
			Average = Average/(samples[k].length);

			for(int j=0;j<samples[k].length;j = j+2){
				if(samples[k][j] > Average*BEATTHRESHOLD){
					bpm = bpm+1;
				}
			}
		}
		double totalTime = samples[0].length/sampleRate*60;
		return bpm/(totalTime*channels); // because in the channels for loop i keep adding to the bpm of previous channel
	}
}
