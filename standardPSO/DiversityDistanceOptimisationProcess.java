package standardPSO;



import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DiversityDistanceOptimisationProcess extends OptimisationProcess {

	ArrayList<DiversityDistanceParticle> swarm = new ArrayList<DiversityDistanceParticle>();
//	double divNow;
	
	//Variables for GIDN function
    int h = 0;
    int b = 3;
    int y = 2;
    double maxT = ITERATION;
    int N = SWARM_SIZE; 
    
    public DiversityDistanceOptimisationProcess(){
        function  = new Functions(PSOMain.functionNo);      
    }

	public void optimisation(){

		initialiseSwarm(); //generate initial population setting velocity and position of each particle
		//			System.out.println("Diversity Distance Swarm initialised");

		setPositionMean();
		calculateDiversity();

		//variable for low diversity detection
//		double divLow = diversity;
//		divNow = diversity;
		double divStart = diversity;
		

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

		//Create  initial neighbourhood for each particle
		for (int j=0; j<SWARM_SIZE; j++){
			ArrayList<DiversityDistanceParticle> n = new ArrayList<DiversityDistanceParticle>();
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
			swarm.get(j).setDBest();
		}

		//Create  initial nonNeighbourhood for each particle
		for (int j=0; j<SWARM_SIZE; j++){
			swarm.get(j).setNonNeighbourhood(swarm);
		}

		// Remove neighbours from nonNeighbour list in TestParticle object
		// System.out.println("nonNeighbour Size: " + swarm.get(0).getNonNeighboursSize());
		for (int k=0; k<SWARM_SIZE; k++){
			if (k == 0){
				swarm.get(k).removeNonNeighbourhood(SWARM_SIZE-1);
				swarm.get(k).removeNonNeighbourhood(k+1);
				swarm.get(k).removeNonNeighbourhood(k);
			}
			else if (k == SWARM_SIZE-1){
				swarm.get(k).removeNonNeighbourhood(k);
				swarm.get(k).removeNonNeighbourhood(k-1);
				swarm.get(k).removeNonNeighbourhood(0);
			}
			else
			{
				swarm.get(k).removeNonNeighbourhood(k+1);
				swarm.get(k).removeNonNeighbourhood(k);
				swarm.get(k).removeNonNeighbourhood(k-1);
			}
		}
		//		System.out.println("nonNeighbour Size: " + swarm.get(0).getNonNeighbourhoodSize());

		//		beginning of iterations
		for(int t=0; t<ITERATION;t++ ) {
			
			 double hNew = (divStart-diversity)*Math.pow(((t+1))/maxT,y);

			 if (h<Math.floor(hNew)){
//			if (divNow/diversity <= 1){
//			if (diversity < (divLow/(t/ITERATION))){
				//			if (diversity <= diversity){
				//				System.out.println(t + "Add neighbour"); //do this for each neighbour
				for (int j=0; j<SWARM_SIZE; j++){

					if(swarm.get(j).getNonNeighbourhoodSize()>0){
						swarm.get(j).addFurthestDistnaceParticleFromNonNeighbourhoods();
					}

				}

				//				System.out.println("Iteration: " + t);
				//				System.out.println("Non Neighbourhood Size: " + swarm.get(0).getNonNeighbourhoodSize());
				//				System.out.println("neighbourhood Size: " + swarm.get(0).getNeighbourhoodSize());
			}


			 h=(int) Math.floor(hNew);



			for (int j=0; j<SWARM_SIZE; j++){
//								if(t<500 && j%50==0){
//								swarm.get(j).updateVelocity();
//								swarm.get(j).reinitialiseVelocity();;
//							}
//							else{
//								swarm.get(j).updateVelocity();
//							}
				swarm.get(j).updateVelocity();
				swarm.get(j).updatePosition();
				swarm.get(j).setFitness();
				swarm.get(j).updatePBest();
				swarm.get(j).setDBest();
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

//			divNow = diversity;
			
			setPositionMean();
			calculateDiversity();

			//			System.out.println("nonNeighbour Size: " + swarm.get(0).getNonNeighbourhoodSize());
		}


		//		System.out.println("gBest: " + gBest);
		//		System.out.print("gBestPosition: ");
		//		for (int i= 0; i<DIMENSION; i++){
		//			System.out.print(gBestPosition[i] + " ");
		//		}
		//		System.out.println();
		//		System.out.println("______________________________________");

		try {
			String content = testOutput;

			File file = new File("F:/PSOData/diversityDistance.txt");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();

			//			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			String content1 = diversityOutput;

			//			File file = new File("/Users/03501507/PSOData/filename.txt");
			File file1 = new File("F:/PSOData/diversityDistanceDiversityOfSwarm.txt");

			// if file doesnt exists, then create it
			if (!file1.exists()) {
				file1.createNewFile();
			}

			FileWriter fw = new FileWriter(file1.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content1);
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}



	public   void initialiseSwarm(){

		for (int j=0; j<SWARM_SIZE; j++){

			//create new particle
			DiversityDistanceParticle p = new DiversityDistanceParticle();

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





