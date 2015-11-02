package main;

import java.io.File;

import main.MusicKey.Mode;
import main.MusicKey.Tonality;

public class MusicPiece {
	private static Tonality tonality;
	private static Mode mode;
	private static double bpm;
	private static File file;
	public MusicPiece(Tonality t, Mode m, double b, File f){
		tonality = t;
		mode = m;
		bpm = b;
		file = f;
	}
	
	public Tonality getTonality(){
		return tonality;
	}
	
	public Mode getMode(){
		return mode;
	}
	
	public double getbpm(){
		return bpm;
	}
	
	public File getFile(){
		return file;
	}

}
