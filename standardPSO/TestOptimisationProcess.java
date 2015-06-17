package standardPSO;
 
import java.util.Random;
import java.util.ArrayList;
 
 
public class TestOptimisationProcess extends OptimisationProcess {
 
    Random generator = new Random();
    ArrayList<TestParticle> swarm = new ArrayList<TestParticle>();
 
    String testOutput ="";
 
    double gBest;
    double[] gBestPosition = new double[DIMENSION];
 
    //set neighbourhood number to 0 initially
    int h = 3;
    int b = 3;
    int y = 2;
    double maxT = ITERATION;
    int N = SWARM_SIZE; 
    
    public TestOptimisationProcess(){
        function  = new Functions(PSOMain.functionNo);      
    }
 
    public void optimisation(){
 
        initialiseSwarm(); //generate initial population setting velocity and position of each particle
        //      System.out.println("Test Swarm initialised");
 
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
 
        //Create  initial tBest neighbourhood for each particle
        for (int j=0; j<SWARM_SIZE; j++){
            ArrayList<TestParticle> n = new ArrayList<TestParticle>();
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
            swarm.get(j).setTBest();
        }
        // Remove neighbours from nonNeighbour list in TestParticle object
        for (int k=0; k<SWARM_SIZE; k++){
            if (k == 0){
                swarm.get(k).removeNonNeighbour(SWARM_SIZE-1);
                swarm.get(k).removeNonNeighbour(k+1);
                swarm.get(k).removeNonNeighbour(k);
            }
            else if (k == SWARM_SIZE-1){
                swarm.get(k).removeNonNeighbour(k);
                swarm.get(k).removeNonNeighbour(k-1);
                swarm.get(k).removeNonNeighbour(0);
            }
            else
            {
                swarm.get(k).removeNonNeighbour(k+1);
                swarm.get(k).removeNonNeighbour(k);
                swarm.get(k).removeNonNeighbour(k-1);
            }
        }
        //      System.out.println("nonNeighbour Size: " + swarm.get(0).getNonNeighboursSize());
 
        //      beginning of iterations
        for(int t=0; t<ITERATION;t++ ) {
 
            //update each particle's neighbourhood size
            double hNew = Math.pow((t+1)/maxT,y) * N + b;
            //          System.out.println( t + "   " + Math.floor(hNew));
 
            if (h<Math.floor(hNew)){
                //System.out.println(t + "Add neighbour");
                for (int j=0; j<SWARM_SIZE; j++){
                    if(swarm.get(j).getNonNeighboursSize()>=4){
                        int index = swarm.get(j).findNonNeighbour();
                        //                  System.out.println(index);
                        swarm.get(j).addNeighbour(swarm.get(index));
                    }
                    else if(swarm.get(j).getNonNeighboursSize()>0){
                        swarm.get(j).addNeighbour(swarm.get(0));
                        swarm.get(j).removeNonNeighbour(0);
                    }
                }
                //              System.out.println("Non Neighbourhood Size: " + swarm.get(0).getNonNeighboursSize());
                //              System.out.println("neighbourhood Size: " + swarm.get(0).getNeighbourhoodSize());
            }
            h=(int) Math.floor(hNew);
 
 
            for (int j=0; j<SWARM_SIZE; j++){
                if(t%50==0 && j%10==0){
                    swarm.get(j).reinitialiseVelocity();;
                }
                else{
                    swarm.get(j).updateVelocity();
                }
                swarm.get(j).updatePosition();
                swarm.get(j).setFitness();
                swarm.get(j).updatePBest();
                swarm.get(j).setTBest();
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
 
 
        //      System.out.println("gBest: " + gBest);
        //      System.out.print("gBestPosition: ");
        //      for (int i= 0; i<DIMENSION; i++){
        //          System.out.print(gBestPosition[i] + " ");
        //      }
        //      System.out.println();
        //      System.out.println("______________________________________");
    }
 
 
 
    public   void initialiseSwarm(){
 
        for (int j=0; j<SWARM_SIZE; j++){
 
            //create new particle
            TestParticle p = new TestParticle();
 
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
                //              System.out.println(j +", "+ i + ": " + swarm.get(j).getPosition(i));
                position += swarm.get(j).getPosition(i);            
            }
            positionMean[i]= position / SWARM_SIZE; 
            //          System.out.println(positionMean[i]);
        }
    }
 
    public void calculateDiversity(){
        divSum=0; diversity=0;
        for (int k=0; k<SWARM_SIZE; k++){
            sum =0; 
            for (int j=0; j<DIMENSION; j++){
                //              System.out.println(swarm.get(k).getPosition(j)-positionMean[j]);
                sum += (swarm.get(k).getPosition(j)-positionMean[j])*(swarm.get(k).getPosition(j)-positionMean[j]);
                //              System.out.println("Sum: " + sum);
            }
            double sqrt = Math.sqrt(sum);
            //          System.out.println("Square root of sum: " + sqrt);
            divSum += sqrt;
        }
        diversity = divSum/SWARM_SIZE;
        diversityOutput += diversity + "\n ";
        //          System.out.println("Diversity: " + diversity);
    }
 
 
 
 
 
 
}