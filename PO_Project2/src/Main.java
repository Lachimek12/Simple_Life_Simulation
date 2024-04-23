import World.World;
import javax.swing.*;
import World.Frame;

public class Main
{
    public static final int MAX_WIDTH = 60;
    public static final int MAX_HEIGHT = 35;

    public static void main(String[] args)
    {
        int width = 0, height = 0;
        boolean correct = false; //to tell if player has given correct map size
        String input; //to take width and height input from user and parse it to integer
        Frame frame; //frame is the main window which is being displayed

        //read width and height until correct
        while (!correct)
        {
            input = JOptionPane.showInputDialog(null, "Podaj szerokosc:");
            if (input != null)
            {
                width = Integer.parseInt(input);
            }
            input = JOptionPane.showInputDialog(null, "Podaj wysokosc:");
            if (input != null)
            {
                height = Integer.parseInt(input);
            }

            if (width <= MAX_WIDTH && height <= MAX_HEIGHT && width > 0 && height > 0)
            {
                correct = true;
            }
            else
            {
                System.out.println("Nieprawidlowe wymiary");
            }
        }

        World world = new World(width, height);
        world.GenerateWorld();

        frame = new Frame(world);

        world.PerformTurn(); //first warm up turn as all the organisms are at 0 age
    }
}