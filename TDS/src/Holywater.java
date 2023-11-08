import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Holywater extends GameObject{
    private Handler handler;
    Random r = new Random();
    int choose = 0;
    boolean isMoving;
    int persuitTimer;

    private BufferedImage enemy_Image;

    public Holywater(int x, int y, ID id, Handler handler, SpriteSheet ss) {
        super(x, y, id, ss);
        this.handler = handler;
        isMoving = false;
        persuitTimer = 0;

        enemy_Image = ss.grabImage(6, 2, 32, 32);
    }

    public void render(Graphics g) {
        g.drawImage(enemy_Image, x, y, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }

    public Rectangle getBoundsBig() {
        return new Rectangle(x - 16, y - 16, 64, 64);
    }

    @Override
    public void tick() {
        for (int i = 0; i < this.handler.object.size(); i++) {
			GameObject tempObject = this.handler.object.get(i);


			if (tempObject.getId() == ID.Priest) {
				if (this.getBounds().intersects(tempObject.getBounds())) {
                    this.handler.removeObject(this);
				}
			}
		}
		
    }
}
