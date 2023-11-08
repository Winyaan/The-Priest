import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class BOSSBullet extends GameObject {

    private Handler handler;

    public BOSSBullet(int x, int y, ID id, Handler handler, int velX, int velY, SpriteSheet ss) {
        super(x, y, id, ss);
        this.handler = handler;
        this.velX = velX/2;
        this.velY = velY/2;
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ID.Block || tempObject.getId() == ID.Priest) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    handler.removeObject(this);
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(x, y, 8, 8);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 8, 8);
    }
}

