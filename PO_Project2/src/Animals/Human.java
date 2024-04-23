package Animals;

import Abstract.Animal;
import World.World;
import Abstract.Organism;
import java.awt.Color;

public class Human extends Animal
{
    public static final int HUMAN_STRENGTH = 5; //humnan standard strength value
    //int values representing each arrow key input
    public static final int KEY_UP = 72;
    public static final int KEY_DOWN = 80;
    public static final int KEY_LEFT = 75;
    public static final int KEY_RIGHT = 77;

    //cooldown is to count turns before next possible power up usage
    private int cooldown, baseStrength; //baseStrength is to tell base strength level (so power up will not go below it during cooldown)

    public Human(int x, int y, World world)
    {
        super(HUMAN_STRENGTH, 4, x, y, 'H', world, "Czlowiek", Color.black);
        cooldown = 0;
        baseStrength = HUMAN_STRENGTH;
    }

    public Human(World world)
    {
        super(HUMAN_STRENGTH, 4, -1, -1, 'H', world, "Czlowiek", Color.black);
        cooldown = 0;
        baseStrength = HUMAN_STRENGTH;
    }

    @Override
    public boolean Action()
    {
        if (GetAge() == NEWBORN)
        {
            SetHumanStats();
            IncreaseAge();
            return true;
        }

        //button is to tell the move direction
        int button = GetWorld().GetButton();
        int moveX = 0, moveY = 0, x = GetX(), y = GetY();
        Organism[][] map = GetWorld().GetMap();

        if (button == KEY_DOWN)
        {
            moveY = 1;
        }
        else if (button == KEY_UP)
        {
            moveY = -1;
        }
        else if (button == KEY_LEFT)
        {
            moveX = -1;
        }
        else if (button == KEY_RIGHT)
        {
            moveX = 1;
        }

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

        if (map[y][x] == null)
        {
            this.SetX(x);
            this.SetY(y);
            map[y][x] = this;
            map[y - moveY][x - moveX] = null;
        }
        else if (moveX != 0 || moveY != 0)
        {
            if (map[y][x].Collision(this))
            {
                ReduceCooldown();
                SetHumanStats();
                return true;
            }

            return false;
        }

        ReduceCooldown();
        SetHumanStats();
        return true;
    }

    @Override
    protected boolean IsTheSame(Organism organism)
    {
        if (organism instanceof Human)
        {
            return true;
        }
        return false;
    }

    @Override
    public Human CreateNew(int x, int y)
    {
        return new Human(x, y, GetWorld());
    }

    //activates human power up if it is possible and worth it (we won't downgrade stats)
    public void ActivatePowerUp()
    {
        if (cooldown == 0 && GetStrength() < 10) //if we are not on cooldown, and we won't deacrease our strength
        {
            super.SetStrength(10);
            cooldown = 5;
            SetHumanStats();

            GetWorld().AddEvent(GetName() + " wypija Mgiczny Eliskir!");
        }
        else
        {
            GetWorld().AddEvent(GetName() + " nie wypija Mgicznego Eliskiru");
        }
    }

    @Override
    //we also update baseStength here with strength value
    public void SetStrength(int strength)
    {
        baseStrength = baseStrength + (strength - GetStrength());
        super.SetStrength(strength);
    }

    public int GetCooldown()
    {
        return cooldown;
    }

    public int GetBaseStrength()
    {
        return baseStrength;
    }

    public void SetHumanStuff(int strength, int cooldown, int baseStr)
    {
        super.SetStrength(strength);
        this.cooldown = cooldown;
        this.baseStrength = baseStr;
    }

    public void SetHumanStats()
    {
        String stats;
        stats = "Sila: " + GetStrength() + " Czas odnowienia: " + (cooldown + GetStrength() - baseStrength);
        GetWorld().SetHumanStats(stats);
    }

    //reduce cooldown and if needed also human strength
    public void ReduceCooldown()
    {
        if (cooldown > 0) //if human is on cooldown
        {
            if (GetStrength() > baseStrength)
            {
                super.SetStrength(GetStrength() - 1);
            }
            else //we decrease cooldown after we are back to baseStrength
            {
                cooldown--;
            }
        }
    }
}
