package util;

import java.util.Collection;
import java.util.Random;

public class MiscUtils {

	public static Object getRandomObject(Collection from) {
		Random rnd = new Random();
		int i = rnd.nextInt(from.size());
		return from.toArray()[i];
	}

}
