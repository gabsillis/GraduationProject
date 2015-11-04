package main;

public class bpm {
	final static double BEATTHRESHOLD = 1.35;
		
	public static double getBPM(int[][] samples,int channels,int frameLength,int frameSize,float sampleRate){

		double highAverage = 0.0;
		double lowAverage = 0.0;
		// only doing for one channel for now
		for(int i=0;i<frameLength; i=i+2){
			highAverage = highAverage+samples[0][i];
			lowAverage = lowAverage+samples[0][i+1];
		}
		highAverage = highAverage/(frameLength*frameSize);
		lowAverage = lowAverage/(frameLength*frameSize);
		double absoluteAverage = (Math.abs(highAverage)+Math.abs(lowAverage))/2;
		double bpm = 0.0;
		for(int j=0;j<60*sampleRate/1024;j = j+1024){// for 1 min of music
			if((Math.abs(samples[0][j])+Math.abs(samples[0][j]))/2 > absoluteAverage*BEATTHRESHOLD){
				bpm = bpm+1;
			}
		}
		return bpm;
	}
}
