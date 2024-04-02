package ball_sorter;

import static java.lang.Integer.parseInt;

import java.io.File;

import javax.swing.ImageIcon;

public class Main
{
    int WIDTH = 800;
    int HEIGHT = 1200;
    
    File file = new File("src\\ball_sorter\\levels\\1.txt");
    File f = new File("src\\ball_sorter\\levels\\");
    
    String fileList[];
    
    ImageIcon icon = new ImageIcon("src\\ball_sorter\\icon.png");
    
    Frame frame;
    
    GamePanel gamePanel = new GamePanel(0, 0, WIDTH, HEIGHT, this, file, 1);
    
    MenuPanel menuPanel;
    
    public void start()
    {
        System.out.println("start");

        fileList = f.list();
        sortList();
        
        frame = new Frame(WIDTH, HEIGHT+37, "Ball sorter", icon);
        
        menuPanel = new MenuPanel(WIDTH, HEIGHT, this, fileList);;
        
        frame.add(menuPanel);
        frame.add(gamePanel);
        gamePanel.hide();  
    }
    
    void startGame(int level)
    {
        gamePanel = new GamePanel(0, 0, WIDTH, HEIGHT, this, new File("src\\ball_sorter\\levels\\" + fileList[level+1]), level+1);
        frame.add(gamePanel);
        showGame();   
    }
    
    public void showGame ()
    {
        gamePanel.showGame();
    }
    
    public void showMenu ()
    {
        menuPanel.show();
    }
    
    public void sortList()
    {
        for(int i = 1; i < fileList.length-1; i++)
        {
            for (int j = i + 1; j < fileList.length; j++)
            {
                int ai = parseInt(fileList[i].substring(0, fileList[i].indexOf('.')));
                int aj = parseInt(fileList[j].substring(0, fileList[j].indexOf('.')));

                if(ai > aj)
                {
                    String temp = fileList[i];
                    fileList[i] = fileList[j];
                    fileList[j] = temp;
                }
            }
        }
    }
    
    public static void main(String[] args)
    {
        Main obj = new Main();
        obj.start();
    }  
}