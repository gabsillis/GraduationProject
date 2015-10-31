package main;
import java.util.Arrays;
import main.MusicKey.Tonality;
import main.MusicKey.Mode;



public class KeyCorrelation {
	// Correlations_[Base Mode]_[Final_Mode]_[tonalityDifference]
	// Base Mode and Final Mode: 0 = major; 1 = minor
	// Tonality Difference: based on integer tonality values, (the double values *2), is the difference in baseDegree*2 look at MusicKey.getSupertonic or MusicKey.getSubdominant etc
	// Many tonality differences are left out because they are irregular music theory wise, the machine learning will take care of any irregular differences that work well	
		public static final double[][][] Correlations = new double[2][2][12];
		
		static{
			Correlations[0][0][0] = 1.0;
			Correlations[0][1][0] = 0.9;
			Correlations[0][0][2] = 0.2;
			Correlations[0][1][2] = 0.25;
			Correlations[0][0][4] = 0.1;
			Correlations[0][1][4] = 0.5;
			Correlations[0][0][5] = 0.85;
			Correlations[0][1][5] = 0.55;
			Correlations[0][0][7] = 0.85;
			Correlations[0][1][7] = 0.7;
			Correlations[0][0][9] = 0.25;
			Correlations[0][1][9] = 0.9;
			Correlations[0][0][11] = 0.5;
			Correlations[0][1][11] = 0.3;
			Correlations[0][0][1] = 0.3;
			Correlations[0][0][10] = 0.75;
			Correlations[0][0][8] = 0.75;
			Correlations[0][0][3] = 0.75;
			
			Correlations[1][0][0] = 0.9;
			Correlations[1][1][0] = 1.0;
			Correlations[1][0][2] = 0.1;
			Correlations[1][0][2] = 0.25;
			Correlations[1][0][4] = 0.8;
			Correlations[1][1][4] = 0.2;
			Correlations[1][0][5] = 0.55;
			Correlations[1][1][5] = 0.75;
			Correlations[1][0][7] = 0.85;
			Correlations[1][1][7] = 0.75;
			Correlations[1][0][9] = 0.75;
			Correlations[1][1][9] = 0.9;
			Correlations[1][0][11] = 0.5;
			Correlations[1][1][11] = 0.5;
			Correlations[1][0][1] = 0.3;
		}
		

		
		
	public double getCorrelation(MusicKey key1, Tonality Tonality_key1, MusicKey key2, Tonality Tonality_key2){
		key1.setTonality(Tonality_key1);
		key2.setTonality(Tonality_key2);
		
		Mode mode1 = key1.getMode();
		Mode mode2 = key2.getMode();
		if(mode1 == Mode.MAJOR){
			// Same Key
			if(key2.getTonality() == key1.getTonality()){
				if(mode2 == mode1){
					return Correlations[0][0][0];
				} else {
					return Correlations[0][1][0];
				}
				
			}
			// Key2 is SuperTonic
			if(key2.getTonality() == key1.getSupertonic()){
				if(mode2 == mode1){
					return Correlations[0][0][2];
				} else {
					return Correlations[0][1][2];
				}
			} 
			// Key2 is Mediant
			if(key2.getTonality() == key1.getMediant()){
				if(mode2 == mode1){
					return Correlations[0][0][4];
				} else {
					return Correlations[0][1][4];
				}
			}
			// Key2 is Subdominant
			if(key2.getTonality() == key1.getSubdominant()){
				if(mode2 == mode1){
					return Correlations[0][0][5];
				} else {
					return Correlations[0][1][5];
				}
			}
			// key2 is Dominant
			if(key2.getTonality() == key1.getDominant()){
				if(mode2 == mode1){
					return Correlations[0][0][7];
				} else {
					return Correlations[0][1][7];
				}
			}
			// key2 is Submediant
			if(key2.getTonality() == key1.getSubmediant()){
				if(mode2 == mode1){
					return Correlations[0][0][9];
				} else {
					return Correlations[0][1][9];
				}
			}
			//key2 is Leading tone
			if(key2.getTonality() == key1.getLeadingTone()){
				if(mode2 == mode1){
					return Correlations[0][0][11];
				} else {
					return Correlations[0][1][11];
				}
			}
			// Special Cases Major
			// Neopolitan
			if(key2.getTonality() == key1.doubleToTonality(key1.getTonality().baseDegree +0.5) && mode2 == Mode.MAJOR){
				return Correlations[0][0][1];
			}
			// flat major VI
			if(key2.getTonality() == key1.doubleToTonality(key1.getTonality().baseDegree +5.0) && mode2 == Mode.MAJOR){
				return Correlations[0][0][10];
			}
			// flat major VII
			if(key2.getTonality() == key1.doubleToTonality(key1.getTonality().baseDegree +4.0) && mode2 == Mode.MAJOR){
				return Correlations[0][0][8];
			}
			// flat major III
			if(key2.getTonality() == key1.doubleToTonality(key1.getTonality().baseDegree +1.5) && mode2 == Mode.MAJOR){
				return Correlations[0][0][3];
			}
			
			
		} else if(mode1 == Mode.MINOR){
			// Same Key
						if(key2.getTonality() == key1.getTonality()){
							if(mode2 == mode1){
								return Correlations[1][0][0];
							} else {
								return Correlations[1][1][0];
							}
							
						}
						// Key2 is SuperTonic
						if(key2.getTonality() == key1.getSupertonic()){
							if(mode2 == mode1){
								return Correlations[1][0][2];
							} else {
								return Correlations[1][1][2];
							}
						} 
						// Key2 is Mediant
						if(key2.getTonality() == key1.getMediant()){
							if(mode2 == mode1){
								return Correlations[1][0][4];
							} else {
								return Correlations[1][1][4];
							}
						}
						// Key2 is Subdominant
						if(key2.getTonality() == key1.getSubdominant()){
							if(mode2 == mode1){
								return Correlations[1][0][5];
							} else {
								return Correlations[1][1][5];
							}
						}
						// key2 is Dominant
						if(key2.getTonality() == key1.getDominant()){
							if(mode2 == mode1){
								return Correlations[1][0][7];
							} else {
								return Correlations[1][1][7];
							}
						}
						// key2 is Submediant
						if(key2.getTonality() == key1.getSubmediant()){
							if(mode2 == mode1){
								return Correlations[1][0][9];
							} else {
								return Correlations[1][1][9];
							}
						}
						//key2 is Leading tone
						if(key2.getTonality() == key1.getLeadingTone()){
							if(mode2 == mode1){
								return Correlations[1][0][11];
							} else {
								return Correlations[1][1][11];
							}
						}
						// Special Cases Minor
						// Neopolitan
						if(key2.getTonality() == key1.doubleToTonality(key1.getTonality().baseDegree +0.5) && mode2 == Mode.MAJOR){
							return Correlations[1][0][1];
						}
						if(key2.getTonality() == key1.doubleToTonality(key1.getTonality().baseDegree +0.5) && mode2 == Mode.MAJOR){
							return Correlations[1][0][1];
						}
		}
		return 0;
		
	}

}
