package org.xiao.shm;

import org.jzy3d.analysis.AbstractAnalysis;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.Scatter;
import org.jzy3d.plot3d.rendering.canvas.Quality;

/**
 * Plots oscillator motion
 * 
 * @author Drew
 *
 */
public class Plotter extends AbstractAnalysis {

	private double[][] x;
	private double[][] y;

	public Plotter(double[][] x, double[][] y) throws Exception {
		this.x = x;
		this.y = y;

		if (x[0].length != y[0].length) {
			throw new Exception();
		}
	}

	@Override
	public void init() throws Exception {

		int numOfBodies = y.length;
		int numOfIterations = y[0].length;

		Coord3d[][] points = new Coord3d[numOfBodies][numOfIterations];
		
		chart = AWTChartComponentFactory.chart(Quality.Advanced, "newt");

		for (int i = 0; i < numOfIterations; i++) {
			for (int j = 0; j < numOfBodies; j++) {

				points[j][i] = new Coord3d(x[j][i], y[j][i], 0);

			}
		}

		for (int j = 0; j < numOfBodies; j++) {
			float red = (float) Math.random();
			float green = (float) Math.random();
			float blue = (float) Math.random();
			float a = 250;
			Color color = new Color(red, green, blue, a);
			
			chart.getScene().add(new Scatter(points[j], color));
		}
	}
}
