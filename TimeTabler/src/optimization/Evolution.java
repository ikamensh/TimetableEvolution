package optimization;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import util.MiscUtils;

public class Evolution {

	private List<Evolvable> population = Lists.newArrayList();
	private List<Evolvable> control = Lists.newArrayList();
	public int nChildren;
	public int nMutations;
	public int nParents;
	public int generation;
	public int avgFitness;
	public int maxFitness;
	int controlSince;

	public Evolution(List population) {
		this.population = population;
		this.generation = 0;
		nChildren = 10;
		nMutations = 1;
		nParents = 2;

	}

	private void setControl() {
		control.clear();
		control.addAll(population);
		controlSince = generation;
	}

	public boolean isStagnant(int thresholdStagnant) {
		return (control.containsAll(population) && (generation - controlSince) > thresholdStagnant);
	}

	private Evolvable getChild(Evolvable parent) {

		Evolvable child = getChildFromParents(parent);
		for (int i = 0; i < nMutations; i++) {
			child.mutate();

		}

		return child;

	}
	
	private Evolvable getChildMutate(Evolvable parent) {

		Evolvable child = parent.copy();
		for (int i = 0; i < nMutations; i++) {
			child.mutate();

		}

		return child;

	}

	private Evolvable getChildFromParents(Evolvable parent) {

		Set<supportsCrossover> parents = Sets.newHashSet();
		while (parents.size() < nParents) {
			parents.add((Evolvable) MiscUtils.getRandomObject(population));
		}

		Evolvable child = (Evolvable) parent.schufflePhenoParents(parents, true);

		return child;

	}

	private Evolvable chooseParent() {

		Collections.sort(population, population.get(0));

		// TODO move the index part into getIndex method
		return getIndexLinearRanking(population);

	}

	private Evolvable getIndexLinearRanking(List<Evolvable> pop) {
		int totalRanks = 0;
		int nElements = pop.size();

		for (int i = 0; i < nElements; i++) {
			totalRanks += i;
		}
		Random random = new Random();

		int dice = random.nextInt(totalRanks);

		int currentBoundary = 0;

		for (int i = nElements; i > 0; i--) {
			currentBoundary += i;
			if (dice < currentBoundary) {
				return pop.get(nElements - i);
			}

		}

		if (true)
			throw new RuntimeException("failed to write proper linear ranking index calculator");

		return null;

	}

	private static Evolvable compete(Evolvable a, Evolvable b) {
		int result = a.compareTo(b);
		if (result > 0)
			return a;
		else
			return b;
	}

	public void run() {
		List<Evolvable> children = Lists.newArrayList();
		for (int i = 0; i < nChildren; i++) {
			children.add(getChild(chooseParent()));
		}

		for (int i = 0; i < nChildren; i++) {
			Evolvable competitor = (Evolvable) MiscUtils.getRandomObject(population);
			population.remove(competitor);
			population.add(compete(children.get(i), competitor));
		}

		updateMaxAvgFitness();

		generation++;

		if (!control.containsAll(population)) {
			setControl();
		}

	}

	public void updateMaxAvgFitness() {

		avgFitness = 0;
		maxFitness = Integer.MIN_VALUE;
		for (Evolvable e : population) {
			int fitness = e.getFitness();
			if (fitness > maxFitness) {

				maxFitness = fitness;

			}
			avgFitness += fitness;
		}
		avgFitness /= population.size();

	}

}
