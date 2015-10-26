import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.media.*;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.filechooser.*;

import org.jtransforms.dct.*;
import org.jtransforms.fft.DoubleFFT_1D;
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
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == open) {
			int returnVal = fc.showOpenDialog(getSamples.this);
			
			if(returnVal == JFileChooser.APPROVE_OPTION){
				File file = fc.getSelectedFile();
				try {
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
					int frameLength = (int) audioInputStream.getFrameLength();
					int frameSize = (int) audioInputStream.getFormat().getFrameSize();
					byte[] eightBitByteArray = new byte[frameLength * frameSize];
					int result = audioInputStream.read(eightBitByteArray);
					int channels = audioInputStream.getFormat().getChannels();
					int[][] samples = new int[channels][frameLength * frameSize];
					int indexFrameLength = frameLength/(2*channels);
					int indexFrameSize = frameSize/(2*channels);	
					DoubleFFT_1D DFTransformer = new DoubleFFT_1D(indexFrameSize);
					
//					double[][] transformValues = new double[channels][(int) Math.floor(frameLength)];
					/* split wav byte sequence in 16 bit streams per channel */
					int sampleIndex = 0;
					for(int t=0; t < eightBitByteArray.length;) {
						for(int channel = 0; channel < channels; channel++){
							int low = (int) eightBitByteArray[t];
							t++;
							int high = (int) eightBitByteArray[t];
							t++;
							int sample = getSixteenBitSample(high,low);
							samples[channel][sampleIndex] = sample;
						}
						sampleIndex++;
					}
					/* sampleIndex is nr of 16 bit entries per channel */
					int fftChunk = 64;
	
					for (int i = 0; i < channels ; i++){ /* run through each channels */
						for (int j=0; j < indexFrameLength; j=j+fftChunk){ /*process all sample frames per channel */		
							double[] a = new double[fftChunk*2];
							for(int k=0;k<fftChunk;k++){
//								a[k] = eightBitByteArray[j+k];
         						a[k] = (double) samples[i][j+k];
							//	a[2*k+1] = 0;
							}
	  					DFTransformer.realForwardFull(a);
							for(int k=0;k<fftChunk;k++){
								System.out.println(sqrt(a[2*k]*a[2*k]+a[2*k+1]*a[2*k+1]));
//								System.out.println(a[2*k+1]);
							}
							System.out.println("end of chunk " + j);
							}
					}
				} catch (UnsupportedAudioFileException | IOException e1) {
					System.out.println("please use supported audio types");
					e1.printStackTrace();
				}
				
			}
		}
		
	}
	
}
