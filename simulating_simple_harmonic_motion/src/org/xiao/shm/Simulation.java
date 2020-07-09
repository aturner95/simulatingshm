package org.xiao.shm;

import org.jzy3d.analysis.AnalysisLauncher;

/**
 * Computes time vs position for oscillator
 * 
 * @author Drew
 *
 */

public class Simulation {

	private int numOfIterations = 1000;
	private final double h = 0.05;
	private final double hh = h / 2;
	private final double h6 = h / 6;

	private double w0;
	private double zeta;

	private double time = 0.0;
	private final double dt = h;

	public void derivatives(double[] y, double[] dydt) {
		dydt[0] = y[1];
		dydt[1] = -((2 * zeta * w0 * y[1]) + (w0 * w0 * y[0]));

	}

	@Deprecated
	public void EulerRoutine(double[] y, double[] dydt) {
		for (int i = 0; i <= y.length - 1; i++) {

			derivatives(y, dydt);

			y[i] = y[i] + (dydt[i] * dt);
		}
	}

	public void RK4Routine(double[] y, double[] dydt) {

		double[] k1 = { 0.0, 0.0 };
		double[] k2 = { 0.0, 0.0 };
		double[] k3 = { 0.0, 0.0 };
		double[] k4 = { 0.0, 0.0 };

		for (int i = 0; i <= y.length - 1; i++) {

			derivatives(y, dydt);

			k1[i] = dydt[i];
			k2[i] = dydt[i] + hh * k1[i];
			k3[i] = dydt[i] + hh * k2[i];
			k4[i] = dydt[i] + k3[i];

			y[i] = y[i] + h6 * (k1[i] + 2 * k2[i] + 2 * k3[i] + k4[i]);
		}
	}

	public void run(Oscillator oscillatorArr[]) throws Exception {

		int numOfBodies = oscillatorArr.length;

		double[][] y0Store = new double[numOfBodies][numOfIterations];
		double[][] x0Store = new double[numOfBodies][numOfIterations];

		for (int i = 0; i < numOfIterations; i++) {

			time = time + dt;
			

			for (int j = 0; j < numOfBodies; j++) {

				Oscillator oscillator = oscillatorArr[j];

				double[] y = oscillator.getY();
				double[] dydt = oscillator.getdYdt();

				w0 = Double.valueOf(oscillator.getW0());
				zeta = Double.valueOf(oscillator.getZeta());

				RK4Routine(y, dydt);
				y0Store[j][i] = y[0];
				x0Store[j][i] = time;

			}
		}
		Plotter plot = new Plotter(x0Store, y0Store);
		AnalysisLauncher.open(plot);
	}
}
