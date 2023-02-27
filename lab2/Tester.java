import java.util.Random;

public class Tester {

	static AVLTree TestTree = new AVLTree();
	static Random random = new Random();
	static int max = 100;
	static int min = 0;
	static int randomInt;
	
	public static void main(String[] args) {

	/*for(int i = 0; i < 10; i++) {
		randomInt = random.nextInt(max-min) + min;
		TestTree.insert(randomInt);
		System.out.println(randomInt);
	}
	
	*/
		//TestTree.insert(20);
		//TestTree.insert(10);
		TestTree.insert(5);
		TestTree.insert(3);
		//TestTree.insert(30);
		//TestTree.insert(25);
		TestTree.insert(7);
		TestTree.insert(2);
		TestTree.insert(1);
		TestTree.insert(0);
		
		
		TestTree.print();
		
		System.out.println();
		
		TestTree.remove(5);
		
		TestTree.print();
	}
		
	}
