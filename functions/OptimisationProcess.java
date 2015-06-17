package functions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;

import standardPSO.Functions;


public class OptimisationProcess implements Constants {

	Random generator = new Random();
	ArrayList<Particle> swarm = new ArrayList<Particle>();

	double gBest;
	double[] gBestPosition = new double[DIMENSION];
	double[] gBestAtEactIteration = new double [ITERATION];


	//Diversity variables
	double[] positionMean = new double[DIMENSION];
	double diversity;
	double sum=0;
	double divSum;

	String testOutput = "";
	String diversityOutput="";
	
	benchmark theBenchmark;

	
	
	public OptimisationProcess(int i){
		theBenchmark = new benchmark(i);
	}
	
	public void optimisation(){



		initialiseSwarm(); //generate initial population setting velocity and position of each particle
		//		System.out.println("GBest Swarm initialised");


		//Diversity calculations
		setPositionMean();				
		calculateDiversity();


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

		//beginning of iterations
		for(int t=0; t<ITERATION;t++ ) {
			for (int j=0; j<SWARM_SIZE; j++){
				swarm.get(j).updateVelocity(gBestPosition);
				swarm.get(j).updatePosition();
				swarm.get(j).setFitness(theBenchmark);
				swarm.get(j).updatePBest();
			}
			//Update gBest
			for (int j=0; j<SWARM_SIZE; j++){
				if (swarm.get(j).getPBest()<gBest){
					min = j;
				}
				gBest = swarm.get(min).getPBest();
				gBestPosition = swarm.get(min).getPBestPosition();
			}


			//			System.out.println("Iteration " + t + " gBest:" + gBest);
			testOutput += gBest + "\n ";
			gBestAtEactIteration[t]=gBest;

			setPositionMean();
			calculateDiversity();

		}

		//		System.out.println("Swarm size:" +swarm.size());


		//		System.out.println("gBest: " + gBest);
		//		System.out.print("gBestPosition: ");
		//		for (int i= 0; i<DIMENSION; i++){
		//			System.out.print(gBestPosition[i] + " ");
		//		}
		//		System.out.println();
		//		System.out.println("______________________________________");

		try {
			String content = testOutput;

			File file = new File("F:/PSOData/gBest.txt");

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
			File file1 = new File("F:/PSOData/gBestDiversityOfSwarm.txt");

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
			Particle p = new Particle();

			//give the particle a location
			double[] pos = new double[DIMENSION];
			for (int i=0; i<DIMENSION; i++ ){
				pos[i] = generator.nextDouble() * (Functions.boundHigh-Functions.boundLow) + Functions.boundLow;
				//System.out.println("Particle " + j + " Positions " + i + ": " + pos[i]  );

			}			

			//give the particle velocity
			double[] vel = new double[DIMENSION];
			for (int i=0; i<DIMENSION; i++ ){
				vel[i] = generator.nextDouble() * (Functions.boundHigh-Functions.boundLow) + Functions.boundLow;
			}

			p.setPosition(pos);
			p.setVelocity(vel);
			p.setFitness(theBenchmark);
			p.setPBest();
			p.setPBestPosition();
			swarm.add(p);
		}
	}

	public double getGBestAtEactIteration(int index){
		return gBestAtEactIteration[index];
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
		//		System.out.println("Diversity: " + diversity);
	}





}




