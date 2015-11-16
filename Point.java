/**
 * This class respresnts a coordinate point
 * It consists of a (latitude, longitude) pair
 * 
 * @author Aditya Sehgal
 * @version 1.0
 */
public class Point {

	private double longitude;
	private double latitude;
	
	public Point(double latitude, double longitude) {
	
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public double getLongitude() {
		return this.longitude;
	}
	
	public double getLatitude() {
		return this.latitude;
	}
	
}