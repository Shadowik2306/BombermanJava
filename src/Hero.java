import java.awt.*;

public class Hero extends GameObject{
    static boolean isAlive = true;

    int powerRange = 1;
    int timeBomb = 0;
    int getMaxCountOfBombs = 1;
    int lives = 3;
    boolean upToBomb = false;
    int posX, posY;


    @Override
    public void drawObj(int x, int y) {
        super.drawObj(x, y);
        if (upToBomb) {
            canvas.setColor(Color.blue);
            canvas.fillOval(x + 25, y + 25, 50, 50);
        }
        canvas.setColor(Color.red);
        canvas.fillRect(x + 35, y + 43, 20, 40);
        canvas.setColor(Color.blue);
        canvas.fillOval(x + 28, y + 10, 33, 33);
    }


    @Override
    public Object whatIs() {
        return Object.Hero;
    }

    public boolean isUpToBomb() {
        return upToBomb;
    }

    public void setUpToBomb(boolean upToBomb) {
        this.upToBomb = upToBomb;
    }

    public void isExisting() {
        timeBomb--;
    }

    public boolean isTimeToPlaceBomb() {
        if (timeBomb <= 0) {
            timeBomb = 3;
            return true;
        }
        return false;
    }

    public static void died() {
        Hero.isAlive = false;
    }

    public static boolean isEnd() {
        return !isAlive;
    }
}
