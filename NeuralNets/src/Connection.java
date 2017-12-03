import java.util.Random;
import java.util.function.DoubleFunction;

public class Connection {
	static double rate = 1;
	double weight;
	Node a;
	Node b;
	double val;
	double mod;
	/**
	 * Create connection from Node a to b
	 * Initial weight [0,1)
	 * @param a input of connection
	 * @param b output of connection
	 */
	public Connection(Node a, Node b)
	{
		this();
		a.addOutConnection(this);
		b.addInConnection(this);
		this.a = a;
		this.b = b;
	}
	
	/**
	 * Create connection from Node a to b with initial weight [low,high)
	 * @param a input of connection
	 * @param b output of connection
	 * @param low lower bound inclusive
	 * @param high upper bound exclusive
	 */
	public Connection(Node a, Node b, double low, double high)
	{
		this(low,high);
		a.addOutConnection(this);
		b.addInConnection(this);
		this.a = a;
		this.b = b;
	}
	
	/**
	 * Create connection with initial weight [a,b)
	 * @param a lower bound inclusive
	 * @param b upper bound exclusive
	 */
	private Connection(double a, double b)
	{
		weight = new Random().nextDouble()*(b-a)+a;
	}
	
	/**
	 * Create connection with initial weight [0,1)
	 */
	private Connection()
	{
		weight = new Random().nextDouble()*1;
	}
	
	/**
	 * Called from input node to calculate and store value being passed to output node
	 * @param x input from input node
	 */
	public void calculateValue(double x)
	{
		val = x*weight;
	}
	
	/**
	 * @return current weight of this connection
	 */
	public double getWeight()
	{
		return weight;
	}
	
	/**
	 * Used to put value into output node, be sure CalculateValue has been called
	 * @return calculated value from input node
	 */
	public double getValue()
	{
		return val;
	}
	
	/**
	 * For use with backpropagation, calculate amount to modify weight based on derivative of outgoing nodes
	 */
	public void calModifyWeight()
	{
		mod = -1*a.activate()*b.deri*rate;
	}
	
	/**
	 * Modify weights based on the values last calculated during backpropagation
	 */
	public void modifyWeight()
	{
		weight -= mod;
	}
}
