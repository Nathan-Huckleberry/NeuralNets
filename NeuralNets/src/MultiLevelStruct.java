import java.util.ArrayList;

public class MultiLevelStruct extends Structure {
	
	public MultiLevelStruct(Node.Type t, int... numNodes)
	{
		if(numNodes.length<2)
			throw new IllegalArgumentException("Not enough layers for input and output layers, layers must be >= 2");
		ArrayList<Node> input = new ArrayList<>();
		ArrayList<Node> output = new ArrayList<>();
		ArrayList<Node> lastLayer = null;
		for(int i = 0; i < numNodes.length; i++)
		{
			ArrayList<Node> thisLayer = new ArrayList<>();
			for(int j = 0; j < numNodes[i]; j++)
			{
				Node b = null;
				if(i==0)
					b = new Node(Node.Preset.CONSTANT);
				else
					b = new Node(t);
				thisLayer.add(b);
				if(lastLayer!=null)
					for(Node a : lastLayer)
						new Connection(a,b);
			}
			lastLayer = thisLayer;
			if(i==0)
				input.addAll(thisLayer);
			if(i==numNodes.length-1)
				output.addAll(thisLayer);
		}
		this.inputNodes = input;
		this.outputNodes = output;
		createLayers();
	}
	
	public MultiLevelStruct(Node.Preset p, int... numNodes)
	{
		if(numNodes.length<2)
			throw new IllegalArgumentException("Not enough layers for input and output layers, layers must be >= 2");
		ArrayList<Node> input = new ArrayList<>();
		ArrayList<Node> output = new ArrayList<>();
		ArrayList<Node> lastLayer = null;
		for(int i = 0; i < numNodes.length; i++)
		{
			ArrayList<Node> thisLayer = new ArrayList<>();
			for(int j = 0; j < numNodes[i]; j++)
			{
				Node b = null;
				if(i==0)
					b = new Node(Node.Preset.CONSTANT);
				else
					b = new Node(p);
				thisLayer.add(b);
				if(lastLayer!=null)
					for(Node a : lastLayer)
						new Connection(a,b);
			}
			lastLayer = thisLayer;
			if(i==0)
				input.addAll(thisLayer);
			if(i==numNodes.length-1)
				output.addAll(thisLayer);
		}
		this.inputNodes = input;
		this.outputNodes = output;
		createLayers();
	}
	
	public MultiLevelStruct(ArrayList<Node> in, ArrayList<Node> out) {
		super(in, out);
		throw new IllegalArgumentException("Use Structure to create generalized structures");
	}

}
