import java.awt.*;

public class Bombs extends GameObject{
    int existing = 3;


    @Override
    public void drawObj(int x, int y) {
        super.drawObj(x, y);
        canvas.setColor(Color.blue);
        canvas.fillOval(x + 25, y + 25, 50, 50);
    }

    @Override
    public Object whatIs() {
        return Object.B;
    }

    public boolean isExisting() {
        existing--;
        if (existing <= 0) return false;
        else return true;
    }

}
