import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class StructureGraphic extends JFrame 
{
	public static final int HEIGHT = 1000;
	public static final int WIDTH = 1000;
	ArrayList<ArrayList<Node>> layers;
	
	private DrawCanvas canvas;
	
	public StructureGraphic(ArrayList<ArrayList<Node>> l)
	{
		layers = l;
		canvas = new DrawCanvas();
		canvas.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		Container cp = getContentPane();
		cp.add(canvas);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setTitle("Graph");
		setVisible(true);
	}
	
	public void go()
	{
		canvas.repaint();
	}
	
	private class DrawCanvas extends JPanel
	{
		public void repaint()
		{
			super.repaint();
		}
		
		@Override
		public void paintComponent(Graphics g2)
		{
			Graphics2D g = (Graphics2D)g2;
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setStroke(new BasicStroke(5));
			double maxNode = 0.0;
			double maxConn = 0.0;
			for(ArrayList<Node> l : layers)
			{
				for(Node n : l)
				{
					for(Connection c : n.in)
					{
						maxConn = Math.max(maxConn, Math.abs(c.weight));
					}
					maxNode = Math.max(maxNode, Math.abs(n.getVal()));
				}
			}
			if(maxConn==0.0)
				maxConn = 0.0001;
			if(maxNode==0.0)
				maxNode = 0.0001;
			super.paintComponent(g);
			setBackground(Color.WHITE);
			
			int layerNum = layers.size();
			int maxLayer = Collections.max(layers, (ArrayList<Node> o1, ArrayList<Node> o2) -> (o1.size()-o2.size())).size();
			int stepHoriz = super.getWidth()/(3*(layerNum-1)+1)*3;
			int stepVert = super.getHeight()/(2*(maxLayer-1)+1)*2;
			int circleSize = Math.min(stepHoriz/3, stepVert/2);
			
			for(int i = 0; i < layers.size(); i++)
			{
				ArrayList<Node> layer = layers.get(i);
				for(int j = 0; j < layer.size(); j++)
				{
					int x = stepHoriz*i;
					int y = stepVert*j;
					{
						int colVal = (int)((1-Math.abs(layer.get(j).getVal())/maxNode)*255);
						if(layer.get(j).getVal() < 0)
							g.setColor(new Color(colVal,0,0));
						else
							g.setColor(new Color(colVal,colVal,colVal));
					}
					g.fillOval(x, y, circleSize, circleSize);
					g.setColor(Color.BLACK);
					g.drawOval(x, y, circleSize, circleSize);
					for(int k = 0; k < i; k++)
					{
						for(int q = 0; q < layers.get(k).size(); q++)
						{
							int ox = stepHoriz*k;
							int oy = stepVert*q;
							boolean b = false;
							for(Connection c : layers.get(k).get(q).out)
								if(c.b==layer.get(j))
								{
									int colVal = (int)((1-Math.abs(c.weight)/maxConn)*255);
									if(c.weight < 0)
										g.setColor(new Color(colVal,0,0));
									else
										g.setColor(new Color(colVal,colVal,colVal));
									b = true;
								}
							if(b)
								g.drawLine(x+circleSize/2, y+circleSize/2, ox+circleSize/2, oy+circleSize/2);
						}
					}
				}
			}
		}
	}
}
