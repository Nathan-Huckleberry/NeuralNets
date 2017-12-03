import java.util.ArrayList;
import java.util.Arrays;

public class Main2 {

	public static void main(String[] args) {
		Node a = new Node(Node.Preset.ADD);
		Node b = new Node(Node.Preset.SIGMOID);
		Node c = new Node(Node.Preset.ADD);
		Node d = new Node(Node.Preset.SOFTPLUS);
		Node e = new Node(Node.Preset.SIGMOID);
		new Connection(a,d);
		new Connection(b,d);
		new Connection(c,d);
		new Connection(d,e);
		ArrayList<Node> in = new ArrayList<Node>();
		ArrayList<Node> out = new ArrayList<Node>();
		in.add(a);
		out.add(e);
		Structure s = new Structure(in,out);
		
		Connection.rate = 1;
		SupervisedNetwork net = new SupervisedNetwork(s);
		double[] input = {.5};
		double[] expect = {1};
		double[] output = null;
		s.activateGraph();
		for(int i = 0; i < 1000; i++)
		{
			net.reset();
			net.forwardPropagate(input);
			output = net.getOutputs();
			net.backPropagate(expect);
			System.out.println(net.getError(expect));
			try {
				Thread.sleep(500);
			} catch (InterruptedException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			s.updateGraphics();
		}
		System.out.println(Arrays.toString(output));
	}

}
