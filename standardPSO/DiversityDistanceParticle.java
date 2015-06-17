package standardPSO;


import java.util.ArrayList;

public class DiversityDistanceParticle extends Particle {

	private ArrayList<DiversityDistanceParticle> neighbourhood = new ArrayList<DiversityDistanceParticle>();
	private ArrayList<DiversityDistanceParticle> nonNeighbourhood = new ArrayList<DiversityDistanceParticle>();
	double[] distances;

	private double dBest;
	private double[] dBestPosition;

	private double[] diffFromMean = new double[Constants.DIMENSION];
	private double sumSqrDiffFromMean = 0;

	public DiversityDistanceParticle() {
		super();
		dBestPosition = new double[Constants.DIMENSION];
	}

	public DiversityDistanceParticle(double[] velocity, double[] position) {
		super(velocity, position);
		dBestPosition = new double[Constants.DIMENSION];
	}

	public void updateVelocity(){
		for (int i=0; i<Constants.DIMENSION; i++){	
			double r1 = generator.nextDouble();
			//System.out.println("r1: " + r1);
			double r2 = generator.nextDouble();
			//System.out.println("r2: " + r2);
			velocity[i] = Constants.CHI * (velocity[i]  + Constants.C1*r1*(pBestPosition[i] - position[i]) + Constants.C2*r2*(dBestPosition[i] - position[i]));

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

	public void setNeighbourhood(ArrayList<DiversityDistanceParticle> n) {
		for (int i=0; i<n.size(); i++){
			neighbourhood.add(n.get(i));
		}
	}

	public void addNeighbour(DiversityDistanceParticle n) {
		neighbourhood.add(n);
	}

	public int getNeighbourhoodSize() {
		return neighbourhood.size();
	}

	public DiversityDistanceParticle getNeighbourhood(int i){
		return neighbourhood.get(i);	
	}

	public void setDBest(){
		int min = 0;
		for (int j=0; j<neighbourhood.size() ; j++){
			if (neighbourhood.get(j).getPBest()<neighbourhood.get(min).getPBest()){
				min = j;
			}
			dBest = neighbourhood.get(min).getPBest();
			for(int i=0; i<Constants.DIMENSION; i++){					
				dBestPosition[i] = neighbourhood.get(min).getPBestPosition(i);
			}
		}
	}

	public double getDBest(){
		return dBest;
	}


	public void setNonNeighbourhood(ArrayList<DiversityDistanceParticle> n) {
		for (int i=0; i<n.size(); i++){
			nonNeighbourhood.add(n.get(i));
		}
	}

	public void removeNonNeighbour(DiversityDistanceParticle n) {
		nonNeighbourhood.remove(n);
	}

	public void addFurthestDistnaceParticleFromNonNeighbourhoods(){
		distances = new double[nonNeighbourhood.size()];
		for (int i=0; i<nonNeighbourhood.size(); i++){
			double d=0;
			for (int j=0; j<Constants.DIMENSION; j++){
				d += (position[j]-nonNeighbourhood.get(i).getPosition(j))*(position[j]-nonNeighbourhood.get(i).getPosition(j));
			}
			d = Math.sqrt(d);
			//			System.out.println(d);
			distances[i]=d;		
		}

		int max = 0;
		for (int j=1; j<nonNeighbourhood.size(); j++){
			if (distances[j]>distances[max]){
				max = j;
			}
		}
		addNeighbour(nonNeighbourhood.get(max));
		removeNonNeighbour(nonNeighbourhood.get(max));
	}

	public void removeNonNeighbourhood(int index){
		nonNeighbourhood.remove(index);
	}

	public int getNonNeighbourhoodSize() {
		return nonNeighbourhood.size();
	}

	public void setDiffFromMean(double[] mean){
		for (int i=0; i<Constants.DIMENSION; i++){
			diffFromMean[i] = mean[i] - position[i];
		}
	}

	public void setSumSqrDiffFromMean(){
		for (int i=0; i<Constants.DIMENSION; i++){
			sumSqrDiffFromMean += (diffFromMean[i]*diffFromMean[i]);
		}
	}

	public double[] getDiveFromMean(){
		return diffFromMean;
	}

	public double getSumSqrDiffFromMean(){
		return sumSqrDiffFromMean;
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
		list += "\ndBest: " + getDBest();
		list += "\nDBest Positions: " ;
		for (int i=0; i<Constants.DIMENSION; i++){
			list += dBestPosition[i] + " ";
		}
		//list += "\n";
		return list; 

	}

}


