public class BruteForce {
  public static void main(String[] args) {
	 int size = 5;
    int[] password = new int[size];
    String[] finalPassword = new String[size];
    for (int i = 0; i < size; i++) {
      password[i] = 0;
      finalPassword[i] = "";
    }
    String pass = "JUNKO";
    System.out.println(computePermutations(size, password, 0, pass));
  }

  private static String computePermutations(int size, int[] password, int position, String pass) {
    String testString = "";
    String assemble = "";
    char[] character_dictionary = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
			 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
			 'u', 'v', 'w', 'x', 'y', 'z'};
    for (int i = 0; i < 36; i++) {
      password[position] = i;

      if (position != size - 1) {
        testString = computePermutations(size, password, position + 1, pass);
        if (testString != "") {
          return testString;
        }
      } else if (position == size - 1) {
        for (int j = 0; j < size; j++) {
        	assemble += character_dictionary[password[j]];
          

        }
         if (assemble.equalsIgnoreCase(pass)) {
          return assemble; //replace this with: return assemble;
        } else {
          assemble = "";
        }
      }


    }
    return "";
  }
}