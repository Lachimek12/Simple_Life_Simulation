package Abstract;

import World.World;
import java.util.Random;
import java.awt.Color;

public abstract class Plant extends Organism
{
    protected static final int SEED_CHANCE = 2; //% of chance to seed a new plant

    public Plant(int strength, int x, int y, char texture, World world, String name, Color color)
    {
        super(strength, 0, x, y, texture, world, name, color);
    }

    @Override
    //this will always return true as plants don't move, so they cannot die during action
    public boolean Action()
    {
        if (GetAge() == NEWBORN) //if newborn, make an adult and skip turn
        {
            IncreaseAge();
            return true;
        }

        int seed; //seed is for keeping rand() number - chance to spawn new plant
        int moveX, moveY, x, y; //moveXY are in range <-1;1> and they modify new plant position
        Organism[][] map;
        Random rand = new Random(); //random number generator

        seed = rand.nextInt(100);
        //so now there 2% chance to spawn, when seed is 1 or 0
        if (seed < SEED_CHANCE) //seeding time
        {
            map = GetWorld().GetMap();
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

            if (map[y][x] == null) //seeded on empty space, just create new plant
            {
                map[y][x] = this.CreateNew(x, y);
                GetWorld().AddMove(map[y][x]);
            }
            else if (moveX != 0 || moveY != 0) //collision with other organism
            {
                map[y][x].Collision(this);
            }
        }

        return true;
    }

    @Override
    public boolean Collision(Organism opponent)
    {
        int x = GetX(), y = GetY(), oppX = opponent.GetX(), oppY = opponent.GetY();
        Organism[][] map = GetWorld().GetMap();

        if (IsTheSame(opponent)) //if there is already plant of the same type - do nothing
        {
            return true;
        }

        if (GetStrength() <= opponent.GetStrength()) //if opponent won
        {
            if (opponent instanceof Plant) //if it is a plant, we create new seed
            {
                map[y][x] = opponent.CreateNew(x, y);
                GetWorld().AddMove(map[y][x]);
            }
            else //if it is an animal, we move it
            {
                map[y][x] = opponent;
                map[oppY][oppX] = null;
                opponent.SetX(x);
                opponent.SetY(y);
            }
            GetWorld().DeleteMove(this);
        }
        else if (!(opponent instanceof Plant)) //if opponent failed and it was an animal
        {
            //we delete plant as well as the animal
            map[oppY][oppX] = null;
            map[y][x] = null;

            GetWorld().AddEvent(opponent.GetName() + " ginie jedzac " + GetName() + " (" + x + "," + y + ")");

            GetWorld().DeleteMove(this);
            return false;
        }

        return true;
    }
}
