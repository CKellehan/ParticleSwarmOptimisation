package standardPSO;

public class MainTest {

	public static void main(String[] args) {

		//gBest
		//		OptimisationProcess g = new OptimisationProcess();
		//		g.optimisation();
		//		
		//		System.out.println("End of process " + g.getGBestAtEactIteration(Constants.ITERATION-1));

		TestOptimisationProcess g = new TestOptimisationProcess();
		g.optimisation();

		System.out.println();
		System.out.println("End of process " + g.getGBestAtEactIteration(Constants.ITERATION-1));
		System.out.print("gBestPosition: ");
		for (int i= 0; i<Constants.DIMENSION; i++){
			System.out.print(g.gBestPosition[i] + " ");
		}
	}

}
