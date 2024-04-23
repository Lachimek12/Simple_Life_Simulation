package Animals;

import Abstract.Animal;
import World.World;
import Abstract.Organism;
import java.awt.Color;
import java.util.Random;

public class Turtle extends Animal
{
    public Turtle(int x, int y, World world)
    {
        super(2 , 1, x, y, 'T', world, "Zolw", Color.MAGENTA);
    }

    public Turtle(World world)
    {
        super(2 , 1, -1, -1, 'T', world, "Zolw", Color.MAGENTA);
    }

    @Override
    public Turtle CreateNew(int x, int y)
    {
        return new Turtle(x, y, GetWorld());
    }

    @Override
    protected boolean IsTheSame(Organism organism)
    {
        if (organism instanceof Turtle)
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

        Random rand = new Random(); //random number generator
        int move = rand.nextInt(4);
        if (move == 0) //only 25% chance to move
        {
            return super.Action();
        }
        return true;
    }

    @Override
    public boolean Collision(Organism opponent)
    {
        if (opponent.GetStrength() >= 5 || this.IsTheSame(opponent)) //if opponent's strength was >= 5 turtle dies
        {
            return super.Collision(opponent);
        }

        GetWorld().AddEvent("Zolw obronil sie przed atakiem " + opponent.GetName() +
            " (" + GetX() + "," + GetY() + ")");

        return true;
    }
}
