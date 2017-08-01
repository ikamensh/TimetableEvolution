package test;

import static org.junit.Assert.*;

import org.junit.Test;

import story.Building;

public class BuildingTest {
	
	String buildingConfig = "01: 100, 50x4, 20x10";
	Building building= new Building(buildingConfig);

	@Test
	public void testBuildingRoomsNumber() {
				
		assertTrue(building.getRooms().size()==15);
	}
	
	@Test
	public void testRoomsUniqueName() {
		
		assertFalse(building.getRooms().get(0).getName().equals(building.getRooms().get(1).getName()));
		assertFalse(building.getRooms().get(3).getName().equals(building.getRooms().get(7).getName()));
		assertFalse(building.getRooms().get(2).getName().equals(building.getRooms().get(4).getName()));
	}
	
	@Test
	public void testRoomsHaveExpectedNames() {
		
		assertTrue(building.getRooms().get(0).getName().equals("01.001"));
		assertTrue(building.getRooms().get(2).getName().equals("01.003"));
		assertTrue(building.getRooms().get(6).getName().equals("01.007"));
		
	}
	
	@Test
	public void testBuildingRoomsSize() {
		
		assertTrue(building.getRooms().get(0).getSize()==100);
		assertTrue(building.getRooms().get(3).getSize()==50);
		assertTrue(building.getRooms().get(13).getSize()==20);
	}

}
