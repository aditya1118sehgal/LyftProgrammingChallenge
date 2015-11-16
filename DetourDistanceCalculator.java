/**
 * This is for the Lyft Programming Challenge
 *
 * Usage: compile :$javac DetourDistanceCalculator.java
 *  	  run     :$java DetourDistanceCalculator a1 a2 b1 b2 c1 c2 d1 d2
 * 					(where a1, a2 represents the latitude, longitude pair for point A)
 *		  eg      :$java DetourDistanceCalculator 0 100 121.2 10 103 50.1 77.9 121.1
 * 
 * I used the below link for reference:
 * http://andrew.hedges.name/experiments/haversine/
 * 
 * @author Aditya Sehgal
 * @version 1.0
 */
public class DetourDistanceCalculator {
	
	private static final double EARTH_RADIUS_MILES = 3961;
	
	private Point A = null;
	private Point B = null;
	private Point C = null;
	private Point D = null;
	
	/**
	 * Takes in routes for first and second driver
	 * Routes are represented as a pair of points
	 * ie Points A and B for driver 1, Points C and D for driver 2
	 */
	public DetourDistanceCalculator(Point A, Point B, Point C, Point D) {
		this.A = A;
		this.B = B;
		this.C = C;
		this.D = D;
	}

	/**
	 * Points are (latitude, longitude) pairs
	 *
	 * @param P1 First Point
	 * @param P2 Second Point
	 * @return distance between Points P1 and P2
	 */
	public static double distanceBetweenTwoPoints(Point P1, Point P2) {
	
		//latitude, longitude pairs:
		//P1
		double lat1= P1.getLatitude();
		double long1= P1.getLongitude();
		//P2
		double lat2 = P2.getLatitude();
		double long2 = P2.getLongitude();
		//difference
		double deltaLong = long2 - long1;
		double deltaLat = lat2 - lat1;
		//a = (sin(dlat/2))^2 + cos(lat1) * cos(lat2) * (sin(dlon/2))^2 
		//need to convert to radians first
		double a = Math.sin(Math.toRadians(deltaLong/2));
		a *= a;
		a *= (Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)));
		double s = Math.sin(Math.toRadians(deltaLat/2));
		s *= s;
		a += s;
		//c = 2 * atan2( sqrt(a), sqrt(1-a) ) 
		double c  = 2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		//return result
		//d = R * c (where R is the radius of the Earth)
		return (c*EARTH_RADIUS_MILES);
	}
	
	/**
	 * Handles the logic for computing the required result
	 */
	private void runCalculator() {
		//calculate distances as:
		//AB
		double distAB = distanceBetweenTwoPoints(A, B);
		//CD
		double distCD = distanceBetweenTwoPoints(C, D);
		//ACDB
		double distAC = distanceBetweenTwoPoints(A, C);
		double distDB = distanceBetweenTwoPoints(D, B);
		double distACDB = distAC + distCD + distDB;
		//CABD
		double distCA = distanceBetweenTwoPoints(C, A);
		double distBD = distanceBetweenTwoPoints(B, D);
		double distCABD = distCA + distAB+ distBD;
		
		//ACDB - AB
		double detourDist1 = distACDB - distAB;
		//CABD - CD
		double detourDist2 = distCABD - distCD;
		minimumDetourDistance(detourDist1, detourDist2);
	}
	
	/**
	 * Determines the shorter of the two detour distances
	 * 
	 * @param detourDist1 the detour distance for Driver1
	 * @param detourDist2 the detour distance for Driver2
	 */
	private void minimumDetourDistance(double detourDist1, double detourDist2) {
		
		System.out.println("\nDetour Distance for Driver1 is: \n\tDISTANCE(A->C->D->B) - DISTANCE(A->B) = " + detourDist1 + " miles");
		System.out.println("Detour Distance for Driver2 is: \n\tDISTANCE(C->A->B->D) - DISTANCE(C->D) = " + detourDist2 + " miles\n");
		if(detourDist1 < detourDist2)
			System.out.println("Detour Distance for Driver 1 is shorter");
		else
			System.out.println("Detour Distance for Driver 2 is shorter");
			
	}
	
	/**
	 * Helper for getInputs
	 * Parses a double from a single String input
	 * 
	 * @param input The input string
	 * @return the parsed double value
	 */
	private static double getDouble(final String input) {
		double d = -1;
		try {
			d = Double.parseDouble(input);
			return d;
		}
		catch(NumberFormatException e) {
			return d;
		}
	}
	
	/**
	 * Helper for main
	 * Converts String array to double array 
	 * 
	 * @param args inputs as String array
	 * @return inputs parsed as double array
	 */
	private static double[] getInputs(String[] args) {
		double[] inputs = new double[8];
		
		for(int i = 0 ; i < 8; i ++) {
			inputs[i] = getDouble(args[i]);
		}
		
		return inputs;
	}
	
	/**
	 * Helper for main
	 * Determines if inputs are valid doubles
	 *
	 * @param inputs array of inputs
	 * @return true if all inputs are double, false otherwise
	 */
	private static boolean isValid(double[] inputs) {
		for( int i = 0; i < 8; i ++)
			if(inputs[i] < 0 )
				return false;
			
		return true;
	}
	
	/**
	 * Main method
	 * Parses and validates inputs, and runs the caluclator
	 * 
	 * @param args The command line inputs
	 */
	public static void main(String[] args) {
		//check number of arguments
		if(args.length != 8) {
			System.out.println("Incorrect command line arguments, please read usage!");
			return;
		}
		//parse arguments 
		double[] inputs = getInputs(args);
		//verify arguments as valid
		if(!isValid(inputs)) {
			System.out.println("Invalid number(s)!");
			return;
		}
		//create corresponding points
		Point A = new Point(inputs[0], inputs[1]);
		Point B = new Point(inputs[2], inputs[3]);
		Point C = new Point(inputs[4], inputs[5]);
		Point D = new Point(inputs[6], inputs[7]);
		//run
		DetourDistanceCalculator d = new DetourDistanceCalculator(A, B, C, D);
		d.runCalculator();
	}
	
	
} //end class DetourDistanceCalculator