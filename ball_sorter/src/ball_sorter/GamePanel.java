package ball_sorter;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import static java.lang.Integer.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements MouseListener, KeyListener, ActionListener
{
    private int WIDTH;
    private int HEIGHT;
    
    private int ballSize = 60;
    private int cupSizeX = 80;
    private int cupSizeY = 240;
    
    private Cup[] c;
    private int cups;
    
    private Ball[] b;
    private int balls;
    
    private int moves = 0;
    private int places[][] = new int[10000][2];
    
    
    private Color background = new Color(28, 28, 28);
    private Color cupColor = new Color(128, 128, 128);
    private Color ballOutline = new Color(255, 255, 255);
    
    
    private Color red = new Color(197, 42, 35);
    private Color green = new Color(16, 100, 50);
    private Color blue = new Color(57, 46, 195);
    private Color gray = new Color(100, 100, 100);
    private Color brown = new Color(125, 74, 8);
    private Color pink = new Color(234, 94, 122);
    private Color purple = new Color(114, 43, 147);
    private Color orange = new Color(230, 140, 65);
    private Color yellow = new Color(240, 215, 88);
    private Color lblue = new Color(86, 163, 230);
    private Color lgreen = new Color(98, 214, 124);
    private Color ygreen = new Color(121, 150, 15);

    private Color colors[] = {red, green, blue, gray, brown, pink, purple, orange, yellow, lblue, lgreen, ygreen};
    
    
    private File gameFile;
    
    private boolean pressedM = false;
    private boolean pressedK = false;
    
    private int buttonSize = 100;
    
    private JButton restartButton;
    private JButton menuButton;
    private JButton undoButton;
    
    private JLabel levelLabel;    
    
    
    private JPanel finishPanel;
    private JLabel finishLabel;
    private JButton nextButton;
    
    
    private Main m;
    private int level;

    public GamePanel (int x, int y, int width, int height, Main m, File f, int level)
    {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.gameFile = f;
        this.m = m;
        this.level = level;

        setBackground(background);
        setSize(WIDTH, HEIGHT);
        setLayout(null);
        setLocation(x, y);
        setFocusable(true);
        grabFocus();    
        
        addMouseListener(this);
        addKeyListener(this);
        
        restartButton = new JButton("Restart");
        restartButton.setBounds(WIDTH/2 - buttonSize/2 + 250, 100 - buttonSize/2, buttonSize, buttonSize);
        restartButton.setFocusable(false);        
        restartButton.addActionListener(this);        
        
        menuButton = new JButton("Menu");
        menuButton.setBounds(WIDTH/2 - buttonSize/2, 100 - buttonSize/2, buttonSize, buttonSize);
        menuButton.setFocusable(false);        
        menuButton.addActionListener(this);  
        
        undoButton = new JButton("Undo");
        undoButton.setBounds(WIDTH/2 - buttonSize/2 - 250, 100 - buttonSize/2, buttonSize, buttonSize);
        undoButton.setFocusable(false);        
        undoButton.addActionListener(this);
        
        add(restartButton);
        add(menuButton);
        add(undoButton);
        
        
        String fileName = f.getName();
        String labelText = fileName.substring(0, fileName.indexOf('.'));
        
        levelLabel = new JLabel("Level " + labelText);
        levelLabel.setFont(new Font("Arial", Font.PLAIN, 75));
        levelLabel.setBounds(0, 175, WIDTH, 100);
        levelLabel.setHorizontalAlignment(JLabel.CENTER);
        levelLabel.setForeground(Color.white);
        add(levelLabel);
        
        
        finishPanel = new JPanel();
        finishPanel.setBounds(0, 550, width, 250);
        finishPanel.setBackground(Color.red);
        finishPanel.setLayout(null);
        finishPanel.hide();
        
        finishLabel = new JLabel("Well Done");
        finishLabel.setFont(new Font("Arial", Font.PLAIN, 75));
        finishLabel.setBounds(0, 0, WIDTH, 125);
        finishLabel.setHorizontalAlignment(JLabel.CENTER);
        finishLabel.setForeground(Color.white);
        
        nextButton = new JButton("Next");
        nextButton.setBounds(WIDTH/2 - 150/2, 150, 150, 75);
        nextButton.setFocusable(false);
        nextButton.addActionListener(this);
        
        finishPanel.add(finishLabel);
        finishPanel.add(nextButton);
        
        add(finishPanel);
        
        start();
    }
    
    private void start()
    {
        String data[] = getFlieLines(gameFile);
        
        cups = parseInt(data[0]);
        balls = parseInt(data[1]);
        
        c = new Cup[cups];
        
        if (cups < 6)
        {
            for (int i = 0; i < cups; i++)
            {
                c[i] = new Cup(WIDTH/(cups + 1) * (i + 1), HEIGHT/20*11, cupSizeX, cupSizeY, cupColor);
            }
        }
        else
        {
            int ctop = cups/2 + cups%2;
            
            for (int i = 0; i < ctop; i++)
            {
                c[i] = new Cup(WIDTH/(ctop + 1) * (i + 1), HEIGHT/20*8, cupSizeX, cupSizeY, cupColor);
            }
            for (int i = ctop; i < cups; i++)
            {
                c[i] = new Cup(WIDTH/(cups/2 + 1) * (i - ctop + 1), HEIGHT/20*15, cupSizeX, cupSizeY, cupColor);
            }
        }
        
        int ball = 0;
        b = new Ball[balls];
        for (int i = 5; i > 1; i--)
        {
            for (int j = 0; j < cups; j++)
            {
                if (data[i].charAt(j) != '0')
                {
                    b[ball] = new Ball(ballSize, c[j], charToColor(data[i].charAt(j)), ballOutline);
                    c[j].add(b[ball]);
                    ball++;
                }
            }
        }
    }
    
    public void showGame()
    {
        show();
        setFocusable(true);
        grabFocus();
    }
    
    public void undo()
    {
        for (int k = 0; k < cups; k++)
        {
            c[k].unselect();
        }
        if (moves > 0)
        {
            c[places[moves-1][1]].add(c[places[moves-1][0]].getTopBall());
            c[places[moves-1][0]].remove();
            moves--;
            repaint();
        }
    }
    
    public void restart()
    {
        moves = 0;
        start();
        repaint();
    }
    
    private String[] getFlieLines (File f)
    {
    	Scanner scanner = null;
        String temp[] = new String[6];
        try
        {
            scanner = new Scanner(f);
            for (int i = 0; i < 6; i++)
            {
                temp[i] = scanner.nextLine();
            }
        }
        catch (FileNotFoundException e)
        {
            JOptionPane.showMessageDialog(null, "Level file not found");
        }
        finally
        {
            scanner.close();
        }
        return temp;
    }
    
    private Color charToColor (char ch)
    {
        return colors[ch - 97];
    }

    
    @Override
    public void paintComponent(Graphics g)  
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < cups; i++)
        {
            c[i].show(g2d);
        }
    }

    @Override
    public void mouseClicked (MouseEvent e) {}
    @Override
    public void mouseEntered (MouseEvent e) {}
    @Override
    public void mouseExited (MouseEvent e) {}
    @Override
    public void mousePressed (MouseEvent e)
    {
        if (!pressedM)
        {
            pressedM = true;
            int selected = -1;
            boolean moved = false;
            for (int i = 0; i < cups; i++)
            {
                if (!c[i].selected() && c[i].inside(e.getX(), e.getY()))
                {
                    for (int j = 0; j < cups; j++)
                    {
                        if (c[j].selected() && i != j && c[i].addPossible(c[j].getTopBall()) && c[j].removePossible())
                        {
                            moved = true;
                            c[i].add(c[j].getTopBall());
                            c[j].remove();
                            moves++;
                            places[moves-1][0] = i;
                            places[moves-1][1] = j;
                            break;
                        }
                    }
                    if (!moved)
                    {
                        c[i].select();
                        selected = i;
                    }
                }
            }
            for (int i = 0; i < cups; i++)
            {
                if (i != selected)
                {
                    c[i].unselect();
                }
            }
            
            int counter = 0;
            for (int i = 0; i < cups; i++)
            {
                if (c[i].sorted())
                {
                    counter++;
                }
            }

            if (counter == cups)
            {
                finishPanel.show();
            }
            
            repaint();
        }
    }  
    @Override
    public void mouseReleased (MouseEvent e)
    {
        pressedM = false;
    }


    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e)
    {
        if (!pressedK)
        {
            pressedK = true;
            char key = e.getKeyChar();
            int cell = 0;
            if (key >= 48 && key <= 57)
            {
                cell = key - 48;
            }
            else if (key >= 65 && key <= 90)
            {
                cell = key - 65 + 10;
            }
            else if (key >= 97 && key <= 122)
            {
                cell = key - 97 + 10;
            }

            int selected = -1;
            boolean moved = false;
            if (cell > 0 && cell <= cups)
            {
                cell--;
                if (!c[cell].selected())
                {
                    for (int j = 0; j < cups; j++)
                    {
                        if (c[j].selected() && cell != j && c[cell].addPossible(c[j].getTopBall()) && c[j].removePossible())
                        {
                            moved = true;
                            c[cell].add(c[j].getTopBall());
                            c[j].remove();
                            moves++;
                            places[moves-1][0] = cell;
                            places[moves-1][1] = j;
                            break;
                        }
                    }
                    if (!moved)
                    {
                        c[cell].select();
                        selected = cell;
                    }
                }
            }
            for (int i = 0; i < cups; i++)
            {
                if (i != selected)
                {
                    c[i].unselect();
                }
            }

            int counter = 0;
            for (int i = 0; i < cups; i++)
            {
                if (c[i].sorted())
                {
                    counter++;
                }
            }

            if (counter == cups)
            {
                finishPanel.show();
            }

            repaint();
        }
    }
    @Override
    public void keyReleased(KeyEvent e)
    {
        pressedK = false;
    }
    
        @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == restartButton)
        {
            finishPanel.hide();
            restart();
        }
        else if (e.getSource() == menuButton)
        {
            finishPanel.hide();
            m.showMenu();
            hide();
        }
        else if (e.getSource() == undoButton)
        {
            finishPanel.hide();
            undo();
        }
        else if (e.getSource() == nextButton)
        {
            String fileName = gameFile.getName();
            hide();
            m.startGame(level++);
        }
    }
}
