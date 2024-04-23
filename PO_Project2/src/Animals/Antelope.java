package Animals;

import Abstract.Animal;
import World.World;
import Abstract.Organism;

import java.awt.*;
import java.util.Random;

public class Antelope extends Animal
{
    public Antelope(int x, int y, World world)
    {
        super(4, 4, x, y, 'A', world, "Antylopa", Color.getHSBColor(130,150,200));
    }

    public Antelope(World world)
    {
        super(4, 4, -1, -1, 'A', world, "Antylopa", Color.getHSBColor(130,150,200));
    }

    @Override
    public Antelope CreateNew(int x, int y)
    {
        return new Antelope(x, y, GetWorld());
    }

    @Override
    protected boolean IsTheSame(Organism organism)
    {
        if (organism instanceof Antelope)
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

        //range is now <-2;2>
        moveX = rand.nextInt(5);
        moveY = rand.nextInt(5);
        moveX -= 2;
        moveY -= 2;
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
        else if (moveX != 0 || moveY != 0)
        {
            return map[y][x].Collision(this);
        }

        return true;
    }

    @Override
    public boolean Collision(Organism opponent)
    {
        if (IsTheSame(opponent) || GetStrength() > opponent.GetStrength()) //if no need to run
        {
            return super.Collision(opponent);
        }
        else //try run from attack
        {
            Random rand = new Random(); //random number generator
            int epicRun = rand.nextInt(2);

            if (epicRun == 0) //epic save
            {
                int x = GetX(), y = GetY();
                Organism[][] map = GetWorld().GetMap();

                //trying to find empty space
                for (int dy = -2; dy <= 2; dy++)
                {
                    for (int dx = -2; dx <= 2; dx++)
                    {
                        if (x + dx >= 0 && x + dx < GetWorld().GetWidth() && y + dy >= 0 && y + dy < GetWorld().GetHeight()
                            && map[y + dy][x + dx] == null)
                        {
                            map[y + dy][x + dx] = this;
                            this.SetX(x + dx);
                            this.SetY(y + dy);
                            map[y][x] = opponent;
                            map[opponent.GetY()][opponent.GetX()] = null;
                            opponent.SetX(x);
                            opponent.SetY(y);

                            GetWorld().AddEvent("Antylopa unika walki z " + opponent.GetName() +
                                " (" + x + "," + y + ")");

                            return true;
                        }
                    }
                }

                return super.Collision(opponent); //if there were no empty space (dead)
            }
            else //dead
            {
                return super.Collision(opponent);
            }
        }
    }
}
