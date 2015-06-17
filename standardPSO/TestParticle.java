package standardPSO;

import java.util.ArrayList;


public class TestParticle extends Particle {

	private ArrayList<TestParticle> neighbourhood = new ArrayList<TestParticle>();
	double tBest;
	double[] tBestPosition;
	private ArrayList<Object> nonNeighbours = new ArrayList<Object>();

	public TestParticle() {
		super();
		tBestPosition = new double[Constants.DIMENSION];
		nonNeighbours = popNonNeighbours();
	}

	public TestParticle(double[] velocity, double[] position) {
		super(velocity, position);
		tBestPosition = new double[Constants.DIMENSION];
		nonNeighbours = popNonNeighbours();
	}

	public void updateVelocity(){
		for (int i=0; i<Constants.DIMENSION; i++){	
			double r1 = generator.nextDouble();
			//System.out.println("r1: " + r1);
			double r2 = generator.nextDouble();
			//System.out.println("r2: " + r2);
			velocity[i] = Constants.CHI * (velocity[i]  + Constants.C1*r1*(pBestPosition[i] - position[i]) + Constants.C2*r2*(tBestPosition[i] - position[i]));

			if (velocity[i]<Functions.boundLow-Functions.boundHigh){
				velocity[i]= Functions.boundLow-Functions.boundHigh ;
			}
			else if (velocity[i]>Functions.boundHigh-Functions.boundLow){
				velocity[i]=Functions.boundHigh-Functions.boundLow;
			}
		} 
	}
	
	public void reinitialiseVelocity(){
		for (int i=0; i<Constants.DIMENSION; i++){	
			velocity[i]  = generator.nextDouble() * (Functions.boundHigh-Functions.boundLow) + Functions.boundLow;
			
			if (velocity[i]<Functions.boundLow-Functions.boundHigh){
				velocity[i]= Functions.boundLow-Functions.boundHigh ;
			}
			else if (velocity[i]>Functions.boundHigh-Functions.boundLow){
				velocity[i]=Functions.boundHigh-Functions.boundLow;
			}
		} 
	}

	public void setNeighbourhood(ArrayList<TestParticle> n) {
		for (int i=0; i<n.size(); i++){
			neighbourhood.add(n.get(i));
		}
	}

	public void addNeighbour(TestParticle n) {
		neighbourhood.add(n);
	}
	
	public int getNeighbourhoodSize() {
		return neighbourhood.size();
	}

	public TestParticle getNeighbourhood(int i){
		return neighbourhood.get(i);	
	}

	public void setTBest(){
		int min = 0;
		for (int j=0; j<neighbourhood.size() ; j++){
			if (neighbourhood.get(j).getPBest()<neighbourhood.get(min).getPBest()){
				min = j;
			}
			tBest = neighbourhood.get(min).getPBest();
			for(int i=0; i<Constants.DIMENSION; i++){					
				tBestPosition[i] = neighbourhood.get(min).getPBestPosition(i);
			}

		}
	}

	public double getTBest(){
		return tBest;
	}

	public ArrayList<Object> popNonNeighbours(){
		ArrayList<Object> array = new ArrayList<Object>();
		for (int i=0; i<Constants.SWARM_SIZE; i++){
			array.add(i);
		}		
		return array;
	}
	
	public void removeNonNeighbour(int index){
		nonNeighbours.remove(index);
	}
	
	public int getNonNeighboursSize() {
		return nonNeighbours.size();
	}

	public int findNonNeighbour(){
		int r;
		r = generator.nextInt((nonNeighbours.size()-3));
		nonNeighbours.remove(r);
		return (int) nonNeighbours.get(r);
	}

	public String toString(){
		String list = "Positions: ";
		for (int i=0; i<Constants.DIMENSION; i++){
			list += position[i] + " ";
		}
		list += "\nVelocities: ";
		for (int i=0; i<Constants.DIMENSION; i++){
			list += velocity[i] + " ";
		}
		list += "\nFitness: " + getFitness();
		list += "\npBest:   " + getPBest() + "\n";
		list += "pBest Positions: " ;
		for (int i=0; i<Constants.DIMENSION; i++){
			list += pBestPosition[i] + " ";
		}
		list += " \nNeighbour: ";
		for (int i=0; i<neighbourhood.size();i++){
			list += "\n " + getNeighbourhood(i).getPBest() ; 
		}
		list += "\ntBest: " + getTBest();
		list += "\nTBest Positions: " ;
		for (int i=0; i<Constants.DIMENSION; i++){
			list += tBestPosition[i] + " ";
		}
		//list += "\n";
		return list; 

	}

}
