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
    
    /**
     * Default EmailGenerator constructor
     * 
     * @throws FileNotFoundException
     * @throws IOException
     */
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
    
    /**
     * EmailGenerator constructor
     * 
     * @param rowMin  The minimum last 3 digits of the studentID
     * @param rowMax  The maximum last 3 digits of the StudentID
     * @param yearMin The minimum second digit of the studentID
     * @param yearMax The maximum second digit of the studentID
     * @throws FileNotFoundException
     * @throws IOException
     */
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
    
    /**
     * Generates a six digit studentID code
     * 
     * @return A randomly generated student ID that fits the parameters of the Email Generator.
     */
    public int generateNewID()
    {
    	return MAX_STUDENT_ID_LENGTH + ( yearMin + (int)(Math.random() * ((yearMax - yearMin) + 1)) ) * ((int)Math.pow(10, 4))
                + ( rowMin + (int)( Math.random() * ( (rowMax - rowMin) + 1 ) ) );
    }
    
    /**
     * Generates a list of every possible ID within the parameters of the Email Generator,
     * and then sorts them for convenience
     */
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
    
    /**
     * Calculates the maximum amount of studentIDs that can be created
     * within the parameters of the Email Generator.
     * 
     * @return studentID list size
     */
    public int getPredictedListLength()
    {
    	return (rowMax - rowMin + 1) * (yearMax - yearMin + 1);
    }
    
    /**
     * Calculates the maximum amount of studentIDs that can be created
     * given the parameters specified by the user.
     * 
     * @param rowMin  The minimum last 3 digits of the studentID
     * @param rowMax  The maximum last 3 digits of the StudentID
     * @param yearMin The minimum second digit of the studentID
     * @param yearMax The maximum second digit of the studentID
     * @return studentID list size
     */
    public int getPredictedListLength(int rowMin, int rowMax, int yearMin, int yearMax)
    {
    	return (rowMax - rowMin + 1) * (yearMax - yearMin + 1);
    }
    
    /**
     * Check yearMin, yearMax, rowMin, rowMax, and the difference
     * between yearMin and yearMax.
     * 
     * The mins cannot be greater than the maxes, and the years
     * cannot be more than 4 years apart.
     * 
     * @return true if the parameters of the EmailGenerator have been
     * 		   set up properly, false otherwise.
     */
    public boolean checkParameters()
    {
    	return yearMin <= yearMax && rowMin <= rowMax && 
    		   yearMax - yearMin <= MAX_YEAR_DIFFERENCE;
    }
    /**
     * Check yearMin, yearMax, rowMin, rowMax, and the difference
     * between yearMin and yearMax.
     * 
     * The mins cannot be greater than the maxes, and the years
     * cannot be more than 4 years apart. 
     * 
     * @param emailInfo {yearMin, yearMax, rowMin, rowMax}
     * @return true if the parameters of the EmailGenerator have been
     *         set up properly, false otherwise.
     */
    public boolean checkParameters(int[] emailInfo)
    {
    	return emailInfo.length == 4 && emailInfo[0] < emailInfo[1] &&
    		   emailInfo[2] < emailInfo[3] &&
    		   emailInfo[1] - emailInfo[0] <= MAX_YEAR_DIFFERENCE;
    }
    
    /**
     * Gets the parameters of the EmailGenerator
     * 
     * @return EmailGenerator parameters in array format
     */
    public int[] getParameters()
    {
    	return new int[] {yearMin, yearMax, rowMin, rowMax};
    }
    
    /**
     * Set the parameters of the EmailGenerator
     * Format: {yearMin, yearMax, rowMin, rowMax}
     * 
     * @param emailInfo 
     */
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
    
    /**
     * Get list of studentIDs
     * 
     * @return List of studentIDs
     */
    public ArrayList<Integer> getStudentIDs()
    {
    	return studentIDs;
    }
    
    /**
     * Writes the generated studentIDs to a file
     * 
     * @throws IOException
     */
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
