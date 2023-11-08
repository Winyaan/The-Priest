
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Wisp extends GameObject{
    private Handler handler;
    Random r = new Random();
    int choose = 0;
    int hp = 50;
    boolean isMoving;
    int persuitTimer;

    private BufferedImage enemy_Image;

    public Wisp(int x, int y, ID id, Handler handler, SpriteSheet ss) {
        super(x, y, id, ss);
        this.handler = handler;
        isMoving = false;
        persuitTimer = 0;

        enemy_Image = ss.grabImage(6, 1, 32, 32);
    }

    @Override
	public void tick() {
		this.x += this.velX;
		this.y += this.velY;

		if (!isMoving) {
			this.persuitTimer++;
		}
		for (int i = 0; i < this.handler.object.size(); i++) {
			GameObject tempObject = this.handler.object.get(i);


			if (tempObject.getId() == ID.Bullet) {
				if (this.getBounds().intersects(tempObject.getBounds())) {
					this.hp -= 50;
					this.handler.removeObject(tempObject);
				}
			}

			if (tempObject.getId() == ID.Priest) {
				if (this.getBounds().intersects(tempObject.getBounds())) {

				}
				if (this.isMoving) {
					this.persuitPlayer(tempObject);
				}
			}
		}

		if (this.hp <= 0) {
			this.handler.removeObject(this);
		}
		// end hp if
		if (this.persuitTimer >= 20) {
			this.isMoving = true;
			this.persuitTimer = 0;
		}
	}


	private void persuitPlayer(GameObject player) {
		if(this.x - player.x  < 400 && this.y - player.y < 400) {
			if (this.x > player.getX()) {
				this.velX = -5;

			}
			if (this.x < player.getX()) {
				this.velX = 5;

			}
			if (this.y > player.getY()) {
				this.velY = -5;

			}
			if (this.y < player.getY()) {
				this.velY = 5;

			}
		} else {
			this.velX = 0;
			this.velY = 0;
		}
		
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
}
