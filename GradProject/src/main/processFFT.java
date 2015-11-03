package main;

public class processFFT {
	
	private static int getDominantFrequencyBinNr (double a[],int nrOfBins){
		int maxBin=0;
		double maxValue=0;
		double currentValue=0;
		for(int i=2;i<nrOfBins;i++){
			currentValue = Math.sqrt(a[2*i]*a[2*i]+a[2*i+1]*a[2*i+1]);
			if (currentValue > maxValue) {
				maxValue = currentValue;
				maxBin=i;
			}
		}
		return maxBin;
	}


	public static NoteHistogram processSamples (int [][] samples,int channels,int sampleIndex, int frameLength, int frameSize, float sampleRate) {

		NoteHistogram aNoteHistogram = new NoteHistogram(440);
        

		int fftChunkSize = 4096;
		int nrOfChunksPerChannel =  (int) Math.floor(sampleIndex/fftChunkSize);
		float binSize = (sampleRate/2)/(fftChunkSize/2);
        float [][][] frequencyPlot = new float [channels][nrOfChunksPerChannel][2]; //only used for debug print
        int dominantBinNr;
        int noteIndex;
		double[] fftInputReal = new double[fftChunkSize];
		double[] fftInputIm = new double[fftChunkSize];
		double[]fftResult;
		for (int i = 0; i < channels ; i++){ /* run through each channels */				
			for (int j=0; j < nrOfChunksPerChannel; j++){ /* run through all chunks */
				for(int k=0;k<fftChunkSize;k++){
						fftInputReal[k] = (double) samples[i][j*fftChunkSize+k];
						fftInputIm[k]=0;
				}
				    fftResult = fftBase.fft(fftInputReal,fftInputIm,true);
				    debugPrint.printResult(fftResult,fftChunkSize/2,j);
				    dominantBinNr = getDominantFrequencyBinNr(fftResult,fftChunkSize/2);
// 				    		
				    frequencyPlot[i][j][0]= Math.round(binSize*dominantBinNr + binSize/2) ; /* Calculate Middle Frequency of the dominant Bin */
      			    frequencyPlot[i][j][1]= Math.round(Math.sqrt(fftResult[2*dominantBinNr]*fftResult[2*dominantBinNr]+fftResult[2*dominantBinNr+1]*fftResult[2*dominantBinNr+1]));/*Store amplitude at dominant frequency */
//
      			    aNoteHistogram.addBinToNoteArrayIndex(binSize, dominantBinNr); 					
			}    
		}
		System.out.println("nr Of Channels : " + channels + "nr of chunks: " + nrOfChunksPerChannel);
//		debugPrint.printCSV(frequencyPlot,channels,nrOfChunksPerChannel);
		aNoteHistogram.debugPrintHistogram();     
        
        
        return aNoteHistogram;
	}
}
