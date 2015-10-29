package main;

import main.MusicKey.Mode;
import main.MusicKey.Tonality;

public class pitchHistogram {
	private static double[] HISTOGRAMVALUES = new double[12];
	public pitchHistogram(double[] HISTOGRAM_VALUES){
		HISTOGRAMVALUES = HISTOGRAM_VALUES;
	}

	// starts at Gsharp
	
	
	 public enum majorProfile{
		 // from rnhart.net/articles/key-finding/
		 DO(6.35),
		 DOSharp(2.23),
		 RE(3.48),
		 RESharp(2.33),
		 MI(4.38),
		 FA(4.09),
		 FASharp(2.52),
		 SO(5.19),
		 SOSharp(2.39),
		 LA(3.66),
		 LASharp(2.29),
		 TI(2.88);
		 
		 final Double MajorWeighting;
		 majorProfile(Double MajorWeighting){
			 this.MajorWeighting = MajorWeighting;
		 }
		 
	 }
	 public enum minorProfile{
		 // from rnhart.net/articles/key-finding/
		 DO(5.38),
		 DOSharp(2.60),
		 RE(3.53),
		 RESharp(2.54),
		 MI(4.75),
		 FA(3.98),
		 FASharp(2.69),
		 SO(3.34),
		 SOSharp(3.17),
		 LA(6.33),
		 LASharp(2.68),
		 TI(3.52);
		 
		 final Double MinorWeighting;
		 minorProfile(Double MinorWeighting){
			 this.MinorWeighting = MinorWeighting;
		 }
	 }
		 
		 public double KrumhanslShmuckler(
				 // note names gs = g sharp for input from histogram
				 double gs,
				 double a,
				 double as,
				 double b,
				 double c,
				 double cs,
				 double d,
				 double ds,
				 double e,
				 double f,
				 double fs, 
				 double g,
				 // an input tonality to determine how x and y lines up see rnhart.net/articles/key-finding
				 Tonality tonality,
				 // an input mode to test for, determines which profile is used
				 Mode mode)
		 {
			 double[][] processingArray = new double[12][2]; // processingArray consists of 12 entries of an x,y pair, processingArray[n][1] is the x value which is the pitch profile
			 if(mode == Mode.MAJOR){
				 processingArray[0][1] = majorProfile.DO.MajorWeighting;
				 processingArray[1][1] = majorProfile.DOSharp.MajorWeighting;
				 processingArray[2][1] = majorProfile.RE.MajorWeighting;
				 processingArray[3][1] = majorProfile.RESharp.MajorWeighting;
				 processingArray[4][1] = majorProfile.MI.MajorWeighting;
				 processingArray[5][1] = majorProfile.FA.MajorWeighting;
				 processingArray[6][1] = majorProfile.FASharp.MajorWeighting;
				 processingArray[7][1] = majorProfile.SO.MajorWeighting;
				 processingArray[8][1] = majorProfile.SOSharp.MajorWeighting;
				 processingArray[9][1] = majorProfile.LA.MajorWeighting;
				 processingArray[10][1] = majorProfile.LASharp.MajorWeighting;
				 processingArray[11][1] = majorProfile.TI.MajorWeighting;
			 } else {
				 // if mode == Mode.MINOR
				 processingArray[0][1] = minorProfile.DO.MinorWeighting;
				 processingArray[1][1] = minorProfile.DOSharp.MinorWeighting;
				 processingArray[2][1] = minorProfile.RE.MinorWeighting;
				 processingArray[3][1] = minorProfile.RESharp.MinorWeighting;
				 processingArray[4][1] = minorProfile.MI.MinorWeighting;
				 processingArray[5][1] = minorProfile.FA.MinorWeighting;
				 processingArray[6][1] = minorProfile.FASharp.MinorWeighting;
				 processingArray[7][1] = minorProfile.SO.MinorWeighting;
				 processingArray[8][1] = minorProfile.SOSharp.MinorWeighting;
				 processingArray[9][1] = minorProfile.LA.MinorWeighting;
				 processingArray[10][1] = minorProfile.LASharp.MinorWeighting;
				 processingArray[11][1] = minorProfile.TI.MinorWeighting;
			 }
			 switch(tonality){
			 
			 case GSHARP:
				 for(int i = 0; i<12; i++){
					 if(i<12){
						 // this is redundant for this case but not others
						 // it is also redundant for this case but not others
						 // this comment is a redundant comment that this if statement is redundant for this case but not others
						 processingArray[i][2] = HISTOGRAMVALUES[i];
					 } else {
						 processingArray[(i-12)][2] = HISTOGRAMVALUES[(i-12)];
					 }
				 }
				 break;
				 
			 case A:
				 int j = 0; // a counter
				 for(int i = 1; i<13; i++){
					 if(i<12){
						 processingArray[i][2] = HISTOGRAMVALUES[j];
						 j++;
					 } else {
						 processingArray[(i-12)][2] = HISTOGRAMVALUES[(j)];
						 j++;
					 }
				 }
				 break;
				 
			 case ASHARP:
				 int k = 0; // a counter
				 for(int i = 2; i<14; i++){
					 if(i<12){
						 processingArray[i][2] = HISTOGRAMVALUES[k];
						 k++;
					 } else {
						 processingArray[(i-12)][2] = HISTOGRAMVALUES[(k)];
						 k++;
					 }
				 }
				 break;
				 
			 case B:
				 int l = 0;
				 for(int i = 3; i<15; i++){
					 if(i<12){
						 processingArray[i][2] = HISTOGRAMVALUES[l];
						 l++;
					 } else {
						 processingArray[(i-12)][2] = HISTOGRAMVALUES[(l)];
						 l++;
					 }
				 }
				 break;
				 
			 case C:
				 int m = 0; // a counter
				 for(int i = 4; i<16; i++){
					 if(i<12){
						 processingArray[i][2] = HISTOGRAMVALUES[m];
						 m++;
					 } else {
						 processingArray[(i-12)][2] = HISTOGRAMVALUES[(m)];
						 m++;
					 }
				 }
				 break;
				 
			 case CSHARP:
				 int n = 0; // a counter
				 for(int i = 5; i<17; i++){
					 if(i<12){
						 processingArray[i][2] = HISTOGRAMVALUES[n];
						 n++;
					 } else {
						 processingArray[(i-12)][2] = HISTOGRAMVALUES[(n)];
						 n++;
					 }
				 }
				 break;
				 
			 case D:
				 int o = 0; // a counter
				 for(int i = 6; i<18; i++){
					 if(i<12){
						 processingArray[i][2] = HISTOGRAMVALUES[o];
						 o++;
					 } else {
						 processingArray[(i-12)][2] = HISTOGRAMVALUES[(o)];
						 o++;
					 }
				 }
				 break;
				 
			 case DSHARP:
				 int p = 0; // a counter
				 for(int i = 7; i<19; i++){
					 if(i<12){
						 processingArray[i][2] = HISTOGRAMVALUES[p];
						 p++;
					 } else {
						 processingArray[(i-12)][2] = HISTOGRAMVALUES[(p)];
						 p++;
					 }
				 }
				 break;
				 
			 case E:
				 int q = 0; // a counter
				 for(int i = 8; i<20; i++){
					 if(i<12){
						 processingArray[i][2] = HISTOGRAMVALUES[q];
						 q++;
					 } else {
						 processingArray[(i-12)][2] = HISTOGRAMVALUES[(q)];
						 q++;
					 }
				 }
				 break;
				 
			 case F:
				 int r = 0; // a counter
				 for(int i = 9; i<21; i++){
					 if(i<12){
						 processingArray[i][2] = HISTOGRAMVALUES[r];
						 r++;
					 } else {
						 processingArray[(i-12)][2] = HISTOGRAMVALUES[(r)];
						 r++;
					 }
				 }
				 break;
				 
			 case FSHARP:
				 int s = 0; // a counter
				 for(int i = 10; i<22; i++){
					 if(i<12){
						 processingArray[i][2] = HISTOGRAMVALUES[s];
						 s++;
					 } else {
						 processingArray[(i-12)][2] = HISTOGRAMVALUES[(s)];
						 s++;
					 }
				 }
				 break;
				 
			 case G:
				 int t = 0; // a counter
				 for(int i = 11; i<23; i++){
					 if(i<12){
						 processingArray[i][2] = HISTOGRAMVALUES[t];
						 t++;
					 } else {
						 processingArray[(i-12)][2] = HISTOGRAMVALUES[(t)];
						 t++;
					 }
				 }
				 break;				 
			 }
			 
			 // end filling array portion
			 
			 // create the means for the x and y columns
			 double xmean = 0.0;
			 double ymean = 0.0;
			 double sumx = 0.0;
			 double sumy = 0.0;
			 for(int i=0;i<12;i++){
				 sumx = sumx + processingArray[i][1];
				 sumy = sumy + processingArray[i][2];
			 }
			 xmean = sumx/12;
			 ymean = sumy/12;
			 // end mean creation
			 
			 // compute the R value
			 //            SUM[(x-xmean)(y-ymean)]
			 // R = --------------------------------------
			 //      Sqrt[SUM(x-xmean)^2*SUM(y-ymean)^2]
			 double top = 0.0;
			 double bottom = 0.0;
			 double bottomx = 0.0;
			 double bottomy = 0.0;
			 
			 for(int k = 0; k < 12; k++){
				 double xdiff = processingArray[k][1]-xmean;
				 double ydiff = processingArray[k][2]-ymean;
				 top = top+((xdiff)*(ydiff));
				 bottomx = bottomx + Math.pow(xdiff, 2);
				 bottomy = bottomy + Math.pow(ydiff, 2);
			 }
			 bottom = Math.sqrt(bottomx*bottomy);
		return top/bottom;
		// boom: mind = blown
		 }
	 
	 
	 public Tonality bestMajorCorrelation(){
		 Tonality highest = Tonality.GSHARP;
		 Tonality[] allTonalities = {MusicKey.Tonality.GSHARP, MusicKey.Tonality.A,MusicKey.Tonality.ASHARP,MusicKey.Tonality.B,MusicKey.Tonality.C,MusicKey.Tonality.D,
		                             MusicKey.Tonality.DSHARP, MusicKey.Tonality.E,MusicKey.Tonality.F,MusicKey.Tonality.FSHARP,MusicKey.Tonality.G};
		 for(int i=1;i<12;i++){
			 	if(
			 			
			 			KrumhanslShmuckler(HISTOGRAMVALUES[0],HISTOGRAMVALUES[1],HISTOGRAMVALUES[2],HISTOGRAMVALUES[3],HISTOGRAMVALUES[4],HISTOGRAMVALUES[5],HISTOGRAMVALUES[6],HISTOGRAMVALUES[7],HISTOGRAMVALUES[8],HISTOGRAMVALUES[9],HISTOGRAMVALUES[10],HISTOGRAMVALUES[11],allTonalities[i],Mode.MAJOR)
			 			>
			 			KrumhanslShmuckler(HISTOGRAMVALUES[0],HISTOGRAMVALUES[1],HISTOGRAMVALUES[2],HISTOGRAMVALUES[3],HISTOGRAMVALUES[4],HISTOGRAMVALUES[5],HISTOGRAMVALUES[6],HISTOGRAMVALUES[7],HISTOGRAMVALUES[8],HISTOGRAMVALUES[9],HISTOGRAMVALUES[10],HISTOGRAMVALUES[11],highest,Mode.MAJOR)	 			
			 			)
			 	{
			 		highest = allTonalities[i];
			 	}
		 }
		 return highest;
	 }
	 
	 public Tonality bestMinorCorrelation(){
		 Tonality highest = Tonality.GSHARP;
		 Tonality[] allTonalities = {MusicKey.Tonality.GSHARP, MusicKey.Tonality.A,MusicKey.Tonality.ASHARP,MusicKey.Tonality.B,MusicKey.Tonality.C,MusicKey.Tonality.D,
		                             MusicKey.Tonality.DSHARP, MusicKey.Tonality.E,MusicKey.Tonality.F,MusicKey.Tonality.FSHARP,MusicKey.Tonality.G};
		 for(int i=1;i<12;i++){
			 	if(
			 			
			 			KrumhanslShmuckler(HISTOGRAMVALUES[0],HISTOGRAMVALUES[1],HISTOGRAMVALUES[2],HISTOGRAMVALUES[3],HISTOGRAMVALUES[4],HISTOGRAMVALUES[5],HISTOGRAMVALUES[6],HISTOGRAMVALUES[7],HISTOGRAMVALUES[8],HISTOGRAMVALUES[9],HISTOGRAMVALUES[10],HISTOGRAMVALUES[11],allTonalities[i],Mode.MINOR)
			 			>
			 			KrumhanslShmuckler(HISTOGRAMVALUES[0],HISTOGRAMVALUES[1],HISTOGRAMVALUES[2],HISTOGRAMVALUES[3],HISTOGRAMVALUES[4],HISTOGRAMVALUES[5],HISTOGRAMVALUES[6],HISTOGRAMVALUES[7],HISTOGRAMVALUES[8],HISTOGRAMVALUES[9],HISTOGRAMVALUES[10],HISTOGRAMVALUES[11],highest,Mode.MINOR)	 			
			 			)
			 	{
			 		highest = allTonalities[i];
			 	}
		 }
		 return highest;
	 }
	 
	 public Tonality bestCorrelationTonality(){
		 Tonality highest = Tonality.GSHARP; // why not copy paste more plus is more scalable
		 if(
		 			
		 			KrumhanslShmuckler(HISTOGRAMVALUES[0],HISTOGRAMVALUES[1],HISTOGRAMVALUES[2],HISTOGRAMVALUES[3],HISTOGRAMVALUES[4],HISTOGRAMVALUES[5],HISTOGRAMVALUES[6],HISTOGRAMVALUES[7],HISTOGRAMVALUES[8],HISTOGRAMVALUES[9],HISTOGRAMVALUES[10],HISTOGRAMVALUES[11],bestMajorCorrelation(),Mode.MAJOR)
		 			>
		 			KrumhanslShmuckler(HISTOGRAMVALUES[0],HISTOGRAMVALUES[1],HISTOGRAMVALUES[2],HISTOGRAMVALUES[3],HISTOGRAMVALUES[4],HISTOGRAMVALUES[5],HISTOGRAMVALUES[6],HISTOGRAMVALUES[7],HISTOGRAMVALUES[8],HISTOGRAMVALUES[9],HISTOGRAMVALUES[10],HISTOGRAMVALUES[11],bestMinorCorrelation(),Mode.MINOR)	 			
		 			)
		 	{
		 		highest = bestMajorCorrelation();
		 	} else{
		 		highest = bestMinorCorrelation();
		 	}
		 return highest;
	 }
	 
	 public Mode bestCorrelationMode(){
		 Mode highest = Mode.NOTMODE;
		 if(
		 			
		 			KrumhanslShmuckler(HISTOGRAMVALUES[0],HISTOGRAMVALUES[1],HISTOGRAMVALUES[2],HISTOGRAMVALUES[3],HISTOGRAMVALUES[4],HISTOGRAMVALUES[5],HISTOGRAMVALUES[6],HISTOGRAMVALUES[7],HISTOGRAMVALUES[8],HISTOGRAMVALUES[9],HISTOGRAMVALUES[10],HISTOGRAMVALUES[11],bestMajorCorrelation(),Mode.MAJOR)
		 			>
		 			KrumhanslShmuckler(HISTOGRAMVALUES[0],HISTOGRAMVALUES[1],HISTOGRAMVALUES[2],HISTOGRAMVALUES[3],HISTOGRAMVALUES[4],HISTOGRAMVALUES[5],HISTOGRAMVALUES[6],HISTOGRAMVALUES[7],HISTOGRAMVALUES[8],HISTOGRAMVALUES[9],HISTOGRAMVALUES[10],HISTOGRAMVALUES[11],bestMinorCorrelation(),Mode.MINOR)	 			
		 			)
		 	{
		 		highest = Mode.MAJOR;
		 	} else{
		 		highest = Mode.MINOR;
		 	}
		 return highest;
	 }
	 
}
