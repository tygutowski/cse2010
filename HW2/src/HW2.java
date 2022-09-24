/*
 * Author: Tyler Gutowski, tgutowski2020@fit.edu or tygutowski@gmail.com
 * Course: CSE 2010, Section 01, Fall 2021
 * Project: Proj 02, hw2S12Individual
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class HW2 {
	// An array that contains all possible password combinations. I would prefer
	// to just increment characters by 1, but unicode goes all over the place, which
	// makes it very confusing
	private static final char[] character_dictionary = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                'u', 'v', 'w', 'x', 'y', 'z',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
	public static void main(String[] args) throws FileNotFoundException {
		// Get the file that you will be reading from via arguments
		Scanner input = new Scanner(new File(args[0]));
		// First line is the max password size
		int maxpasswordLength = Integer.parseInt(input.nextLine());
		// While the input has a next line
		ArrayList<String> password_list = new ArrayList<String>();
		while(input.hasNextLine()) {
			// Create a string that contains the contents of the next line
			String expected = input.nextLine();
			// Iterate through each available size
			// Starting from 1, up to maxpasswordLength
			for(int size = 1; size <= maxpasswordLength; size++) {
				// Make a new integer array with the size of the current
				// password length
				char[] password = new char[size];
			    // For every index in the current password
			    // Get the decrypted_password using recursive method.
			    String decrypted_password = computePermutations(password, size, 0, expected);
			    // Decrypted password has an empty return value, so make sure
			    // to check if the password is empty or not, and only print
			    // the password if the decrypted_password is not empty.
			    if(!decrypted_password.isEmpty()) {
			    	// Add password to password_list, in case we want to edit
			    	// it later
			    	password_list.add(decrypted_password);
			    	// Break to ensure it does not continue decrypting. This saves a lot of
			    	// time for easy-to-crack passwords!
			    	break;
		    	}
		 	}
		}
		// For each password in password_list,
		// print the password. We do this rather
		// Than immediately printing in case we
		// want to edit the list later
		for (String password : password_list) {
			System.out.println(password);
		}
  	}
	// Recursive method that returns either a correct password, or an empty string 
	// dependant upon if a password is discovered when decrypting.
	private static String computePermutations(char[] password, int current_size, int position, String expected) {
	    // For every item in the character dictionary (every possible password character)
	    
		for (int i = 0; i < character_dictionary.length; i++) {
	    	// Iterate through every possible character at the current position
	    	password[position] = character_dictionary[i];
	    	// If the current position is NOT the last character in the current
	    	// size of the password that you are looking for
	    	if (position + 1 != current_size) {
			    // Recurse, increasing the position that you are editing by one
	    		position += 1;
	    		String possible_password = computePermutations(password, current_size, position, expected);
			    // If the returned password is NOT null
	    		if (!possible_password.isEmpty()) {
	    			// Return the password!
			    	return possible_password;
			    }
		    } 
	    	// If the current position IS the last character in the current
	    	// size of the password you are looking for
	    	else if (position + 1 == current_size) {
	    		// Turn the character list into a string
	    		String fragmented_password = new String(password);
	    		// Encrypt the fragmented_password
	    		// If the fragmented encrypted password is equivalent to the expected
	    		// encrypted password, then you have your return value!
			    if (HW2crypto.encrypt(fragmented_password).equalsIgnoreCase(expected)) {
			    	return fragmented_password;
			    }
		    }  	
	    }
    	// If you reach the end of the max size and the password isn't detected,
    	// simply return "". Then the size will increment by one until the max size is
		// reached. I would have liked to made this recursive, but it wasn't easily
		// possible using a character array.
	    return "";
	}
}