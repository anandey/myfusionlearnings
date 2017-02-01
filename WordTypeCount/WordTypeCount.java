import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


public class WordTypeCount extends JFrame
{
    private JTextArea inputField;
    private JLabel prompt;
    private JTextArea display;
    private JButton goButton;
    
    private Map map;
    
    public WordTypeCount()
    {
        super("Word Type Count");
        inputField=new JTextArea(3,20);
        
        map=new HashMap();
        
        goButton=new JButton("Go");
        goButton.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent event){createMap(); display.setText(createOutput());}});
        
        prompt=new JLabel("Enter a string");
        display=new JTextArea(15,20);
        display.setEditable(false);
        
        JScrollPane displayScrollPane=new JScrollPane(display);
        
        Container container=getContentPane();
        container.setLayout(new FlowLayout());
        container.add(prompt);
        container.add(inputField);
        container.add(goButton);
        container.add(displayScrollPane);
        
        setSize(400,400);
        show();
    }
    
    private void createMap()
    {
        String input=inputField.getText();
        StringTokenizer tokenizer=new StringTokenizer(input);
        
        while(tokenizer.hasMoreTokens())
        {
            String word=tokenizer.nextToken().toLowerCase();
            
            if(map.containsKey(word))
            {
                Integer count=(Integer) map.get(word);
                
                map.put(word,new Integer(count.intValue()+1));
            }
            else
                map.put(word,new Integer(1));
        }
        
    }
    
    
    
    private String createOutput()
    {
        StringBuffer output=new StringBuffer("");
        Iterator keys=map.keySet().iterator();
        
        while(keys.hasNext())
        {
            Object currentKey=keys.next();
            output.append(currentKey+"\t"+map.get(currentKey)+"\n");
        }
        
        output.append("Word Count: "+map.size()+"\n");
        output.append("isEmpty: "+map.isEmpty()+"\n");
        
        return output.toString();
    }
    
    public static void main(String[] arg)
    {
        WordTypeCount application=new WordTypeCount();
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}