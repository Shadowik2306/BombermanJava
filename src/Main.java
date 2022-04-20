import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main extends JPanel implements ActionListener, KeyListener {
    boolean buttonPressed = false;
    boolean endHappened = false;
    Image img = Toolkit.getDefaultToolkit().getImage("C:\\Users\\user\\IdeaProjects\\BomberMan\\logo.png");
    Map map;
    int[][] basicMap = new int[10][10];
    int y = -250;
    static int time = 0;

    static Scanner in = new Scanner(System.in);
    static String nickname;

    String[] names = new String[100];
    int[] scores = new int[100];
    int point = 0;
    
    Scanner mapGenerator;
    {
        try {
            mapGenerator = new Scanner(new File("src/level.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    Scanner ratingGenerator;
    {
        try {
            ratingGenerator = new Scanner(new File("rating.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    Timer timer = new Timer(20, this);
    JFrame frame;
    int second = 0;

    public static void main(String[] args) {
        nickname = in.nextLine();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1020, 1050);;
        frame.setUndecorated(false);
        frame.add(new Main(frame));
        frame.setVisible(true);
    }

    public Main(JFrame frame) {
        this.frame = frame;
        setFocusable(true);
        addKeyListener(this);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                basicMap[i][j] = mapGenerator.nextInt();
            }

        }
        map = new Map(basicMap);
        timer.start();
    }

    public void paint(Graphics g) {
        if (endHappened) {
            g.setColor(Color.white);
            g.fillRect(0, 0, 1300, 1300);
            g.setColor(Color.red);
            g.setFont(new Font("Sans Serif", Font.PLAIN, 75));
            if (Enemy.isEnd()) {
                g.drawString("YOU ARE WIN", 200, 300);
            }
            else {
                g.drawString("YOU ARE LOSE", 200, 300);
            }
            g.setFont(new Font("Sans Serif", Font.PLAIN, 55));
            g.drawString("Rating", 400, 400);
            g.setFont(new Font("Sans Serif", Font.PLAIN, 35));
            int[] copyScore = scores.clone();
            int mi = 0;
            int ind = 0;
            for (int i = 0; i < Math.min(5, point); i++) {
                mi = 9999999;
                for (int j = 0; j < 100; j++) {
                    if (copyScore[j] != 0 && copyScore[j] < mi) {
                        mi = copyScore[j];
                        ind = j;
                    }
                }
                g.drawString(names[ind], 300, 400 + (i + 1) * 50);
                g.drawString(mi + " sec.", 500, 400 + (i + 1) * 50);
                copyScore[ind] = 0;
            }
        }
        else {
            if (buttonPressed) {
                if (Enemy.isEnd() || Hero.isEnd()) {
                    if (Enemy.isEnd()) {
                        FileWriter writer = null;
                        try {
                            writer = new FileWriter("rating.txt", true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        assert writer != null;
                        BufferedWriter bufferWriter = new BufferedWriter(writer);
                        try {
                            bufferWriter.write(nickname + "\n");
                            bufferWriter.write(Integer.toString(time));
                            bufferWriter.write("\n");
                            bufferWriter.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    String name;
                    int score;
                    boolean change = true;
                    while (ratingGenerator.hasNext()) {
                        name = ratingGenerator.nextLine();
                        score = Integer.parseInt(ratingGenerator.nextLine());
                        names[point] = name;
                        scores[point] = score;
                        point++;
                    }
                    System.out.println(Arrays.toString(names));

                    System.out.println(Arrays.toString(scores));
                    endHappened = true;
                } else {
                    map.getGraphics(g);
                    map.drawMap();
                }
            } else {
                g.setColor(Color.white);
                g.fillRect(0, 0, 2000, 2000);
                if (y < 250) y++;
                g.drawImage(img, 0, y, null);
            }
        }
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        if (!(Enemy.isEnd() || Hero.isEnd()) && (buttonPressed)) {
            second += 20;
            if (second > 800) {
                time++;
                second = 0;
                map.timeLeft();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                map.moveHero(Directions.Left);
                break;
            case KeyEvent.VK_RIGHT:
                map.moveHero(Directions.Right);
                break;
            case KeyEvent.VK_UP:
                map.moveHero(Directions.Up);
                break;
            case KeyEvent.VK_DOWN:
                map.moveHero(Directions.Down);
                break;
            case KeyEvent.VK_SPACE:
                if (!buttonPressed) buttonPressed = true;
                else map.heroPlaceBomb();
                break;
            default:
                buttonPressed = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
