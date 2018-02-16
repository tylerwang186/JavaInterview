/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package course;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;


/**
 *
 * @author yihanwang
 */
public class Course {
    
    private ArrayList<Pair<Integer,Integer>> newArray = new ArrayList<Pair<Integer,Integer>>(); 
    //private CopyOnWriteArrayList<Integer> intList = new CopyOnWriteArrayList<Integer>();
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        // TODO code application logic here
        Course course = new Course();
        
        System.out.println(course.findNewCourse(course.readFile()));
    }
    
    public ArrayList<Pair<Integer,Integer>> readFile() throws IOException{
        
        FileInputStream fis = new FileInputStream(new File("./prerequisites.xls"));
        //create workbook instance that refers to .xlsx file
        HSSFWorkbook wb = new HSSFWorkbook(fis);
        //create a shhet object to retrive the sheet;
        HSSFSheet sheet = wb.getSheetAt(0);
        //that is for evaluating the cell type;
        FormulaEvaluator forlulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
        
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
    
    /*
    public int findCourse(int courseId) throws IOException{
        readFile();
        
        int i = 0;
        //first loop to check the course id in the array
        while(i<newArray.size()){
            if(newArray.get(i).first == courseId){
                intList.add(newArray.get(i).second);
                //then loop the new arrayList to check the course id 
                //until all prerequisites are added in the new arrayList
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
    */    

    public int findNewCourse(ArrayList<Pair<Integer,Integer>> courseArray){
        int preNumber = 0;
        //CopyOnWriteArrayList<Integer> intList = new CopyOnWriteArrayList<Integer>();
        CopyOnWriteArrayList<Integer> allCourse = new CopyOnWriteArrayList<Integer>();
        
        for(int courseId = 0; courseId < courseArray.size();courseId++){
            allCourse.add(courseArray.get(courseId).first);
        }
        
        //first loop to check the course id in the array
        for(int o: allCourse){
            CopyOnWriteArrayList<Integer> intList = new CopyOnWriteArrayList<Integer>();
            int i = 0;
            while(i < newArray.size()){
                    if(newArray.get(i).first == o){
                    intList.add(newArray.get(i).second);
                    //then loop the new arrayList to check the course id 
                    //until all prerequisites are added in the new arrayList
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
            if(preNumber < intList.size()){
                //System.out.println(intList.size());
                preNumber = intList.size();
            }
            
        }
        return preNumber;
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
