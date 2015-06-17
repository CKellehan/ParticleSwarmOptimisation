package functions;

public interface Constants {


	int SWARM_SIZE = 60;
	int ITERATION = 10000;
	int DIMENSION = 30;
	double C1 = 2.05;
	double C2 = 2.05;
	double PHI = C1 + C2;
	double CHI = 2/(Math.abs(2-PHI-Math.sqrt(Math.pow(PHI, 2) - 4*PHI)));


	//	//1.Sphere
	//	//2.Rosenbrock
	//	//3.Ackley
	//	//4.Griewank
	//	//5.Rastrigin
	//	//6.Schaffer - use 2D
	//	//7. Griewank10 - use 10D
	int functionNo = 10;

}

