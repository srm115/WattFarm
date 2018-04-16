package WattFarmSource;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;


@SuppressWarnings("serial")
public class Graphic extends JPanel{
	
	private List<Integer> data;


	public Graphic(List<Integer> data) {
	    this.data = data;
	 }
	
	
	JFrame plot;
	
	private static final int PREF_W = 800;
	private static final int PREF_H = 650;
	private static final int GRAPH_POINT_WIDTH = 12;
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		double xScale = ((double) getWidth() - 2) / (data.size() - 1);
		double yScale = ((double) getHeight() - 2) / (data.size() - 1);
		
		
		List<Point> points = new ArrayList<Point>();
		for(int i = 0; i < data.size(); i++) {
			int newX = (int) (i * xScale);
			int newY = (int) (data.get(i) * yScale);
			points.add(new Point(newX, newY));
		}
		
		Stroke oldStroke = g2.getStroke();
		
		for (int i = 0; i < points.size() - 1; i++) {
			int x1 = points.get(i).x;
			int y1 = points.get(i).y;
			int x2 = points.get(i + 1).x;
			int y2 = points.get(i + 1).y;
			g2.drawLine(x1, y1, x2, y2);         
		}
		
		g2.setStroke(oldStroke);
	      for (int i = 0; i < points.size(); i++) {
	         int x = points.get(i).x - GRAPH_POINT_WIDTH / 2;
	         int y = points.get(i).y - GRAPH_POINT_WIDTH / 2;;
	         int ovalW = GRAPH_POINT_WIDTH;
	         int ovalH = GRAPH_POINT_WIDTH;
	         g2.fillOval(x, y, ovalW, ovalH);
	      }
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(PREF_W, PREF_H);
	}
	
	public void exportPlot() {
		
	}
	
	public void createPlot() {
		
	}
}