/* Importing packages, whose classes are to be used */

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JFileChooser;
import javax.swing.ButtonGroup;

/*
 * Author: Anandeswararao Yerrapati Description: This class is to create a GUI
 * for user.
 */
public class WFWindow extends JFrame implements ActionListener
{
	/* Declare required objects */

	// Menus of the form
	JMenu mnFile = null;
	// Options in the menus
	JMenuItem miSource = null, miTarget = null, miExit = null;
	// To hold source and destination paths
	JTextField txtSrc = null, txtDest = null;
	JList lFileTypes = null; // To display acceptable file types
	// Buttons used to display files choosers
	JButton btSrcBrowse = null, btDestBrowse = null;
	JButton btWrite = null;// To start writing files
	// To provied some options
	JCheckBox chSize = null, chIncludeSubFolders = null, chAppend = null;
	// To provied some more options
	JRadioButton rbListSelected = null, rbListAll = null;
	JTextArea txtFiles = null;// To show list of files
	JLabel lbStatus = null;// To display status
	
	// Array of file types to be allowed
	String[] stFileTypes = { "c", "c++", "css", "doc", "ini", "java", "rtf",
			"tcl", "txt", "xml" };
	// To Store selected file types which is initilized by all file types used
	String stSelectedTypes = null;
	
	// To print either selected type of files or all the files
	boolean displaySelectedTypes;
	
	/*
	 * Constructor
	 */
	WFWindow()
	{
		/* Defining objects */

		mnFile = new JMenu("File");
		miSource = new JMenuItem("Source Directory");
		miTarget = new JMenuItem("Target File");
		miExit = new JMenuItem("Exit");
		
		txtSrc = new JTextField(20);
		txtSrc.setEditable(false);
		txtDest = new JTextField(20);
		txtDest.setEditable(false);
		
		// Sorting the extension names
		Arrays.sort(stFileTypes);
		lFileTypes = new JList(stFileTypes);
		
		btSrcBrowse = new JButton("Browse");
		btDestBrowse = new JButton("Browse");
		btWrite = new JButton("Start Writing Files");
		
		chSize = new JCheckBox("Show Target File Size");
		chIncludeSubFolders = new JCheckBox("Include Sub Folders");
		chAppend = new JCheckBox("Append Target File", true);
		
		rbListSelected = new JRadioButton("List Selected Type Files");
		rbListAll = new JRadioButton("List All Files",true);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(rbListSelected);
		bg.add(rbListAll);
		
		txtFiles = new JTextArea(10, 40);
		
		lbStatus = new JLabel("Ready to write files.");
		
		// Add actionlistener to the required objects
		miSource.addActionListener(this);
		miTarget.addActionListener(this);
		miExit.addActionListener(this);
		btSrcBrowse.addActionListener(this);
		btDestBrowse.addActionListener(this);
		btWrite.addActionListener(this);
		chAppend.addActionListener(this);
		rbListSelected.addActionListener(this);
		rbListAll.addActionListener(this);
		
		/* Designing of Form */

		JMenuBar mb = new JMenuBar();
		setJMenuBar(mb);
		mnFile.add(miSource);
		mnFile.add(miTarget);
		mnFile.add(miExit);
		mb.add(mnFile);
		
		JPanel p1 = new JPanel();
		p1.add(new JLabel("Source Directory"));
		p1.add(txtSrc);
		p1.add(btSrcBrowse);
		JPanel p4 = new JPanel();
		p4.add(new JLabel("File Types"));
		p4.add(new JScrollPane(lFileTypes));
		JPanel p5 = new JPanel();
		p5.add(new JLabel("Target File"));
		p5.add(txtDest);
		p5.add(btDestBrowse);
		
		JPanel p2 = new JPanel(new GridLayout(3, 1));
		p2.add(chSize);
		p2.add(chIncludeSubFolders);
		p2.add(chAppend);
		
		JPanel p3 = new JPanel();
		p3.add(rbListSelected);
		p3.add(rbListAll);
		
		this.setLayout(new FlowLayout());
		
		add(p1);
		add(p4);
		add(p2);
		add(p5);
		add(p3);
		add(btWrite);
		add(new JScrollPane(txtFiles));
		add(lbStatus);
		
	}/* End of the Constructor */
	
	/*
	 * Handling the buttons and menus in the form.
	 */

	public void actionPerformed(ActionEvent ae)
	{
		if (ae.getSource() == miExit)
			System.exit(0);
		else if (ae.getSource() == miSource)
		{
			try
			{
				FileOperations.fileChooser(this, txtSrc,
						JFileChooser.DIRECTORIES_ONLY);
			} catch (Exception ex)
			{
				ex.printStackTrace();
			}
		} else if (ae.getSource() == miTarget)
		{
			try
			{
				FileOperations.fileChooser(this, txtDest,
						JFileChooser.FILES_ONLY);
			} catch (Exception ex)
			{
				ex.printStackTrace();
			}
		} else if (ae.getSource() == rbListSelected)
			displaySelectedTypes = true;
		else if (ae.getSource() == rbListAll)
			displaySelectedTypes = false;
		else if (ae.getSource() == btSrcBrowse)
		{
			/* Get the root directory path */
			try
			{
				FileOperations.fileChooser(this, txtSrc,
						JFileChooser.DIRECTORIES_ONLY);
			} catch (Exception ex)
			{
				ex.printStackTrace();
			}
		} else if (ae.getSource() == btDestBrowse)
		{
			/* Get the target directory path */

			try
			{
				FileOperations.fileChooser(this, txtDest,
						JFileChooser.FILES_ONLY);
			} catch (Exception ex)
			{
				ex.printStackTrace();
			}
			
		} else if (ae.getSource() == btWrite)
		{
			
			int iLength = 0;
			iLength = lFileTypes.getSelectedValues().length;
									
			Object obj[] = lFileTypes.getSelectedValues();
			
			// Validate the form before proceeding
			if (validateForm() == false)
				return;
			
			lbStatus.setText("Writing files...");
			
			stSelectedTypes = obj[0].toString();
			txtFiles.setText("The extensions you selected are "
					+ stSelectedTypes);
			for (int iLen = 1; iLen < iLength; iLen++)
			{
				stSelectedTypes += "," + obj[iLen].toString();
				txtFiles.append(", " + obj[iLen].toString());
			}
			txtFiles.append(" ...\n");
			
			FileOperations fop = new FileOperations();
			fop.setAppend(chAppend.isSelected());
			fop.setExtensions(stSelectedTypes);
			fop.setTargetFile(txtDest.getText());
			fop.setIncludeSubFolders(chIncludeSubFolders.isSelected());
			try
			{
				fop.check(txtSrc.getText(), txtFiles, displaySelectedTypes);
			} catch (Exception ex)
			{
				ex.printStackTrace();
			}
			
			lbStatus.setText("Process completed.");
			JOptionPane.showMessageDialog(this, "Process completed.", "Status", JOptionPane.INFORMATION_MESSAGE);
			
			if (chSize.isSelected())
			{
				String stMsg = null;
				stMsg = "File size is " + fop.getSize() + " bytes";
				JOptionPane.showMessageDialog(this, stMsg, "Information", JOptionPane.INFORMATION_MESSAGE);
			}
			
		}// End of btWrite action
		
	} /* End of the actionPerformed() */
	
	/*
	 * validateForm method is to validate whether the user has given proper inputs
	 * */
	private boolean validateForm()
	{
		if (txtSrc.getText().length() <= 0)
		{
			JOptionPane.showMessageDialog(this, "Please select a source directory.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (lFileTypes.getSelectedIndex() == -1)
		{
			JOptionPane.showMessageDialog(this,"Please select file types to write." , "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (txtDest.getText().length() <= 0)
		{
			JOptionPane.showMessageDialog(this, "Please select a target file.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
		
	}// End of validateForm()
	
	/*
	 * main method to start the application
	 * */
	public static void main(String[] arg)
	{
		WFWindow wf = new WFWindow();
		wf.show();
		wf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		wf.setSize(450, 550);
		wf.setResizable(false);
		wf.setTitle("WriteFiles");
	}
	
}/* End of the WFWindow class */
