package test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import Classes_resource.Group;
import Classes_resource.Professor;
import Classes_resource.Room;
import classes.PlannedClass;
import classes.Subject;
import classes.Timeslot;
import optimization.Timetable;
import util.Mapper;

public class TimetableTest {

	static Professor prof = Mockito.mock(Professor.class);
	static Group group = Mockito.mock(Group.class);
	static Subject subject = Mockito.mock(Subject.class);
	static Professor prof2 = Mockito.mock(Professor.class);
	static Group group2 = Mockito.mock(Group.class);
	static Subject subject2 = Mockito.mock(Subject.class);
	static Room room1 = new Room(10, "TestRoom1");
	static Room room2 = new Room(10, "TestRoom2");

	static Mapper mapper = new Mapper();

	static PlannedClass c = new PlannedClass(prof, group, subject);
	static PlannedClass c2 = new PlannedClass(prof2, group2, subject2);
	static Timetable tt;
	static Timetable tt2;
	static Timetable tt3;

	static boolean initOnce = true;

	@Before
	public void initTimeslots() {
		if (initOnce) {
			Timeslot.generateTimeSlots();

			Mapper.addRoom(room1);
			Mapper.addRoom(room2);

			c.finishRandom();
			c2.finishRandom();

			Timetable.addClass(c);
			Timetable.addClass(c2);
			tt = new Timetable();
			tt.randomize();
			initOnce = false;
		}

	}

	@Test
	public void testGetNeighbours() {

		System.out.println(tt.scheduledClasses.size());
		List<Timetable> neighbours = tt.getNeighbours();
		System.out.println(tt.scheduledClasses.size());
		assertTrue(tt.scheduledClasses.size() == 2);
		int expectedSize = (Mapper.timeslots.size() - 1 + Mapper.rooms.size() - 1) * tt.scheduledClasses.size();

		assertTrue(Mapper.rooms.values().contains(c.room));
		assertTrue(Mapper.timeslots.values().contains(c.time));

		System.out.println(neighbours.size());
		System.out.println(expectedSize);
		assertTrue(neighbours.size() == expectedSize);

		/**
		 * Checks that resource sets and scheduled classes are unique for each
		 * timetable option
		 */

		for (Timetable i : neighbours)
			for (Timetable j : neighbours) {
				assertTrue(i == j || i.scheduledClasses != j.scheduledClasses);
			}

	}

	@Test
	public void testClassSequence() {

		tt2 = new Timetable(tt);
		tt3 = new Timetable();
		tt3.randomize();

		assertTrue(tt.scheduledClasses.size() == 2);
		assertTrue(tt2.scheduledClasses.size() == 2);
		assertTrue(tt3.scheduledClasses.size() == 2);

		tt.getNeighbours();

		assertTrue(tt.scheduledClasses.size() == 2);

		assertTrue(tt.scheduledClasses.get(0).prof == tt2.scheduledClasses.get(0).prof);
		assertTrue(tt.scheduledClasses.get(0).prof == tt3.scheduledClasses.get(0).prof);
		assertTrue(tt.scheduledClasses.get(0).group == tt2.scheduledClasses.get(0).group);
		assertTrue(tt.scheduledClasses.get(0).group == tt3.scheduledClasses.get(0).group);
		assertTrue(tt.scheduledClasses.get(0).subject == tt2.scheduledClasses.get(0).subject);
		assertTrue(tt.scheduledClasses.get(0).subject == tt3.scheduledClasses.get(0).subject);

		assertTrue(tt.scheduledClasses.get(1).prof == tt2.scheduledClasses.get(1).prof);
		assertTrue(tt.scheduledClasses.get(1).prof == tt3.scheduledClasses.get(1).prof);
		assertTrue(tt.scheduledClasses.get(1).group == tt2.scheduledClasses.get(1).group);
		assertTrue(tt.scheduledClasses.get(1).group == tt3.scheduledClasses.get(1).group);
		assertTrue(tt.scheduledClasses.get(1).subject == tt2.scheduledClasses.get(1).subject);
		assertTrue(tt.scheduledClasses.get(1).subject == tt3.scheduledClasses.get(1).subject);
	}

}
