package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import classes.PlannedClass;
import classes.Timeslot;
import optimization.Timetable;
import optimization.launcher;
import util.Mapper;

public class ResourceIntTest {

	static int resSize;
	static int profsSize;
	static int groupsSize;
	static int roomsSize;

	@Before
	public void setUp() throws Exception {

		launcher.readFiles();
		Mapper.initResources();
		Timeslot.generateTimeSlots();
		Timetable.buildClassList();

	}

	@Test
	public void testGetNeighbours() {

		Timetable tt1 = new Timetable();

		int nClasses = tt1.scheduledClasses.size();
		System.out.println(nClasses);

		List<Timetable> neighbours = tt1.getNeighbours();

		for (Timetable tt2 : neighbours) {
			int nDifferences = 0;

			for (PlannedClass c1 : tt1.scheduledClasses) {
				int index = tt1.scheduledClasses.indexOf(c1);
				PlannedClass c2 = tt2.scheduledClasses.get(index);

				if (!((c1.room == c2.room) && (c1.time == c2.time))) {

				} else {
					nDifferences++;
				}

			}
			assertEquals(nDifferences, 1);
		}

	}

	@Test
	public void testInit1() {

		profsSize = Mapper.profs.values().size();
		roomsSize = Mapper.rooms.values().size();
		groupsSize = Mapper.groups.values().size();
		resSize = Mapper.resources.size();
		assertTrue(profsSize > 2);
		assertTrue(roomsSize > 2);
		assertTrue(groupsSize > 2);
		assertTrue(resSize > 2);

	}

	@Test
	public void testInit2() {

		assertTrue(profsSize == Mapper.profs.values().size());
		assertTrue(roomsSize == Mapper.rooms.values().size());
		assertTrue(groupsSize == Mapper.groups.values().size());
		assertTrue(resSize == Mapper.resources.size());

	}

	@Test
	public void testRegistrations() {

		Timetable timetable = new Timetable();
		int fitness = timetable.getFitness();
		assertEquals(fitness, timetable.getFitness());

		Timetable timetable2 = new Timetable(timetable);
		Timetable timetable3 = new Timetable(timetable2);

		for (PlannedClass c : timetable.scheduledClasses) {
			int index = timetable.scheduledClasses.indexOf(c);
			PlannedClass c2 = timetable2.scheduledClasses.get(index);
			assertSame(c.prof, c2.prof);
			assertSame(c.group, c2.group);
			assertSame(c.room, c2.room);
			assertSame(c.time, c2.time);
			assertSame(c.subject, c2.subject);
			assertNotSame(c.timetable, c2.timetable);

		}

		// test that only 1 neighbour differs in getNeighbours()

		System.out.println(fitness);
		System.out.println(timetable2.getFitness());
		System.out.println(timetable3.getFitness());

		assertEquals(fitness, timetable2.getFitness());
		assertEquals(timetable2.getFitness(), timetable3.getFitness());

	}

}
