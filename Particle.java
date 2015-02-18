package standardPSO;
import java.util.Random;

public class Particle{

	Random generator = new Random();

	protected double fitness;
	protected double[] velocity;
	protected double[] position;
	protected double pBest;
	protected double[] pBestPosition;

	public Particle() {
		velocity = new double[Constants.DIMENSION];
		position = new double[Constants.DIMENSION];
		pBestPosition = new double[Constants.DIMENSION];
	}

	public Particle(double[] velocity, double[] position) {
		this.velocity = velocity;
		this.position = position;
	}


	public double[] getVelocity() {
		return velocity;
	}

	public void setVelocity(double[] velocity) {
		this.velocity = velocity;
	}

	public void updateVelocity(double[] gBestPosition){
		for (int i=0; i<Constants.DIMENSION; i++){	
			double r1 = generator.nextDouble();
			//System.out.println("r1: " + r1);
			double r2 = generator.nextDouble();
			//System.out.println("r2: " + r2);
			velocity[i] = Constants.CHI * (velocity[i]  + Constants.C1*r1*(pBestPosition[i] - position[i]) + Constants.C2*r2*(gBestPosition[i] - position[i]));

			if (velocity[i]<Functions.BOUND_LOW-Functions.BOUND_HIGH){
				velocity[i]= Functions.BOUND_LOW-Functions.BOUND_HIGH ;
			}
			else if (velocity[i]>Functions.BOUND_HIGH-Functions.BOUND_LOW){
				velocity[i]=Functions.BOUND_HIGH-Functions.BOUND_LOW;
			}
		} 


	}
	public double[] getPosition() {
		return position;
	}

	public void setPosition(double[] position) {
		this.position = position;
	}

	public void updatePosition(){
		for (int i=0; i<Constants.DIMENSION; i++){
			if (position[i] + velocity[i] < Functions.BOUND_LOW){
				position[i] = 2*(Functions.BOUND_LOW) - (position[i]) - (velocity[i]);			
			}
			else if (position[i] + velocity[i] > Functions.BOUND_HIGH){
				position[i] = 2*(Functions.BOUND_HIGH) - (position[i]) - (velocity[i]);			
			}
			else{
				position[i] = position[i] + velocity[i];
			}
		}
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness() {
		fitness = Functions.evaluate(position);
		//        fitness = Rosenbrock.evaluate(position);
	}


	public double getPBest() {
		return pBest;
	}

	public void setPBest() {
		pBest = fitness;
	}
	public double[] getPBestPosition() {
		return pBestPosition;
	}

	public double getPBestPosition(int i) {
		return pBestPosition[i];
	}

	public void setPBestPosition() {
		for(int i=0; i<Constants.DIMENSION; i++){					
			pBestPosition[i] = position[i];
		}
	}

	public void updatePBest() {
		if (fitness < pBest){
			pBest = fitness;
			for(int i=0; i<Constants.DIMENSION; i++){					
				pBestPosition[i] = position[i];
			}
		}
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
		list += "\n";
		return list; 

	}
}

