package classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import util.Mapper;

public class Timeslot {

	public String timeAt;
	public int timeQuality;
	static Map<String, Integer> qualityMap = new HashMap<String, Integer>();
	public static ArrayList<Timeslot> weekSequence = new ArrayList<Timeslot>();

	public Timeslot(String at, int q) {

		timeAt = at;
		timeQuality = q;

	}

	public String getTimeAt() {
		return timeAt;
	}

	public static void generateTimeSlots() {
		String[] weekDays = { "Mo", "Tue", "Wed", "Thu", "Fri" };
		// String[] weekDays = { "Mo", "Tue" };
		String[] timePerDay = { "08", "10", "12", "14", "16", "18" };
		qualityMap.put("08", 3);
		qualityMap.put("10", 4);
		qualityMap.put("12", 4);
		qualityMap.put("14", 3);
		qualityMap.put("16", 2);
		qualityMap.put("18", 1);

		for (String day : weekDays) {
			for (String time : timePerDay) {
				Timeslot temp = new Timeslot(day + time, qualityMap.get(time));
				Mapper.addTimeSlot(temp);
				weekSequence.add(temp);

			}
		}

	}

}
