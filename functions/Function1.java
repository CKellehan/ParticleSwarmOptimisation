package functions;

import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.CommonOps;
import org.ejml.ops.RandomMatrices;

import java.util.Random;


import standardPSO.Constants;
import standardPSO.Functions;

//f3 Shifted Rotated High Conditioned Elliptic Function
public class Function1 {
	static Random rand = new Random();
	static double[] shiftOptimum;
	static DenseMatrix64F m;

	public static void main(String[] args) {
		

		int dimension = 30;

		double[] pos = new double[dimension];
		double[] z = new double[dimension];
		
		

		for(int i=0; i<dimension; i++){
			pos[i]=0;
		}
	
		
		generateShiftOptimum();
		
//		for(int i=0; i<dimension; i++){
//			z[i]=pos[i]-shiftOptimum[i];
//		}
		
		generateOrthogonalMatrix();
		
		z= multiplyByOrthogonalMatrix(pos);
		
		
		
		double sum=0;
		for(int i=0; i<Constants.DIMENSION; i++){				
			sum += Math.pow(1000000,(i-1/Constants.DIMENSION-1))*Math.pow(z[i],2);		
		}



		sum -= 450;

		System.out.print("Sum: " + sum );
	}
	
	public static void generateShiftOptimum(){
		shiftOptimum = new double[Constants.DIMENSION];
		for (int i=0; i<Constants.DIMENSION; i++ ){
			shiftOptimum[i] = rand.nextDouble() * (Functions.boundHigh-Functions.boundLow) + Functions.boundLow;
		}
		for (int i=0; i<Constants.DIMENSION; i++ ){
			System.out.println(shiftOptimum[i]);
		}
	}

	public static void generateOrthogonalMatrix(){
		m = RandomMatrices.createOrthogonal(Constants.DIMENSION, Constants.DIMENSION, rand);
		m.print();
		
	}

	public static double[] multiplyByOrthogonalMatrix(double[] z){
		DenseMatrix64F a = new DenseMatrix64F(Constants.DIMENSION, Constants.DIMENSION);
		for (int i=0; i<Constants.DIMENSION;i++){
			a.add(0, i, z[i]);
		}
		DenseMatrix64F c = new DenseMatrix64F(Constants.DIMENSION, Constants.DIMENSION);
		CommonOps.mult(a,  m,  c);

		double[] newArray = new double[Constants.DIMENSION];

		for (int i=0; i<Constants.DIMENSION;i++){
			newArray[i] = c.get(0, i);
		}
		return newArray;
	}



}
