package com.mycompany.server;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class EarlyMorning extends RecursiveTask<ArrayList<Module>> {

    private Module[] day ;
    private static ArrayList<Module> out ;
    public EarlyMorning(Module[] day){
        this.day = day ;
    }

    @Override
    public ArrayList<Module> compute() {
        int length = day.length;

        if (length <= 1) {
            out = new ArrayList<>() ;
            out.add(day[0]) ;
            newTimes(out) ;
            return out ;
        }

        int middle = length / 2;
        Module[] leftArr = new Module[middle] ;
        Module[] rightArr = new Module[length - middle] ;

        int l = 0 ;
        int r = 0 ;
        for (;l < length; l++){
            if (l < middle)
                leftArr[l] = day[l] ;
            else{
                rightArr[r] = day[l] ;
                r++ ;
            }
        }


        EarlyMorning left = new EarlyMorning(leftArr) ;
        EarlyMorning right = new EarlyMorning(rightArr) ;
        /*
        left.fork() ;
        right.compute() ;
        left.join() ;

         */

        day = merge(leftArr,rightArr,day);
        out = new ArrayList<>(Arrays.asList(day)) ;

        out = newTimes(out) ;
        return out ;
    }


    private static Module[] merge(Module[] left, Module[] right, Module[] array){

        int leftSize = array.length / 2;
        int rightSize = array.length - leftSize ;
        int i=0, l=0, r=0 ;

        while (l < leftSize && r < rightSize){
            if (left[l].getStartTime().isBefore(right[r].getStartTime())) {
                array[i] = left[l];

                l++ ;
                i++ ;
            }else{
                array[i] = right[r] ;

                r++ ;
                i++ ;
            }
        }
        while (l < leftSize){
            array[i] = left[l];

            l++ ;
            i++ ;

        }
        while (r < rightSize){
            array[i] = right[r];

            r++ ;
            i++ ;

        }
        return array ;

    }

    private static ArrayList<Module> newTimes(ArrayList<Module> arrayList){
        Module module ;

        for (int i = 0; i < arrayList.size(); i++){
             module = arrayList.get(i) ;

             switch (i){
                case 0: module.setStartTime(LocalTime.of(9,0)); break ;
                case 1: module.setStartTime(LocalTime.of(10,0)); break ;
                case 2: module.setStartTime(LocalTime.of(11,0)); break ;
                case 3: module.setStartTime(LocalTime.of(12,0)); break ;
                case 4: module.setStartTime(LocalTime.of(13,0)); break ;
                case 5: module.setStartTime(LocalTime.of(14,0)); break ;
                case 6: module.setStartTime(LocalTime.of(15,0)); break ;
                case 7: module.setStartTime(LocalTime.of(16,0)); break ;
                case 8: module.setStartTime(LocalTime.of(17,0)); break ;
            }
        }
        return out ;
    }

}
