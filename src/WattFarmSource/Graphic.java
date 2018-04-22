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
	
	JFrame plot;
	
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT= 650;
	private static final int POINT_WIDTH = 12;
	
	private static final int BORDER = 30;
   private static final Color LINE_COLOR = Color.blue;
   private static final Color POINT_COLOR = Color.red;
   private static final Stroke LINE = new BasicStroke(3f);
   private static final int Y_SCALE = 10;
	
	private static int MAX_Y = 0;

	//Constructor
	public Graphic(List<Integer> data) {
	    this.data = data;
	    for(int i = 0; i < data.size(); i++) {
	    	if(data.get(i) > MAX_Y)
	    		MAX_Y = data.get(i);
	    }
	 }
	
	
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		double xScale = ((double) getWidth() - BORDER) / (data.size() - 1);
		double yScale = ((double) getHeight() - BORDER) / (data.size() - 1);
		
		
		List<Point> points = new ArrayList<Point>();
		for(int i = 0; i < data.size(); i++) {
			int newX = (int) (i * xScale + BORDER);
			int newY = (int) ((MAX_Y - data.get(i)) * yScale + BORDER);
			points.add(new Point(newX, newY));
		}
		
		
		//Draw Axis
		g2.drawLine(BORDER, getHeight() - BORDER, BORDER, BORDER);
	    g2.drawLine(BORDER, getHeight() - BORDER, getWidth() - BORDER, getHeight() - BORDER);
		
		
		
		
		
		//Make axis scales
	      for (int i = 0; i < Y_SCALE; i++) {
	         int x0 = BORDER;
	         int x1 = POINT_WIDTH + BORDER;
	         int y0 = getHeight() - (((i + 1) * (getHeight() - BORDER * 2)) / Y_SCALE + BORDER);
	         int y1 = y0;
	         g2.drawLine(x0, y0, x1, y1);
	      }
	      
	      for (int i = 0; i < data.size() - 1; i++) {
	          int x0 = (i + 1) * (getWidth() - BORDER * 2) / (data.size() - 1) + BORDER;
	          int x1 = x0;
	          int y0 = getHeight() - BORDER;
	          int y1 = y0 - POINT_WIDTH;
	          g2.drawLine(x0, y0, x1, y1);
	       }
	      
	     
	    Stroke oldStroke = g2.getStroke();
	    g2.setColor(LINE_COLOR);
	    g2.setStroke(LINE);
		for (int i = 0; i < points.size() - 1; i++) {
			int x1 = points.get(i).x;
			int y1 = points.get(i).y;
			int x2 = points.get(i + 1).x;
			int y2 = points.get(i + 1).y;
			g2.drawLine(x1, y1, x2, y2);         
		}
		
		g2.setStroke(oldStroke);
		g2.setColor(POINT_COLOR);
	      for (int i = 0; i < points.size(); i++) {
	         int x = points.get(i).x - POINT_WIDTH / 2;
	         int y = points.get(i).y - POINT_WIDTH / 2;;
	         int ovalW = POINT_WIDTH;
	         int ovalH = POINT_WIDTH;
	         g2.fillOval(x, y, ovalW, ovalH);
	      }
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
	}
	
	public void exportPlot() {
		
	}
	
	public static void createPlot(List<Integer> data) {
		Graphic mainPanel = new Graphic(data);
	      JFrame frame = new JFrame("Graphic");
	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      frame.getContentPane().add(mainPanel);
	      frame.pack();
	      frame.setLocationByPlatform(true);
	      frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		List<Integer> data = new ArrayList<Integer>();
	      Random random = new Random();
	      int maxDataPoints = 30;
	      int maxScore = 20;
	      for (int i = 0; i < maxDataPoints ; i++) {
	         data.add(random.nextInt(maxScore));
	      }
		createPlot(data);
	}
}