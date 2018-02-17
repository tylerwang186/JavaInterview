/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package course;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author yihanwang
 */
public class Course {
    
    private ArrayList<Pair<Integer,Integer>> newArray = new ArrayList<Pair<Integer,Integer>>(); 
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        // TODO code application logic here
        Course course = new Course();
        
        System.out.println(course.preventInfiniteLoop(course.readFile()));
        //System.out.println(course.findCourse(3));
        System.out.println("In order to complete as many of the provided course units as possible, \n adhering to the pre-requisite requirements,"
        + "\n Students need to complete units as the following order: " + course.reOrder(course.preventInfiniteLoop(course.readFile())));
    }
    
    /**
     * This method is to read the excel file 
     * and store data retrieved from the file into an ArrayLsit,then return it
     * @return and ArrayList of course Id and its prerequisites
     */
    public ArrayList<Pair<Integer,Integer>> readFile() throws IOException{
        
        FileInputStream fis = new FileInputStream(new File("./prerequisites.xls"));
        //create workbook instance that refers to .xlsx file
        HSSFWorkbook wb = new HSSFWorkbook(fis);
        //create a shhet object to retrive the sheet;
        HSSFSheet sheet = wb.getSheetAt(0);
        
        int lastrownum = sheet.getLastRowNum();
        for(int i =1; i < lastrownum + 1;i++){
            Row row = sheet.getRow(i);
            Cell cell1 = row.getCell(0);
            Cell cell2 = row.getCell(1);
            int number1 = (int)cell1.getNumericCellValue();
            int number2 = (int)cell2.getNumericCellValue();
            newArray.add(new Pair(number1,number2));
        }
        return newArray;
    }
         
    /**
     * This method is to collect all courses Id
     * @param courseArray
     * @return all courses Id
     */
    public CopyOnWriteArrayList collectCourseId(ArrayList<Pair<Integer,Integer>> courseArray){
        CopyOnWriteArrayList<Integer> allCourseId = new CopyOnWriteArrayList<>();
  
        //store all course Id into an ArrayList
        for(int courseId = 0; courseId < courseArray.size();courseId++){
            allCourseId.add(courseArray.get(courseId).first);
        }
        return allCourseId;
    }
    
    /**
    * This method is to find the most courses that can be completed within all courses provided
    * adhering to the prerequisite requirements and return the courses
    * @param Pair
    * @return all courses that can be selected
    */
    public CopyOnWriteArrayList findAllCourses(ArrayList<Pair<Integer,Integer>> courseArray,int course){
        CopyOnWriteArrayList<Integer> preList = new CopyOnWriteArrayList<>();
        int i = 0;
            while(i < courseArray.size()){
                    //check if the course has prerequisites
                    if(courseArray.get(i).first == course){
                        
                        
                        if(!preList.contains(courseArray.get(i).second)){
                            preList.add(courseArray.get(i).second);
                            //then loop the new arrayList to check the course id 
                            //until all prerequisites are added in the new arrayList
                            
                            
                            for(int n = 0;n < preList.size();n++){
                                int c = 0;
                                while(c<courseArray.size()){
                                    if(courseArray.get(c).first.equals(preList.get(n))){
                                        if(!preList.contains(courseArray.get(c).second)){
                                            preList.add(courseArray.get(c).second);
                                        }   
                                    }   
                                    c++;                 
                                }
                            }     
                        }
                    }
                i++;
            }
        return preList;
    }
    
    /**
    * This method is to find the most courses that can be completed within all courses provided
    * adhering to the prerequisite requirements and return the courses
    * @param Pair
    * @return all courses that can be selected
    */
    public CopyOnWriteArrayList preventInfiniteLoop(ArrayList<Pair<Integer,Integer>> courseArray){
        int preNumber = 0;
        CopyOnWriteArrayList<Integer> allCourseId = collectCourseId(courseArray);
        CopyOnWriteArrayList<Integer> finalArray = new CopyOnWriteArrayList<>();
       
        //iterate all courses in the ArrayList and store the course Id into a new ArrayList
        for(int course:allCourseId){
            CopyOnWriteArrayList<Integer> preList = findAllCourses(courseArray,course);
            
            //to check if the prerequisite list contains the course itself
            //if so the course cannot be selected
            if(preNumber < preList.size() & !preList.contains(course)){
                preNumber = preList.size();
                System.out.print(course);
                finalArray = preList;
            }             
        }
        return finalArray;
    }
    
    /**
     * This method is to re-order the course Id
     * @param courseOrder
     * @return the re-ordered courses Id
     */
    public CopyOnWriteArrayList reOrder(CopyOnWriteArrayList<Integer>courseOrder){
        CopyOnWriteArrayList<Integer> allCourseId = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<Integer> newCourseOrder = new CopyOnWriteArrayList<>();
        //store all course Id into an ArrayList
        for(int courseId = 0; courseId < newArray.size();courseId++){
            allCourseId.add(newArray.get(courseId).first);
        }
        System.out.println(allCourseId);
        for(int i = 0; i < courseOrder.size(); i++){
            if(!allCourseId.contains(courseOrder.get(i))){
                newCourseOrder.add(courseOrder.get(i));
                courseOrder.remove(courseOrder.get(i));
            }             
        }
        Collections.sort(courseOrder);
        newCourseOrder.addAll(courseOrder);
        return newCourseOrder;
    } 
}

/**
 * The class is created to represent each course with its prerequisite 
 * @author yihanwang
 * @param <A>
 * @param <B> 
 */

class Pair<A,B>{
    public final A first;
    public final B second;
    
    public Pair(final A first,final B second){
        this.first = first;
        this.second = second;
    }
}
