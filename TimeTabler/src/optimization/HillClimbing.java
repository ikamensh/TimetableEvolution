package optimization;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;

public class HillClimbing {

	@SuppressWarnings("unchecked")
	Climbable hillClimb(Climbable object, int nIterations) {

		// Map<Climbable, Integer> scores = Maps.newHashMap();
		List<Climbable> neighbours = Lists.newArrayList();
		Climbable best = object;

		for (int i = 0; i < nIterations; i++) {
			neighbours.clear();
			neighbours.addAll((Collection<? extends Climbable>) object.getNeighbours());
			for (Climbable c : neighbours) {
				// scores.put( c, c.getFitness());
				if (c.getFitness() > best.getFitness()) {

					best = c;

				}

			}
			if (best == object) {
				System.out.println("The hill was climbed in " + i + " iterations.");
				for (Climbable climbable : neighbours) {
					if (climbable != object) {
						climbable.cleanUp();
					}

				}
				break;

			} else {
				object = best;

				for (Climbable climbable : neighbours) {
					if (climbable != object) {
						climbable.cleanUp();
					}

				}
			}

		}

		return object;

	}

}
