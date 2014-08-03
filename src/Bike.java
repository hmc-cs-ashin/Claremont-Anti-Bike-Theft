package com.example.claremontantibiketheft;
import java.io.Serializable;

public class Bike implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public String serial = "";
	public String descript = "";
	public boolean isStolen = false;
	public double latStolen = 0.0;
	public double longStolen = 0.0;
	//time stamp var for when bike was registered
	
	public Bike (String a, String d, double b, double c) {
		serial = a;
		descript = d;
		latStolen = b;
		longStolen = c;
	}
}
