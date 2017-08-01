package optimization;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import Classes_resource.Group;
import Classes_resource.Professor;
import classes.StudyProgram;
import classes.Subject;
import classes.Timeslot;
import stats.MultiRunner;
import story.Building;
import story.Uni;
import util.Mapper;
import util.ResourceReader;

public class launcher {

	static Uni uni = new Uni();

	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	public static void readFiles() throws Exception {
		ResourceReader r = new ResourceReader();
		String buildings = r.getFile("/resources/Buildings.dat");
		String programs = r.getFile("/resources/StudyPrograms.dat");
		String subjects = r.getFile("/resources/Subjects.dat");
		String professors = r.getFile("/resources/Professors.dat");
		String groups = r.getFile("/resources/Groups.dat");

		String lines[] = buildings.split("\\r?\\n");

		for (String line : lines) {
			System.out.println(line);
			uni.campus.add(new Building(line));
		}

		lines = professors.split("\\r?\\n");

		for (String line : lines) {
			System.out.println(line);
			Professor temp = new Professor(line);
			Mapper.addProfessor(temp);
		}

		lines = subjects.split("\\r?\\n");

		for (String line : lines) {
			System.out.println(line);
			Subject temp = new Subject(line);
			Mapper.addSubject(temp);
			String test = temp.name + " ";
			assert (Mapper.getSubject(test.trim()) == temp);
		}

		lines = programs.split("\\r?\\n");
		System.out.println((Mapper.subjects.toString()));
		for (String line : lines) {
			System.out.println(line);
			StudyProgram temp = new StudyProgram(line);
			Mapper.addProgram(temp);
		}

		lines = groups.split("\\r?\\n");
		for (String line : lines) {
			System.out.println(line);
			Group temp = new Group(line);
			Mapper.addGroup(temp);
		}

	}

	private static void printState() {
		System.out.println("Uni has following buildings on its campus:");
		for (Building building : uni.campus)
			System.out.println(building.toString());

		System.out.println("Uni has following study courses:");
		for (StudyProgram prog : Mapper.programs.values())
			System.out.println(prog.toString());

		System.out.println("Uni has following study courses:");
		for (Group group : Mapper.groups.values())
			System.out.println(group.toString());

	}

	public static void main(String[] args) throws Exception {

		readFiles();
		Mapper.initResources();
		Timeslot.generateTimeSlots();
		Timetable.buildClassList();

		// Evolution evolution;
		//
		// PrintWriter writer;

		// printState();

		// HillClimbing hillclimbing = new HillClimbing();
		//
		// List<Timetable> population = Lists.newArrayList();

		// for (int i = 0; i < 10; i++) {
		// population.add(new Timetable());
		//
		// }

		// evolution = new Evolution(population, population.size());

		// for (int i = 0; i < 10; i++) {
		// Timetable temp = population.get(i);
		// System.out.println("Fitness of timetable #" + i + " is " +
		// temp.getFitness());
		//
		// int nClasses = temp.scheduledClasses.size();
		// System.out.println(nClasses);
		//
		// temp = (Timetable) hillclimbing.hillClimb(temp, 10);
		//
		// System.out.println("Fitness of timetable #" + i + " is " +
		// temp.getFitness());
		//
		// writer = new PrintWriter("C:\\newfolder\\timetables" + i + ".txt",
		// "UTF-8");
		//
		// for (Resource r : Mapper.resources)
		// writer.println(r.printTimeTable(temp, Mapper.timeslots.values()));
		// writer.flush();
		// }

		// while (evolution.generation < 10000) {
		// if (evolution.isStagnant(75))
		// break;
		// evolution.run();
		// System.out.println(evolution.generation + " | " +
		// evolution.avgFitness + " | " + evolution.maxFitness);
		// }

		MultiRunner multiRunner1 = new MultiRunner();
		MultiRunner multiRunner2 = new MultiRunner();
		MultiRunner multiRunner3 = new MultiRunner();

		multiRunner1.valuesToTest = new int[] { 1, 2 };
		multiRunner2.valuesToTest = new int[] { 3, 4 };
		multiRunner3.valuesToTest = new int[] { 5, 6 };

		Thread t1 = new Thread(multiRunner1, "My Thread1");
		Thread t2 = new Thread(multiRunner2, "My Thread2");
		Thread t3 = new Thread(multiRunner3, "My Thread3");

		t1.start();
		t2.start();
		t3.start();

	}

}
