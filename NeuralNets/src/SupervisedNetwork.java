import java.util.ArrayList;

/**
 * Supervised neural network created based on graph structure
 * typical method calls should be reset -> forwardPropagate -> getOutput -> backPropagate
 * @author Nathan Huckleberry
 *
 */
public class SupervisedNetwork {
	
	Structure netStruct;
	
	public SupervisedNetwork(Structure struct)
	{
		netStruct = struct;
	}
	
	public void reset()
	{
		for(ArrayList<Node> layer : netStruct.layers)
		{
			for(Node n : layer)
			{
				n.reset();
			}
		}
	}
	
	public void forwardPropagate(double[] inputs)
	{
		if(inputs.length!=netStruct.inputNodes.size())
			throw new IllegalArgumentException("Input size and input struct are different sizes");
		reset();
		for(int i = 0; i < inputs.length; i++)
		{
			Node n = netStruct.inputNodes.get(i);
			n.input(inputs[i]);
		}
		for(ArrayList<Node> layer : netStruct.layers)
		{
			for(Node n : layer)
			{
				n.getInputs();
				n.activate();
			}
		}
	}
	
	public double[] getOutputs()
	{
		double[] out = new double[netStruct.outputNodes.size()];
		for(int i = 0; i < out.length; i++)
			out[i] = netStruct.outputNodes.get(i).getVal();
		return out;
	}
	
	public void backPropagate(double[] expected)
	{
		if(expected.length!=netStruct.outputNodes.size())
			throw new IllegalArgumentException("Output struct and expected value lengths are not the same");
		for(int i = 0; i < expected.length; i++)
		{
			Node n = netStruct.outputNodes.get(i);
			n.derive(expected[i]);
			for(Connection c : n.in)
				c.calModifyWeight();
		}
		for(int i = netStruct.layers.size()-2; i >= 1; i--)
		{
			for(Node n : netStruct.layers.get(i))
			{
				n.derive();
				for(Connection c : n.in)
				{
					c.calModifyWeight();
				}
			}
		}
		for(int i = netStruct.layers.size()-1; i >= 1; i--)
		{
			for(Node n : netStruct.layers.get(i))
			{
				for(Connection c : n.in)
					c.modifyWeight();
			}
		}
	}
	
	public double getError(double[] expected)
	{
		if(expected.length!=netStruct.outputNodes.size())
			throw new IllegalArgumentException("Output struct and expected value lengths are not the same");
		double err = 0.0;
		for(int i = 0; i < expected.length; i++)
		{
			err += Math.pow(expected[i]-netStruct.outputNodes.get(i).getVal(), 2);
		}
		return err;
	}
}
