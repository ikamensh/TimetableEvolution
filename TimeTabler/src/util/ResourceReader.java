package util;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ResourceReader {
	
	public String getFile(String fileName) {

		StringBuilder result = new StringBuilder("");

		//Get file from resources folder
		
		File file = new File(getClass().getResource(fileName).getFile());

		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line).append("\n");
			}

			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return result.toString();

	  }

}
