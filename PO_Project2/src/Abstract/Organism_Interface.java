package Abstract;

import World.World;

import java.awt.*;

public interface Organism_Interface
{
    boolean Action(); //performs organism action, returns true if organism survived turn
    //performs collision (collision is called when organism is attacked), returns true if opponent won/survived fight
    boolean Collision(Organism opponent);
    Organism CreateNew(int x, int y); //creates new object of the same type
    void Draw(); //draws texture in current cursor position
    int GetInitiative();
    int GetStrength();
    int GetX();
    int GetY();
    void SetX(int x);
    void SetY(int y);
    World GetWorld();
    int GetAge();
    void IncreaseAge(); //increases age by 1
    void SetStrength(int strength);
    String GetName();
    char GetTexture();
    void SetAge(int age);
    Color GetColor();
}
