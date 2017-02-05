package model.schedule;

import java.util.ArrayList;
import java.util.List;

import model.constraint.TimeConstraint;
import model.course.LessonGroup;


public class Schedule {
	private final List<LessonGroup> lessons;
	private final List<TimeConstraint> constraints;
	
	public Schedule(){
		lessons = new ArrayList<>();
		constraints = new ArrayList<>();
	}
	public Schedule(final List<LessonGroup> lessons,final List<TimeConstraint> constraints){
		this.lessons = new ArrayList<>(lessons);
		this.constraints = new ArrayList<>(constraints);
	}
	
	/**
	 * 
	 * @param ¢
	 * @return true if lessonGroup can be added to lessond without causing a collision
	 */
	public boolean addLesson(final LessonGroup ¢) {
		/*if(!lessons.contains(¢)) // add equals to lessonsgroup
			lessons.add(¢);*/
		if(lessons.isEmpty()){
			lessons.add(¢);
			return true;
		}
		for(final LessonGroup l : lessons)
			if (l.isCLashWIth(¢))
				return false;
		lessons.add(¢);
		return true;
	}
	
	public void removeLesson(final LessonGroup ¢) {
		lessons.remove(¢);
	}
	
	public void addConstraint(final TimeConstraint ¢) {
		if(!constraints.contains(¢))
			constraints.add(¢);
	}
	
	public void removeConstraint(final TimeConstraint ¢) {
		constraints.remove(¢);
	}
	
	
	public List<TimeConstraint> getConstraints() {
		return constraints;
	}
	
	
	public List<LessonGroup> getLessonGroups() {
		return lessons;
	}
	
	public Timetable getTimetable() {
		return new Timetable(lessons);
			
	}
	
	public boolean hasLesson(final LessonGroup ¢) {
		return lessons.contains(¢);
	}
	
	public boolean hasConstraint(final TimeConstraint ¢) {
		return constraints.contains(¢);
	}
	
	public boolean isLegalSchedule(){
		for(int i=0; i < lessons.size(); ++i){
			for(int j=i+1; j < lessons.size(); ++j)
				if (lessons.get(i).isCLashWIth(lessons.get(j)))
					return false;
			for(final TimeConstraint ¢ : constraints)
				if (lessons.get(i).isCLashWIth(¢))
					return false;
		}
		return true;
	}
	
	@Override
	public String toString(){
		return lessons + "";
	}
	
	
}
