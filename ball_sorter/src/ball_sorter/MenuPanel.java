package ball_sorter;

import java.awt.*;
import java.awt.event.*;
import static java.lang.String.valueOf;
import javax.swing.*;

public class MenuPanel extends JPanel implements ActionListener
{
    private String[] fileList;

    private JButton backButton;

    private JPanel levelsPanel;
    private JPanel levelsScrollPannel;
    private JPanel levelPanels[];
    private JButton levelButtons[];
    private JScrollPane scrollPanel;
	
    private int buttonSize = 100;
    
    private Color background = new Color(28, 28, 28);
    
    private Main m;
    
    
    public MenuPanel(int width, int height, Main m, String[] list)
    {
    	this.fileList = list; 
    	this.m = m;
    	
        setLayout(null);
        setLocation(0, 0);
        setSize(width, height);
        setBackground(background);
        
        backButton = new JButton("Back");
        backButton.setBounds(width/2 - buttonSize/2 - 250, 100 - buttonSize/2, buttonSize, buttonSize);
        backButton.setFocusable(false);  
        backButton.addActionListener(this);
        add(backButton);        
        
        
        levelsPanel = new JPanel();
        levelsPanel.setLocation(100, 250);
        levelsPanel.setSize(width-200, height-350);
        levelsPanel.setLayout(new BorderLayout());
        add(levelsPanel);
        
        levelsScrollPannel = new JPanel();
        levelsScrollPannel.setSize(600, 1000);
        levelsScrollPannel.setLayout(new GridLayout(20, 5, 50, 50));
        levelsScrollPannel.setBackground(background);

        levelPanels = new JPanel[100];
        for (int i = 0; i < 100; i++)
        {
            levelPanels[i] = new JPanel();
            levelPanels[i].setFocusable(false);
            levelPanels[i].setPreferredSize(new Dimension(80, 80));
            levelPanels[i].setBackground(Color.blue);
            levelPanels[i].setBackground(background);//-------------------------
            levelPanels[i].setLayout(null);
            levelsScrollPannel.add(levelPanels[i]);
        }
        
        String btnName;       
        levelButtons = new JButton[fileList.length-1];
        for (int i = 0; i < list.length-1; i++)
        {
            btnName = fileList[i+1].substring(0, fileList[i+1].indexOf('.'));
            
            levelButtons[i] = new JButton(btnName);
            levelButtons[i].setFocusable(false);
            levelButtons[i].setBounds(0, 0, 80, 80);
            levelButtons[i].addActionListener(this);
            levelPanels[i].add(levelButtons[i]);
        }
        
        scrollPanel = new JScrollPane(levelsScrollPannel);        
        scrollPanel.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
        scrollPanel.setBorder(null);
        scrollPanel.getVerticalScrollBar().setUnitIncrement(16);
        levelsPanel.add(scrollPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == backButton)
        {
            m.showGame();
            hide();
        }

        for(int i = 0; i < fileList.length-1; i++)
        {
            if(e.getSource() == levelButtons[i])
            {
                m.startGame(i);
                hide();
            }
        }
    }
}