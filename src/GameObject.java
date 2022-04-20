import java.awt.*;

public class GameObject {
    Graphics canvas;

    public void setBackground(int x, int y) {
        canvas.setColor(Color.gray);
        canvas.fillRect(x, y, 100, 100);
    }

    public void drawObj(int x, int y){
        setBackground(x, y);
    }

    public void getGraphics(Graphics g) {
        canvas = g;
    }

    public Object whatIs() {
        return Object.Nothing;
    }

}
