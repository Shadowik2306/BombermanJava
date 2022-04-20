import java.awt.*;

public class Enemy extends GameObject{
    static int countOfEnemies = 3;

    int speed = 2;
    int powerRange = 1;
    int getMaxCountOfBombs = 1;
    int stepsToPlaceBombs = 5;

    boolean upToBomb = false;
    int timeToPlaceBomb = (int)(Math.random() * -5);
    boolean isActive = true;
    int placedBomb = 0;
    int xBm = 0, yBm = 0;

    int[][] path;

    @Override
    public void drawObj(int x, int y) {
        super.drawObj(x, y);
        if (upToBomb) {
            canvas.setColor(Color.blue);
            canvas.fillOval(x + 25, y + 25, 50, 50);
        }
        canvas.setColor(Color.green);
        canvas.fillRect(x + 35, y + 43, 20, 40);
        canvas.setColor(Color.blue);
        canvas.fillOval(x + 28, y + 10, 33, 33);
    }

    @Override
    public Object whatIs() {
        return Object.Enemy;
    }

    public boolean isExisting() {
        if (Math.random() * 100 < (timeToPlaceBomb - 4) * 10) {
            timeToPlaceBomb = 0;
            upToBomb = true;
            return true;
        }
        timeToPlaceBomb++;
        return false;
    }

    public boolean isUpToBomb() {
        return upToBomb;
    }

    public void setUpToBomb(boolean upToBomb) {
        this.upToBomb = upToBomb;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isPlaceBomb() {
        if (placedBomb > 0) {
            placedBomb--;
            return true;
        }
        return false;
    }

    public int[] getPosB() {
        return new int[] {xBm, yBm};
    }

    public void setBomb(int x, int y) {
        placedBomb = 3;
        xBm = x;
        yBm = y;
    }

    public void setPath(int[][] path) {
        this.path = path;
    }

    public int[][] getPath() {
        return path;
    }

    static void die() {
        countOfEnemies--;
    }

    static boolean isEnd() {
        return countOfEnemies <= 0;
    }
}
