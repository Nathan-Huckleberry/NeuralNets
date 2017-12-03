import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.SwingUtilities;

public class Structure {
	ArrayList<Node> inputNodes;
	ArrayList<Node> outputNodes;
	ArrayList<ArrayList<Node>> layers;
	boolean graphActive;
	StructureGraphic g;
	
	public Structure()
	{
		
	}
	
	public Structure(ArrayList<Node> in, ArrayList<Node> out)
	{
		if(in.isEmpty()||out.isEmpty())
			throw new IllegalArgumentException("Input and Output layers must not be empty");
		inputNodes = in;
		outputNodes = out;
		createLayers();
	}
	
	public void activateGraph()
	{
		graphActive = true;
		
		g = new StructureGraphic(layers);
	}
	
	public void updateGraphics()
	{
		if(graphActive)
			g.go();
	}
	
	public void createLayers()
	{
		layers = new ArrayList<ArrayList<Node>>();
		Queue<Object> q = new LinkedList<Object>();
		for(Node n : outputNodes)
		{
			q.add(n);
			q.add(0);
		}
		while(!q.isEmpty())
		{
			Node n = (Node)q.poll();
			int d = (int)q.poll();
			int m = -1;
			for(int l = 0; l < layers.size(); l++)
			{
				ArrayList<Node> layer = layers.get(l);
				for(int i = layer.size()-1; i >= 0; i--)
				{
					if(layer.get(i)==n)
					{
						if(l<d)
							layer.remove(i);
						m = l;
					}
				}
			}
			if(d<=m)
				continue;
			if(d>=layers.size())
				layers.add(new ArrayList<>());
			layers.get(d).add(n);
			for(Connection x : n.in)
			{
				q.add(x.a);
				q.add(d+1);
			}
		}
		Collections.reverse(layers);
	}
}
