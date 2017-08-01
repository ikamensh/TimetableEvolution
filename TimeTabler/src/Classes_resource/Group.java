package Classes_resource;

import classes.Semester;
import classes.Semester.SemSeason;
import classes.StudyProgram;
import classes.StudyProgram.StudyPrograms;
import util.Mapper;

public class Group extends Resource {

	public int semesterNumber;
	public int studentsCount;
	public StudyProgram program;
	Semester semesterSince;

	public Group(int semesterNumber, int studentsCount, StudyProgram program) {

		this.semesterNumber = semesterNumber;
		this.studentsCount = studentsCount;
		this.program = program;
	}

	public Group(String string) throws Exception {

		super();
		SemSeason season;
		String[] substrings1 = string.split(":");
		name = substrings1[0].trim();

		String[] substrings2 = substrings1[0].split("_");

		if (substrings2[1].contains("WS"))
			season = SemSeason.WINTER;
		else if (substrings2[1].contains("SS"))
			season = SemSeason.SUMMER;
		else
			throw new Exception("input string does not specify semester as summer or winter");

		semesterSince = new Semester(Integer.parseInt(substrings2[1].replaceAll("[^\\d]", "")), season);

		substrings2 = substrings1[1].split("x");

		program = Mapper.getProgram(StudyPrograms.valueOf(substrings2[0].trim()));

		studentsCount = Integer.parseInt(substrings2[1].trim());

		semesterNumber = Semester.howManySemestersAgo(semesterSince);

	}

	public Group() {
		// TODO Auto-generated constructor stub
	}

	public String toString() {
		String result;
		result = "Group " + name + " studies " + program.getName() + " in " + (semesterNumber + 1) + " semester.";
		result += " It has " + studentsCount + " students;";

		return result;

	}

}
