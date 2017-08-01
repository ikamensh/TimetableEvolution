package classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import util.Mapper;

public class StudyProgram {

	public enum StudyPrograms {
		ELECTRONICS_BSC, MECHANICAL_ENGINEERING_BSC, INDUSTRIAL_ENGINEERING_BSC, SYSTEMS_ENGINEERING_BSC, BIONICS_MSC, BIOMATERIALS_BSC;
	}

	int lengthSemesters;
	String name;
	public Map<Integer, ArrayList<Subject>> subjectsPerSemester;

	public StudyProgram(String source) {

		String[] s = source.split(":");
		name = s[0].trim();
		lengthSemesters = Integer.parseInt(s[1].trim());

		String[] semesters = s[2].split(";");

		assert (semesters.length == lengthSemesters);
		String[] subjects;
		subjectsPerSemester = new HashMap<Integer, ArrayList<Subject>>();
		for (int i = 0; i < lengthSemesters; i++) {
			subjectsPerSemester.put(i, new ArrayList<Subject>());
			subjects = semesters[i].split(",");
			for (String sub : subjects) {
				System.out.println(sub.trim());
				subjectsPerSemester.get(i).add(Mapper.getSubject(sub.trim()));
			}
		}

	}

	public String toString() {
		String result;
		result = "Program " + name + " contains following: ";
		Integer semesterNumber = 1;
		for (ArrayList<Subject> s : subjectsPerSemester.values()) {
			result = result + " semester #" + semesterNumber.toString();
			for (Subject sub : s)
				result += " " + sub.name + ",";
			semesterNumber++;
		}

		return result;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
