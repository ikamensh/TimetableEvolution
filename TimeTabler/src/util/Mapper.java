package util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import Classes_resource.Group;
import Classes_resource.Professor;
import Classes_resource.Resource;
import Classes_resource.Room;
import classes.StudyProgram;
import classes.StudyProgram.StudyPrograms;
import classes.Subject;
import classes.Timeslot;

public class Mapper {

	public static Map<String, Professor> profs = new HashMap<String, Professor>();
	public static Map<String, Room> rooms = new HashMap<String, Room>();
	public static Map<String, Group> groups = new HashMap<String, Group>();
	public static Map<StudyPrograms, StudyProgram> programs = new HashMap<StudyPrograms, StudyProgram>();
	public static Map<String, Subject> subjects = new HashMap<String, Subject>();
	public static Map<String, Timeslot> timeslots = new HashMap<String, Timeslot>();
	public static List<Resource> resources = Lists.newArrayList();

	public static void addRoom(Room room) {

		rooms.put(room.getName(), room);

	}

	public static void addTimeSlot(Timeslot timeSlot) {

		timeslots.put(timeSlot.getTimeAt(), timeSlot);

	}

	public static void addProfessor(Professor professor) {

		profs.put(professor.getName(), professor);

	}

	public static void addGroup(Group group) {

		groups.put(group.getName(), group);

	}

	public static void addProgram(StudyProgram program) {

		programs.put(StudyPrograms.valueOf(program.getName()), program);

	}

	public static void addSubject(Subject subject) {

		subjects.put(subject.getName(), subject);

	}

	public static Room getRoom(String name) {

		return rooms.get(name);

	}

	public static Professor getProfessor(String name) {

		return profs.get(name);

	}

	public static Group getGroup(String name) {

		return groups.get(name);

	}

	public static Subject getSubject(String name) {

		return subjects.get(name);

	}

	public static StudyProgram getProgram(StudyPrograms name) {

		return programs.get(name);

	}

	public static void initResources() {
		resources = Lists.newArrayList();
		resources.addAll(profs.values());
		resources.addAll(rooms.values());
		resources.addAll(groups.values());

	}

	public static List<Resource> getResources() {
		return resources;
	}

}
