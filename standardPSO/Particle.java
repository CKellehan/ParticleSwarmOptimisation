
package standardPSO;
import java.util.Random;
 
public class Particle{
 
    Random generator = new Random();
 
    protected double fitness;
    protected double[] velocity;
    protected double[] position;
    protected double pBest;
    protected double[] pBestPosition;
     
    //diversity variables
    private double[] diffFromMean = new double[Constants.DIMENSION];
    private double sumSqrDiffFromMean = 0;
 
    public Particle() {
        velocity = new double[Constants.DIMENSION];
        position = new double[Constants.DIMENSION];
        pBestPosition = new double[Constants.DIMENSION];        
    }
 
    public Particle(double[] velocity, double[] position) {
        this.velocity = velocity;
        this.position = position;
        velocity = new double[Constants.DIMENSION];
        position = new double[Constants.DIMENSION];
        pBestPosition = new double[Constants.DIMENSION];
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
 
            if (velocity[i]<Functions.boundLow-Functions.boundHigh){
                velocity[i]= Functions.boundLow-Functions.boundHigh ;
            }
            else if (velocity[i]>Functions.boundHigh-Functions.boundLow){
                velocity[i]=Functions.boundHigh-Functions.boundLow;
            }
        } 
 
 
    }
    public double[] getPosition() {
        return position;
    }
     
    public double getPosition(int i) {
        return position[i];
    }
 
 
    public void setPosition(double[] position) {
        this.position = position;
    }
 
    public void updatePosition(){
        for (int i=0; i<Constants.DIMENSION; i++){
            if (position[i] + velocity[i] < Functions.boundLow){
                position[i] = 2*(Functions.boundLow) - (position[i]) - (velocity[i]);           
            }
            else if (position[i] + velocity[i] > Functions.boundHigh){
                position[i] = 2*(Functions.boundHigh) - (position[i]) - (velocity[i]);          
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
        list += "\n";
        return list; 
 
    }
}