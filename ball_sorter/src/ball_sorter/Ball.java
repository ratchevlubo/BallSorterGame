package ball_sorter;

import java.awt.*;

public class Ball 
{
    private int x;
    private int y;
    private int size;
    
    private Color color;
    private Color outline;
    
    private Cup cup;

    public Ball(int size, Cup cup, Color color, Color outline)
    {
        this.size = size;
        this.cup = cup;
        this.color = color;
        this.outline = outline;
    }

    public void show(Graphics2D g2d)
    {
        g2d.setPaint(color);
        g2d.fillOval(x - size/2, y - size/2, size, size);
        g2d.setPaint(outline);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawOval(x - size/2, y - size/2, size, size);
    }

    public void move(Cup cup)
    {
        this.cup = cup;
        this.x = cup.nextBallX();
        this.y = cup.nextBallY(size);
    }

    public void select()
    {
        this.y = cup.selectedBallY(size);
    }

    public void unselect()
    {
        this.y = cup.nextBallY(size);
    }
    
    public Color getColor()
    {
        return color;
    }
}