package Plants;

import Abstract.Plant;
import World.World;
import Abstract.Organism;
import java.awt.*;

public class Pine_Borscht extends Plant
{
    public Pine_Borscht(int x, int y, World world)
    {
        super(10, x, y, 'p', world, "Barszcz Sosnowskiego", Color.BLUE);
    }

    public Pine_Borscht(World world)
    {
        super(10, -1, -1, 'p', world, "Barszcz Sosnowskiego", Color.BLUE);
    }

    @Override
    public Pine_Borscht CreateNew(int x, int y)
    {
        return new Pine_Borscht(x, y, GetWorld());
    }

    @Override
    protected boolean IsTheSame(Organism organism)
    {
        if (organism instanceof Pine_Borscht)
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

        int x = GetX(), y = GetY();
        int width = GetWorld().GetWidth(), height = GetWorld().GetHeight();
        Organism[][] map = GetWorld().GetMap();

        //killing all animals around
        for (int dy = -1; dy <= 1; dy++)
        {
            for (int dx = -1; dx <= 1; dx++)
            {
                if (x + dx >= 0 && x + dx < width && y + dy >= 0 && y + dy < height && (dy != 0 || dx != 0))
                {
                    if (map[y + dy][x + dx] != null && map[y + dy][x + dx].GetStrength() <= GetStrength() && !(map[y + dy][x + dx] instanceof Plant))
                    {
                        GetWorld().AddEvent(GetName() + " zabija " + map[y + dy][x + dx].GetName() +
                            " (" + (x + dx) + "," + (y + dy) + ")");

                        GetWorld().DeleteMove(map[y + dy][x + dx]);
                        map[y + dy][x + dx] = null;
                    }
                }
            }
        }

        super.Action();

        return true;
    }
}
