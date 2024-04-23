package Plants;

import Abstract.Plant;
import World.World;
import Abstract.Organism;
import java.awt.*;

public class Dandelion extends Plant
{
    public Dandelion(int x, int y, World world)
    {
        super(0, x, y, 'd', world, "Mleczyk", Color.yellow);
    }

    public Dandelion(World world)
    {
        super(0, -1, -1, 'd', world, "Mleczyk", Color.yellow);
    }

    @Override
    public Dandelion CreateNew(int x, int y)
    {
        return new Dandelion(x, y, GetWorld());
    }

    @Override
    protected boolean IsTheSame(Organism organism)
    {
        if (organism instanceof Dandelion)
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

        super.Action();
        super.Action();
        super.Action();
        return true;
    }
}
