package standardPSO;
import java.util.Random;
import java.util.ArrayList;


public class VNOptimisationProcess extends OptimisationProcess {

	Random generator = new Random();
	ArrayList<VNParticle> swarm = new ArrayList<VNParticle>();

	String testOutput = "";


	double gBest;
	double[] gBestPosition = new double[DIMENSION];
	
    public VNOptimisationProcess(){
        function  = new Functions(PSOMain.functionNo);      
    }

	public void optimisation(){

		initialiseSwarm(); //generate initial population setting velocity and position of each particle
		//		System.out.println("von Nuemann Swarm initialised");

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
			testOutput += gBest + "\n ";
			gBestAtEactIteration[t]=gBest;
		}

		//		System.out.println("gBest: " + gBest);
		//		System.out.print("gBestPosition: ");
		//		for (int i= 0; i<DIMENSION; i++){
		//			System.out.print(gBestPosition[i] + " ");
		//		}
		//		System.out.println();
		//		System.out.println("______________________________________");


	}



	public   void initialiseSwarm(){

		for (int j=0; j<SWARM_SIZE; j++){

			//create new particle
			VNParticle p = new VNParticle();

			//give the particle a location
			double[] pos = new double[DIMENSION];
			for (int i=0; i<DIMENSION; i++ ){
				pos[i] = generator.nextDouble() * (Functions.boundHigh-Functions.boundLow) + Functions.boundLow;

			}			

			//give the particle velocity
			double[] vel = new double[DIMENSION];
			for (int i=0; i<DIMENSION; i++ ){
				vel[i] = generator.nextDouble() * (Functions.boundHigh-Functions.boundLow) + Functions.boundLow;
			}

			p.setPosition(pos);
			p.setVelocity(vel);
			p.setFitness();
			p.setPBest();
			p.setPBestPosition();
			swarm.add(p);
		}
	}
	
	public void setPositionMean(){
		for (int i=0; i<DIMENSION; i++ ){
			double position = 0;
			for (int j=0; j<SWARM_SIZE; j++){	
				//				System.out.println(j +", "+ i + ": " + swarm.get(j).getPosition(i));
				position += swarm.get(j).getPosition(i);			
			}
			positionMean[i]= position / SWARM_SIZE;	
			//			System.out.println(positionMean[i]);
		}
	}

	public void calculateDiversity(){
		divSum=0; diversity=0;
		for (int k=0; k<SWARM_SIZE; k++){
			sum =0; 
			for (int j=0; j<DIMENSION; j++){
				//				System.out.println(swarm.get(k).getPosition(j)-positionMean[j]);
				sum += (swarm.get(k).getPosition(j)-positionMean[j])*(swarm.get(k).getPosition(j)-positionMean[j]);
				//				System.out.println("Sum: " + sum);
			}
			double sqrt = Math.sqrt(sum);
			//			System.out.println("Square root of sum: " + sqrt);
			divSum += sqrt;
		}
		diversity = divSum/SWARM_SIZE;
		diversityOutput += diversity + "\n ";
		//			System.out.println("Diversity: " + diversity);
	}






}



