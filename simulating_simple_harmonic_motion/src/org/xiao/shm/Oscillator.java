package org.xiao.shm;

import java.io.Serializable;

/**
 * Oscillator bean class. JavaFX files seem to want to deal with String,
 * hence why this class may look a bit strange, double fields however
 * encapsulated as Strings...
 * 
 * @author Drew
 *
 */

public class Oscillator implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double x;
	private double v;
	private double w0;
	private double zeta;
	
	private double [] y = new double[2];
	private double [] dydt = new double [2];

	public Oscillator(String x, String v, String w0, String zeta) {
		this.x = Double.valueOf(x);
		this.v = Double.valueOf(v);
		this.w0 = Double.valueOf(w0);
		this.zeta = Double.valueOf(zeta);
		
		this.y[0] = this.x;
		this.y[1] = this.v;
	}

	public String getPosition() {
		return "" + x;
	}

	public void setPosition(String x) {
		this.x = Double.valueOf(x);
	}

	public String getVelocity() {
		return "" + v;
	}

	public void setVelocity(String v) {
		this.x = Double.valueOf(v);
	}

	public String getW0() {
		return "" + w0;
	}

	public void setW0(String w0) {
		this.w0 = Double.valueOf(w0);
	}

	public String getZeta() {
		return "" + zeta;
	}

	public void setZeta(String zeta) {
		this.zeta = Double.valueOf(zeta);
	}
	
	public double [] getY() {
		return y;
	}
	
	public double [] getdYdt() {
		return dydt;
	}
	
}
