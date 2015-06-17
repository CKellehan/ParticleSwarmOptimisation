package standardPSO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class PSOMain {

	static int runAmount=25;

	static int functionNo;

	public static void main(String[] args) {

		functionNo = 5;

		//		for(functionNo=2; functionNo<=5; functionNo ++){



		String output = "";
		double[][] averageGBest = new double[runAmount][Constants.ITERATION];

		//gBest
		for (int i=0; i<runAmount; i++){
			OptimisationProcess g = new OptimisationProcess();
			g.optimisation();
			for (int j=0; j<Constants.ITERATION; j++){
				averageGBest[i][j]= g.getGBestAtEactIteration(j);
			}
		}
		output = setAverage(averageGBest, "gBest", functionNo);
		setStandardDeviation(averageGBest, "gBest");
		//printing gBest average data to file
		printToFile(output, "gBestAverage", functionNo);


		//lBest
		output = "";
		averageGBest = new double[runAmount][Constants.ITERATION];
		for (int i=0; i<runAmount; i++){
			LBestOptimisationProcess l = new LBestOptimisationProcess();
			l.optimisation();
			for (int j=0; j<Constants.ITERATION; j++){
				averageGBest[i][j]= l.getGBestAtEactIteration(j);
			}
		}
		output = setAverage(averageGBest, "lBest", functionNo);
		setStandardDeviation(averageGBest, "lBest");
		//printing gBest average data to file
		printToFile(output, "lBestAverage", functionNo);

		//von Nuemann
		output = "";
		averageGBest = new double[runAmount][Constants.ITERATION];
		for (int i=0; i<runAmount; i++){
			VNOptimisationProcess v = new VNOptimisationProcess();
			v.optimisation();
			for (int j=0; j<Constants.ITERATION; j++){
				averageGBest[i][j]= v.getGBestAtEactIteration(j);
			}
		}
		output = setAverage(averageGBest, "vnBest", functionNo);
		setStandardDeviation(averageGBest, "vnBest");
		//printing gBest average data to file
		printToFile(output, "vBestAverage", functionNo);

		//		//GIDN
		//		output = "";
		//		averageGBest  = new double[runAmount][Constants.ITERATION];
		//		for (int i=0; i<runAmount; i++){
		//			GIDNOptimisationProcess gidn = new GIDNOptimisationProcess();
		//			gidn.optimisation();
		//			for (int j=0; j<Constants.ITERATION; j++){
		//				averageGBest[i][j]= gidn.getGBestAtEactIteration(j);
		//			}
		//		}
		//		output = setAverage(averageGBest, "GIDNBest", functionNo);
		//		//printing gBest average data to file
		//		printToFile(output, "GIDNAverage", functionNo);
		//
		//		//Test
		//		output = "";
		//		averageGBest = new double[runAmount][Constants.ITERATION];
		//		for (int i=0; i<runAmount; i++){
		//			TestOptimisationProcess t = new TestOptimisationProcess();
		//			t.optimisation();
		//			for (int j=0; j<Constants.ITERATION; j++){
		//				averageGBest[i][j]= t.getGBestAtEactIteration(j);
		//			}
		//		}
		//		output = setAverage(averageGBest, "TestBest", functionNo);
		//		//printing gBest average data to file
		//		printToFile(output, "TestAverage", functionNo);
		//
		//		//Diversity Random
		//		output = "";
		//		averageGBest = new double[runAmount][Constants.ITERATION];
		//		for (int i=0; i<runAmount; i++){
		//			DiversityRandomOptimisationProcess r = new DiversityRandomOptimisationProcess();
		//			r.optimisation();
		//			for (int j=0; j<Constants.ITERATION; j++){
		//				averageGBest[i][j]= r.getGBestAtEactIteration(j);
		//			}
		//		}
		//		output = setAverage(averageGBest, "diversity Random Best", functionNo);
		//		//printing gBest average data to file
		//		printToFile(output, "DiversityRandomAverage", functionNo);
		//
		//		//Diversity Distance
		//		output = "";
		//		averageGBest = new double[runAmount][Constants.ITERATION];
		//		for (int i=0; i<runAmount; i++){
		//			DiversityDistanceOptimisationProcess d = new DiversityDistanceOptimisationProcess();
		//			d.optimisation();
		//			for (int j=0; j<Constants.ITERATION; j++){
		//				averageGBest[i][j]= d.getGBestAtEactIteration(j);
		//			}
		//		}
		//		output = setAverage(averageGBest, "diversity Distance Best", functionNo);
		//		//printing gBest average data to file
		//		printToFile(output, "DiversityDistanceAverage", functionNo);

		//		}
	}


	//Printing results to file
	public static void printToFile(String testOutput, String fileName, int f){

		@SuppressWarnings("unused")
		Date today = new Date();
		System.out.println();
		try {
			String content = testOutput;
			//			File file = new File("F:/PSOData/"+ fileName + "" + today.getTime()+".txt");
			File file = new File("F:/PSOData/"+ fileName +""+ f+".txt");
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
			//			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void setStandardDeviation(double[][] averageGBest, String name){

		double average, sumSqrs = 0, sumSqrt, stDev;
		double sum=0;
		for (int j=0; j<runAmount; j++){		
			sum += averageGBest[j][Constants.ITERATION-1];	
			System.out.println(averageGBest[j][Constants.ITERATION-1]);
		}
		average= sum/runAmount;


		for (int j=0; j<runAmount; j++){		
			sumSqrs += (averageGBest[j][Constants.ITERATION-1] - average)*(averageGBest[j][Constants.ITERATION-1] - average);
		}

		sumSqrt = Math.sqrt(sumSqrs);

		stDev = sumSqrt/runAmount;

		System.out.println(name + " standard deviation " + stDev);





	}

	public static String setAverage(double[][] averageGBest, String name, int f){
		String output = "";
		double[] average = new double[Constants.ITERATION];
		for (int i=0; i<Constants.ITERATION; i++){
			double sum=0;
			for (int j=0; j<runAmount; j++){		
				sum += averageGBest[j][i];	
			}
			average[i]= sum/runAmount;

			output += average[i] + "\n ";
		}
		System.out.println("Average " +name+" at end function " + f + ":  " + average[Constants.ITERATION-1]);
		return output;
	}


}
