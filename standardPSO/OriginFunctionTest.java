package standardPSO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class OriginFunctionTest {

	public static void main(String[] args) {
		
		Functions f = new Functions(Constants.functionNo);

		Particle p = new Particle();
		System.out.println("New particle created");

		//give the particle a location
		double[] pos = new double[Constants.DIMENSION];
		
		
		Scanner scan;
		File file = new File("F:/PSOData/supportData/high_cond_elliptic_rot_data.txt");
		try {
			scan = new Scanner(file);
			int i =0;
			while(scan.hasNextDouble()&& i<Constants.DIMENSION)
			{	
					pos[i] = scan.nextDouble();
					System.out.print(pos[i] + " : ");
					i++;
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		
		
		
		
		
		
		for (int i=0; i<Constants.DIMENSION; i++ ){
			System.out.print(pos[0] + " : ");
		}
		
		p.setPosition(pos);
		p.setFitness();
		System.out.println();
		System.out.println("Fitness of particle " + p.getFitness());
		
		
	}
}
