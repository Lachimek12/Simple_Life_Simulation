package World;

import java.util.*;
import Abstract.Organism;
import Animals.*;
import Plants.*;
import java.io.*;

public class World
{
    private int width, height, button; //button is for human to tell his move direction
    private PriorityQue moveOrder;
    private Organism[][] map; //2d map of arrays representing organisms in the world
    private List<String> events; //list of events which happened during current turn
    private String humanStats; //String to display human strength and power up cooldown
    private Random rand; //random number generator
    private Game_Panel gamePanel; //displaying the whole map and text

    private void NukeTheWorld() //destroys whole world data
    {
        moveOrder = null;
        map = null;
        events.clear();
        width = 0;
        height = 0;
        button = ' ';
        humanStats = "";
    }

    public World(int width, int height)
    {
        this.width = width;
        this.height = height;
        button = ' ';
        moveOrder = null;
        map = new Organism[height][width];
        events = new ArrayList<>();
        humanStats = "";
        rand = new Random();
        gamePanel = new Game_Panel(this);
    }

    //creates new world with random organisms on it (there will be at least 1 of each species (if map is big enough))
    public void GenerateWorld()
    {
        int x, y, chance; //chance is to keep rand() values
        //allOrganisms stores all organisms which will be created (if enough space) in order : lowest initiative to the highest
        Vector<Organism> allOrganisms = new Vector<>();
        allOrganisms.add(new Dandelion(this));
        allOrganisms.add(new Grass(this));
        allOrganisms.add(new Guarana(this));
        allOrganisms.add(new Deadly_Nightshade(this));
        allOrganisms.add(new Pine_Borscht(this));
        allOrganisms.add(new Turtle(this));
        allOrganisms.add(new Antelope(this));
        allOrganisms.add(new Sheep(this));
        allOrganisms.add(new Human(this));
        allOrganisms.add(new Wolf(this));
        allOrganisms.add(new Fox(this));

        //tmp and iterate are to help adding objects to moveOrder
        PriorityQue tmp;
        PriorityQue iterate;

        for (Organism it : allOrganisms) //first we spawn 1 object of each type
        {
            x = rand.nextInt(width); //0 to width-1
            y = rand.nextInt(height); //0 to width-1
            //if place is taken try to find another one (don't try if map smaller than the amount of organisms)
            if (map[y][x] != null && allOrganisms.size() < width * height)
            {
                while (map[y][x] != null)
                {
                    x = rand.nextInt(width);
                    y = rand.nextInt(height);
                }
            }
            if (map[y][x] == null)
            {
                it.SetX(x);
                it.SetY(y);
                map[y][x] = it;
                tmp = new PriorityQue();
                tmp.next = moveOrder;
                tmp.organism = map[y][x];
                moveOrder = tmp;
            }
        }

        for (y = 0; y < height; y++) //here we randomly spawn random organisms, but we don't override already taken places
        {
            for (x = 0; x < width; x++)
            {
                if (map[y][x] == null)
                {
                    chance = rand.nextInt(100); //this const value can be modified to change % chacne, should not be < number of organisms
                    if (chance < allOrganisms.size()) //so chance to spawn object is : number of objects/const * 100%
                    {
                        tmp = new PriorityQue();
                        iterate = moveOrder;

                        if (allOrganisms.get(chance) instanceof Dandelion)
                        {
                            map[y][x] = new Dandelion(x, y, this);
                        }
                        else if (allOrganisms.get(chance) instanceof Grass)
                        {
                            map[y][x] = new Grass(x, y, this);
                        }
                        else if (allOrganisms.get(chance) instanceof Guarana)
                        {
                            map[y][x] = new Guarana(x, y, this);
                        }
                        else if (allOrganisms.get(chance) instanceof Deadly_Nightshade)
                        {
                            map[y][x] = new Deadly_Nightshade(x, y, this);
                        }
                        else if (allOrganisms.get(chance) instanceof Pine_Borscht)
                        {
                            map[y][x] = new Pine_Borscht(x, y, this);
                        }
                        else if (allOrganisms.get(chance) instanceof Turtle)
                        {
                            map[y][x] = new Turtle(x, y, this);
                        }
                        else if (allOrganisms.get(chance) instanceof Antelope)
                        {
                            map[y][x] = new Antelope(x, y, this);
                        }
                        else if (allOrganisms.get(chance) instanceof Sheep)
                        {
                            map[y][x] = new Sheep(x, y, this);
                        }
                        else if (allOrganisms.get(chance) instanceof Wolf)
                        {
                            map[y][x] = new Wolf(x, y, this);
                        }
                        else if (allOrganisms.get(chance) instanceof Fox)
                        {
                            map[y][x] = new Fox(x, y, this);
                        }

                        if (map[y][x] != null)
                        {
                            tmp.organism = map[y][x];
                            if (iterate != null)
                            {
                                //finding proper spot in PriorityQue
                                if (iterate.organism.GetInitiative() < tmp.organism.GetInitiative())
                                {
                                    tmp.next = iterate;
                                    moveOrder = tmp;
                                }
                                else
                                {
                                    while (iterate.next != null && iterate.next.organism.GetInitiative() >= tmp.organism.GetInitiative())
                                    {
                                        iterate = iterate.next;
                                    }
                                    tmp.next = iterate.next;
                                    iterate.next = tmp;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    //draws world, legend and events
    public void DrawWorld()
    {
        gamePanel.repaint();
    }

    //performs action on each organism in PriorityQue list
    public void PerformTurn()
    {
        events.clear(); //new round - new events
        humanStats = "Martwy"; //If human is alive, this will be overwritten by human action

        PriorityQue move = moveOrder;
        PriorityQue next;
        while (move != null)
        {
            if (move.organism.Action()) //as action tells if organism survived, we check if we have to delete current organism
            {
                move = move.next;
            }
            else
            {
                next = move.next;
                DeleteMove(move.organism);
                move = next;
            }
        }
    }

    //activates (if there is no cooldown and if it won't downgrade human stats) human superpower
    public void PowerUp()
    {
        events.clear();

        PriorityQue move = moveOrder;
        Human tmp;
        while (move != null && !(move.organism instanceof Human)) //finding human
        {
            move = move.next;
        }
        if (move != null)
        {
            tmp = (Human) move.organism;
            tmp.ActivatePowerUp();
        }
    }

    public Game_Panel GetGamePanel()
    {
        return gamePanel;
    }

    public int GetWidth()
    {
        return width;
    }

    public int GetHeight()
    {
        return height;
    }

    public List<String> GetEvents()
    {
        return events;
    }

    public Organism[][] GetMap()
    {
        return map;
    }

    public String GetHumanStats()
    {
        return humanStats;
    }

    //deletes dead organism from the PriorityQue
    public void DeleteMove(Organism dead)
    {
        PriorityQue tmp = moveOrder;
        PriorityQue move;
        if (tmp.organism == dead)
        {
            moveOrder = moveOrder.next;
            tmp = null;
        }
        else
        {
            while (tmp.next.organism != dead)
            {
                tmp = tmp.next;
            }
            move = tmp.next;
            tmp.next = move.next;
            move.organism = null;
            move = null;
        }
    }

    //adds newborn to the PriorityQue
    public void AddMove(Organism newborn)
    {
        PriorityQue tmp = moveOrder;
        PriorityQue move = new PriorityQue();
        move.organism = newborn;

        if (tmp.organism.GetInitiative() < newborn.GetInitiative())
        {
            move.next = tmp;
            moveOrder = move;
        }
        else
        {
            while (tmp.next != null && tmp.next.organism.GetInitiative() >= newborn.GetInitiative())
            {
                tmp = tmp.next;
            }
            move.next = tmp.next;
            tmp.next = move;
        }
    }

    public int GetButton()
    {
        return button;
    }

    public void SetButton(int button)
    {
        this.button = button;
    }

    //adds event to events list
    public void AddEvent(String event)
    {
        events.add(event);
    }

    public void SetHumanStats(String stats)
    {
        humanStats = stats;
    }

    //performs save procedure
    public boolean SaveWorld(String name)
    {
        PriorityQue tmp = moveOrder;
        Human humanoid;
        char txt;
        int size;

        //create file to write to
        try (PrintWriter writer = new PrintWriter(new FileWriter(name)))
        {
            writer.println(width);
            writer.println(height);

            while (tmp != null) //writing all organisms from PriorityQue
            {
                if (tmp.organism.GetTexture() == 'H')
                {
                    humanoid = (Human) tmp.organism;

                    writer.println(humanoid.GetTexture());
                    writer.println(humanoid.GetStrength());
                    writer.println(humanoid.GetX());
                    writer.println(humanoid.GetY());
                    writer.println(humanoid.GetAge());
                    writer.println(humanoid.GetCooldown());
                    writer.println(humanoid.GetBaseStrength());
                }
                else
                {
                    writer.println(tmp.organism.GetTexture());
                    writer.println(tmp.organism.GetStrength());
                    writer.println(tmp.organism.GetX());
                    writer.println(tmp.organism.GetY());
                    writer.println(tmp.organism.GetAge());
                }

                tmp = tmp.next;
            }

            //we write txt = '.' after all organisms, to tell when we will have to stop loading them and start reading events
            txt = '.';
            writer.println(txt);

            size = events.size();
            writer.println(size);

            for (String event : events)
            {
                writer.println(event);
            }

            writer.println(humanStats);

            return true;
        }
        catch (IOException e)
        {
            System.err.println("Error opening file: " + e.getMessage());
            return false;
        }
    }

    //performs load procedure
    public boolean LoadWorld(String name)
    {
        PriorityQue tmp;
        Human humanoid;
        char txt;
        int strength, x, y, age, cooldown, baseStr, eventsNumber;

        // Open the file for reading
        try (Scanner fileScanner = new Scanner(new File(name)))
        {
            NukeTheWorld();

            width = fileScanner.nextInt();
            height = fileScanner.nextInt();

            map = new Organism[height][width];

            moveOrder = new PriorityQue();
            tmp = moveOrder;
            name = fileScanner.next();
            txt = name.charAt(0);

            while (txt != '.') //reading organisms untill 31 end sign
            {
                tmp.next = new PriorityQue();
                tmp = tmp.next;

                if (txt == 'H')
                {
                    strength = fileScanner.nextInt();
                    x = fileScanner.nextInt();
                    y = fileScanner.nextInt();
                    age = fileScanner.nextInt();
                    cooldown = fileScanner.nextInt();
                    baseStr = fileScanner.nextInt();

                    tmp.organism = new Human(x, y, this);
                    humanoid = (Human) tmp.organism;
                    humanoid.SetHumanStuff(strength, cooldown, baseStr);
                    humanoid.SetAge(age);
                }
                else
                {
                    strength = fileScanner.nextInt();
                    x = fileScanner.nextInt();
                    y = fileScanner.nextInt();
                    age = fileScanner.nextInt();

                    switch (txt)
                    {
                        case 'W':
                            tmp.organism = new Wolf(x, y, this);
                            break;
                        case 'T':
                            tmp.organism = new Turtle(x, y, this);
                            break;
                        case 'S':
                            tmp.organism = new Sheep(x, y, this);
                            break;
                        case 'p':
                            tmp.organism = new Pine_Borscht(x, y, this);
                            break;
                        case 'u':
                            tmp.organism = new Guarana(x, y, this);
                            break;
                        case 'g':
                            tmp.organism = new Grass(x, y, this);
                            break;
                        case 'F':
                            tmp.organism = new Fox(x, y, this);
                            break;
                        case 'x':
                            tmp.organism = new Deadly_Nightshade(x, y, this);
                            break;
                        case 'd':
                            tmp.organism = new Dandelion(x, y, this);
                            break;
                        case 'A':
                            tmp.organism = new Antelope(x, y, this);
                            break;
                    }

                    tmp.organism.SetStrength(strength);
                    tmp.organism.SetAge(age);
                }

                map[y][x] = tmp.organism;
                name = fileScanner.next();
                txt = name.charAt(0);
            }
            tmp = moveOrder;
            moveOrder = moveOrder.next;
            tmp = null;

            eventsNumber = fileScanner.nextInt();
            fileScanner.nextLine(); //read '\n' left by an int

            for (int i = 0; i < eventsNumber; i++) //reading events
            {
                name = fileScanner.nextLine();
                AddEvent(name);
            }

            name = fileScanner.nextLine();
            SetHumanStats(name);

            return true;
        }
        catch (IOException e)
        {
            System.err.println("Error opening file: " + e.getMessage());
            return false;
        }
    }
}
