package main;

public class bpmCorrelation {
	public static double getCorrelation(double bpm1, double bpm2){
		if(bpm1 == bpm2){
			return 1;
		} else {
			return 1/Math.pow(bpm1-bpm2,2);
		}
	}
}
