package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import classes.Semester;
import classes.Semester.SemSeason;

public class SemesterTest {

	@Before
	public void before()
	{
		Semester.setCurrentSemester(new Semester(2016,SemSeason.WINTER));		
	}
	
	@Test
	public void testSemesterDifference() {
		Semester SS14 = new Semester(2014,SemSeason.SUMMER);
		Semester WS14 = new Semester(2014,SemSeason.WINTER);
		Semester WS12 = new Semester(2012,SemSeason.WINTER);
		Semester WS16 = new Semester(2016,SemSeason.WINTER);
		
		//System.out.println((new Integer(Semester.howManySemestersAgo(SS14)).toString()));
		
		assertEquals(5,Semester.howManySemestersAgo(SS14));
		assertEquals(4,Semester.howManySemestersAgo(WS14));
		assertEquals(8,Semester.howManySemestersAgo(WS12));
		assertEquals(0,Semester.howManySemestersAgo(WS16));
		
	}

}
