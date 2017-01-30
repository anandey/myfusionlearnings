import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;
import javax.swing.text.JTextComponent;
import java.awt.Frame;

/*
 * To provide methods of file operations useful to the tool
 */
class FileOperations
{
	String stExts = null;// To store user input extensions
	String stTargetFile = null; // To store user target file
	boolean bAppend = false;// To store whether the target file to be append
	boolean includeSubFolders;// To store whether files in sub folders to
	
	// be considered
	
	/* Setters */
	public void setExtensions(String stExts)
	{
		this.stExts = stExts;
	}
	
	public void setTargetFile(String stTargetFile)
	{
		this.stTargetFile = stTargetFile;
	}
	
	public void setAppend(boolean bAppend)
	{
		this.bAppend = bAppend;
	}
	
	public void setIncludeSubFolders(boolean includeSubFolders)
	{
		this.includeSubFolders = includeSubFolders;
	}
	
	/* To get the size of the target file */
	public long getSize()
	{
		long size = 0;
		try
		{
			size = (new File(stTargetFile)).length();
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return size;
	}
	
	/*
	 * Description: To write the content of a given file into target file.
	 * Inputs: File file - path of the source file
	 */
	public void doWrite(File file) throws Exception
	{		
		File fTargetFile=null;
		BufferedReader reader=null;//For input file
		PrintWriter dataWriter = null;//To write data to the target file
		PrintWriter titleWriter=null;//To write titles to the titles file
		
		try
		{	
			//Defining the file objects
			fTargetFile = new File(stTargetFile);			
			reader=new BufferedReader(new FileReader(file.getCanonicalPath()));
			dataWriter=new PrintWriter(new FileWriter(fTargetFile.getCanonicalPath(),bAppend),true);
			titleWriter=new PrintWriter(new FileWriter(fTargetFile.getParent()+ "/titles_" + fTargetFile.getName(),bAppend),true);

			//Writing file name to the target file
			dataWriter.println(file.toString());
			
			// Underlining the heading
			String underline = "";
			for (int i = 0; i < file.toString().length(); i++)
				underline += "=";
			
			//Write the undeline to the file
			dataWriter.println(underline);			
			
			/* Write file name in the titles file */
			titleWriter.println(file.toString());
			
			/* Copy content of the file into target file */			
			String data=" ";//To store data line
			while ((data=reader.readLine())!= null)
				dataWriter.println(data);
			
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
	
	/*
	 * Descripion: This method checks for the required extensions in the
	 * directory. If the extension exists then it invoke doWrite() method to
	 * copy the file. Input: String stDir- Path of the root directory
	 */
	public void check(String stDir, JTextArea txtFiles,
			boolean displaySelectedTypes) throws Exception
	{
		// Store all the files in the directory
		File[] list = (new File(stDir)).getCanonicalFile().listFiles();
		
		File file = null; // To operate each file from the above list
		
		for (int iIndex = 0; iIndex < list.length; iIndex++)
		{
			file = list[iIndex];
			
			/* Check whether the file is directory or single file */
			if (file.isDirectory())
			{
				/*
				 * invoke the method using recursive by passing the
				 * sub-directory
				 */
				if (includeSubFolders == true)
					check(file.getCanonicalPath(), txtFiles,
							displaySelectedTypes);
			} else
			{
				/* Get the name of the file */
				String stTemp = null; //To store file name temporarily
				stTemp = (new File(file.getCanonicalPath())).getName();
				/* Get the extension of the file */
				stTemp = stTemp.substring(stTemp.lastIndexOf('.') + 1,
						stTemp.length());
				
				if (displaySelectedTypes == false)
				{
					txtFiles.append("\n" + file.getCanonicalPath());
				}
				
				/*
				 * Check whether the extension is exists in the user input
				 * extension list. If exists then copy that file to target
				 * file.
				 */

				if (stExts.indexOf(stTemp) != -1)
				{
					if (displaySelectedTypes == true)
					{
						txtFiles.append("\n" + file.getCanonicalPath());
					}
					//Invoking method to copy the content of the file
					doWrite(file);
				}
			}/* End of if-else */
		}/* End of for loop */
	}/* End of check() */
	
	/*
	 * fileChooser() is to display FileChooser on request.
	 * It would be directory only or file only, FileChooser.
	 * */
	public static void fileChooser(Frame parent, JTextComponent txtField,
			int selectionMode) throws Exception
	{
		JFileChooser fRoot = new JFileChooser();
		fRoot.setFileSelectionMode(selectionMode);
		//Adding Only Directory filter
		if(selectionMode==JFileChooser.DIRECTORIES_ONLY)			
			fRoot.setFileFilter(new FileFilter(){
			@Override
			public boolean accept(File arg0){return true;}
			@Override
			public String getDescription(){return "Directories only";}
			});//End of setFileFilter
		
		if (fRoot.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION)
			txtField.setText(fRoot.getSelectedFile().toString());
		
	}//End of fileChooser
	
}/* End of FileOperations Class */
