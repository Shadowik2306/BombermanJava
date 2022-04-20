import java.awt.*;

public class BreakableWall extends Wall{
    @Override
    public void drawObj(int x, int y) {
        canvas.setColor(Color.PINK);
        canvas.fillRect(x, y, 100, 100);
    }

    @Override
    public Object whatIs() {
        return Object.BW;
    }
}
