package main;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import main.MusicKey.Mode;
import main.MusicKey.Tonality;


public class MusicPiece {
	private Tonality tonality;
	private Mode mode;
	private double beatsPerMinute;
	private File file;
	private boolean isValid;
	
 	public MusicPiece(File f){
		// file constructor referenced by f
		file = f;
		int frameLength,frameSize,bitsPerSample,channels,sampleIndex;
		int [][] samples;
		float sampleRate;
		// create AudioInputStream from f
		AudioInputStream aio = null;
		try {
			// read audiofile metadata
			aio = AudioSystem.getAudioInputStream(f);
			frameLength = (int) aio.getFrameLength();
			frameSize= aio.getFormat().getFrameSize();
			sampleRate = aio.getFormat().getSampleRate();
			bitsPerSample = aio.getFormat().getSampleSizeInBits();
			channels = aio.getFormat().getChannels();
			
			boolean isBigEndian = aio.getFormat().isBigEndian();
			byte[] eightBitByteArray = new byte[frameLength * frameSize];
			int bytesRead = aio.read(eightBitByteArray);
			// turn bytestream in 16 bit values
			samples = new int[channels][frameLength * frameSize];
			sampleIndex = 0;
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
			beatsPerMinute = main.bpm.getBPM(samples,channels,frameLength,frameSize,sampleRate);
			NoteHistogram aNoteHistogram = main.processFFT.processSamples(samples,channels,sampleIndex,frameLength,frameSize,sampleRate);
			pitchHistogram pH = new pitchHistogram(aNoteHistogram.get12NoteHistogram());
			tonality = pH.bestCorrelationTonality();
			mode = pH.bestCorrelationMode();
			System.out.println("beats per minute" + beatsPerMinute);
			System.out.println("tonality "+ tonality);
			System.out.println("mode " + mode);
		    isValid=true;
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			isValid= false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
		    isValid=false;
		}
	}
 	
	public boolean isValid(){
		return isValid;
	}
	public Tonality getTonality(){
		return tonality;
	}
	
	public Mode getMode(){
		return mode;
	}
	
	public double getbpm(){
		return beatsPerMinute;
	}
	
	public File getFile(){
		return file;
	}

}
