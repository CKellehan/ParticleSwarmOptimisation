package ideas;
import hirondelle.date4j.DateTime;

import org.ejml.alg.generic.GenericMatrixOps;
import org.ejml.ops.RandomMatrices;
import org.ejml.data.DenseMatrix64F;
import org.ejml.simple.SimpleBase;
import org.ejml.ops.CommonOps;

import standardPSO.Constants;

import java.util.Random;

public class MatrixTest {

	public static void main(String[] args){

		DateTime now = new DateTime("2015-04-08");
		System.out.println(now);

		Random rand = new Random();
		
		int dim =3;
		double sum =0;

		DenseMatrix64F a = new DenseMatrix64F(dim, dim);
		
		double[] array = new double[]{-52.76959633,86.55621057,-43.19378744};

		for (int i=0; i<dim;i++){
			a.add(0, i, array[i]);
		}

		a.print();

		System.out.println("________________________________");

		DenseMatrix64F b =  new DenseMatrix64F(dim, dim);
		
		
		b.add(0, 0, 0.102);
		b.add(0, 1, 0.122);
		b.add(0, 2, -0.195);
		b.add(1, 0, 0.289);
		b.add(1, 1, 0.006);
		b.add(1, 2, -0.119);
		b.add(2, 0, -0.287);
		b.add(2, 1, -0.223);
		b.add(2, 2, 0);
		b.print();
		
		DenseMatrix64F c = new DenseMatrix64F(dim,dim);

		CommonOps.mult(a,  b,  c);
		System.out.println("________________________________");
		c.print();


		double[] newArray = new double[dim];
		
		for (int i=0; i<dim;i++){
			newArray[i] = c.get(0, i);
			System.out.println(newArray[i]);
		}


		sum = elliptic(newArray);			
		sum -= 450;	
		System.out.println("Sum: " +sum);




	}
	
	public static double elliptic(double[] pos){
		double sum=0;	

		for(int i=0; i<Constants.DIMENSION; i++){				
			sum += Math.pow(1000000,(i-1/Constants.DIMENSION-1))*Math.pow(pos[i],2);		
		}
		return sum;
	}



}
