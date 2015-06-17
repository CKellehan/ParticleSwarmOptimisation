package ideas;

public class ExaamTest {

	public static void main(String[] args) {
		boolean[] numbers = new boolean[100];

		for (int i=0; i<numbers.length; i++){
			numbers[i]=true;
		}

		int[] primes = {2,3,5,7,11,13,17,19,23,29,31}; 

		for (int j=0; j<primes.length; j++){
			for (int i=2; i<numbers.length; i++){

				if (primes[j]==i){
					numbers[i]=true;
				}
				else if (i%primes[j]==0){
					numbers[i]=false;
				}
	
			}
		}
		

		for (int i=2; i<numbers.length; i++){
			System.out.println(i +" "+ numbers[i]);
		}

	}

}
