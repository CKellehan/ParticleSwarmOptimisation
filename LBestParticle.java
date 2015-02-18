package standardPSO;
import java.util.ArrayList;


public class LBestParticle extends Particle {

	private ArrayList<LBestParticle> neighbourhood = new ArrayList<LBestParticle>();
	double lBest;
	double[] lBestPosition;

	public LBestParticle() {
		super();
		lBestPosition = new double[Constants.DIMENSION];
	}

	public LBestParticle(double[] velocity, double[] position) {
		super(velocity, position);
		lBestPosition = new double[Constants.DIMENSION];
	}

	public void updateVelocity(){
		for (int i=0; i<Constants.DIMENSION; i++){	
			double r1 = generator.nextDouble();
			//System.out.println("r1: " + r1);
			double r2 = generator.nextDouble();
			//System.out.println("r2: " + r2);
			velocity[i] = Constants.CHI * (velocity[i]  + Constants.C1*r1*(pBestPosition[i] - position[i]) + Constants.C2*r2*(lBestPosition[i] - position[i]));

			if (velocity[i]<Functions.BOUND_LOW-Functions.BOUND_HIGH){
				velocity[i]= Functions.BOUND_LOW-Functions.BOUND_HIGH ;
			}
			else if (velocity[i]>Functions.BOUND_HIGH-Functions.BOUND_LOW){
				velocity[i]=Functions.BOUND_HIGH-Functions.BOUND_LOW;
			}
		} 
	}
	
	
	public void setNeighbourhood(ArrayList<LBestParticle> n) {
		for (int i=0; i<n.size(); i++){
			neighbourhood.add(n.get(i));
		}
	}

	public LBestParticle getNeighbourhood(int i){
		return neighbourhood.get(i);	
	}

	public void setLBest(){
		int min = 0;
		for (int j=0; j<neighbourhood.size() ; j++){
			if (neighbourhood.get(j).getPBest()<neighbourhood.get(min).getPBest()){
				min = j;
			}
			lBest = neighbourhood.get(min).getPBest();
			for(int i=0; i<Constants.DIMENSION; i++){					
				lBestPosition[i] = neighbourhood.get(min).getPBestPosition(i);
			}

		}
	}

	public double getLBest(){
		return lBest;
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
		list += "\nlBest: " + getLBest();
		list += "\nlBest Positions: " ;
		for (int i=0; i<Constants.DIMENSION; i++){
			list += lBestPosition[i] + " ";
		}
		//list += "\n";
		return list; 

	}

}
