package World;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import Abstract.Organism;
import Animals.*;
import Plants.*;

public class Game_Panel extends JPanel
{
    //map layout data in pixels
    private final int squareSize = 20;
    private final int squareDistanceSpace = 4;
    private final int mapOffsetX = 300;
    private final int mapOffsetY = 100;

    private World world;
    private JLabel textLabel;
    private String[] names;

    public Game_Panel(World world)
    {
        this.world = world;
        textLabel = new JLabel("Strzalki - poruszanie sie po mapie i rozpoczynanie nowej tury");
        textLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(textLabel);
        names = new String[]{"Antylopa", "Lis", "Owca", "Zolw", "Wilk", "Mlecz", "Wilcze jagody", "Trawa", "Guarana", "Barszcz Sosnowskiego"};

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                //there we calculate y and x map position
                int y = (e.getY() - mapOffsetY) / (squareSize + squareDistanceSpace);
                int x = (e.getX() - mapOffsetX) / (squareSize + squareDistanceSpace);

                //we check if 'y * (squareSize + squareDistanceSpace) + (squareSize - 1) >= e.getY() - mapOffsetY'
                //because we don't want to count space between squares
                if (y >= 0 && y * (squareSize + squareDistanceSpace) + (squareSize - 1) >= e.getY() - mapOffsetY && y < world.GetHeight() &&
                        x >= 0 && x < world.GetWidth() && x * (squareSize + squareDistanceSpace) + (squareSize - 1) >= e.getX() - mapOffsetX &&
                        world.GetMap()[y][x] == null)
                {
                    String selectedName = (String) JOptionPane.showInputDialog(null,"Wybierz organizm",
                            "Name Selection",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            names,
                            null);

                    if (selectedName != null)
                    {
                        if (selectedName.equals("Mlecz"))
                        {
                            world.GetMap()[y][x] = new Dandelion(x, y, world);
                        }
                        else if (selectedName.equals("Trawa"))
                        {
                            world.GetMap()[y][x] = new Grass(x, y, world);
                        }
                        else if (selectedName.equals("Guarana"))
                        {
                            world.GetMap()[y][x] = new Guarana(x, y, world);
                        }
                        else if (selectedName.equals("Wilcze jagody"))
                        {
                            world.GetMap()[y][x] = new Deadly_Nightshade(x, y, world);
                        }
                        else if (selectedName.equals("Barszcz Sosnowskiego"))
                        {
                            world.GetMap()[y][x] = new Pine_Borscht(x, y, world);
                        }
                        else if (selectedName.equals("Zolw"))
                        {
                            world.GetMap()[y][x] = new Turtle(x, y, world);
                        }
                        else if (selectedName.equals("Antylopa"))
                        {
                            world.GetMap()[y][x] = new Antelope(x, y, world);
                        }
                        else if (selectedName.equals("Owca"))
                        {
                            world.GetMap()[y][x] = new Sheep(x, y, world);
                        }
                        else if (selectedName.equals("Wilk"))
                        {
                            world.GetMap()[y][x] = new Wolf(x, y, world);
                        }
                        else if (selectedName.equals("Lis"))
                        {
                            world.GetMap()[y][x] = new Fox(x, y, world);
                        }
                        world.AddMove(world.GetMap()[y][x]);
                        repaint(); // Redraw the panel
                    }
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Organism[][] map = world.GetMap();
        int Posx = mapOffsetX; //starting x position
        int Posy = mapOffsetY; //starting y position
        Color color; //color for squares

        for (int y = 0; y < world.GetHeight(); y++)
        {
            for (int x = 0; x < world.GetWidth(); x++)
            {
                if (map[y][x] != null)
                {
                   color = map[y][x].GetColor();
                }
                else
                {
                    color = Color.white;
                }
                g.setColor(color);
                g.fillRect(Posx, Posy, squareSize, squareSize);
                Posx += squareSize + squareDistanceSpace;
            }

            Posx = mapOffsetX;
            Posy += squareSize + squareDistanceSpace;
        }

        drawLegend(g);
        drawEvents(g);
    }

    private void drawLegend(Graphics g)
    {
        int legendSquareSize = 20; //size of colorful box
        int stringBoxDistance = 10; //text distance from sqaure box
        int stringBoxHeight = -5; //correction for text height level
        int Posx = 50; //starting x position
        int Posy = 40; //starting y position

        Color color = Color.getHSBColor(130,150,200);
        g.setColor(color);
        g.fillRect(Posx, Posy, legendSquareSize, legendSquareSize);
        String legendText = "antylopa";
        g.setColor(Color.BLACK);
        g.drawString(legendText, Posx + legendSquareSize + stringBoxDistance, Posy + legendSquareSize + stringBoxHeight);

        Posx += legendSquareSize + 80; //distance between each legend point

        color = Color.yellow;
        g.setColor(color);
        g.fillRect(Posx, Posy, legendSquareSize, legendSquareSize);
        legendText = "mlecz";
        g.setColor(Color.BLACK);
        g.drawString(legendText, Posx + legendSquareSize + stringBoxDistance, Posy + legendSquareSize + stringBoxHeight);

        Posx += legendSquareSize + 70;

        color = Color.red;
        g.setColor(color);
        g.fillRect(Posx, Posy, legendSquareSize, legendSquareSize);
        legendText = "wilcze jagody";
        g.setColor(Color.BLACK);
        g.drawString(legendText, Posx + legendSquareSize + stringBoxDistance, Posy + legendSquareSize + stringBoxHeight);

        Posx += legendSquareSize + 110;

        color = Color.orange;
        g.setColor(color);
        g.fillRect(Posx, Posy, legendSquareSize, legendSquareSize);
        legendText = "lis";
        g.setColor(Color.BLACK);
        g.drawString(legendText, Posx + legendSquareSize + stringBoxDistance, Posy + legendSquareSize + stringBoxHeight);

        Posx += legendSquareSize + 60;

        color = Color.green;
        g.setColor(color);
        g.fillRect(Posx, Posy, legendSquareSize, legendSquareSize);
        legendText = "trawa";
        g.setColor(Color.BLACK);
        g.drawString(legendText, Posx + legendSquareSize + stringBoxDistance, Posy + legendSquareSize + stringBoxHeight);

        Posx += legendSquareSize + 80;

        color = Color.cyan;
        g.setColor(color);
        g.fillRect(Posx, Posy, legendSquareSize, legendSquareSize);
        legendText = "guarana";
        g.setColor(Color.BLACK);
        g.drawString(legendText, Posx + legendSquareSize + stringBoxDistance, Posy + legendSquareSize + stringBoxHeight);

        Posx += legendSquareSize + 80;

        color = Color.black;
        g.setColor(color);
        g.fillRect(Posx, Posy, legendSquareSize, legendSquareSize);
        legendText = "czlowiek";
        g.setColor(Color.BLACK);
        g.drawString(legendText, Posx + legendSquareSize + stringBoxDistance, Posy + legendSquareSize + stringBoxHeight);

        Posx += legendSquareSize + 80;

        color = Color.BLUE;
        g.setColor(color);
        g.fillRect(Posx, Posy, legendSquareSize, legendSquareSize);
        legendText = "barszcz sosnowskiego";
        g.setColor(Color.BLACK);
        g.drawString(legendText, Posx + legendSquareSize + stringBoxDistance, Posy + legendSquareSize + stringBoxHeight);

        Posx += legendSquareSize + 150;

        color = Color.pink;
        g.setColor(color);
        g.fillRect(Posx, Posy, legendSquareSize, legendSquareSize);
        legendText = "owca";
        g.setColor(Color.BLACK);
        g.drawString(legendText, Posx + legendSquareSize + stringBoxDistance, Posy + legendSquareSize + stringBoxHeight);

        Posx += legendSquareSize + 80;

        color = Color.MAGENTA;
        g.setColor(color);
        g.fillRect(Posx, Posy, legendSquareSize, legendSquareSize);
        legendText = "zolw";
        g.setColor(Color.BLACK);
        g.drawString(legendText, Posx + legendSquareSize + stringBoxDistance, Posy + legendSquareSize + stringBoxHeight);

        Posx += legendSquareSize + 80;

        color = Color.GRAY;
        g.setColor(color);
        g.fillRect(Posx, Posy, legendSquareSize, legendSquareSize);
        legendText = "wilk";
        g.setColor(Color.BLACK);
        g.drawString(legendText, Posx + legendSquareSize + stringBoxDistance, Posy + legendSquareSize + stringBoxHeight);
    }

    private void drawEvents(Graphics g)
    {
        int Posx = 5; //starting x position
        int Posy = 100; //starting y position

        g.setColor(Color.BLACK);
        g.drawString("Statystyki czlowieka: " + world.GetHumanStats(), Posx, Posy);
        Posy += 20;
        g.drawString("Zdarzenia:", Posx, Posy);
        Posy += 20;

        for (String event : world.GetEvents())
        {
            g.drawString(event, Posx, Posy);
            Posy += 15;
        }
    }
}