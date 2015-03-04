package standardPSO;
public class Functions {

	//		public static final double BOUND_LOW = -5.12;
	//		public static final double BOUND_HIGH = 5.12;
	//	
	//		public static double evaluate(double[] pos){
	//			double sum = 0;
	//			for(int i=0; i<Constants.DIMENSION; i++){
	//				sum += Math.pow(pos[i],2);
	//			}
	//			return sum;
	//		}

	//	//Rosenbrock
	//	public static final double BOUND_LOW = -2.048;
	//	public static final double BOUND_HIGH = 2.048;
	//
	//	public static double evaluate(double[] pos){
	//		double sum = 0;
	//		for(int i=0; i<Constants.DIMENSION-1; i++){
	//			sum += 100*Math.pow(pos[i+1]-(pos[i])*(pos[i]),2) + Math.pow(pos[i]-1, 2);
	//		}
	//		return sum;
	//	}

	//Ackley
	public static final double BOUND_LOW = -32;
	public static final double BOUND_HIGH = 32;

	public static double evaluate(double[] pos){

		double sum1 = 0, sum2 = 0; 
		double a = 20.0;
		double b = 0.2;
		double c = 2*Math.PI;

		for ( int i = 0; i < pos.length ; i++) {
			sum1 += pos[i]*pos[i];
			sum2 += Math.cos(c *pos[i]);
		}

		double part1 = -a * Math.exp(-b*Math.sqrt(sum1/30.0));
		double part2 = -Math.exp(sum2/30.0);
		double part3 = Math.exp(1);
		double part4 = a;
		return part1 + part2 + part3 +part4;
	}

	//	//Griewank
	//	public static final double BOUND_LOW = -600;
	//	public static final double BOUND_HIGH = 600;
	//
	//	public static double evaluate(double[] pos){
	//
	//		double sum = 0, prod = 1; 
	//
	//		for ( int i = 0; i < pos.length ; i++) {
	//			sum += pos[i]*pos[i];
	//			double div =Math.sqrt(i+1);
	//			prod *= Math.cos(pos[i]/div);
	//		}
	//
	//		double part1 = 1;
	//		double part2 = (sum/4000);
	//		double part3 = -prod;
	//		return part1 + part2 + part3;
	//	}

	//Rastrigin
	//	public static final double BOUND_LOW = -5.12;
	//	public static final double BOUND_HIGH = 5.12;
	//
	//	public static double evaluate(double[] pos){
	//
	//		double sum = 0; 
	//		double a = 10.0;
	//		double n = Constants.DIMENSION;
	//		double c = 2*Math.PI;
	//
	//		for ( int i = 0; i < pos.length ; i++) {
	//			sum += pos[i]*pos[i] - a*Math.cos(c * pos[i]);			
	//		}
	//
	//		double answer = a*n + sum;
	//
	//		return answer;
	//	}

	//	//Schaffer -2D
	//		public static final double BOUND_LOW = -100;
	//		public static final double BOUND_HIGH = 100;
	//	
	//		public static double evaluate(double[] pos){
	//	
	//			double part1 = 0.5;
	//			double x =pos[0]; 
	//			double y =pos[1];
	//			double part2 = Math.sin(x*x - y*y)*Math.sin(x*x - y*y) -0.5;
	//			double part3 = (1 + 0.001*(x*x + y*y))*(1 + 0.001*(x*x + y*y));
	//			double answer = part1 + (part2 / part3);
	//		
	//			return answer;
	//		}

	//	//Griewank10 - use 10D
	//		public static final double BOUND_LOW = -600;
	//		public static final double BOUND_HIGH = 600;
	//	
	//		public static double evaluate(double[] pos){
	//	
	//			double sum = 0, prod = 1; 
	//	
	//			for ( int i = 0; i < pos.length ; i++) {
	//				sum += pos[i]*pos[i];
	//				double div =Math.sqrt(i+1);
	//				prod *= Math.cos(pos[i]/div);
	//			}
	//	
	//			double part1 = 1;
	//			double part2 = (sum/4000);
	//			double part3 = -prod;
	//			return part1 + part2 + part3;
	//		}
}
