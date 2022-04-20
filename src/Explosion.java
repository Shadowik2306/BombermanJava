import java.awt.*;

public class Explosion extends GameObject {
    int existing = 1;

    @Override
    public void drawObj(int x, int y) {
        canvas.setColor(Color.red);
        canvas.fillRect(x, y, 100, 100);
    }

    @Override
    public Object whatIs() {
        return Object.EX;
    }

    public boolean isExisting() {
        existing--;
        if (existing <= 0) return false;
        else return true;
    }

    public void itWasLower() {
        existing++;
    }
}
