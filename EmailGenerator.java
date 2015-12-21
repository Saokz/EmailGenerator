import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class EmailGenerator {

    private final int MAX_STUDENT_ID_LENGTH = 100000;
    private final int MAX_YEAR_DIFFERENCE = 4;
    
    private int yearMax, yearMin, rowMin, rowMax;
    private FileWriter writer;
    private ArrayList<Integer> studentIDs;
    
    public EmailGenerator() throws FileNotFoundException, IOException
    {
    	yearMax = 0; yearMin = 0;
    	rowMin = 0; rowMax = 0;
    	studentIDs = new ArrayList<Integer>();
    	File file = new File("emails.txt");
        if (!file.exists())
            file.createNewFile();
        writer = new FileWriter(file);
    }
    
    public EmailGenerator(int yearMin, int yearMax, int rowMin, int rowMax) throws FileNotFoundException, IOException
    {
    	this.yearMin = yearMin; this.yearMax = yearMax; 
    	this.rowMin = rowMin; this.rowMax = rowMax;
    	
    	studentIDs = new ArrayList<Integer>();
    	File file = new File("Emails.txt");
    	if (!file.exists())
    		file.createNewFile();
    	writer = new FileWriter(file);
    }
    
    public int generateNewID()
    {
    	return MAX_STUDENT_ID_LENGTH + ( yearMin + (int)(Math.random() * ((yearMax - yearMin) + 1)) ) * ((int)Math.pow(10, 4))
                + ( rowMin + (int)( Math.random() * ( (rowMax - rowMin) + 1 ) ) );
    }
    
    public void generateIDList()
    {
    	System.out.println("Starting...");
    	
    	int count = 0;
        while(studentIDs.size() < getPredictedListLength())
        {
        	int studentID = generateNewID();
        	
        	if(studentIDs.isEmpty())
        	{
        		studentIDs.add(studentID);
        		
        		count++;
        		System.out.println(studentID + " created");
        	}
        	
        	for(int i = 0; i < studentIDs.size(); i++)
        	{
        		if (studentID == studentIDs.get(i) )
        		{
        			System.out.println();
        			System.out.println("Duplicate found: " + studentID);
        			
        			studentID = generateNewID();
        			
        			System.out.println("Generating new ID: " + studentID);
        			System.out.println();
        			
        			i = 0;
        		}
        	}
        	studentIDs.add(studentID);
        	
        	System.out.println(studentID + " created");
        	count++;
        	
        }
        System.out.println("generated " + count + " IDs");
        System.out.println("Sorting...");
        
        Collections.sort(studentIDs);
        
        System.out.println("Done.");
    }
    
    public int getPredictedListLength()
    {
    	return (rowMax - rowMin + 1) * (yearMax - yearMin + 1);
    }
    
    public int getPredictedListLength(int rowMin, int rowMax, int yearMin, int yearMax)
    {
    	return (rowMax - rowMin + 1) * (yearMax - yearMin + 1);
    }
    
    public boolean checkParameters()
    {
    	return yearMin <= yearMax && rowMin <= rowMax && 
    		   yearMax - yearMin <= MAX_YEAR_DIFFERENCE;
    }
    
    public boolean checkParameters(int[] emailInfo)
    {
    	return emailInfo.length == 4 && emailInfo[0] < emailInfo[1] &&
    		   emailInfo[2] < emailInfo[3] &&
    		   emailInfo[1] - emailInfo[0] <= MAX_YEAR_DIFFERENCE;
    }
    
    public int[] getParameters()
    {
    	return new int[] {yearMin, yearMax, rowMin, rowMax};
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
    }
    
    public ArrayList<Integer> getStudentIDs()
    {
    	return studentIDs;
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
