package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import core.GameState;
import core.Window;

public class MenuHandler {

    private Window w;

    private int SelectorX = 0, SelectorY = 0;
    private int Selector_index = 0;
    private int SelectorLeftBuffer = 16;
    private int SelectorWidth = 16;
    private int MenuX = 1100;
    private int MenuY = 250;
    public ArrayList<Integer> Selector_list = new ArrayList<>();
    public int selector_size = 3;
    private String fileName = "saving.txt"; 
    private boolean check_continueGame = true;



    public MenuHandler(Window w)    {
        this.w = w;
    }

    public void continueGame()  {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            if ((line = br.readLine()) != null) {
                w.level.player.x = Double.parseDouble(line);
                w.level.player.y = Double.parseDouble(br.readLine());
                w.menu.Selector_list.add(0, 0);
                w.menu.selector_size = 4;
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    public void Render(Graphics g)  {

        while (check_continueGame)  {
            continueGame();
            check_continueGame = false;
        }
        // text
        g.setColor(Color.red);

        String GameName = "JUMP BOI", ContinueS = "Continue", StartGameS = "New Game", OptionS = "Options", ExitS = "Exit",
        subTittle = "Leap to Triumph: Master the Heights in JumpBoi",
        body = "Supreme Heights Await: Test Your Limits in the Endless Skies of JumpKing!",
        guide1 = "Use W A S D to navigate the menu and control your character.",
        guide2 = "Jump up to reach the peak and be careful not to fall down—it'll hurt a lot!",
        guide3 = "Good Luck Have Fun!";

        // JUMP BOI
        g.setFont(new Font("Snap ITC", Font.BOLD, 100));
        FontMetrics fontM = g.getFontMetrics(g.getFont());
        g.drawString(GameName, MenuX - (fontM.stringWidth(GameName)/2), MenuY);
        
        g.setColor(Color.white);

        // BODY
        g.setFont(new Font("Bell MT", Font.ITALIC, 20));
        fontM = g.getFontMetrics(g.getFont());
        g.drawString(body, MenuX - 750 - (fontM.stringWidth(body)/2), MenuY + 40);
        g.drawString(guide1, MenuX - 750 - (fontM.stringWidth(body)/2), MenuY + 80);
        g.drawString(guide2, MenuX - 750 - (fontM.stringWidth(body)/2), MenuY + 120);
        g.drawString(guide3, MenuX - 750 - (fontM.stringWidth(body)/2), MenuY + 160);

        // MENU
        g.setFont(new Font("Algerian", Font.BOLD, 18));
        fontM = g.getFontMetrics(g.getFont());
        g.drawString(subTittle, MenuX - (fontM.stringWidth(subTittle)/2), MenuY + 40);

        g.setFont(new Font("Algerian", Font.BOLD, 40));
        fontM = g.getFontMetrics(g.getFont());
        
        g.drawString(StartGameS, MenuX - (fontM.stringWidth(StartGameS)/2), MenuY + 250);
        Selector_list.add(1);
        SelectorX = MenuX - (fontM.stringWidth(StartGameS)/2) - SelectorWidth - SelectorLeftBuffer;
        if (Selector_list.get(Selector_index) == 1)  {
            SelectorY = MenuY + 250 - SelectorWidth - 6;
        }

        g.drawString(OptionS, MenuX - (fontM.stringWidth(OptionS)/2), MenuY + 350);
        Selector_list.add(2);
        if (Selector_list.get(Selector_index) == 2)  {
            SelectorY = MenuY + 350 - SelectorWidth - 6;
        }

        g.drawString(ExitS, MenuX - (fontM.stringWidth(ExitS)/2), MenuY + 450);
        Selector_list.add(3);
        if (Selector_list.get(Selector_index) == 3) {
            SelectorY = MenuY + 450 - SelectorWidth - 6;
        } 

        if (Selector_list.get(0) == 0)  {
            g.drawString(ContinueS, MenuX - (fontM.stringWidth(ContinueS)/2), MenuY + 150);
        }
        
        if (Selector_list.get(Selector_index) == 0)  {
            SelectorY = MenuY + 150 - SelectorWidth - 6;
        }

        // Selector
        g.setColor(Color.white);
        if (SelectorX != 0)  g.fillOval(SelectorX, SelectorY, SelectorWidth, SelectorWidth);

    }

    public void tick() {

    }

    public void keyPress(int key) {
        if (key == KeyEvent.VK_S)   {
            Selector_index += 1 ;
            if (Selector_index > selector_size - 1)  Selector_index = 0;
        }
        else if (key == KeyEvent.VK_W)   {
            Selector_index -= 1 ;
            if (Selector_index < 0)  Selector_index = selector_size - 1;
        } 
        else if (key == KeyEvent.VK_ENTER)  {
            switch (Selector_list.get(Selector_index)) {
                case 0:
                    w.gs = GameState.Game;
                    break;
                case 1:
                    w.gs = GameState.Game;
                    w.level.player.x = 48+700;
                    w.level.player.y = 660;
                    //w.level.player.velx = 0.0;
                    w.level.player.vely = 1.0;
                    break;
                case 3:
                    saveProgress();
                    break;
                default:
                    break;
            }
        }
    }

    public void keyRelease(int key) {

    }

    public void saveProgress() {
        UIManager.put("OptionPane.background", new Color(255, 255, 204));
        UIManager.put("Panel.background", new Color(255, 255, 204));

        int response = JOptionPane.showConfirmDialog(null, "Would you like to save your progress?", "Jump boi", JOptionPane.YES_NO_CANCEL_OPTION);

        if (response == JOptionPane.YES_OPTION) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, false))) {
                bw.write(Double.toString(w.level.player.x)); // Ghi dữ liệu vào tệp
                bw.newLine(); // Xuống dòng
                bw.write(Double.toString(w.level.player.y));
            } catch (IOException e) {
                System.err.println("Error writing to the file: " + e.getMessage());
            }
            System.exit(0);
        } 
        else if (response == JOptionPane.NO_OPTION) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, false))) {
                bw.write(""); // Ghi dữ liệu vào tệp
            } catch (IOException e) {
                System.err.println("Error writing to the file: " + e.getMessage());
            }
            System.exit(0);
        }
    }

    




}

