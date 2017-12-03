import java.util.ArrayList;
import java.util.function.DoubleFunction;
import java.util.function.Function;

public class Node {
	
	enum Preset
	{
		ADD (x->x, x->1.0),
		SIGMOID (x -> 1.0/(1.0+Math.exp(-x)), x->1.0/(1.0+Math.exp(-x))*(1-1.0/(1.0+Math.exp(-x)))),
		SOFTPLUS (x -> Math.log(1+Math.exp(x)), x->1.0/(1.0+Math.exp(-x))),
		CONSTANT (x -> 1.0, x -> 0.0);
		DoubleFunction<Double> activator;
		DoubleFunction<Double> derivative;
		
		Preset(DoubleFunction<Double> act, DoubleFunction<Double> der)
		{
			activator = act;
			derivative = der;
		}
	}
	
	public static class Type
	{
		DoubleFunction<Double> activator;
		DoubleFunction<Double> derivative;
		
		Type(DoubleFunction<Double> act, DoubleFunction<Double> der)
		{
			activator = act;
			derivative = der;
		}
		
		Type(Preset p)
		{
			activator = p.activator;
			derivative = p.derivative;
		}
	}
	
	DoubleFunction<Double> activator;
	DoubleFunction<Double> derivative;
	ArrayList<Connection> in = new ArrayList<>();
	ArrayList<Connection> out = new ArrayList<>();
	double sum = 0.0;
	double deri = 0.0;
	double val = 0.0;
	
	/**
	 * Create node of custom defined type
	 * @param t type to be created
	 */
	Node(Type t)
	{
		activator = t.activator;
		derivative = t.derivative;
	}
	
	/**
	 * create node using preset
	 * @param p preset to create node with
	 */
	Node(Preset p)
	{
		activator = p.activator;
		derivative = p.derivative;
	}
	
	/**
	 * Should only be called by Connection class
	 * @param a Connection leaving this node
	 */
	void addOutConnection(Connection a)
	{
		out.add(a);
	}
	
	/**
	 * Should only be called by Connection class
	 * @param a Connection entering this node
	 */
	void addInConnection(Connection a)
	{
		in.add(a);
	}
	
	/**
	 * Calculates sum of inputs from incoming connections
	 */
	public void getInputs()
	{
		for(Connection c : in)
			sum += c.getValue();
	}
	
	/**
	 * For use with input layer, inputs x as value in this node
	 * @param x input value
	 */
	public void input(double x)
	{
		sum = x;
	}
	
	/**
	 * node to unactivated state, sum = 0
	 */
	public void reset()
	{
		sum = 0.0;
	}
	
	/**
	 * Activates node using activation function and current sum of this node
	 * @return value of activating with this sum
	 */
	public double activate()
	{
		val = activator.apply(sum);
		for(Connection c : out)
			c.calculateValue(val);
		return activator.apply(sum);
	}
	
	/**
	 * Get value of activation after using activation function
	 * @return value of activation
	 */
	public double getVal()
	{
		return val;
	}
	
	/**
	 * Use for backpropagation, calculates and stores derivative, for calls on non-output nodes
	 */
	void derive()
	{
		double partials = 0.0;
		for(Connection c : out)
		{
			partials += c.b.deri*c.weight;
		}
		deri = partials*derivative.apply(val);
	}
	
	/**
	 * Use for backpropagation, calculates and store derivative, for calls on output nodes
	 * (expected-actual)*derivative(actual)
	 * @param expected value
	 */
	void derive(double expected)
	{
		deri = (expected-val)*derivative.apply(val);
	}
}
