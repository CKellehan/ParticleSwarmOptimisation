package standardPSO;
import java.util.ArrayList;

public class VNParticle extends Particle {
	
	private ArrayList<VNParticle> neighbourhood = new ArrayList<VNParticle>();
	double vNBest;
	double[] vNBestPosition;

	public VNParticle() {
		super();
		vNBestPosition = new double[Constants.DIMENSION];
	}

	public VNParticle(double[] velocity, double[] position) {
		super(velocity, position);
		vNBestPosition = new double[Constants.DIMENSION];
	}

	
	public void updateVelocity(){
		for (int i=0; i<Constants.DIMENSION; i++){	
			double r1 = generator.nextDouble();
			//System.out.println("r1: " + r1);
			double r2 = generator.nextDouble();
			//System.out.println("r2: " + r2);
			velocity[i] = Constants.CHI * (velocity[i]  + Constants.C1*r1*(pBestPosition[i] - position[i]) + Constants.C2*r2*(vNBestPosition[i] - position[i]));

			if (velocity[i]<Functions.BOUND_LOW-Functions.BOUND_HIGH){
				velocity[i]= Functions.BOUND_LOW-Functions.BOUND_HIGH ;
			}
			else if (velocity[i]>Functions.BOUND_HIGH-Functions.BOUND_LOW){
				velocity[i]=Functions.BOUND_HIGH-Functions.BOUND_LOW;
			}
		} 
	}
	
	
	public void setNeighbourhood(ArrayList<VNParticle> n) {
		for (int i=0; i<n.size(); i++){
			neighbourhood.add(n.get(i));
		}
	}

	public VNParticle getNeighbourhood(int i){
		return neighbourhood.get(i);	
	}

	public void setVNBest(){
		int min = 0;
		for (int j=0; j<neighbourhood.size() ; j++){
			if (neighbourhood.get(j).getPBest()<neighbourhood.get(min).getPBest()){
				min = j;
			}
			vNBest = neighbourhood.get(min).getPBest();
			for(int i=0; i<Constants.DIMENSION; i++){					
				vNBestPosition[i] = neighbourhood.get(min).getPBestPosition(i);
			}

		}
	}

	public double getVNBest(){
		return vNBest;
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
		list += "\nVNBest: " + getVNBest();
		list += "\nNVBest Positions: " ;
		for (int i=0; i<Constants.DIMENSION; i++){
			list += vNBestPosition[i] + " ";
		}
		//list += "\n";
		return list; 

	}

}
