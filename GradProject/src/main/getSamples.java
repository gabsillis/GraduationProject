package main;
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

import org.jtransforms.dct.*;
import org.jtransforms.fft.DoubleFFT_1D;
import org.jtransforms.fft.DoubleFFT_2D;

import static java.lang.Math.sqrt;



public class getSamples extends JPanel implements ActionListener{
//	static private final String newline = "\n";
//	static private final int FOURIER_ARRAY_LENGTH = 16;
	
	JButton open, save;
	JTextArea log;
	JFileChooser fc;
	
	public static double[] copyFromIntArray(int[] source) {
	    double[] dest = new double[source.length];
	    for(int i=0; i<source.length; i++) {
	        dest[i] = source[i];
	    }
	    return dest;
	}
	
	public getSamples() {
		fc = new JFileChooser();
		open = new JButton("open a song ...");
		open.addActionListener(this);
	}
	
	protected int getSixteenBitSample(int high, int low) {
	    return ((high << 8) + (low & 0x00ff)) ;
	} 
	public int getDominantFrequencyBinNr (double a[],int nrOfBins){
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

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == open) {
			int returnVal = fc.showOpenDialog(getSamples.this);
			
			if(returnVal == JFileChooser.APPROVE_OPTION){
				File file = fc.getSelectedFile();
				try {
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
					int frameLength = (int) audioInputStream.getFrameLength();
					int frameSize = (int) audioInputStream.getFormat().getFrameSize();
					float sampleRate = audioInputStream.getFormat().getSampleRate();
					int bitsPerSample = audioInputStream.getFormat().getSampleSizeInBits();
					boolean isBigEndian = audioInputStream.getFormat().isBigEndian();
					byte[] eightBitByteArray = new byte[frameLength * frameSize];
					int bytesRead = audioInputStream.read(eightBitByteArray);
					int channels = audioInputStream.getFormat().getChannels();
					int[][] samples = new int[channels][frameLength * frameSize];
//					DoubleFFT_1D DFTransformer = new DoubleFFT_1D(indexFrameSize);
//					printByteStream(eightBitByteArray,eightBitByteArray.length);
					/* split wav byte sequence in 16 bit streams per channel */
					int sampleIndex = 0;
					int sample;
					byte [] makeInt = new byte [4];
					for(int t=0; t < bytesRead;t=t+2) {
						for(int channel = 0; channel < channels; channel++){
							if (isBigEndian) {
								makeInt[0]=0;
								makeInt[1]=0;
								makeInt[2]= eightBitByteArray[t];
								makeInt[3]=eightBitByteArray[t+1];
								sample = ByteBuffer.wrap(makeInt).order(ByteOrder.BIG_ENDIAN).getInt();
							}else {
								makeInt[0]= eightBitByteArray[t];
								makeInt[1]=eightBitByteArray[t+1];
								makeInt[2]= 0;
								makeInt[3]=0;
								sample = ByteBuffer.wrap(makeInt).order(ByteOrder.LITTLE_ENDIAN).getInt();
		    				}
							samples[channel][sampleIndex] = sample;
						}
						sampleIndex++;
					}
					/* sampleIndex is nr of 16 bit entries per channel */
//					debugPrint.printSampleChannel(samples,0,sampleIndex);
					int fftChunkSize = 4096;
					int nrOfChunksPerChannel =  (int) Math.floor(sampleIndex/fftChunkSize);
					float binSize = (sampleRate/2)/(fftChunkSize/2);
	                float [][][] frequencyPlot = new float [channels][nrOfChunksPerChannel][2];
	                int dominantBinNr;
	                double [] noteArrayFrequencies = NoteHistogram.builtNoteArrayFrequencies((double)440);
	                int [] noteHistogram = NoteHistogram.initNoteHistogram();
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
	  					    System.out.println(dominantBinNr);
	  					    frequencyPlot[i][j][0]= Math.round(binSize*dominantBinNr + binSize/2) ; /* Calculate Middle Frequency of the dominant Bin */
	  					    frequencyPlot[i][j][1]= Math.round(Math.sqrt(fftResult[2*dominantBinNr]*fftResult[2*dominantBinNr]+fftResult[2*dominantBinNr+1]*fftResult[2*dominantBinNr+1]));/*Store amplitude at dominant frequency */
	  					    noteIndex = NoteHistogram.binToNoteArrayIndex(binSize, dominantBinNr, noteArrayFrequencies);
	  					    noteHistogram[noteIndex]++;
						}    
					}
					System.out.println("nr Of Channels : " + channels + "nr of chunks: " + nrOfChunksPerChannel);
					debugPrint.printCSV(frequencyPlot,channels,nrOfChunksPerChannel);
					debugPrint.printHistogram(noteHistogram);
					
			}catch (UnsupportedAudioFileException | IOException e1) {
					System.out.println("please use supported audio types");
					e1.printStackTrace();
			}
				
			}
		}
    }
}
