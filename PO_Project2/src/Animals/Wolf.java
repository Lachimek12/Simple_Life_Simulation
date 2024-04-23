package Animals;

import Abstract.Animal;
import World.World;
import Abstract.Organism;
import java.awt.*;

public class Wolf extends Animal
{
    public Wolf(int x, int y, World world)
    {
        super(9, 5, x, y, 'W', world, "Wilk", Color.GRAY);
    }

    public Wolf(World world)
    {
        super(9, 5, -1, -1, 'W', world, "Wilk", Color.GRAY);
    };

    @Override
    public Wolf CreateNew(int x, int y)
    {
        return new Wolf(x, y, GetWorld());
    }

    @Override
    protected boolean IsTheSame(Organism organism)
    {
        if (organism instanceof Wolf)
        {
            return true;
        }
        return false;
    }
}
