import java.awt.AWTException;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class EmailGenerator {

    private final int MAX_STUDENT_ID_LENGTH = 100000;
    private final int MAX_YEAR_DIFFERENCE = 4;
    
    private int yearMax, yearMin, rowMin, rowMax, maxDuplicates;
    private FileWriter writer;
    private ArrayList<Integer> studentIDs;
    
    public EmailGenerator() throws FileNotFoundException, IOException
    {
    	yearMax = 0; yearMin = 0; rowMin = 0; rowMax = 0; maxDuplicates = 0;
    	studentIDs = new ArrayList<Integer>();
    	File file = new File("emails.txt");
        if (!file.exists())
            file.createNewFile();
        writer = new FileWriter(file);
    }
    
    public EmailGenerator(int yearMin, int yearMax, int rowMin, int rowMax, int maxDuplicates) throws FileNotFoundException, IOException
    {
    	this.yearMin = yearMin; this.yearMax = yearMax; 
    	this.rowMin = rowMin; this.rowMax = rowMax;
    	this.maxDuplicates = maxDuplicates;
    	
    	studentIDs = new ArrayList<Integer>();
    	File file = new File("Emails.txt");
    	if (!file.exists())
    		file.createNewFile();
    	writer = new FileWriter(file);
    }
    
    public void generateIDs()
    {
    	System.out.println("Starting...");
    	
    	int duplicates = 0;
    	int count = 0;
        while(duplicates <= maxDuplicates)
        {
        	int studentID = MAX_STUDENT_ID_LENGTH + ( yearMin + (int)(Math.random() * ((yearMax - yearMin) + 1)) ) * ((int)Math.pow(10, 4))
                    + ( rowMin + (int)( Math.random() * ( (rowMax - rowMin) + 1 ) ) );
        	
        	if(studentIDs.isEmpty())
        	{
        		studentIDs.add(studentID);
        		count++;
        		System.out.println(studentID + " created");
        	}
        	//Need to add items to the list AFTER I'm done reading through it
        	for(int i: studentIDs)
        	{
        		if(studentID == i)
        		{
        			duplicates++;
        		}else
        		{
        			studentIDs.add(studentID);
        			count++;
        			System.out.println(studentID + " created");
        		}
        	}
        }
        System.out.println(count + " IDs created.");
        System.out.println("Sorting...");
        Collections.sort(studentIDs);
    }
    
    public boolean checkParameters()
    {
    	return yearMin <= yearMax && rowMin <= rowMax && 
    		   yearMax - yearMin <= MAX_YEAR_DIFFERENCE;
    }
    
    public boolean checkParameters(int[] emailInfo)
    {
    	return emailInfo.length == 5 && emailInfo[0] < emailInfo[1] &&
    		   emailInfo[2] < emailInfo[3] &&
    		   emailInfo[1] - emailInfo[0] <= MAX_YEAR_DIFFERENCE;
    }
    
    public int[] getParameters()
    {
    	return new int[] {yearMin, yearMax, rowMin, rowMax, maxDuplicates};
    }
    
    public void setParameters(int[] emailInfo)
    {
    	if (!checkParameters(emailInfo) )
    	{
    		System.out.println("Incorrect Information");
    	}
    	
    	emailInfo[0] = yearMin;
    	emailInfo[1] = yearMax;
    	emailInfo[2] = rowMin;
    	emailInfo[3] = rowMax;
    	emailInfo[4] = maxDuplicates;
    }
    
    public void writeEmailsToFile()throws IOException
    {
    	for (int i: studentIDs)
    	{
    		writer.write(i + "@students.d120.org, " + "\n");
    	}
    	writer.flush();
    	writer.close();
    }
    
}
