package ball_sorter;

import java.awt.*;

public class Cup
{
    private int x;
    private int y;
    private int sizeX;
    private int sizeY;

    private int balls = 0;
    
    private boolean selected = false;
    
    private Ball[] b = new Ball[4];
    
    private Color color;

    
    public Cup(int x, int y, int sizeX, int sizeY, Color color)
    {
        this.x = x - sizeX/2;
        this.y = y - sizeY/2;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.color = color;
    }

    public void show(Graphics2D g2d)
    {
        g2d.setPaint(color);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawLine(x, y, x, y + sizeY);
        g2d.drawLine(x + sizeX, y, x + sizeX, y + sizeY);
        g2d.drawRoundRect(x - 5, y - 20, sizeX+10, 20, 5, 5);
        g2d.drawArc(x, y + sizeY - sizeX/2, sizeX, sizeX, 180, 180);
        for(int i = 0; i < balls; i++)
        {
            this.b[i].show(g2d);
        }
    }

    public void add(Ball b)
    {
        if (balls < 4)
        {
            this.b[balls] = b;
            balls++;
            b.move(this);
        }
    }

    public void remove()
    {
        if (balls > 0)
        {
            this.b[balls-1] = null;
            balls--;
        }
    }

    public void select()
    {
        selected = true;
        if (balls > 0)
        {
            b[balls-1].select();
        }
    }

    public void unselect()
    {
        selected = false;
        if (balls > 0)
        {
            b[balls-1].unselect();
        }
    }

    public boolean inside(int mx, int my)
    {     
        return mx > x && mx < x + sizeX && my > y - 20 && my < y + sizeY + sizeX/2;
    }
    
    public int nextBallX()
    {
        return x + sizeX / 2;
    }
    
    public int nextBallY(int ballSize)
    {
        return y + sizeY - ballSize * (balls - 1) - (balls - 1) * 5;
    }
    
    public int selectedBallY(int ballSize)
    {
        return y - ballSize;
    }
    
    public boolean selected()
    {
        return selected;
    }
    
    public Ball getTopBall()
    {
        if (balls > 0)
        {
            return b[balls - 1];
        }
        else
        {
            return null;
        }
    }

    public boolean addPossible(Ball b)
    {
        if (getTopBall() == null)
        {
            return true;
        }
        else
        {
            return (b != null && getTopBall().getColor() == b.getColor() && balls < 4) || getTopBall() == null;
        }
    }
    
    public boolean removePossible()
    {
        return balls > 0; 
    }
    
    public boolean sorted()
    {
        int counter = 0;
        if (balls == 4)
        {
            for (int i = 0; i < 3; i++)
            {
                if (b[i].getColor() == b[i+1].getColor())
                {
                    counter++;
                }
            }
        }
        else if (balls == 0)
        {
            return true;
        }
        
        return counter == 3;
    }
}