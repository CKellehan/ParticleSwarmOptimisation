package functions;

import standardPSO.Constants;

public class MainFunctions {

	public static void main(String[] args) {

		benchmark theBenchmark = new benchmark(1);
		
		double[] positions = new double[]{-3.9311900e+001,  5.8899900e+001, -4.6322400e+001, -7.4651500e+001, -1.6799700e+001,
			-8.0544100e+001, -1.0593500e+001,  2.4969400e+001,  8.9838400e+001,  9.1119000e+000,
			-1.0744300e+001, -2.7855800e+001, -1.2580600e+001,  7.5930000e+000,  7.4812700e+001,
			6.8495900e+001, -5.3429300e+001,  7.8854400e+001, -6.8595700e+001,  6.3743200e+001,  
			3.1347000e+001, -3.7501600e+001,  3.3892900e+001, -8.8804500e+001, -7.8771900e+001, 
			-6.6494400e+001,  4.4197200e+001,  1.8383600e+001,  2.6521200e+001,  8.4472300e+001};
		
		for (int i=0; i<positions.length; i++)
		{
			System.out.println(positions[i]);
		}
		
		System.out.println("Fitness " +theBenchmark.runTest(1, positions));
		
		
		
	



	}

}
