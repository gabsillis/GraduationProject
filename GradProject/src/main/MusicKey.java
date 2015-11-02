package main;


public class MusicKey {
	private Tonality Tonality;
	private Mode Mode;
	
	public void setMode(Mode Mode){
		this.Mode = Mode;
	}
	
	public Mode getMode(){
		return this.Mode;
	}
	
	public void setTonality(Tonality Tonality){
		this.Tonality = Tonality;
	}
	
	public Tonality getTonality(){
		return this.Tonality;
	}
	
	public enum Mode{
		MAJOR,
		MINOR,
		NOTMODE, //for translating scale degree chords to modes when it is a diminished or augmented chord
		// This contains the two most common modes, further development could include many other modes such as
		//Ionian, Aeolian, Dorian, Locrian, Phrygian, Lydian, and Mixolydian also melodic, harmonic, and natural minors.
	}
	
	public enum Tonality{
		GSHARP(0.5),
		A(1.0),
		ASHARP(1.5),
		B(2.0),
		C(2.5),
		CSHARP(3.0),
		D(3.5),
		DSHARP(4.0),
		E(4.5),
		F(5.0),
		FSHARP(5.5),
		G(6.0);
		
		final Double baseDegree; // to set a base numerical degree for each note
		Tonality(Double baseDegree){
			this.baseDegree = baseDegree;
		}
	}
	
	
	public int TonalityToInt(Double input){
		return (int) (input*2);
	}

	Tonality doubleToTonality(Double input){
		input = input%7; // modulus with 7
		if(input == 0.5){
			return Tonality.GSHARP;
		} else if(input == 1){
			return Tonality.A;
		} else if(input == 1.5){
			return Tonality.ASHARP;
		} else if(input == 2){
			return Tonality.B;
		} else if(input == 2.5){
			return Tonality.C;
		} else if(input == 3){
			return Tonality.CSHARP;
		} else if(input == 3.5){
			return Tonality.D;
		} else if(input == 4){
			return Tonality.DSHARP;
		} else if(input == 4.5){
			return Tonality.E;
		}else if(input == 5){
			return Tonality.F;
		} else if(input == 5.5){
			return Tonality.FSHARP;
		} else if(input == 6){
			return Tonality.G;
		}
		// a default that hopefully never gets used but to stop compiler errors
		return Tonality.A;
		
	}
	
	
	public Tonality getSupertonic(){
		if(getMode() == Mode.MAJOR){
			return doubleToTonality(getTonality().baseDegree +1);
		} else{
			return doubleToTonality(getTonality().baseDegree +1);
		}
	}
	
	public Tonality getMediant(){
		if(getMode() == Mode.MAJOR){
			return doubleToTonality(getTonality().baseDegree +2);
		} else{
			return doubleToTonality(getTonality().baseDegree +1.5);
		}
	}
	
	public Tonality getSubdominant(){
		if(getMode() == Mode.MAJOR){
			return doubleToTonality(getTonality().baseDegree +2.5);
		} else{
			return doubleToTonality(getTonality().baseDegree +2.5);
		}
	}
	
	public Tonality getDominant(){
		if(getMode() == Mode.MAJOR){
			return doubleToTonality(getTonality().baseDegree +3.5);
		} else{
			return doubleToTonality(getTonality().baseDegree +3.5);
		}
	}
	
	public Tonality getSubmediant(){
		if(getMode() == Mode.MAJOR){
			return doubleToTonality(getTonality().baseDegree +4.5);
		} else{
			return doubleToTonality(getTonality().baseDegree +4);
		}
	}
	
	public Tonality getLeadingTone(){
		if(getMode() == Mode.MAJOR){
			return doubleToTonality(getTonality().baseDegree +5.5);
		} else{
			return doubleToTonality(getTonality().baseDegree +5.5);
		}
	}
	
	public Mode getSupertonicMode(){
		if(getMode() == Mode.MAJOR){
			return Mode.MINOR;
		} else {
			return Mode.NOTMODE;
		}
	}
	
	public Mode getMediantMode(){
		if(getMode() == Mode.MAJOR){
			return Mode.MINOR;
		} else {
			return Mode.MAJOR;
		}
	}
	
	public Mode getSubdominantMode(){
		if(getMode() == Mode.MAJOR){
			return Mode.MAJOR;
		} else{
			return Mode.MINOR;
		}
	}
	
	public Mode getDominantMode(){
		if(getMode() == Mode.MAJOR){
			return Mode.MAJOR;
		} else {
			return Mode.MINOR; // This one will need to be handled as a special case because the Major V uses a leading tone which provides a stronger dominant
		}
	}
	
	public Mode getSubmediantMode(){
		if(getMode() == Mode.MAJOR){
			return Mode.MINOR;
		} else {
			return Mode.MAJOR;
		}
	}
	
	public Mode getLeadingToneMode(){
		if(getMode() == Mode.MAJOR){
			return Mode.NOTMODE;
		} else {
			return Mode.MAJOR;
		}
	}
}
