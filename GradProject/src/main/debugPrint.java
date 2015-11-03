package main;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class debugPrint {
	public static void printCSV (float fPlot[][][],int ch,int chunks){
		File plotFile = new File("c:/GianniProject/FrequencyPlot.csv");
    	FileWriter myWriter;
		try {
				myWriter = new FileWriter(plotFile);
				PrintWriter myPW = new PrintWriter(myWriter);
				for ( int i=0;i<ch;i++)
					for (int j=0;j<chunks;j++)
						myPW.println(fPlot[i][j][0] + "," + fPlot[i][j][1]);		
				myWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	public void printByteStream (byte byteStream[],int length){
		File plotFile = new File("c:/GianniProject/ByteStream.csv");
    	FileWriter myWriter;
		try {
				myWriter = new FileWriter(plotFile);
				PrintWriter myPW = new PrintWriter(myWriter);
				for ( int i=0;i<length;i++)
						myPW.println(byteStream[i]);		
				myWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	public static void printSampleChannel (int sample[][],int channel,int length){
		File plotFile = new File("c:/GianniProject/IndexChannel.csv");
    	FileWriter myWriter;
		try {
				myWriter = new FileWriter(plotFile);
				PrintWriter myPW = new PrintWriter(myWriter);
				for ( int i=0;i<length;i++)
						myPW.println(sample[channel][i]);		
				myWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}	
	public static void printResult (double prtRes[],int length,int chunkNr){
		File plotFile = new File("c:/GianniProject/ResultChunk.csv");
    	FileWriter myWriter;
		try {
				myWriter = new FileWriter(plotFile);
				PrintWriter myPW = new PrintWriter(myWriter);
				myPW.println(chunkNr);
				for ( int i=0;i<length;i++)
						myPW.println(Math.sqrt(prtRes[2*i]*prtRes[2*i]+prtRes[2*i+1]*prtRes[2*i+1]));		
				myWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}	

}
