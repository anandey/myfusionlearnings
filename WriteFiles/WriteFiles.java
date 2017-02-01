
/*

This program is used to copy all files's content into a single file i.e; output.doc.
This program takes inputs file extensions which to be copied and
	the root directory path which contains the files to be copied.

*/

import java.io.*;

class WriteFiles
{
       static String exts=null;

       static void doWrite(File f) throws Exception
        {
                String file=f.getCanonicalPath();

                String temp=new File(file).getName(); //substring(file.lastIndexOf('/')+1,file.length());
                temp=temp.substring(temp.indexOf('.')+1,temp.length());

	System.out.println("file name: "+file+"; extension is:"+temp);

                if(exts.indexOf(temp)!=-1)
                {
        System.out.println("File is "+f.getAbsolutePath()+"\n Cannonical path"+f.getCanonicalPath()+"\n\n");

			try{

			FileInputStream fin=new FileInputStream(f.getCanonicalPath());
			//FileReader fin=new FileReader(f.getCanonicalPath());
        	        FileOutputStream fout=new FileOutputStream("output.doc",true);
			fout.write(("\n"+f.toString()+"\n").getBytes());
			FileOutputStream fout1=new FileOutputStream("titles.doc",true);
			fout1.write(("\n"+f.toString()+"\n").getBytes());
			fout1.close();
			fout.write(("======================== \n\n").getBytes());
                	int n=0;
	                while(true)
        	        {
                	        n=fin.read();
				if(n!=-1)	fout.write(n);
				else break;
	                }

			fout.write("\n".getBytes());


			}catch(Exception ex){ex.printStackTrace();}

                       /*FileInputStream fin=new FileInputStream(f.getCanonicalPath());

                       FileWriter fw=new FileWriter("./Output.doc");
                       int n=0;
                       while(1)
                       {
                        	n=fin.read();
				if(n!=-1)
	                        fw.write(n);
				else break;
                       }*/




                }
        }

        static void check(String filename) throws Exception
        {
                File f=new File(filename).getCanonicalFile();
                File[] list=f.listFiles();
	/*System.out.println("-------------------------------------");
	System.out.println(list+"file name of fun...."+filename + " file obj"+ f.getCanonicalPath());
	System.out.println("-------------------------------------");
	*/

                File f1=null;

                for(int i=0;i<list.length;i++)
                {
                        f1=list[i];//new File(list[i]);
	System.out.println(f1.getAbsolutePath()+" isDir:"+ f1.isDirectory()+" isFile:"+f1.isFile());
                        if(f1.isDirectory())
                                check(f1.getCanonicalPath());
                        else
                                doWrite(f1);
                }
        }

        public static void main(String[] arg)
        {
                try
                {
                        DataInputStream din=new DataInputStream(System.in);
                        System.out.println("Enter extensions to be consider seperate by comma");
                        exts=din.readLine();
                        System.out.println("Enter directory with path ");
                        check(din.readLine());

                }catch(Exception ex){ex.printStackTrace();}
        }
}

