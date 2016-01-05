import java.io.FileNotFoundException;
import java.io.IOException;

public class main {
	
	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		EmailGenerator randy = new EmailGenerator(6, 9, 001, 900);
		randy.generateIDList();
		randy.writeEmailsToFile();
	}
	
}
