package optimization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import Classes_resource.Group;
import Classes_resource.Resource;
import Classes_resource.Room;
import classes.PlannedClass;
import classes.Subject;
import classes.Timeslot;
import util.Mapper;
import util.MiscUtils;

public class Timetable implements Climbable, Evolvable {

	static ArrayList<PlannedClass> classesToBeTaught = new ArrayList<PlannedClass>();

	public ArrayList<PlannedClass> scheduledClasses;

	public Map<Resource, Map<Timeslot, Set<PlannedClass>>> schedule;
	public Map<Resource, Set<Timeslot>> freeTime;
	public Map<Resource, Set<Timeslot>> conflicts;

	private int fitness;

	public Timetable() {

		schedule = Maps.newHashMap();
		freeTime = Maps.newHashMap();
		conflicts = Maps.newHashMap();

		for (Resource r : Mapper.resources) {
			schedule.put(r, new HashMap<Timeslot, Set<PlannedClass>>());
			freeTime.put(r, Sets.newHashSet(Timeslot.weekSequence));
			conflicts.put(r, Sets.newHashSet());

		}

		scheduledClasses = Lists.newArrayList();
		randomize();
		register();

	}

	public Timetable(Timetable t) {

		scheduledClasses = Lists.newArrayList();

		schedule = Maps.newHashMap();
		freeTime = Maps.newHashMap();
		conflicts = Maps.newHashMap();

		for (Resource r : Mapper.resources) {
			schedule.put(r, new HashMap<Timeslot, Set<PlannedClass>>());
			freeTime.put(r, Sets.newHashSet(Timeslot.weekSequence));
			conflicts.put(r, Sets.newHashSet());

		}

		for (PlannedClass c : t.scheduledClasses)
			scheduledClasses.add(new PlannedClass(c, this));
		register();

	}

	public void randomize() {

		scheduledClasses.clear();

		for (PlannedClass c : classesToBeTaught)
			scheduledClasses.add(new PlannedClass(c, this));

		for (PlannedClass c : scheduledClasses) {
			c.finishRandom();
		}

	}

	public static void addClass(PlannedClass c) {
		classesToBeTaught.add(c);
	}

	private static void addGroupsClasses(Group group) {
		for (Subject subject : group.program.subjectsPerSemester.get(group.semesterNumber)) {
			for (int i = 0; i < subject.getHoursPerWeek(); i++) {
				classesToBeTaught.add(new PlannedClass(subject.getTaughtBy(), group, subject));
			}
		}

	}

	public static void buildClassList() {
		for (Group group : Mapper.groups.values()) {
			addGroupsClasses(group);

		}

	}

	public int getFitness() {
		int result = 0;
		for (PlannedClass c : scheduledClasses)
			result += c.group.studentsCount * c.time.timeQuality;

		result -= getPenalty();

		fitness = result;

		return result;

	}

	public int getPenalty() {
		int result = 0;

		for (Resource r : Mapper.resources) {
			result += countCollisions(r) * 100;
		}

		for (PlannedClass c : scheduledClasses) {
			result += c.penaltyRoomSize();
		}

		return result;

	}

	public void register() {
		for (PlannedClass c : scheduledClasses) {

			c.registerClass();

		}

	}

	public List<Timetable> getNeighbours() {

		List<Timetable> result = Lists.newArrayList();
		Timetable temp;
		int index;

		for (PlannedClass c : scheduledClasses) {
			index = scheduledClasses.indexOf(c);
			for (Room r : Mapper.rooms.values()) {
				if (r != c.room) {
					temp = new Timetable(this);
					temp.scheduledClasses.get(index).room = r;
					result.add(temp);

				}

			}

			for (Timeslot time : Mapper.timeslots.values()) {
				if (time != c.time) {
					temp = new Timetable(this);
					temp.scheduledClasses.get(index).time = time;
					result.add(temp);

				}

			}
		}

		// result.addAll(this.getNeighboursExchangeRooms());
		// result.addAll(this.getNeighboursExchangeTimeslots());

		return result;

	}

	public List<Timetable> getNeighboursExchangeRooms() {
		List<Timetable> result = Lists.newArrayList();
		Timetable temp;
		Room tempRoom;
		int index1;
		int index2;

		for (PlannedClass c1 : scheduledClasses) {
			for (PlannedClass c2 : scheduledClasses) {
				index1 = scheduledClasses.indexOf(c1);
				index2 = scheduledClasses.indexOf(c2);
				temp = new Timetable(this);

				tempRoom = temp.scheduledClasses.get(index1).room;
				temp.scheduledClasses.get(index1).room = temp.scheduledClasses.get(index2).room;
				temp.scheduledClasses.get(index2).room = tempRoom;

				result.add(temp);

			}
		}

		return result;

	}

	// public List<Timetable> getNeighboursExchangeTimeslots() {
	// List<Timetable> result = Lists.newArrayList();
	// Timetable temp;
	// Timeslot tempTimeslot;
	// int index1;
	// int index2;
	//
	// for (PlannedClass c1 : scheduledClasses) {
	// for (PlannedClass c2 : scheduledClasses) {
	// index1 = scheduledClasses.indexOf(c1);
	// index2 = scheduledClasses.indexOf(c2);
	// temp = new Timetable(this);
	//
	// tempTimeslot = temp.scheduledClasses.get(index1).time;
	// temp.scheduledClasses.get(index1).time =
	// temp.scheduledClasses.get(index2).time;
	// temp.scheduledClasses.get(index2).time = tempTimeslot;
	//
	// result.add(temp);
	//
	// }
	// }
	//
	// return result;
	//
	// }

	public void mutate() {

		singleMutate();

	}

	private void singleMutate() {

		Random random = new Random();

		PlannedClass c = (PlannedClass) MiscUtils.getRandomObject(scheduledClasses);
		if (random.nextBoolean()) {
			Room r = (Room) MiscUtils.getRandomObject(Mapper.rooms.values());
			c.room = r;

		} else

		{
			Timeslot t = (Timeslot) MiscUtils.getRandomObject(Mapper.timeslots.values());
			c.time = t;

		}

	}

	private void doubleMutate() {

		PlannedClass c = (PlannedClass) MiscUtils.getRandomObject(scheduledClasses);
		Room r = (Room) MiscUtils.getRandomObject(Mapper.rooms.values());
		c.room = r;

		Timeslot t = (Timeslot) MiscUtils.getRandomObject(Mapper.timeslots.values());
		c.time = t;

	}

	@Override
	public supportsCrossover schufflePhenoParents(Set<supportsCrossover> parents, boolean timeAndRoomFromSameParent) {

		Timetable result = new Timetable();

		for (PlannedClass c : result.scheduledClasses) {
			int index = result.scheduledClasses.indexOf(c);
			Timetable parent = (Timetable) MiscUtils.getRandomObject(parents);
			c.time = parent.scheduledClasses.get(index).time;
			if (!timeAndRoomFromSameParent) {
				parent = (Timetable) MiscUtils.getRandomObject(parents);

			}
			c.room = parent.scheduledClasses.get(index).room;

		}

		return result;

	}

	public void cleanUp() {
		for (Resource r : Mapper.resources) {

			schedule.remove(r);
			conflicts.remove(r);
			freeTime.remove(r);

		}

	}

	// TODO does not empty timeslots to free time!
	public void removePlannedClass(PlannedClass c, Resource r) {

		schedule.get(r).get(c.time).remove(c);

		if (conflicts.get(r).contains(c.time) && schedule.get(r).get(c.time).size() == 1) {
			schedule.get(r).remove(c.time);
		}

		if (schedule.get(r).get(c.time).size() == 0) {
			schedule.get(r).remove(c.time);
		}

		if (schedule.get(r).size() == 0) {
			schedule.remove(r);
		}

	}

	public int countCollisions(Resource ref) {

		int result = 0;

		if (schedule.containsKey(ref)) {
			conflicts.put(ref, Sets.newHashSet());

			for (Timeslot t : schedule.get(ref).keySet()) {
				int classesAtTimeT;
				Set<PlannedClass> classes = schedule.get(ref).get(t);
				classesAtTimeT = classes.size();

				if (classesAtTimeT > 1) {
					conflicts.get(ref).add(t);
					result += classesAtTimeT - 1;

				}
			}
		}

		return result;

	}

	@Override
	public int compare(Evolvable arg0, Evolvable arg1) {
		if (arg0.getFitness() > arg1.getFitness()) {
			return 1;
		} else if (arg0.getFitness() < arg1.getFitness()) {
			return -1;
		}

		return 0;
	}

	@Override
	public int compareTo(Evolvable o) {

		if (this.getFitness() > o.getFitness()) {
			return 1;
		} else if (this.getFitness() < o.getFitness()) {
			return -1;
		}

		return 0;
	}

	@Override
	public Evolvable copy() {

		return new Timetable(this);
	}

	public String printTimeTable(Resource ref, Collection<Timeslot> timeslots) {

		String result = "";
		result += "Classes for " + ref.name + " : \n";
		for (Timeslot t : Timeslot.weekSequence) {

			if (schedule.containsKey(ref) && schedule.get(ref).containsKey(t)) {
				Set<PlannedClass> classes = schedule.get(ref).get(t);
				if (classes != null) {

					for (PlannedClass c : classes) {
						result += t.timeAt + ": " + c.toString() + "\n";
					}
				}
			}

		}

		return result;

	}

}
