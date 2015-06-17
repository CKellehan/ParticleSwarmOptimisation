package ideas;
import java.util.ArrayList;
import java.util.Random;

public class ArrayListTest {

	public static void main(String[] args) {

		Random generator = new Random();

		ArrayList<Object> array = new ArrayList<Object>();
		for (int i=0; i<10; i++){
			array.add(i);
		}

		for (Object o : array){
			System.out.print(o + " ");
		}

		int r;
		for (int i=0; i<10; i++){

			r= generator.nextInt(array.size());
			System.out.println();
			System.out.println();
			System.out.println("_________index: " + r + "_________value: " + array.get(r));
			System.out.println();

			array.remove(r);
			System.out.println("Array size " + array.size());

			for (Object o : array){
				System.out.print(o + " ");
			}
		}

	}

}
