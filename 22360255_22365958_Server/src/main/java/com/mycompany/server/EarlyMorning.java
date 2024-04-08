package com.mycompany.server;

import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

public class EarlyMorning extends RecursiveTask<String> {

    private Module[] day ;
    private ArrayList<Module> out = new ArrayList<>() ;
    private int lo,hi ;
    public EarlyMorning(Module[] day,int lo,int hi){
        this.day = day ;
        this.lo = lo ;
        this.hi = hi ;

    }

    @Override
    public String compute(){

        if (hi - lo == 1){
            Module temp ;
            if (day[lo].getStartTime().isAfter(day[hi].getStartTime())) {
                temp = day[lo];
                day[lo] = day[hi];
                day[hi] = temp;
            }
        }else{
            int mid =  (hi - lo) / 2;
            EarlyMorning left = new EarlyMorning(day,lo,mid) ;
            EarlyMorning right = new EarlyMorning(day,mid + 1,hi) ;
            left.compute() ;
            right.fork() ;
            right.join() ;

            while (out.size() < day.length) {
                if (left.lo == right.lo){
                    out.add(right.day[right.lo]);
                    right.lo++ ;
                } else if (right.lo == right.lo + 2) {
                    out.add(left.day[left.lo]) ;
                   left.lo++ ;
                }else if (left.day[left.lo].getStartTime().isBefore(right.day[right.lo].getStartTime())){
                    out.add(left.day[left.lo]);
                    left.lo++ ;
                } else {
                    out.add(right.day[right.lo]);
                    right.lo++;
                }
            }
        }

        return null;
    }


}
