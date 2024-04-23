package Abstract;

import World.World;
import java.util.Random;
import java.awt.Color;

public abstract class Animal extends Organism
{
    public Animal(int strength, int initiative, int x, int y, char texture, World world, String name, Color color)
    {
        super(strength, initiative, x, y, texture, world, name, color);
    }

    @Override
    public boolean Action()
    {
        if (GetAge() == NEWBORN) //if newborn, make an adult and skip turn
        {
            IncreaseAge();
            return true;
        }

        int moveX, moveY, x, y; //moveXY are in range <-1;1> and they modify animal position
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

        if (map[y][x] == null) //moved to empty space, just move this animal
        {
            this.SetX(x);
            this.SetY(y);
            map[y][x] = this;
            map[y - moveY][x - moveX] = null;
        }
        else if (moveX != 0 || moveY != 0) //collision with other organism
        {
            return map[y][x].Collision(this);
        }

        return true;
    }

    @Override
    public boolean Collision(Organism opponent)
    {
        int x = GetX(), y = GetY(), oppX = opponent.GetX(), oppY = opponent.GetY();
        Organism[][] map = GetWorld().GetMap();
        String event = "";

        if (IsTheSame(opponent)) //trying to spawn newborn (we spawn only on empty space)
        {
            for (int dy = -1; dy <= 1; dy++)
            {
                for (int dx = -1; dx <= 1; dx++)
                {
                    //trying for both of the animals

                    if (x + dx >= 0 && x + dx < GetWorld().GetWidth() && y + dy >= 0 && y + dy < GetWorld().GetHeight()
                        && map[y + dy][x + dx] == null)
                    {
                        map[y + dy][x + dx] = this.CreateNew(x + dx, y + dy);
                        GetWorld().AddMove(map[y + dy][x + dx]);

                        event = "Rodzi sie nowy : " + GetName() + " ! (" + (y + dy) + "," + (x + dx) + ")";
                        GetWorld().AddEvent(event);

                        return true;
                    }

                    if (oppX + dx >= 0 && oppX + dx < GetWorld().GetWidth() && oppY + dy >= 0 && oppY + dy < GetWorld().GetHeight()
                        && map[oppY + dy][oppX + dx] == null)
                    {
                        map[oppY + dy][oppX + dx] = this.CreateNew(oppX + dx, oppY + dy);
                        GetWorld().AddMove(map[oppY + dy][oppX + dx]);

                        event = "Rodzi sie nowy : " + GetName() + " ! (" + (x + dx) + "," + (y + dy) + ")";
                        GetWorld().AddEvent(event);

                        return true;
                    }
                }
            }
        }
        else if (GetStrength() <= opponent.GetStrength()) //if opponent won
        {
            if (opponent instanceof Animal) //if it is an animal, we move it
            {
                map[y][x] = opponent;
                map[oppY][oppX] = null;
                opponent.SetX(x);
                opponent.SetY(y);

                event = opponent.GetName() + " zabija " + GetName() + " (" + x + "," + y + ")";
                GetWorld().AddEvent(event);
            }
            else //if it is a plant, we create new seed
            {
                map[y][x] = opponent.CreateNew(x, y);
                GetWorld().AddMove(map[y][x]);

                event = opponent.GetName() + " zabija " + GetName() + " rozsiewajac swoje trujace nasiona" +
                        " (" + x + "," + y + ")";
                GetWorld().AddEvent(event);
            }
            GetWorld().DeleteMove(this);
        }
        else if (opponent instanceof Animal) //if opponent failed and it was an animal
        {
            map[oppY][oppX] = null;
            event = opponent.GetName() + " ginie probujac zaatakowac " + GetName() +
                    " (" + x + "," + y + ")";
            GetWorld().AddEvent(event);

            return false;
        }
        return true;
    }
}
