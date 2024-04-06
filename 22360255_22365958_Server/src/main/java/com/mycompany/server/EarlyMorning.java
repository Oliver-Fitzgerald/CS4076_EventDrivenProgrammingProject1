package com.mycompany.server;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

public class EarlyMorning extends RecursiveTask<String> {

    CourseList courses ;
    ArrayList<Module>[] days = new ArrayList[7] ;
    public EarlyMorning(CourseList courses){this.courses = courses ;}
    @Override
    public String compute(){
        for (int i = 0; i < 7; i++)
            days[i] = new ArrayList<Module>() ;

        //Add modules to relevant day
        for (Course course:courses.getCourses()) {
            for (int i = 0; i < course.getModCount(); i++) {
                DayOfWeek day = course.getModules()[i].getDay();
                switch (day) {
                    case DayOfWeek.MONDAY:
                        days[0].add(course.getModules()[i]);
                        break;
                    case DayOfWeek.TUESDAY:
                        days[1].add(course.getModules()[i]);
                        break;
                    case DayOfWeek.WEDNESDAY:
                        days[2].add(course.getModules()[i]);
                        break;
                    case DayOfWeek.THURSDAY:
                        days[3].add(course.getModules()[i]);
                        break;
                    case DayOfWeek.FRIDAY:
                        days[4].add(course.getModules()[i]);
                        break;
                    case DayOfWeek.SATURDAY:
                        days[5].add(course.getModules()[i]);
                        break;
                    case DayOfWeek.SUNDAY:
                        days[6].add(course.getModules()[i]);
                        break;
                }
            }
        }

        //sort by start time
        for (int i = 0; i < 7;i++)
            EarlyMorning.sortByTime(days[i]) ;

        //change time

        return "" ;
    }

    /**
     *Sorts all the modules in a day by time
     * @param dayOfModules  dayOfModules a list of modules on in a day
     * @return the modules in that day sorted by the time from earliest to latest
     */
    private static ArrayList<Module> sortByTime(ArrayList<Module> dayOfModules){
       ArrayList<Module> sorted = new ArrayList<>() ;

       int hi = dayOfModules.size(), lo = dayOfModules.size() / 2 ;

       Module[] dayOfModulesArr = dayOfModules.toArray(Module[]::new) ;

       for (int i = 0; i)
       Thread left = new Thread(EarlyMorning.sortByTime());
       return sorted ;
    }

}
