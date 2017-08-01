package stats;

import java.util.List;

import com.google.common.collect.Lists;

import optimization.Evolution;
import optimization.Timetable;

public class MultiRunner implements Runnable {

	public int valuesToTest[] = { 1, 2, 3, 5, 8, 13, 21, 30, 50 };
	// int nTries[]={10,10,10,50,25,15,10,5};

	int popSize = 20;

	int results[];

	public MultiRunner() {

	}

	private int trial(int stgLimit, int nParents) {
		int result = 0;

		List<Timetable> population = Lists.newArrayList();

		for (int i = 0; i < popSize; i++) {
			population.add(new Timetable());

		}

		Evolution evolution = new Evolution(population);
		evolution.nChildren = population.size();
		evolution.nParents = nParents;

		while (evolution.generation < 10000) {
			if (evolution.isStagnant(stgLimit))
				break;
			evolution.run();
		}

		result = evolution.avgFitness;

		return result;
	}

	public void run() {

		for (int limit : valuesToTest) {
			int score = 0;
			for (int i = 0; i < 10; i++) {
				score += trial(15, limit);

			}
			System.out.println("For nParents = " + limit + " the score was: " + score);

		}

	}

}
