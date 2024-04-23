package Plants;

import Abstract.Plant;
import World.World;
import Abstract.Organism;
import java.awt.*;

public class Grass extends Plant
{
    public Grass(int x, int y, World world)
    {
        super(0, x, y, 'g', world, "Trawa", Color.green);
    }

    public Grass(World world)
    {
        super(0, -1, -1, 'g', world, "Trawa", Color.green);
    }

    @Override
    public Grass CreateNew(int x, int y)
    {
        return new Grass(x, y, GetWorld());
    }

    @Override
    protected boolean IsTheSame(Organism organism)
    {
        if (organism instanceof Grass)
        {
            return true;
        }
        return false;
    }
}
