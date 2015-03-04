package standardPSO;
import java.util.Random;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LBestOptimisationProcess implements Constants {

	Random generator = new Random();
	ArrayList<LBestParticle> swarm = new ArrayList<LBestParticle>();

	String testOutput ="";

	double gBest;
	double[] gBestPosition = new double[DIMENSION];

	public void optimisation(){

		initialiseSwarm(); //generate initial population setting velocity and position of each particle
		System.out.println("LBest Swarm initialised");

		//Set gBest
		int min = 0;
		for (int j=1; j<SWARM_SIZE; j++){
			if (swarm.get(j).getPBest()<swarm.get(min).getPBest()){
				min = j;
			}
			gBest = swarm.get(min).getPBest();
			gBestPosition = swarm.get(min).getPBestPosition();
			testOutput = gBest + "\n ";
		}

		//Create LBest neighbourhood for each particle
		for (int j=0; j<SWARM_SIZE; j++){
			ArrayList<LBestParticle> n = new ArrayList<LBestParticle>();
			if (j == 0){
				n.add(swarm.get(SWARM_SIZE-1));
				n.add(swarm.get(j));
				n.add(swarm.get(j+1));
			}
			else if (j == SWARM_SIZE-1){
				n.add(swarm.get(j-1));
				n.add(swarm.get(j));
				n.add(swarm.get(0));
			}
			else
			{
				n.add(swarm.get(j-1));
				n.add(swarm.get(j));
				n.add(swarm.get(j+1));
			}

			swarm.get(j).setNeighbourhood(n);
			swarm.get(j).setLBest();
		}

		//beginning of iterations
		for(int t=0; t<ITERATION;t++ ) {
			for (int j=0; j<SWARM_SIZE; j++){
				swarm.get(j).updateVelocity();
				swarm.get(j).updatePosition();
				swarm.get(j).setFitness();
				swarm.get(j).updatePBest();
				swarm.get(j).setLBest();
			}
			//Update gBest
			for (int j=0; j<SWARM_SIZE; j++){
				if (swarm.get(j).getPBest()<gBest){
					min = j;
				}
				gBest = swarm.get(min).getPBest();
				gBestPosition = swarm.get(min).getPBestPosition();
			}
			testOutput += gBest + "\n ";
		
		}

		

		System.out.println("gBest: " + gBest);
		System.out.print("gBestPosition: ");
		for (int i= 0; i<DIMENSION; i++){
			System.out.print(gBestPosition[i] + " ");
		}
		System.out.println();
		System.out.println("______________________________________");
		
		try {
			String content = testOutput;
 
//			File file = new File("/Users/03501507/PSOData/filename.txt");
			File file = new File("F:/PSOData/lBest.txt");
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
 
			System.out.println("Done");
 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}



	public   void initialiseSwarm(){

		for (int j=0; j<SWARM_SIZE; j++){

			//create new particle
			LBestParticle p = new LBestParticle();

			//give the particle a location
			double[] pos = new double[DIMENSION];
			for (int i=0; i<DIMENSION; i++ ){
				pos[i] = generator.nextDouble() * (Functions.BOUND_HIGH-Functions.BOUND_LOW) + Functions.BOUND_LOW;

			}			

			//give the particle velocity
			double[] vel = new double[DIMENSION];
			for (int i=0; i<DIMENSION; i++ ){
				vel[i] = generator.nextDouble() * (Functions.BOUND_HIGH-Functions.BOUND_LOW) + Functions.BOUND_LOW;
			}

			p.setPosition(pos);
			p.setVelocity(vel);
			p.setFitness();
			p.setPBest();
			p.setPBestPosition();
			swarm.add(p);
		}
	}




}



