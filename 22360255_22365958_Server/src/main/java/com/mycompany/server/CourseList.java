package com.mycompany.server;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
@XmlRootElement(name="courseList")
public class CourseList {
    private volatile ArrayList<Course> courses;

    public CourseList(){
        courses = new ArrayList<>();
    }

    @XmlElement(name="course")
    public ArrayList<Course> getCourses(){
        return courses;
    }

    public synchronized boolean add(Course c){
        return this.courses.add(c);
    }

    public synchronized boolean remove(Course c){
        return this.courses.remove(c);
    }

    public Course get(int c){
        return this.courses.get(c);
    }

    public int size(){
        return this.courses.size();
    }

}
