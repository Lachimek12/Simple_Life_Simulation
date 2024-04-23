package Plants;

import Abstract.Plant;
import World.World;
import Abstract.Organism;
import java.awt.*;

public class Guarana extends Plant
{
    public Guarana(int x, int y, World world)
    {
        super(0, x, y, 'u', world, "Guarana", Color.cyan);
    }

    public Guarana(World world)
    {
        super(0, -1, -1, 'u', world, "Guarana", Color.cyan);
    }

    @Override
    public Guarana CreateNew(int x, int y)
    {
        return new Guarana(x, y, GetWorld());
    }

    @Override
    protected boolean IsTheSame(Organism organism)
    {
        if (organism instanceof Guarana)
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean Collision(Organism opponent)
    {
        if (!(opponent instanceof Plant)) //if we collide with animal, increase its strength
        {
            opponent.SetStrength(opponent.GetStrength() + 3);

            GetWorld().AddEvent(opponent.GetName() + " zjada " + GetName() + " i zwieksza swoja sile o 3!" +
                    " (" + GetX() + "," + GetY() + ")");
        }
        return super.Collision(opponent);
    }
}
