import java.util.Random;

public class TestClass {

	static int tableSize = 9;
	static SymbolTable st = new SymbolTable(tableSize);
	static Random random = new Random();
	static String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	public static void main(String[] args) {
		
		st.put("abcd", null);
		
		st.put("acbd", 'b');
		
		st.dump();
		
		System.out.println();
		
		st.put("adcb", 'c');
		
		st.put("abcd", 'd');
		
		st.dump();
		
		System.out.println();
		
		st.put("abcd", null);

		st.dump();
		
	}
}

