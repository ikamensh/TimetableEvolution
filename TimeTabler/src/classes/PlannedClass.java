package classes;

import java.util.HashSet;
import java.util.List;

import com.google.common.collect.Lists;

import Classes_resource.Group;
import Classes_resource.Professor;
import Classes_resource.Resource;
import Classes_resource.Room;
import optimization.Timetable;
import util.Mapper;
import util.MiscUtils;

public class PlannedClass {

	public Professor prof;
	public Group group;
	public Timeslot time;
	public Room room;
	public Subject subject;
	public Timetable timetable;

	public PlannedClass(Professor p, Group g, Subject s) {
		prof = p;
		group = g;
		subject = s;

	}

	public void finishRandom() {
		time = (Timeslot) MiscUtils.getRandomObject(Mapper.timeslots.values());
		room = (Room) MiscUtils.getRandomObject(Mapper.rooms.values());

	}

	public PlannedClass(PlannedClass c, Timetable timetable) {

		prof = c.prof;
		group = c.group;
		time = c.time;
		room = c.room;
		subject = c.subject;
		this.timetable = timetable;

	}

	public void registerClass() {

		List<Resource> temp = Lists.newArrayList(prof, group, room);

		addPlannedClass(prof);
		addPlannedClass(group);
		addPlannedClass(room);

	}

	public void addPlannedClass(Resource r) {

		if (!timetable.schedule.get(r).containsKey(time)) {
			timetable.schedule.get(r).put(time, new HashSet<PlannedClass>());
			timetable.freeTime.get(r).remove(time);
		}

		timetable.schedule.get(r).get(time).add(this);

	}

	public void deregisterClass() {

		timetable.removePlannedClass(this, prof);
		timetable.removePlannedClass(this, group);
		timetable.removePlannedClass(this, room);

	}

	public void changeTime(Timeslot newTimeslot) {
		Resource[] res = { prof, room, group };

		for (Resource r : res) {
			timetable.removePlannedClass(this, r);
		}

		time = newTimeslot;

		for (Resource r : res) {
			addPlannedClass(r);
		}
	}

	public void changeRoom(Room newRoom) {

		timetable.removePlannedClass(this, room);

		room = newRoom;

		addPlannedClass(newRoom);

	}

	public int penaltyRoomSize() {
		if (room.size >= group.studentsCount) {
			return 0;
		} else {
			return group.studentsCount - room.size;
		}

	}

	public PlannedClass() {
		// TODO Auto-generated constructor stub
	}

	public Professor getProf() {
		return prof;
	}

	public void setProf(Professor prof) {
		this.prof = prof;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Timeslot getTime() {
		return time;
	}

	public void setTime(Timeslot time) {
		this.time = time;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public void setTimetable(Timetable t) {
		timetable = t;

	}

	public String toString() {
		String result = room.name + " " + group.name + " " + subject.name + " " + prof.name;

		return result;

	}

}
