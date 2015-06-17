package standardPSO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;
import java.util.Scanner;

import java.util.StringTokenizer;

import functions.HCJob;
import functions.benchmark;

public class Functions {
	static Random rand = new Random();

	static int functionNo;

	static final public int NUM_FUNC = 10;
	private static MyHCJob theJob;

	public static double boundLow =-5.12;
	public static double boundHigh = 5.12;

	// Shifted global optimum
	private static double[] m_o;
	private static double[][] m_matrix;
	private static double[][] m_A;
	private static double[][] m_a;
	private static double[][] m_b;

	private final static double[][] m_o22 = new double[NUM_FUNC][Constants.DIMENSION];
	private final  double[][] m_o2;
	private static double[][][] m_M;

	private static double[] m_testPoint;
	private static double[] m_testPointM;
	private static double[] m_fmax;

	static double constant;
	double[][] m_data;

	// In order to avoid excessive memory allocation,
	// a fixed memory buffer is allocated for each function object.
	private static double[] m_A2;
	private static double[] m_B;
	private static double[] m_w;
	private static double[] m_z;
	private static double[] m_zM;
	private static double[][] m_z2;
	private static double[][] m_zM2;

	//Constructor
	public Functions(int f){

		theJob = new MyHCJob();

		//		m_o2 = new double[NUM_FUNC][Constants.DIMENSION];
		m_M = new double[NUM_FUNC][Constants.DIMENSION][Constants.DIMENSION];

		m_o = new double[Constants.DIMENSION];
		m_matrix = new double[Constants.DIMENSION][Constants.DIMENSION];
		m_A = new double[Constants.DIMENSION][Constants.DIMENSION];
		m_a = new double[Constants.DIMENSION][Constants.DIMENSION];
		m_b = new double[Constants.DIMENSION][Constants.DIMENSION];

		m_A2 = new double[Constants.DIMENSION];
		m_B = new double[Constants.DIMENSION];
		m_z = new double[Constants.DIMENSION];
		m_zM = new double[Constants.DIMENSION];

		m_testPoint = new double[Constants.DIMENSION];
		m_testPointM = new double[Constants.DIMENSION];
		m_fmax = new double[NUM_FUNC];

		m_w = new double[NUM_FUNC];
		m_z2 = new double[NUM_FUNC][Constants.DIMENSION];
		m_zM2 = new double[NUM_FUNC][Constants.DIMENSION];

		m_o2 = new double[NUM_FUNC][Constants.DIMENSION];



		functionNo = f;         
		if (functionNo ==8 ){
			generateShiftOptimum("sphere_func_data");
		}
		else if (functionNo == 9 ){
			generateShiftOptimum("schwefel_102_data");
		}
		else if (functionNo == 10 ){			
			// Load the shifted global optimum
			loadRowVectorFromFile("F:/PSOData/supportData/high_cond_elliptic_rot_data.txt", Constants.DIMENSION, m_o);
			// Load the matrix
			loadMatrixFromFile("F:/PSOData/supportData/elliptic_M_D30.txt", Constants.DIMENSION, Constants.DIMENSION, m_matrix);
			constant = Math.pow(1.0e6, 1.0/(Constants.DIMENSION-1.0));			
		}
		else if (functionNo == 11 ){
			generateShiftOptimum("schwefel_102_data");
		}
		else if (functionNo == 12 ){
			m_data = new double[Constants.DIMENSION+1][Constants.DIMENSION];

			// Load the shifted global optimum
			loadMatrixFromFile("F:/PSOData/supportData/schwefel_206_data.txt", Constants.DIMENSION+1, Constants.DIMENSION, m_data);
			for (int i = 0 ; i < Constants.DIMENSION ; i ++) {
				if ((i+1) <= Math.ceil(Constants.DIMENSION / 4.0))
					m_o[i] = -100.0;
				else if ((i+1) >= Math.floor((3.0 * Constants.DIMENSION) / 4.0))
					m_o[i] = 100.0;
				else
					m_o[i] = m_data[0][i];
			}
			for (int i = 0 ; i < Constants.DIMENSION ; i ++) {
				for (int j = 0 ; j < Constants.DIMENSION ; j ++) {
					m_A[i][j] = m_data[i+1][j];
				}
			}
			benchmark.Ax(m_B, m_A, m_o);
		}
		else if (functionNo == 13 ){
			generateShiftOptimum("rosenbrock_func_data");

		}
		else if (functionNo == 14 ){
			// Load the shifted global optimum
			loadRowVectorFromFile("F:/PSOData/supportData/griewank_func_data.txt", Constants.DIMENSION, m_o);
			// Load the matrix
			loadMatrixFromFile("F:/PSOData/supportData/griewank_M_D30.txt", Constants.DIMENSION, Constants.DIMENSION, m_matrix);

		}

		else if (functionNo == 15 ){

			// Load the shifted global optimum
			loadRowVectorFromFile("F:/PSOData/supportData/ackley_func_data.txt", Constants.DIMENSION, m_o);
			// Load the matrix
			loadMatrixFromFile("F:/PSOData/supportData/ackley_M_D30.txt", Constants.DIMENSION, Constants.DIMENSION, m_matrix);

			for (int i = 0 ; i < Constants.DIMENSION ; i += 2) {
				m_o[i] = -32.0;
			}
		}

		else if (functionNo == 16 ){
			// Load the shifted global optimum
			loadRowVectorFromFile("F:/PSOData/supportData/rastrigin_func_data.txt", Constants.DIMENSION, m_o);
		}

		else if (functionNo == 17 ){
			// Load the shifted global optimum
			loadRowVectorFromFile("F:/PSOData/supportData/rastrigin_func_data.txt", Constants.DIMENSION, m_o);
			// Load the matrix
			loadMatrixFromFile("F:/PSOData/supportData/rastrigin_M_D30.txt", Constants.DIMENSION, Constants.DIMENSION, m_matrix);
		}
		else if (functionNo == 18 ){
			// Load the shifted global optimum
			loadRowVectorFromFile("F:/PSOData/supportData/weierstrass_data.txt", Constants.DIMENSION, m_o);
			// Load the matrix
			loadMatrixFromFile("F:/PSOData/supportData/weierstrass_M_D30.txt", Constants.DIMENSION, Constants.DIMENSION, m_matrix);
		}
		else if (functionNo == 19 ){
			m_data = new double[100+100+1][Constants.DIMENSION];
			loadMatrixFromFile("F:/PSOData/supportData/schwefel_213_data.txt", m_data.length, Constants.DIMENSION, m_data);
			for (int i = 0 ; i < Constants.DIMENSION ; i ++) {
				for (int j = 0 ; j < Constants.DIMENSION ; j ++) {
					m_a[i][j] = m_data[i][j];
					m_b[i][j] = m_data[100+i][j];
				}
				m_o[i] = m_data[100+100][i];
			}
			for (int i = 0 ; i < Constants.DIMENSION ; i ++) {
				m_A2[i] = 0.0;
				for (int j = 0 ; j < Constants.DIMENSION ; j ++) {
					m_A2[i] += (m_a[i][j] * Math.sin(m_o[j]) + m_b[i][j] * Math.cos(m_o[j]));
				}
			}
		}

		else if (functionNo == 20 ){
			loadRowVectorFromFile("F:/PSOData/supportData/EF8F2_func_data.txt", Constants.DIMENSION, m_o);
			// z = x - o + 1 = x - (o - 1)
			// Do the "(o - 1)" part first
			for (int i = 0 ; i < Constants.DIMENSION ; i ++) {
				m_o[i] -= 1.0;
			}

		}
		else if (functionNo == 21 ){
			// Load the shifted global optimum
			benchmark.loadRowVectorFromFile("F:/PSOData/supportData/E_ScafferF6_func_data.txt", Constants.DIMENSION, m_o);
			// Load the matrix
			benchmark.loadMatrixFromFile("F:/PSOData/supportData/E_ScafferF6_M_D30.txt", Constants.DIMENSION, Constants.DIMENSION, m_matrix);
		}

		else if (functionNo == 22){

			double[] m_sigma = {
					1.0,	1.0,	1.0,	1.0,	1.0,
					1.0,	1.0,	1.0,	1.0,	1.0
			};
			double[] m_lambda = {
					1.0,		1.0,		10.0,		10.0,
					5.0/60.0,	5.0/60.0,	5.0/32.0,	5.0/32.0,
					5.0/100.0,	5.0/100.0
			};
			double[] m_func_biases = {
					0.0,	100.0,	200.0,	300.0,	400.0,
					500.0,	600.0,	700.0,	800.0,	900.0
			};
			// Load the shifted global optimum
			loadMatrixFromFile("F:/PSOData/supportData/hybrid_func1_data.txt", NUM_FUNC, Constants.DIMENSION, m_o2);
			// Generate identity matrices
			for (int i = 0 ; i < NUM_FUNC ; i ++) {
				for (int j = 0 ; j < Constants.DIMENSION ; j ++) {
					for (int k = 0 ; k < Constants.DIMENSION ; k ++) {
						m_M[i][j][k] = 0.0;
					}
				}
				for (int j = 0 ; j < Constants.DIMENSION ; j ++) {
					m_M[i][j][j] = 1.0;
				}
			}
			// Initialize the hybrid composition job object
			theJob.num_func = NUM_FUNC;
			theJob.num_dim = Constants.DIMENSION;
			theJob.C = 2000.0;
			theJob.sigma = m_sigma;
			theJob.biases = m_func_biases;
			theJob.lambda = m_lambda;
			theJob.o = m_o2;
			theJob.M = m_M;
			theJob.w = m_w;
			theJob.z = m_z2;
			theJob.zM = m_zM2;
			// Calculate/estimate the fmax for all the functions involved
			for (int i = 0 ; i < NUM_FUNC ; i ++) {
				for (int j = 0 ; j < Constants.DIMENSION ; j ++) {
					m_testPoint[j] = (5.0 / m_lambda[i]);
				}
				rotate(m_testPointM, m_testPoint, m_M[i]);
				m_fmax[i] = Math.abs(theJob.basic_func1(i, m_testPointM));
			}
			theJob.fmax = m_fmax;
		}

		else if (functionNo == 23 || functionNo == 24){

			double[] m_sigma = {
					1.0,	1.0,	1.0,	1.0,	1.0,
					1.0,	1.0,	1.0,	1.0,	1.0
			};
			double[] m_lambda = {
					1.0,		1.0,		10.0,		10.0,
					5.0/60.0,	5.0/60.0,	5.0/32.0,	5.0/32.0,
					5.0/100.0,	5.0/100.0
			};
			double[] m_func_biases = {
					0.0,	100.0,	200.0,	300.0,	400.0,
					500.0,	600.0,	700.0,	800.0,	900.0
			};
			// Load the shifted global optimum
			loadMatrixFromFile("F:/PSOData/supportData/hybrid_func1_data.txt", NUM_FUNC, Constants.DIMENSION, m_o2);
			// Load the matrix
			loadNMatrixFromFile("F:/PSOData/supportData/hybrid_func1_M_D30.txt", NUM_FUNC, Constants.DIMENSION, Constants.DIMENSION, m_M);

			// Initialize the hybrid composition job object
			theJob.num_func = NUM_FUNC;
			theJob.num_dim = Constants.DIMENSION;
			theJob.C = 2000.0;
			theJob.sigma = m_sigma;
			theJob.biases = m_func_biases;
			theJob.lambda = m_lambda;
			theJob.o = m_o2;
			theJob.M = m_M;
			theJob.w = m_w;
			theJob.z = m_z2;
			theJob.zM = m_zM2;
			// Calculate/estimate the fmax for all the functions involved
			for (int i = 0 ; i < NUM_FUNC ; i ++) {
				for (int j = 0 ; j < Constants.DIMENSION ; j ++) {
					m_testPoint[j] = (5.0 / m_lambda[i]);
				}
				rotate(m_testPointM, m_testPoint, m_M[i]);
				m_fmax[i] = Math.abs(theJob.basic_func(i, m_testPointM));
			}
			theJob.fmax = m_fmax;
		}


		else if (functionNo == 25){
			double[] m_sigma = {
					1.0,	2.0,	1.5,	1.5,	1.0,	1.0,
					1.5,	1.5,	2.0,	2.0
			};
			double[] m_lambda = {
					2.0*5.0/32.0,	5.0/32.0,	2.0*1,	1.0,			2.0*5.0/100.0,
					5.0/100.0,		2.0*10.0,	10.0,	2.0*5.0/60.0,	5.0/60.0
			};
			double[] m_func_biases = {
					0.0,	100.0,	200.0,	300.0,	400.0,
					500.0,	600.0,	700.0,	800.0,	900.0
			};
			// Load the shifted global optimum
			loadMatrixFromFile("F:/PSOData/supportData/hybrid_func2_data.txt", NUM_FUNC, Constants.DIMENSION, m_o2);
			for (int i = 0 ; i < Constants.DIMENSION ; i ++) {
				m_o2[9][i] = 0.0;
			}
			// Load the matrix
			benchmark.loadNMatrixFromFile("F:/PSOData/supportData/hybrid_func2_M_D30.txt", NUM_FUNC, Constants.DIMENSION, Constants.DIMENSION, m_M);

			// Initialize the hybrid composition job object
			theJob.num_func = NUM_FUNC;
			theJob.num_dim = Constants.DIMENSION;
			theJob.C = 2000.0;
			theJob.sigma = m_sigma;
			theJob.biases = m_func_biases;
			theJob.lambda = m_lambda;
			theJob.o = m_o2;
			theJob.M = m_M;
			theJob.w = m_w;
			theJob.z = m_z2;
			theJob.zM = m_zM2;
			// Calculate/estimate the fmax for all the functions involved
			for (int i = 0 ; i < NUM_FUNC ; i ++) {
				for (int j = 0 ; j < Constants.DIMENSION ; j ++) {
					m_testPoint[j] = (5.0 / m_lambda[i]);
				}
				benchmark.rotate(m_testPointM, m_testPoint, m_M[i]);
				m_fmax[i] = Math.abs(theJob.basic_func1(i, m_testPointM));
			}
			theJob.fmax = m_fmax;

		}

		else if (functionNo == 26){
			double[] m_sigma = {
					0.1,	2.0,	1.5,	1.5,	1.0,	1.0,
					1.5,	1.5,	2.0,	2.0
			};
			double[] m_lambda = {
					0.1*5.0/32.0,	5.0/32.0,	2.0*1,	1.0,			2.0*5.0/100.0,
					5.0/100.0,		2.0*10.0,	10.0,	2.0*5.0/60.0,	5.0/60.0
			};
			double[] m_func_biases = {
					0.0,	100.0,	200.0,	300.0,	400.0,
					500.0,	600.0,	700.0,	800.0,	900.0
			};

			// Load the shifted global optimum
			loadMatrixFromFile("F:/PSOData/supportData/hybrid_func2_data.txt", NUM_FUNC, Constants.DIMENSION, m_o2);
			for (int i = 0 ; i < Constants.DIMENSION ; i ++) {
				m_o2[9][i] = 0.0;
			}
			// Load the matrix
			loadNMatrixFromFile("F:/PSOData/supportData/hybrid_func2_M_D30.txt", NUM_FUNC, Constants.DIMENSION, Constants.DIMENSION, m_M);

			// Initialize the hybrid composition job object
			theJob.num_func = NUM_FUNC;
			theJob.num_dim = Constants.DIMENSION;
			theJob.C = 2000.0;
			theJob.sigma = m_sigma;
			theJob.biases = m_func_biases;
			theJob.lambda = m_lambda;
			theJob.o = m_o2;
			theJob.M = m_M;
			theJob.w = m_w;
			theJob.z = m_z2;
			theJob.zM = m_zM2;
			// Calculate/estimate the fmax for all the functions involved
			for (int i = 0 ; i < NUM_FUNC ; i ++) {
				for (int j = 0 ; j < Constants.DIMENSION ; j ++) {
					m_testPoint[j] = (5.0 / m_lambda[i]);
				}
				benchmark.rotate(m_testPointM, m_testPoint, m_M[i]);
				m_fmax[i] = Math.abs(theJob.basic_func1(i, m_testPointM));
			}
			theJob.fmax = m_fmax;
		}

		else if (functionNo == 27){
			double[] m_sigma = {
					1.0,	2.0,	1.5,	1.5,	1.0,	1.0,
					1.5,	1.5,	2.0,	2.0
			};
			double[] m_lambda = {
					2.0*5.0/32.0,	5.0/32.0,	2.0*1,	1.0,			2.0*5.0/100.0,
					5.0/100.0,		2.0*10.0,	10.0,	2.0*5.0/60.0,	5.0/60.0
			};
			double[] m_func_biases = {
					0.0,	100.0,	200.0,	300.0,	400.0,
					500.0,	600.0,	700.0,	800.0,	900.0
			};
			// Load the shifted global optimum
			loadMatrixFromFile("F:/PSOData/supportData/hybrid_func2_data.txt", NUM_FUNC, Constants.DIMENSION, m_o2);
			for (int i = 0 ; i < Constants.DIMENSION ; i ++) {
				m_o2[9][i] = 0.0;
			}
			for (int i = 1 ; i < Constants.DIMENSION ; i += 2) {
				m_o2[0][i] = 5.0;
			}
			// Load the matrix
			loadNMatrixFromFile("F:/PSOData/supportData/hybrid_func2_M_D30.txt", NUM_FUNC, Constants.DIMENSION, Constants.DIMENSION, m_M);

			// Initialize the hybrid composition job object
			theJob.num_func = NUM_FUNC;
			theJob.num_dim = Constants.DIMENSION;
			theJob.C = 2000.0;
			theJob.sigma = m_sigma;
			theJob.biases = m_func_biases;
			theJob.lambda = m_lambda;
			theJob.o = m_o2;
			theJob.M = m_M;
			theJob.w = m_w;
			theJob.z = m_z2;
			theJob.zM = m_zM2;
			// Calculate/estimate the fmax for all the functions involved
			for (int i = 0 ; i < NUM_FUNC ; i ++) {
				for (int j = 0 ; j < Constants.DIMENSION ; j ++) {
					m_testPoint[j] = (5.0 / m_lambda[i]);
				}
				benchmark.rotate(m_testPointM, m_testPoint, m_M[i]);
				m_fmax[i] = Math.abs(theJob.basic_func1(i, m_testPointM));
			}
			theJob.fmax = m_fmax;

		}

		else if (functionNo == 28){
			double[] m_sigma = {
					1.0,	1.0,	1.0,	1.0,	1.0,
					2.0,	2.0,	2.0,	2.0,	2.0
			};
			double[] m_lambda = {
					5.0*5.0/100.0,	5.0/100.0,	5.0*1.0,	1.0,			5.0*1.0,
					1.0,			5.0*10.0,	10.0,		5.0*5.0/200.0,	5.0/200.0
			};
			double[] m_func_biases = {
					0.0,	100.0,	200.0,	300.0,	400.0,
					500.0,	600.0,	700.0,	800.0,	900.0
			};
			// Load the shifted global optimum
			loadMatrixFromFile("F:/PSOData/supportData/hybrid_func3_data.txt", NUM_FUNC, Constants.DIMENSION, m_o2);
			// Load the matrix
			loadNMatrixFromFile("F:/PSOData/supportData/hybrid_func3_M_D30.txt", NUM_FUNC, Constants.DIMENSION, Constants.DIMENSION, m_M);

			// Initialize the hybrid composition job object
			theJob.num_func = NUM_FUNC;
			theJob.num_dim = Constants.DIMENSION;
			theJob.C = 2000.0;
			theJob.sigma = m_sigma;
			theJob.biases = m_func_biases;
			theJob.lambda = m_lambda;
			theJob.o = m_o2;
			theJob.M = m_M;
			theJob.w = m_w;
			theJob.z = m_z2;
			theJob.zM = m_zM2;
			// Calculate/estimate the fmax for all the functions involved
			for (int i = 0 ; i < NUM_FUNC ; i ++) {
				for (int j = 0 ; j < Constants.DIMENSION ; j ++) {
					m_testPoint[j] = (5.0 / m_lambda[i]);
				}
				benchmark.rotate(m_testPointM, m_testPoint, m_M[i]);
				m_fmax[i] = Math.abs(theJob.basic_func2(i, m_testPointM));
			}
			theJob.fmax = m_fmax;

		}

		else if (functionNo == 29){

			double[] m_sigma = {
					1.0,	1.0,	1.0,	1.0,	1.0,
					2.0,	2.0,	2.0,	2.0,	2.0
			};
			double[] m_lambda = {
					5.0*5.0/100.0,	5.0/100.0,	5.0*1.0,	1.0,			5.0*1.0,
					1.0,			5.0*10.0,	10.0,		5.0*5.0/200.0,	5.0/200.0
			};
			double[] m_func_biases = {
					0.0,	100.0,	200.0,	300.0,	400.0,
					500.0,	600.0,	700.0,	800.0,	900.0
			};

			// Load the shifted global optimum
			loadMatrixFromFile("F:/PSOData/supportData/hybrid_func3_data.txt", NUM_FUNC, Constants.DIMENSION, m_o2);
			// Load the matrix
			loadNMatrixFromFile("F:/PSOData/supportData/hybrid_func3_HM_D30.txt", NUM_FUNC, Constants.DIMENSION, Constants.DIMENSION, m_M);

			// Initialize the hybrid composition job object
			theJob.num_func = NUM_FUNC;
			theJob.num_dim = Constants.DIMENSION;
			theJob.C = 2000.0;
			theJob.sigma = m_sigma;
			theJob.biases = m_func_biases;
			theJob.lambda = m_lambda;
			theJob.o = m_o2;
			theJob.M = m_M;
			theJob.w = m_w;
			theJob.z = m_z2;
			theJob.zM = m_zM2;
			// Calculate/estimate the fmax for all the functions involved
			for (int i = 0 ; i < NUM_FUNC ; i ++) {
				for (int j = 0 ; j < Constants.DIMENSION ; j ++) {
					m_testPoint[j] = (5.0 / m_lambda[i]);
				}
				benchmark.rotate(m_testPointM, m_testPoint, m_M[i]);
				m_fmax[i] = Math.abs(theJob.basic_func2(i, m_testPointM));
			}
			theJob.fmax = m_fmax;

		}

		else if (functionNo == 30){
			double[] m_sigma = {
					1.0,	1.0,	1.0,	1.0,	1.0,
					2.0,	2.0,	2.0,	2.0,	2.0
			};
			double[] m_lambda = {
					5.0*5.0/100.0,	5.0/100.0,	5.0*1.0,	1.0,			5.0*1.0,
					1.0,			5.0*10.0,	10.0,		5.0*5.0/200.0,	5.0/200.0
			};
			double[] m_func_biases = {
					0.0,	100.0,	200.0,	300.0,	400.0,
					500.0,	600.0,	700.0,	800.0,	900.0
			};

			// Load the shifted global optimum
			loadMatrixFromFile("F:/PSOData/supportData/hybrid_func3_data.txt", NUM_FUNC, Constants.DIMENSION, m_o22);
			// Load the matrix
			loadNMatrixFromFile("F:/PSOData/supportData/hybrid_func3_M_D30.txt", NUM_FUNC, Constants.DIMENSION, Constants.DIMENSION, m_M);

			// Initialize the hybrid composition job object
			theJob.num_func = NUM_FUNC;
			theJob.num_dim = Constants.DIMENSION;
			theJob.C = 2000.0;
			theJob.sigma = m_sigma;
			theJob.biases = m_func_biases;
			theJob.lambda = m_lambda;
			theJob.o = m_o22;
			theJob.M = m_M;
			theJob.w = m_w;
			theJob.z = m_z2;
			theJob.zM = m_zM2;
			// Calculate/estimate the fmax for all the functions involved
			for (int i = 0 ; i < NUM_FUNC ; i ++) {
				for (int j = 0 ; j < Constants.DIMENSION ; j ++) {
					m_testPoint[j] = (5.0 / m_lambda[i]);
				}
				benchmark.rotate(m_testPointM, m_testPoint, m_M[i]);
				m_fmax[i] = Math.abs(theJob.basic_func2(i, m_testPointM));
			}
			theJob.fmax = m_fmax;
		}

		else if (functionNo == 31){
			double[] m_sigma = {
					2.0,	2.0,	2.0,	2.0,	2.0,
					2.0,	2.0,	2.0,	2.0,	2.0
			};
			double[] m_lambda = {
					10.0,		5.0/20.0,	1.0,	5.0/32.0,	1.0,
					5.0/100.0,	5.0/50.0,	1.0,	5.0/100.0,	5.0/100.0
			};
			double[] m_func_biases = {
					0.0,	100.0,	200.0,	300.0,	400.0,
					500.0,	600.0,	700.0,	800.0,	900.0
			};
			// Load the shifted global optimum
			loadMatrixFromFile("F:/PSOData/supportData/hybrid_func4_data.txt", NUM_FUNC, Constants.DIMENSION , m_o2);
			// Load the matrix
			loadNMatrixFromFile("F:/PSOData/supportData/hybrid_func4_M_D30.txt", NUM_FUNC, Constants.DIMENSION , Constants.DIMENSION , m_M);

			// Initialize the hybrid composition job object
			theJob.num_func = NUM_FUNC;
			theJob.num_dim = Constants.DIMENSION ;
			theJob.C = 2000.0;
			theJob.sigma = m_sigma;
			theJob.biases = m_func_biases;
			theJob.lambda = m_lambda;
			theJob.o = m_o2;
			theJob.M = m_M;
			theJob.w = m_w;
			theJob.z = m_z2;
			theJob.zM = m_zM2;
			// Calculate/estimate the fmax for all the functions involved
			for (int i = 0 ; i < NUM_FUNC ; i ++) {
				for (int j = 0 ; j < Constants.DIMENSION  ; j ++) {
					m_testPoint[j] = (5.0 / m_lambda[i]);
				}
				benchmark.rotate(m_testPointM, m_testPoint, m_M[i]);
				m_fmax[i] = Math.abs(theJob.basic_func3(i, m_testPointM));
			}
			theJob.fmax = m_fmax;
		}
		else if (functionNo == 32){
			double[] m_sigma = {
					2.0,	2.0,	2.0,	2.0,	2.0,
					2.0,	2.0,	2.0,	2.0,	2.0
			};
			double[] m_lambda = {
					10.0,		5.0/20.0,	1.0,	5.0/32.0,	1.0,
					5.0/100.0,	5.0/50.0,	1.0,	5.0/100.0,	5.0/100.0
			};
			double[] m_func_biases = {
					0.0,	100.0,	200.0,	300.0,	400.0,
					500.0,	600.0,	700.0,	800.0,	900.0
			};
			// Load the shifted global optimum
			loadMatrixFromFile("F:/PSOData/supportData/hybrid_func4_data.txt", NUM_FUNC, Constants.DIMENSION, m_o2);
			// Load the matrix
			loadNMatrixFromFile("F:/PSOData/supportData/hybrid_func4_M_D30.txt", NUM_FUNC, Constants.DIMENSION, Constants.DIMENSION, m_M);

			// Initialize the hybrid composition job object
			theJob.num_func = NUM_FUNC;
			theJob.num_dim = Constants.DIMENSION;
			theJob.C = 2000.0;
			theJob.sigma = m_sigma;
			theJob.biases = m_func_biases;
			theJob.lambda = m_lambda;
			theJob.o = m_o2;
			theJob.M = m_M;
			theJob.w = m_w;
			theJob.z = m_z2;
			theJob.zM = m_zM2;
			// Calculate/estimate the fmax for all the functions involved
			for (int i = 0 ; i < NUM_FUNC ; i ++) {
				for (int j = 0 ; j < Constants.DIMENSION ; j ++) {
					m_testPoint[j] = (5.0 / m_lambda[i]);
				}
				benchmark.rotate(m_testPointM, m_testPoint, m_M[i]);
				m_fmax[i] = Math.abs(theJob.basic_func3(i, m_testPointM));
			}
			theJob.fmax = m_fmax;

		}




	}


	public static double evaluate(double[] pos ){
		double sum = 0;


		//Sphere
		if (functionNo == 1){
			boundLow = -5.12;
			boundHigh = 5.12;
			sum = sphere(pos);
			//          System.out.println(sum);
		}

		//Rosenbrock
		else if (functionNo == 2){
			boundLow = -2.048;
			boundHigh = 2.048;
			sum = rosenbrock(pos);

		}

		//Ackley
		else if (functionNo == 3){
			boundLow = -32;
			boundHigh = 32;
			sum = ackley(pos);
		}

		//4.Griewank
		else if (functionNo == 4){
			boundLow = -600;
			boundHigh = 600;
			sum = griewank(pos);
		}

		//5.Rastrigin
		else if (functionNo == 5){
			boundLow = -5.12;
			boundHigh = 5.12;
			sum = rastrigin(pos);
		}

		//6. Schaffer - use 2D
		else if (functionNo == 6){
			boundLow = -100;
			boundHigh = 100;
			sum = schaffer(pos) ;
		}

		//7. Griewank10 - use 10D
		else if (functionNo == 7){
			boundLow = -600;
			boundHigh = 600;
			sum = griewank10(pos);
		}

		//8. f1 Shift Sphere
		else if (functionNo == 8){
			boundLow = -100;
			boundHigh = 100;

			double[] z = new double[Constants.DIMENSION];
			//Shift
			for(int i=0; i<Constants.DIMENSION; i++){
				z[i]=pos[i]-m_o[i];
			}
			//Pass shifted values to sphere function
			sum = sphere(z);
			sum -= 450;
		}

		//9. f2 Shift Schwefel's Problem
		else if (functionNo == 9){
			boundLow = -100;
			boundHigh = 100;
			double[] z = new double[Constants.DIMENSION];
			for(int i=0; i<Constants.DIMENSION; i++){
				z[i]=pos[i]-m_o[i];
			}
			sum = schwefel(z);
			sum -= 450;
		}

		//10. f3 Shifted Rotated High Conditioned Elliptic Function
		else if (functionNo == 10){
			boundLow = -100;
			boundHigh = 100;

			shift(m_z, pos, m_o);

			rotate(m_zM, m_z, m_matrix);

			for (int i = 0 ; i < Constants.DIMENSION ; i ++) {
				sum += Math.pow(constant, i) * m_zM[i] * m_zM[i];
			}

			sum -= 450; 
		}

		//11. f4 Shifted 
		else if (functionNo == 11){
			boundLow = -100;
			boundHigh = 100;

			double[] z = new double[Constants.DIMENSION];
			for(int i=0; i<Constants.DIMENSION; i++){
				z[i]=pos[i]-m_o[i];
			}
			sum = schwefel(z);
			sum *= (1.0 + 0.4 * Math.abs(rand.nextGaussian()));
			sum -= 450;             

		}

		//12. f5 Shifted 
		else if (functionNo == 12){
			boundLow = -100;
			boundHigh = 100;

			double max = Double.NEGATIVE_INFINITY;

			Ax(m_z, m_A, pos);

			for (int i = 0 ; i < Constants.DIMENSION ; i ++) {
				double temp = Math.abs(m_z[i] - m_B[i]);
				if (max < temp)
					max = temp;
			}
			sum = max -310;             
		}

		//13. Shifted Rosenbrock
		else if (functionNo == 13){
			boundLow = -100;
			boundHigh = 100;

			double[] z = new double[Constants.DIMENSION];
			for(int i=0; i<Constants.DIMENSION; i++){
				z[i]=pos[i]-m_o[i];
			}
			sum = rosenbrock(z);
			sum += 390;

		}

		//14. Shifted Rotated Griewank
		else if (functionNo == 14){
			boundLow = -600;
			boundHigh = 600;

			shift(m_z, pos, m_o);
			rotate(m_zM, m_z, m_matrix);

			sum = griewank(m_zM);
			sum -= 180;

		}
		//15. Shifted Rotated Ackley - Global Optimum at bounds
		else if (functionNo == 15){
			boundLow = -32;
			boundHigh = 32;

			shift(m_z, pos, m_o);
			rotate(m_zM, m_z, m_matrix);

			sum = ackley(m_zM);
			sum -= 140;
		}

		//16. Shifted Rastrigin 
		else if (functionNo == 16){
			boundLow = -5;
			boundHigh = 5;

			shift(m_z, pos, m_o);

			sum = rastrigin(m_z);
			sum -= 330;
		}
		//17. Shifted Rotated Rastrigin 
		else if (functionNo == 17){
			boundLow = -5;
			boundHigh = 5;

			shift(m_z, pos, m_o);
			rotate(m_zM, m_z, m_matrix);

			sum = rastrigin(m_z);
			sum -= 330;
		}

		//18. Shifted Rotated Weierstrass 
		else if (functionNo == 18){
			boundLow = -0.5;
			boundHigh = 0.5;

			shift(m_z, pos, m_o);
			xA(m_zM, m_z, m_matrix);

			sum = benchmark.weierstrass(m_zM);

			sum += 90;
		}

		//19. Schwefel's Problem 
		else if (functionNo == 19){
			boundLow = -Math.PI;
			boundHigh = Math.PI;

			for (int i = 0 ; i < Constants.DIMENSION ; i ++) {
				m_B[i] = 0.0;
				for (int j = 0 ; j < Constants.DIMENSION ; j ++) {
					m_B[i] += (m_a[i][j] * Math.sin(pos[j]) + m_b[i][j] * Math.cos(pos[j]));
				}

				double temp = m_A2[i] - m_B[i];
				sum += (temp * temp);
			}

			sum -= 460;
		}

		//20. Shifted Expanded Grieweank plus Rosenbrock  
		else if (functionNo == 20){
			boundLow = -5;
			boundHigh = 5;

			shift(m_z, pos, m_o);
			sum = benchmark.F8F2(m_z);
			sum -= 130;
		}

		//21. Shifted Rotated Expanded Scaffer F6  
		else if (functionNo == 21){
			boundLow = -100;
			boundHigh = 100;

			shift(m_z, pos, m_o);
			rotate(m_zM, m_z, m_matrix);

			sum = benchmark.EScafferF6(m_zM);
			sum -= 300;
		}

		//22. Hybrid Composite Function 
		else if (functionNo == 22){
			boundLow = -5;
			boundHigh = 5;

			sum = benchmark.hybrid_composition(pos, theJob);
			sum += 120;
		}

		//23. Rotated Version of Hybrid Composite Function F15 
		else if (functionNo == 23){
			boundLow = -5;
			boundHigh = 5;

			sum = benchmark.hybrid_composition(pos, theJob);
			sum += 120;
		}

		//24. F17: F16 (23) with Noise in Fitness 
		else if (functionNo == 24){
			boundLow = -5;
			boundHigh = 5;

			sum = benchmark.hybrid_composition(pos, theJob);
			sum *= (1.0 + 0.2 * Math.abs(rand.nextGaussian()));
			sum += 120;
		}

		//25. F18: Rotated Hybrid Composition Function  
		else if (functionNo == 25){
			boundLow = -5;
			boundHigh = 5;

			sum = benchmark.hybrid_composition(pos, theJob);
			sum += 10;
		}

		//26. F19: Rotated Hybrid Composition Function with narrow basin global optimum 
		else if (functionNo == 26){
			boundLow = -5;
			boundHigh = 5;

			sum = benchmark.hybrid_composition(pos, theJob);
			sum += 10;

		}

		//27. F20: Rotated Hybrid Composition Function with Global optimum on the Bounds
		else if (functionNo == 27){
			boundLow = -5;
			boundHigh = 5;

			sum = benchmark.hybrid_composition(pos, theJob);
			sum += 10;

		}

		//28. F21: Rotated Hybrid Composition Function 
		else if (functionNo == 28){
			boundLow = -5;
			boundHigh = 5;

			sum = benchmark.hybrid_composition(pos, theJob);
			sum += 360;
		}

		//29. F22: Rotated Hybrid Composition Function with High Condition Number Matrix
		else if (functionNo == 29){
			boundLow = -5;
			boundHigh = 5;

			sum = benchmark.hybrid_composition(pos, theJob);
			sum += 360;

		}

		//30. F23: Non-Continuous Rotated Hybrid Composition Function 
		else if (functionNo == 30){		

			for (int i = 0 ; i < Constants.DIMENSION ; i ++) {
				pos[i] = benchmark.myXRound(pos[i], m_o22[0][i]);
			}

			sum = benchmark.hybrid_composition(pos, theJob);

			sum += 360;

		}

		//31. F24: Rotated Hybrid Composition Function  
		else if (functionNo == 31){		

			sum = benchmark.hybrid_composition(pos, theJob);

			sum += 260;

		}

		//32. F25: Rotated Hybrid Composition Function without Bounds 
		else if (functionNo == 32){		

			sum = benchmark.hybrid_composition(pos, theJob);

			sum += 260;

		}

		return sum;
	}


	public static double sphere(double[] pos){
		double sum=0;
		for(int i=0; i<Constants.DIMENSION; i++){
			//          System.out.print(pos[i]);
			sum += Math.pow(pos[i],2);
		}       
		return sum;
	}

	public static double rosenbrock(double[] pos){
		double sum=0;
		for(int i=0; i<Constants.DIMENSION-1; i++){
			sum += 100*Math.pow(pos[i+1]-(pos[i])*(pos[i]),2) + Math.pow(pos[i]-1, 2);
		}       
		return sum;
	}

	public static double ackley(double[] pos){
		double sum=0;
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
		sum = part1 + part2 + part3 +part4;
		return sum;
	}

	public static double griewank(double[] pos){
		double sum=0;
		double sumPos = 0, prod = 1;
		for ( int i = 0; i < pos.length ; i++) {
			sumPos += pos[i]*pos[i];
			double div =Math.sqrt(i+1);
			prod *= Math.cos(pos[i]/div);
		}
		double part1 = 1;
		double part2 = (sumPos/4000);
		double part3 = -prod;
		sum = part1 + part2 + part3;
		return sum;
	}

	public static double rastrigin(double[] pos){
		double sum=0;
		double sumPos = 0; 
		double a = 10.0;
		double n = Constants.DIMENSION;
		double c = 2*Math.PI;
		for ( int i = 0; i < pos.length ; i++) {
			sumPos += pos[i]*pos[i] - a*Math.cos(c * pos[i]);           
		}
		sum = a*n + sumPos;
		return sum;
	}

	public static double schaffer(double[] pos){
		double sum=0;
		double part1 = 0.5;
		double x =pos[0]; 
		double y =pos[1];
		double part2 = Math.sin(x*x - y*y)*Math.sin(x*x - y*y) -0.5;
		double part3 = (1 + 0.001*(x*x + y*y))*(1 + 0.001*(x*x + y*y));
		sum = part1 + (part2 / part3);
		return sum;
	}

	public static double griewank10(double[] pos){
		double sum=0;
		double sumPos = 0, prod = 1; 
		for ( int i = 0; i < pos.length ; i++) {
			sumPos += pos[i]*pos[i];
			double div =Math.sqrt(i+1);
			prod *= Math.cos(pos[i]/div);
		}
		double part1 = 1;
		double part2 = (sumPos/4000);
		double part3 = -prod;
		sum = part1 + part2 + part3;
		return sum;
	}

	public static double schwefel(double[] pos){
		double sum=0;   

		double sum1 = 0;

		for(int i=0; i<Constants.DIMENSION; i++){
			sum += sum1;
			sum1=0;
			for(int j=0; j<=i; j++){
				sum1 += Math.pow(pos[i],2);     
			}
		}       
		return sum;
	}

	public static double elliptic(double[] pos){
		double sum=0;   

		for(int i=0; i<Constants.DIMENSION; i++){                
			sum += Math.pow(1000000,(i-1/Constants.DIMENSION-1))*Math.pow(pos[i],2);        
		}
		return sum;
	}




	public static void generateShiftOptimum(String fileName){

		Scanner scan;
		File file = new File("F:/PSOData/supportData/"+fileName+".txt");
		try {
			scan = new Scanner(file);
			m_o = new double[Constants.DIMENSION];
			int i =0;
			while(i<Constants.DIMENSION) //scan.hasNextDouble()&&
			{   
				m_o[i] = scan.nextDouble();
				//                  System.out.print(shiftOptimum[i] + " : ");
				i++;
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}


	}


	// Shift
	static public void shift(double[] results, double[] x, double[] o) {
		for (int i = 0 ; i < x.length ; i ++) {
			results[i] = x[i] - o[i];
		}
	}

	// Rotate
	static public void rotate(double[] results, double[] x, double[][] matrix) {
		xA(results, x, matrix);
	}

	//
	// Matrix & vector operations
	//

	// (1xD) row vector * (Dx1) column vector = (1) scalar
	static public double xy(double[] x, double[] y) {
		double result = 0.0;
		for (int i = 0 ; i < x.length ; i ++) {
			result += (x[i] * y[i]);
		}

		return (result);
	}

	// (1xD) row vector * (DxD) matrix = (1xD) row vector
	static public void xA(double[] result, double[] x, double[][] A) {
		for (int i = 0 ; i < result.length ; i ++) {
			result[i] = 0.0;
			for (int j = 0 ; j < result.length ; j ++) {
				result[i] += (x[j] * A[j][i]);
			}
		}
	}

	// (DxD) matrix * (Dx1) column vector = (Dx1) column vector
	static public void Ax(double[] result, double[][] A, double[] x) {
		for (int i = 0 ; i < result.length ; i ++) {
			result[i] = 0.0;
			for (int j = 0 ; j < result.length ; j ++) {
				result[i] += (A[i][j] * x[j]);
			}
		}
	}

	//
	// Utility functions for loading data from the given text file
	//
	static public void loadTestDataFromFile(String file, int num_test_points, int test_dimension, double[][] x, double[] f) {
		try {
			BufferedReader brSrc = new BufferedReader(new FileReader(file));
			loadMatrix(brSrc, num_test_points, test_dimension, x);
			loadColumnVector(brSrc, num_test_points, f);
			brSrc.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	static public void loadRowVectorFromFile(String file, int columns, double[] row) {
		try {
			BufferedReader brSrc = new BufferedReader(new FileReader(file));
			loadRowVector(brSrc, columns, row);
			brSrc.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	static public void loadRowVector(BufferedReader brSrc, int columns, double[] row) throws Exception {
		String stToken;
		StringTokenizer stTokenizer = new StringTokenizer(brSrc.readLine());
		for (int i = 0 ; i < columns ; i ++) {
			stToken = stTokenizer.nextToken();
			row[i] = Double.parseDouble(stToken);
		}
	}

	static public void loadColumnVectorFromFile(String file, int rows, double[] column) {
		try {
			BufferedReader brSrc = new BufferedReader(new FileReader(file));
			loadColumnVector(brSrc, rows, column);
			brSrc.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	static public void loadColumnVector(BufferedReader brSrc, int rows, double[] column) throws Exception {
		String stToken;
		for (int i = 0 ; i < rows ; i ++) {
			StringTokenizer stTokenizer = new StringTokenizer(brSrc.readLine());
			stToken = stTokenizer.nextToken();
			column[i] = Double.parseDouble(stToken);
		}
	}

	static public void loadNMatrixFromFile(String file, int N, int rows, int columns, double[][][] matrix) {
		try {
			BufferedReader brSrc = new BufferedReader(new FileReader(file));
			for (int i = 0 ; i < N ; i ++) {
				loadMatrix(brSrc, rows, columns, matrix[i]);
			}
			brSrc.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	static public void loadMatrixFromFile(String file, int rows, int columns, double[][] matrix) {
		try {
			BufferedReader brSrc = new BufferedReader(new FileReader(file));
			loadMatrix(brSrc, rows, columns, matrix);
			brSrc.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	static public void loadMatrix(BufferedReader brSrc, int rows, int columns, double[][] matrix) throws Exception {
		for (int i = 0 ; i < rows ; i ++) {
			loadRowVector(brSrc, columns, matrix[i]);
		}
	}

	private class MyHCJob extends HCJob {
		public double basic_func(int func_no, double[] x) {
			double result = 0.0;
			switch(func_no) {
			case 0:
			case 1:
				result = rastrigin(x);
				break;
			case 2:
			case 3:
				result = benchmark.weierstrass(x);
				break;
			case 4:
			case 5:
				result = griewank(x);
				break;
			case 6:
			case 7:
				result = ackley(x);
				break;
			case 8:
			case 9:
				result = sphere(x);
				break;
			default:
				System.err.println("func_no is out of range.");
				System.exit(-1);
			}
			return (result);
		}

		public double basic_func1(int func_no, double[] x) {
			double result = 0.0;
			switch(func_no) {
			case 0:
			case 1:
				result = ackley(x);
				break;
			case 2:
			case 3:
				result = rastrigin(x);
				break;
			case 4:
			case 5:
				result = sphere(x);
				break;
			case 6:
			case 7:
				result = benchmark.weierstrass(x);
				break;
			case 8:
			case 9:
				result = griewank(x);
				break;
			default:
				System.err.println("func_no is out of range.");
				System.exit(-1);
			}
			return (result);
		}

		public double basic_func2(int func_no, double[] x) {
			double result = 0.0;
			switch(func_no) {
			case 0:
			case 1:
				result = benchmark.EScafferF6(x);
				break;
			case 2:
			case 3:
				result = rastrigin(x);
				break;
			case 4:
			case 5:
				result = benchmark.F8F2(x);
				break;
			case 6:
			case 7:
				result = benchmark.weierstrass(x);
				break;
			case 8:
			case 9:
				result = griewank(x);
				break;
			default:
				System.err.println("func_no is out of range.");
				System.exit(-1);
			}
			return (result);
		}

		public double basic_func3(int func_no, double[] x) {
			double result = 0.0;
			// This part is according to Matlab reference code
			switch(func_no) {
			case 0:
				result = benchmark.weierstrass(x);
				break;
			case 1:
				result = benchmark.EScafferF6(x);
				break;
			case 2:
				result = benchmark.F8F2(x);
				break;
			case 3:
				result = ackley(x);
				break;
			case 4:
				result = rastrigin(x);
				break;
			case 5:
				result = griewank(x);
				break;
			case 6:
				result = benchmark.EScafferF6NonCont(x);
				break;
			case 7:
				result = benchmark.rastriginNonCont(x);
				break;
			case 8:
				result = benchmark.elliptic(x);
				break;
			case 9:
				result = benchmark.sphere_noise(x);
				break;
			default:
				System.err.println("func_no is out of range.");
				System.exit(-1);
			}
			return (result);
		}


	}


}