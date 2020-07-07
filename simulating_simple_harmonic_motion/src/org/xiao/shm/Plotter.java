package org.xiao.shm;

import org.jzy3d.analysis.AbstractAnalysis;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.Scatter;
import org.jzy3d.plot3d.rendering.canvas.Quality;

public class Plotter extends AbstractAnalysis {

	private double[] x;
	private double[] y;

	public Plotter(double[] x, double[] y) throws Exception {
		this.x = x;
		this.y = y;

		if (x.length != y.length) {
			throw new Exception();
		}
	}

	@Override
	public void init() throws Exception {

		Coord3d[][] points = new Coord3d[1][x.length];

		for (int i = 0; i < x.length; i++) {

			points[0][i] = new Coord3d(x[i], y[i], 0);

		}

		chart = AWTChartComponentFactory.chart(Quality.Advanced, "newt");

		float red = (float) Math.random();
		float green = (float) Math.random();
		float blue = (float) Math.random();
		float a = 250;
		Color color = new Color(red, green, blue, a);

		chart.getScene().add(new Scatter(points[0], color));

	}
}