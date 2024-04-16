package com.mycompany.server;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="course")
public class Course{
    private volatile Module[] modules;
    private String code;
    private int modCount = 0;

    public Course(Module[] modules, String code){
        this.modules = modules;
        this.code = code;
    }

    public Course(String code){
        this(new Module[5], code);
    }

    public Course(){
        this(new Module[5], "");
    }

    public synchronized void addModule(Module mod) {
        this.modules[modCount] = mod;
        modCount++;
    }

    public synchronized void removeModule(int index) throws IncorrectActionException{
        modules[index] = null ;
        modCount-- ;

        for (int i = index; i < modCount;i++)
            if (i + 1 < 5)
             modules[i] = modules[i + 1] ;

    }

    @Override
    public boolean equals(Object obj){
        if(this == obj)
            return true;
        if(obj == null || getClass() != obj.getClass())
            return false;

        Course other = (Course) obj;
        return code == other.getCode() && this.modules.equals(other.getModules());
    }

    public int overlaps(Module check){
        for(Module mod : modules){
            if(mod.getStartTime().isBefore(check.getEndTime()) &&
                    check.getStartTime().isBefore(mod.getEndTime())) {
                if (mod.getDate().equals(check.getDate()) && mod.getRoomCode().equals(check.getRoomCode())) {
                    return 2;
                }
                return 1;
            }
        }
        return 0;
    }

    @XmlElement(name="modules")
    public Module[] getModules() {
        return modules;
    }

    public void setModules(Module[] modules) {
        this.modules = modules;
    }

    @XmlElement(name="courseCode")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getModCount(){ return modCount; }

    @Override
    public String toString(){
        String out = code + ":\n";

        for(Module mod : modules){
            if(mod != null)
                out += mod + "\n";
        }

        return out.substring(0, out.length()-2);
    }
}
