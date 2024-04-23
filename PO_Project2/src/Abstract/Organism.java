package Abstract;

import World.World;
import java.awt.Color;

public abstract class Organism implements Organism_Interface
{
    //mark for newborn organism, newborns don't perform action during the turn they were created
    protected static final int NEWBORN = 0;

    //age detects newborns (newborns don't perform action in the current turn)
    //age = newborn - no action, else - does action
    private int strength, initiative, x, y, age = NEWBORN;
    private char texture; //char prepresenting organism in saved file
    private World world;
    private String name;
    private Color color; //color representing organism on the map

    //checks if organisms are of the same type, true if so, else false
    protected abstract boolean IsTheSame(Organism organism);

    public Organism(int strength, int initiative, int x, int y, char texture, World world, String name, Color color)
    {
        this.strength = strength;
        this.initiative = initiative;
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.world = world;
        this.name = name;
        this.color = color;
    }

    @Override
    public int GetInitiative()
    {
        return initiative;
    }

    @Override
    public int GetStrength()
    {
        return strength;
    }

    @Override
    public int GetX()
    {
        return x;
    }

    @Override
    public int GetY()
    {
        return y;
    }

    @Override
    public void SetX(int x)
    {
        this.x = x;
    }

    @Override
    public void SetY(int y)
    {
        this.y = y;
    }

    @Override
    public void Draw()
    {
        System.out.print(texture);
    }

    @Override
    public World GetWorld()
    {
        return world;
    }

    @Override
    public int GetAge()
    {
        return age;
    }

    @Override
    public void IncreaseAge()
    {
        age++;
    }

    @Override
    public void SetStrength(int strength)
    {
        this.strength = strength;
    }

    @Override
    public String GetName()
    {
        return name;
    }

    @Override
    public char GetTexture()
    {
        return texture;
    }

    @Override
    public void SetAge(int age)
    {
        this.age = age;
    }

    @Override
    public Color GetColor()
    {
        return color;
    }
}
