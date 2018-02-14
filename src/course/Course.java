/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package course;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;




/**
 *
 * @author yihanwang
 */
public class Course {
    
    private ArrayList<Pair<Integer,Integer>> newArray = new ArrayList<Pair<Integer,Integer>>(); 
    private CopyOnWriteArrayList<Integer> intList = new CopyOnWriteArrayList<Integer>();
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        // TODO code application logic here
        
    }
    
    public int findCourse(int number){
        newArray.add(new Pair(2,1));
        newArray.add(new Pair(2,6));
        newArray.add(new Pair(2,11));
        newArray.add(new Pair(3,1));
        newArray.add(new Pair(3,6));
        newArray.add(new Pair(3,7));
        newArray.add(new Pair(3,8));
        newArray.add(new Pair(3,12));
        newArray.add(new Pair(4,1));
        newArray.add(new Pair(4,3));
        newArray.add(new Pair(4,9));
        newArray.add(new Pair(5,1));
        newArray.add(new Pair(5,3));
        newArray.add(new Pair(5,4));
        newArray.add(new Pair(5,12));
        newArray.add(new Pair(6,1));
        newArray.add(new Pair(7,6));
        newArray.add(new Pair(8,6));
        newArray.add(new Pair(8,7));
        newArray.add(new Pair(8,12));
        newArray.add(new Pair(9,5));
        newArray.add(new Pair(11,8));
        newArray.add(new Pair(11,10));
        int i = 0;
      
        while(i<newArray.size()){
            if(newArray.get(i).first == number){
                intList.add(newArray.get(i).second);
                
                for(int n = 0;n < intList.size();n++){
                    int c = 0;
                    while(c<newArray.size()){
                        if(newArray.get(c).first == intList.get(n)){
                            if(!intList.contains(newArray.get(c).second)){
                                intList.add(newArray.get(c).second);
                                int n2 = intList.size();
                            }
                        }
                        c++;
                    }
                }
            }
            i++;
        }
        return intList.size();
    }
    
    
}

class Pair<A,B>{
    public final A first;
    public final B second;
    
    public Pair(final A first,final B second){
        this.first = first;
        this.second = second;
    }
}
