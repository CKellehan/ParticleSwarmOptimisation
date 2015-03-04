package standardPSO;
import java.util.Random;
import java.util.ArrayList;

public class VNOptimisationProcess implements Constants {

	Random generator = new Random();
	ArrayList<VNParticle> swarm = new ArrayList<VNParticle>();


	double gBest;
	double[] gBestPosition = new double[DIMENSION];

	public void optimisation(){

		initialiseSwarm(); //generate initial population setting velocity and position of each particle
		System.out.println("Swarm initialised");

		//Set gBest
		int min = 0;
		for (int j=1; j<SWARM_SIZE; j++){
			if (swarm.get(j).getPBest()<swarm.get(min).getPBest()){
				min = j;
			}
			gBest = swarm.get(min).getPBest();
			gBestPosition = swarm.get(min).getPBestPosition();
		}
		System.out.println("gBest: " + gBest);
		System.out.print("gBestPosition: ");
		for (int i= 0; i<DIMENSION; i++){
			System.out.print(gBestPosition[i] + " ");
		}
		System.out.println();
		System.out.println("______________________________________");


		//Create VNBest neighbourhood for each particle
		for (int j=0; j<SWARM_SIZE; j++){
			ArrayList<VNParticle> n = new ArrayList<VNParticle>();
			if (j == 0){
				n.add(swarm.get(SWARM_SIZE-5));
				n.add(swarm.get(SWARM_SIZE-1));
				n.add(swarm.get(j));
				n.add(swarm.get(j+1));
				n.add(swarm.get(j+5));
			}
			else if (j < 5){
				n.add(swarm.get(j+SWARM_SIZE-5));
				n.add(swarm.get(j-1));
				n.add(swarm.get(j));
				n.add(swarm.get(j+1));
				n.add(swarm.get(j+5));
			}
			else if (j == SWARM_SIZE-1){
				n.add(swarm.get(j-5));
				n.add(swarm.get(j-1));
				n.add(swarm.get(j));
				n.add(swarm.get(j-SWARM_SIZE+1));
				n.add(swarm.get(j-SWARM_SIZE+5));
			}
			else if (j > SWARM_SIZE-6){
				n.add(swarm.get(j-5));
				n.add(swarm.get(j-1));
				n.add(swarm.get(j));
				n.add(swarm.get(j+1));
				n.add(swarm.get(j-SWARM_SIZE+5));
			}
			else{
				n.add(swarm.get(j-5));
				n.add(swarm.get(j-1));
				n.add(swarm.get(j));
				n.add(swarm.get(j+1));
				n.add(swarm.get(j+5));
			}

			swarm.get(j).setNeighbourhood(n);
			swarm.get(j).setVNBest();
		}

		//print current particles, positions, velocities and fitness
		for (int j=0; j<SWARM_SIZE; j++){
			System.out.println(swarm.get(j).toString());
			System.out.println(j);
		}
		System.out.println("______________________________");


		//beginning of iterations
		for(int t=0; t<ITERATION;t++ ) {
			for (int j=0; j<SWARM_SIZE; j++){
				swarm.get(j).updateVelocity();
				swarm.get(j).updatePosition();
				swarm.get(j).setFitness();
				swarm.get(j).updatePBest();
				swarm.get(j).setVNBest();
			}
			//Update gBest
			for (int j=0; j<SWARM_SIZE; j++){
				if (swarm.get(j).getPBest()<gBest){
					min = j;
				}
				gBest = swarm.get(min).getPBest();
				gBestPosition = swarm.get(min).getPBestPosition();
			}

			//			for (int j=0; j<SWARM_SIZE; j++){
			//				System.out.println(swarm.get(j).toString());
			//				System.out.println(j);
			//			}
			//			System.out.println("______________________________");
			//			System.out.println();




			//print current particles, positions, velocities and fitness
			//						for (int j=0; j<SWARM_SIZE; j++){
			//							System.out.println(swarm.get(j).toString());
			//						}
			System.out.println("Iteration: " + t);
			System.out.println("gBest: " + gBest);
			System.out.print("gBestPosition: ");
			for (int i= 0; i<DIMENSION; i++){
				System.out.print(gBestPosition[i] + " ");
			}
			System.out.println();
			System.out.println("______________________________________");
		}

		System.out.println("Swarm size:" +swarm.size());
		//		for (int j=0; j<SWARM_SIZE; j++){
		//			System.out.println(swarm.get(j).toString());
		//		}
	}



	public   void initialiseSwarm(){

		for (int j=0; j<SWARM_SIZE; j++){

			//create new particle
			VNParticle p = new VNParticle();

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



