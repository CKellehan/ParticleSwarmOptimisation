package ideas;

public class GIDN {

	public GIDN() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		int b = 2;
		int y = 2;
		double maxT = 10;
		int N = 50; 
		
		for (int t=0; t<maxT; t++){
			double h = Math.pow(t/maxT,y) * N + b;
			System.out.println( t + "   " + Math.floor(h));
		}
		

	}
	
	

}
