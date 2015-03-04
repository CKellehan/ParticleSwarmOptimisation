package standardPSO;

public interface Constants {


	int SWARM_SIZE = 60;
	int ITERATION = 1000;
	int DIMENSION = 30;
	double C1 = 2.05;
	double C2 = 2.05;
	double PHI = C1 + C2;
	double CHI = 2/(Math.abs(2-PHI-Math.sqrt(Math.pow(PHI, 2) - 4*PHI)));

	//int FUNCTION=1;

}
