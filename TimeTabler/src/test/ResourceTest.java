package test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import Classes_resource.Group;
import Classes_resource.Professor;
import Classes_resource.Room;
import classes.PlannedClass;
import classes.Subject;
import classes.Timeslot;
import optimization.Timetable;

public class ResourceTest {

	static Professor prof;
	static Group group;
	static Subject subject;
	static Timeslot timeslot;
	static Room room;
	static PlannedClass c;
	static PlannedClass c2;
	static Timetable t;

	@Before
	public void initRes() {

		prof = new Professor("ProfessorTest");
		group = new Group();
		subject = new Subject();
		timeslot = new Timeslot("test_time", 1);
		room = new Room(1, "test_room");
		c = new PlannedClass(prof, group, subject);
		c2 = new PlannedClass(prof, group, subject);

		t = mock(Timetable.class);

		group.name = "TestGroup";
		subject.name = "Learning Mockito";

		c.setTime(timeslot);
		c.setRoom(room);
		c.setTimetable(t);
		c2.setTime(timeslot);
		c2.setRoom(room);
		c2.setTimetable(t);

	}

	@Test
	public void testAddPlannedClass() {

		c.registerClass();
		c2.registerClass();

		assertTrue("prof should have one entry in his timetable", t.schedule.get(prof).values().size() == 1);
		assertTrue("group should have one entry in its timetable", t.schedule.get(group).values().size() == 1);
		assertTrue("group should have two colliding classes at time timeslot",
				t.schedule.get(room).get(timeslot).size() == 2);
	}

	// @Test
	// public void testCountCollisions() {
	//
	// prof.addPlannedClass(c);
	// room.addPlannedClass(c);
	//
	// assertTrue(room.countCollisions(t) == 0);
	//
	// room.addPlannedClass(c2);
	//
	// assertTrue(room.countCollisions(t) == 1);
	// assertTrue(prof.countCollisions(t) == 0);
	//
	// }
	//
	// @Test
	// public void testRemovePlannedClass() {
	//
	// prof.addPlannedClass(c);
	// room.addPlannedClass(c);
	//
	// assertTrue("prof should have one entry in his timetable",
	// prof.getSchedule().get(t).values().size() == 1);
	// assertTrue("room should have one entry in its timetable",
	// room.getSchedule().get(t).values().size() == 1);
	//
	// room.removePlannedClass(c);
	//
	// assertFalse("room should have no ref to this timetable",
	// room.getSchedule().containsKey(t));
	//
	// }

}
