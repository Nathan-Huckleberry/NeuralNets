import java.util.ArrayList;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		Structure s = new MultiLevelStructWithBias(Node.Preset.SOFTPLUS, 3, 10, 10, 10, 3);
		Connection.rate = 0.00001;
		SupervisedNetwork net = new SupervisedNetwork(s);
		double[] input = {.5,.3,1};
		double[] expect = {.5,5,1};
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
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			s.updateGraphics();
		}
		System.out.println(Arrays.toString(output));
	}

}
