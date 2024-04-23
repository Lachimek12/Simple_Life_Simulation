package Animals;

import Abstract.Animal;
import World.World;
import Abstract.Organism;
import java.awt.*;
import java.util.Random;

public class Fox extends Animal
{
    public Fox(int x, int y, World world)
    {
        super(3, 7, x, y, 'F', world, "Lis", Color.orange);
    }

    public Fox(World world)
    {
        super(3, 7, -1, -1, 'F', world, "Lis", Color.orange);
    }

    @Override
    public Fox CreateNew(int x, int y)
    {
        return new Fox(x, y, GetWorld());
    }

    @Override
    protected boolean IsTheSame(Organism organism)
    {
        if (organism instanceof Fox)
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean Action()
    {
        if (GetAge() == NEWBORN)
        {
            IncreaseAge();
            return true;
        }

        int moveX, moveY, x, y;
        Organism[][] map = GetWorld().GetMap();
        Random rand = new Random(); //random number generator

        moveX = rand.nextInt(3);
        moveY = rand.nextInt(3);
        moveX--;
        moveY--;
        x = GetX();
        y = GetY();

        if (x + moveX < 0 || x + moveX >= GetWorld().GetWidth())
        {
            moveX = 0;
        }
        if (y + moveY < 0 || y + moveY >= GetWorld().GetHeight())
        {
            moveY = 0;
        }

        x += moveX;
        y += moveY;

        if (map[y][x] == null)
        {
            this.SetX(x);
            this.SetY(y);
            map[y][x] = this;
            map[y - moveY][x - moveX] = null;
        }
        else if ((moveX != 0 || moveY != 0) && map[y][x].GetStrength() <= GetStrength()) //avoid attacking stronger opponent
        {
            return map[y][x].Collision(this);
        }

        return true;
    }
}
