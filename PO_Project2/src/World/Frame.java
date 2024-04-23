package World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Frame extends JFrame
{
    //int values representing each arrow key input
    public static final int KEY_UP = 72;
    public static final int KEY_DOWN = 80;
    public static final int KEY_LEFT = 75;
    public static final int KEY_RIGHT = 77;

    public Frame (World world)
    {
        this.setTitle("Michal Wesiora, 193126");
        JButton nextButton = new JButton("Nastepna tura");
        JButton powerUpButton = new JButton("Aktywuj umiejetnosc");
        JButton saveButton = new JButton("Zapisz gre");
        JButton loadButton = new JButton("Wczytaj gre");
        //setting false focus so that keyListener will be working even after pressing a button
        nextButton.setFocusable(false);
        powerUpButton.setFocusable(false);
        saveButton.setFocusable(false);
        loadButton.setFocusable(false);

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                world.SetButton(0);
                world.PerformTurn();
                world.DrawWorld();
            }
        });

        powerUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                world.PowerUp();
                world.DrawWorld();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String fileName = JOptionPane.showInputDialog("Podaj nazwe pliku:");

                if (fileName != null && world.SaveWorld(fileName))
                {
                    JOptionPane.showMessageDialog(null, "Gra zostala zapisana do pliku: " + fileName);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Blad zapisu");
                }
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String fileName = JOptionPane.showInputDialog("Podaj nazwe pliku:");

                if (fileName != null && world.LoadWorld(fileName))
                {
                    JOptionPane.showMessageDialog(null, "Wczytano poprawnie");
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Blad odczytu");
                }
                world.DrawWorld();
            }
        });

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1800, 950);
        this.getContentPane().add(world.GetGamePanel());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(nextButton);
        buttonPanel.add(powerUpButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e)
            {
                int keyCode = e.getKeyCode();

                // Perform action based on the pressed arrow key
                switch (keyCode)
                {
                    case KeyEvent.VK_UP:
                        world.SetButton(KEY_UP);
                        world.PerformTurn();
                        world.DrawWorld();
                        break;
                    case KeyEvent.VK_DOWN:
                        world.SetButton(KEY_DOWN);
                        world.PerformTurn();
                        world.DrawWorld();
                        break;
                    case KeyEvent.VK_LEFT:
                        world.SetButton(KEY_LEFT);
                        world.PerformTurn();
                        world.DrawWorld();
                        break;
                    case KeyEvent.VK_RIGHT:
                        world.SetButton(KEY_RIGHT);
                        world.PerformTurn();
                        world.DrawWorld();
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        this.setFocusable(true);
        this.setVisible(true);
        this.requestFocusInWindow();
    }
}
