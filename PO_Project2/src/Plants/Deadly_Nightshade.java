package Plants;

import Abstract.Plant;
import World.World;
import Abstract.Organism;
import java.awt.*;

public class Deadly_Nightshade extends Plant
{
    public Deadly_Nightshade(int x, int y, World world)
    {
        super(99, x, y, 'x', world, "Wilcze Jagody", Color.red);
    }

    public Deadly_Nightshade(World world)
    {
        super(99, -1, -1, 'x', world, "Wilcze Jagody", Color.red);
    }

    @Override
    public Deadly_Nightshade CreateNew(int x, int y)
    {
        return new Deadly_Nightshade(x, y, GetWorld());
    }

    @Override
    protected boolean IsTheSame(Organism organism)
    {
        if (organism instanceof Deadly_Nightshade)
        {
            return true;
        }
        return false;
    }
}
