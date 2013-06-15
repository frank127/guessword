package test.guess;

import java.util.List;
import java.util.Scanner;

public class TestReader
 {
	private static String validate; // = "syaroettncuz";
	private static int word_length; // = 7;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args!=null && args.length ==2 && validateInput(args[0], 1) && validateInput(args[1], 2))
		{
			word_length = Integer.parseInt(args[1]);
			validate = args[0];
			
		} else {
			Scanner in = new Scanner(System.in);

			String s1;

			do {
				System.out.println("Enter possible string(12 letters): ");
				s1 = in.nextLine();
			} while (!validateInput(s1, 1));

			String s2;
			do {
				System.out.println("Enter word length: ");
				s2 = in.nextLine();
			} while (!validateInput(s2, 2));
			
			word_length = Integer.parseInt(s2);
			validate = s1;
		}		

		TxtParser parser = new TxtParser(word_length, validate);

		List<String> txtValidate = parser.getValidateWords();
		System.out.println("Condition: " + validate
				+ " ------------------------------------------");

		System.out.println("Valid------------------------------------------");
		for (int i = 0; i < txtValidate.size(); i++) {
			if ((i % 10) == 0 && i > 0) {
				System.out.println();
			}
			System.out.print((i + 1) + ": " + txtValidate.get(i) + " ");
		}
	}

	private static boolean validateInput(String s, int type) {
		boolean ret = false;

		if (1 == type) {
			if (s.length() == 12) {
				// letters
				for (int i = 0; i < s.length(); i++) {
					char a = s.charAt(i);
					if ((a >= 65 && a <= 90) || (a >= 97 && a <= 122)) {
						continue;
					} else {
						System.out.println("invalid input(mubt be A-Z, a-z)");
						return false;
					}
				}
			} else {
				System.out.println("String length is not 12");
				return false;
			}
		} else {
			// integer
			try {
				int num = Integer.parseInt(s);
				if (num < 2 || num > 8) {
					System.out.println("Length has to be in 2-8");
					return false;
				}
			} catch (NumberFormatException nfe) {
				System.out.println("Invalid input (2 - 8)");
				return false;
			}

		}

		ret = true;
		return ret;
	}

}
