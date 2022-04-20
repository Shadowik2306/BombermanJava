import java.awt.*;

public class UnbreakableWall extends Wall{
    @Override
    public void drawObj(int x, int y) {
        canvas.setColor(Color.black);
        canvas.fillRect(x, y, 100, 100);
    }

    @Override
    public Object whatIs() {
        return Object.UW;
    }
}
