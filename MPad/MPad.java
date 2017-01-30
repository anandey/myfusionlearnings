import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

class Terminator extends WindowAdapter
{
        public void WindowClosing(WindowEvent we)
        {
                System.exit(0);
        }
}

class MsgBox extends JDialog implements ActionListener
{
        MsgBox(Frame parent,String title,String st)
	{
                super(parent,title,false);
		setLayout(new FlowLayout());
                setSize(300,100);
                add(new Label(st));
                JButton b;
                getContentPane().add(b=new JButton("Ok"));
                b.addActionListener(this);
        /**/        addWindowListener(new Terminator());
	}

        public void actionPerformed(ActionEvent e)
	{
        	dispose();
        }
}//END OF MSGBOX CLASS

class InputBox extends JDialog implements ActionListener
{
        JTextField t;
        InputBox(Frame parent,String title,String st)
	{
                super(parent,title,false);
		setLayout(new FlowLayout());
                setSize(300,100);
                add(new Label(st));
                add(t=new JTextField(20));
                JButton b;
                add(b=new JButton("Ok"));
                b.addActionListener(this);
        /**/        addWindowListener(new Terminator());
	}
        public String getString()
        {
                return t.getText().trim();
        }
        public void actionPerformed(ActionEvent e)
	{
        	dispose();
        }
}//END OF InputBOX CLASS

public class MPad extends JFrame implements ActionListener
{
        JTextArea t;
        JMenu filemnu,editmnu,toolsmnu,helpmnu;
        JMenuItem newopt,openopt,saveopt,saveasopt,printopt,exitopt;//FILE OPTIONS
        JMenuItem findopt,findnextopt;  //EDIT OPTIONS
        JMenuItem calcopt,wordcountopt; //TOOLS OPTIONS
        JMenuItem helpopt,aboutmeopt;      //HELP OPTIONS
        JMenuBar mb;

        FileDialog f;

        int pos,c;
        String st;

        public MPad() throws Exception
        {
                setSize(700,500);
                setTitle("MPad-Untitled");

                t=new JTextArea();
                getContentPane().add(new JScrollPane(t));

                mb=new JMenuBar();

                //MENUS INTIALIZATION

                filemnu=new JMenu("File");
                editmnu=new JMenu("Edit");
                toolsmnu=new JMenu("Tools");
                helpmnu=new JMenu("Help");

                //FILE MENU OPTIONS INITIALIZATION

                newopt=new JMenuItem("New");
                openopt=new JMenuItem("Open");
                saveopt=new JMenuItem("Save");
                saveasopt=new JMenuItem("Save As");
                printopt=new JMenuItem("Print");
                exitopt=new JMenuItem("Exit");

                newopt.addActionListener(this);
                openopt.addActionListener(this);
                saveopt.addActionListener(this);
                saveasopt.addActionListener(this);
                printopt.addActionListener(this);
                exitopt.addActionListener(this);


                filemnu.add(newopt);filemnu.add(openopt);
                filemnu.add(saveopt);filemnu.add(saveasopt);
                filemnu.add(printopt);filemnu.add(exitopt);

                //EDIT MENU OPTIONS INITIALIZATION

                findopt=new JMenuItem("Find");
                findnextopt=new JMenuItem("Find Next");

                findopt.addActionListener(this);
                findnextopt.addActionListener(this);

                editmnu.add(findopt);editmnu.add(findnextopt);

                //TOOLS MENU OPTIONS INITALIZATION

                calcopt=new JMenuItem("Caleculator");
                wordcountopt=new JMenuItem("WordCount");
                toolsmnu.add(calcopt);toolsmnu.add(wordcountopt);

                calcopt.addActionListener(this);
                wordcountopt.addActionListener(this);

                //HELP MENU OPTIONS INITIALIZATION

                helpopt=new JMenuItem("HelpTopics");
                aboutmeopt=new JMenuItem("About me");

                helpmnu.add(helpopt);helpmnu.add(aboutmeopt);
                helpopt.addActionListener(this);
                aboutmeopt.addActionListener(this);

                mb.add(filemnu);mb.add(editmnu);
                mb.add(toolsmnu);mb.add(helpmnu);

                setJMenuBar(mb);
 /**/               addWindowListener(new Terminator());

        }
        public void actionPerformed(ActionEvent e) //throws Exception
        {
                if(e.getSource()==newopt)
                {
                        t.setText("");

                }//END OF NEWOPT
                else
                if(e.getSource()==openopt) //throws Exception
                {
                        FileDialog f=new FileDialog(this,"Open a file",FileDialog.LOAD);
                        f.show();
                        try
                        {
                                FileInputStream fin=new FileInputStream(f.getFile());
                                int nb=fin.available();
                                byte[] buff=new byte[nb];
                                int ct=fin.read(buff,0,nb);
                                String st=new String(buff);
                                t.setText(st);

                        }
                        catch(Exception ex)
                        {
                                MsgBox d=new MsgBox(this,"MPad","You enterd a wrong filename: "+ex);
                                d.setVisible(true);
                        }
                } //END OF OPENOPT
                else
                if(e.getSource()==saveopt)
                {
                        FileDialog f=new FileDialog(this,"SaveAs file",FileDialog.SAVE);
                        f.show();
                }//END OF SAVEOPT
                else
                if(e.getSource()==findopt)
                {
                        InputBox ib=new InputBox(this,"Finding","Enter text to be find");
                        ib.setVisible(true);
                        st=ib.getString();
                        if(st.length()>0)
                        {
                                pos=t.getText().indexOf(st);
                                if(pos>0)
                                {
                                        t.setSelectionStart(pos);
                                        t.setSelectionEnd(st.length());
                                }
                        }
                }//END OF FINDOPT
                else
                if(e.getSource()==findnextopt)
                {
                        pos=t.getText().indexOf(st,pos+1);
                        if(pos!=-1)
                        {
                                t.setSelectionStart(pos);
                                t.setSelectionEnd(st.length());
                        }
                        else
                        {
                                MsgBox mb=new MsgBox(this,"Finding","Searching is Completed");
                                mb.show();
                        }
                }//END OF FINDNEXTOPT
                else
                if(e.getSource()==calcopt)
                {
                        try
                        {
                                Runtime.getRuntime().exec("calc.exe");
                        }
                        catch(Exception ex)
                        {
                                MsgBox mb=new MsgBox(this,"Error","Caleculator is not found");
                                mb.show();
                        }
                }//END OF CALCOPT
                else
                if(e.getSource()==wordcountopt)
                {
                        MsgBox mb=new MsgBox(this,"WordCount","Total Charecters are: "+t.getText().length());
                        mb.show();
                }//END OF WORDCOUNTOPT
                else
                if(e.getSource()==aboutmeopt)
                {
                        try
                        {
                                Runtime.getRuntime().exec("mwp.exe");
                        }
                        catch(Exception ex)
                        {
                                MsgBox mb=new MsgBox(this,"Error","Aboutme form is not found");
                                mb.show();
                        }

                }//END OF ABOUTMEOPT
                else
                if(e.getSource()==exitopt)
                {
                        dispose();
                }//END OF EXITOPT
                else
                {
                        MsgBox d=new MsgBox(this,"MPad","The poreject is on the progress");
                        d.setVisible(true);
                }

        }

        public static void main(String[] args) throws Exception
        {
                MPad p=new MPad();
                p.show();
                p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                p.setDefaultLookAndFeelDecorated(true);
        }
}
