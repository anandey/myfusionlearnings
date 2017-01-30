/**
 * 
 */
import java.io.*;
/**
 * @author Anand_Yerrapati
 *
 */
class Test
{
	boolean bAppend;
	String stTargetFile;
	
	/**
	 * 
	 */
	public Test()
	{
		// TODO Auto-generated constructor stub
	}
	public void doWrite(File file) throws Exception
	{
		BufferedReader reader=null;
		PrintWriter dataWriter = null;
		PrintWriter titleWriter=null;
		try
		{			
			File fTargetFile = new File(stTargetFile);
			/* Open an input stream to read the file */
			reader=new BufferedReader(new FileReader(file.getCanonicalPath()));
			
			/* Open an output stream to copy file content into target file */
			
			dataWriter=new PrintWriter(new FileWriter(fTargetFile.getCanonicalPath(),bAppend),true);
			
			/* Write file name in the target file */
			dataWriter.println(file.toString());
			
			// Underlining the heading
			String underline = "";
			for (int i = 0; i < file.toString().length(); i++)
				underline += "=";			
			
			dataWriter.println(underline);
			
			/*
			 * Open an output stream to store names of the files into
			 * titles.doc
			 */
			
			titleWriter=new PrintWriter(new FileWriter(fTargetFile.getParent()+ "/titles_" + fTargetFile.getName(),bAppend),true);
			/* Write file name in the titles.doc file */
			titleWriter.println(file.toString());
			
			/* Copy content of the file into target file */		
		
			String data;//To store data line
			while ((data=reader.readLine())!= null)
			{
				/* Write to target from buffer */
				dataWriter.println(data);
			}
			
			/* Write new line in the target file */
			dataWriter.println();		
			
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally 
		{
	            if (reader != null) 
	                reader.close();	            
	            if (dataWriter!= null) 
	                dataWriter.close();
	            if(titleWriter!=null)
	          	  titleWriter.close();
	      }

		
	}/* End of doWrite() */
	public String getStTargetFile()
	{
		return stTargetFile;
	}
	public void setStTargetFile(String stTargetFile)
	{
		this.stTargetFile = stTargetFile;
	}
	public void setBAppend(boolean append)
	{
		bAppend = append;
	}
	
}
