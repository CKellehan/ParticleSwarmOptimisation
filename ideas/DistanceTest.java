package ideas;

import java.util.ArrayList;

public class DistanceTest {

	public static void main(String[] args) {

		int dimension=2;
		int swarmSize=4;

		ArrayList<double[]> swarm = new ArrayList<double[]>();

		for(int i=0; i<swarmSize;i++){
			swarm.add(new double[dimension]);
		}

		double[] distances = new double[swarm.size()];

		swarm.get(0)[0]= (-1);
		swarm.get(0)[1]= (1);
		swarm.get(1)[0]= (1);
		swarm.get(1)[1]= (1);
		swarm.get(2)[0]= (-1);
		swarm.get(2)[1]= (0);
		swarm.get(3)[0]= (0);
		swarm.get(3)[1]= (0);


		//calculating the distance from each point

		for (int k=0; k<swarm.size(); k++){
			
			System.out.println("Particle "+k+" Distances:");
			for (int i=0; i<swarm.size(); i++){
				double d=0;
				for (int j=0; j<dimension; j++){
					d += (swarm.get(k)[j]-swarm.get(i)[j])*(swarm.get(k)[j]-swarm.get(i)[j]);
				}
				d = Math.sqrt(d);
				System.out.println(d);
				distances[i]=d;
			}


			//find max distance
			int max = 0;
			for (int j=1; j<swarm.size(); j++){
				if (distances[j]>distances[max]){
					max = j;
				}
			}
			double maxDistance = distances[max];
			System.out.println();
			System.out.println("Max distance from particle " + k + " is particle " +max + ": " + maxDistance);
		}
	}

}
