package classes;

import Classes_resource.Professor;
import Classes_resource.Room;
import util.Mapper;

public class Subject {

	public String name;
	int hoursPerWeek;
	Room requiredLocation;
	Professor taughtBy;

	public Subject() {
		super();
	}

	public Subject(String name, int hoursPerWeek, Professor taughtBy) {

		this.name = name;
		this.hoursPerWeek = hoursPerWeek;
		this.taughtBy = taughtBy;
	}

	public Subject(String source) {
		String[] s;
		String s1;
		s = source.split(":");
		s1 = s[0];
		s = s[1].split(",");

		this.name = s1.trim();
		this.hoursPerWeek = Integer.parseInt(s[0].trim());
		this.taughtBy = Mapper.getProfessor(s[1].trim());

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHoursPerWeek() {
		return hoursPerWeek;
	}

	public Professor getTaughtBy() {
		return taughtBy;
	}

}
