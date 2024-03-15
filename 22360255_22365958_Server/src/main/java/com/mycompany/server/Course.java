package com.mycompany.server;

public class Course{
    private Module[] modules;
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

    public void addModule(Module mod) {
        this.modules[modCount] = mod;
        modCount++;
    }

    public void removeModule() throws IncorrectActionException{

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

    public Module[] getModules() {
        return modules;
    }

    public void setModules(Module[] modules) {
        this.modules = modules;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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
