package story;
import java.util.ArrayList;

import Classes_resource.Room;
import util.Mapper;

/* constructor using a .dat file;
 * expected syntax:
 * Building1_name : room_size_1 x room_amount_1, ... 
 * Building2_name : room_size_2 x room_amount_2, ... 
 */

public class Building {
	
	
	String name;
	private ArrayList<Room> rooms;
	int currentRoomNumber;

	public Building(String source) {
		
		setRooms(new ArrayList<Room>());
		String[] s2 = source.split(":");
		name = s2[0];
		currentRoomNumber=1;
		
		String[] roomSizes=s2[1].split(",");
			
		
		for(String s : roomSizes)
			if(s.contains("x"))
				{
				String[] ss=s.split("x");
				int n=Integer.parseInt(ss[1].trim());
				int size=Integer.parseInt(ss[0].trim());
				
				assert n>0 : "n is not above zero";
				assert size>0 : "size is not above zero";
				
				for(int i=0;i<n;i++)
					{
					Room temp=new Room(size,getRoomName());
					getRooms().add(temp);
					Mapper.addRoom(temp);
					}					
				
				}
			else
			{
				getRooms().add(new Room(Integer.parseInt(s.trim()),getRoomName()));
			}
		
	
	}


	
	public String toString()
	{
		String result=name + " has following rooms:";
		
		for(Room r : getRooms())
		{
			result += r.name + " of size " + new Integer(r.size).toString() + "; ";		
			
		}
		
		return result;
		
		
	}
	
	private String getRoomName()
	{
		String num = new Integer(currentRoomNumber).toString();
		while(num.length()<3)
			num = "0"+num;
		
		currentRoomNumber+=1;
		
		return name+"."+num;		
		
	}



	public ArrayList<Room> getRooms() {
		return rooms;
	}



	public void setRooms(ArrayList<Room> rooms) {
		this.rooms = rooms;
	}
	
}
