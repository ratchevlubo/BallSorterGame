package ball_sorter;

import javax.swing.*;

public class Frame extends JFrame
{
    Frame(int width, int height, String title, ImageIcon icon)
    {
        setTitle(title);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setSize(width, height);
        setLocationRelativeTo(null);
        setVisible(true);
        setIconImage(icon.getImage());
    }
}
