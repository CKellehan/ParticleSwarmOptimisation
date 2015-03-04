package standardPSO;
public class PSOMain {

	public static void main(String[] args) {
				
		OptimisationProcess g = new OptimisationProcess();
		g.optimisation();
		
		LBestOptimisationProcess l = new LBestOptimisationProcess();
		l.optimisation();
		
		VNOptimisationProcess v = new VNOptimisationProcess();
		v.optimisation();
		
		GIDNOptimisationProcess t = new GIDNOptimisationProcess();
		t.optimisation();


	}

}
