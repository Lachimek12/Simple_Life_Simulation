package Animals;

import Abstract.Animal;
import World.World;
import Abstract.Organism;
import java.awt.*;

public class Sheep extends Animal
{
    public Sheep(int x, int y, World world)
    {
        super(4, 4, x, y, 'S', world, "Owca", Color.pink);
    }

    public Sheep(World world)
    {
        super(4, 4, -1, -1, 'S', world, "Owca", Color.pink);
    }

    @Override
    public Sheep CreateNew(int x, int y)
    {
        return new Sheep(x, y, GetWorld());
    }

    @Override
    protected boolean IsTheSame(Organism organism)
    {
        if (organism instanceof Sheep)
        {
            return true;
        }
        return false;
    }
}
