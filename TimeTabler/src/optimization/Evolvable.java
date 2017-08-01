package optimization;

import java.util.Comparator;

public interface Evolvable
		extends Mutatable, Comparator<Evolvable>, Comparable<Evolvable>, HasFitness, supportsCrossover {

	public Evolvable copy();

}
