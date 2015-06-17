package ideas;
import java.util.ArrayList;

public class DiversityTest {

	public static void main(String[] args) {

		int dimension=2;
		int swarmSize=4;
		double sum=0;
		double divSum=0;
		double diversity;

		double[] positionMean = new double[dimension]; 

		ArrayList<double[]> swarm = new ArrayList<double[]>();

		for(int i=0; i<swarmSize;i++){
			swarm.add(new double[dimension]);
		}
		
		swarm.get(0)[0]= (-1);
		swarm.get(0)[1]= (1);
		swarm.get(1)[0]= (1);
		swarm.get(1)[1]= (1);
		swarm.get(2)[0]= (-1);
		swarm.get(2)[1]= (0);
		swarm.get(3)[0]= (0);
		swarm.get(3)[1]= (0);

//		for(int i=0; i<swarmSize;i++){
//			swarm.get(i)[0]= (i/2);
//			swarm.get(i)[1]= (i+1);
//		}
//		for(int i=0; i<swarmSize;i++){
//			System.out.println(swarm.get(i)[0]);
//			System.out.println(swarm.get(i)[1]);
//		}
//

		for (int i=0; i<dimension; i++ ){
			double position = 0;
			for (int j=0; j<swarmSize; j++){	
				position += swarm.get(j)[i];			
			}
			positionMean[i]= position / swarmSize;	
			System.out.println(positionMean[i]);
		}

		for (int k=0; k<swarmSize; k++){
			sum =0;
			for (int j=0; j<dimension; j++){
				System.out.println(swarm.get(k)[j]-positionMean[j]);
				sum += (swarm.get(k)[j]-positionMean[j])*(swarm.get(k)[j]-positionMean[j]);
				System.out.println("Sum: " + sum);
			}
			double sqrt = Math.sqrt(sum);
			System.out.println("Square root of sum: " + sqrt);
			divSum += sqrt;
		}
		diversity = divSum/swarmSize;
		System.out.println("Diversity: " + diversity);


	}



}
