import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class BOSS extends GameObject {

	private Handler handler;
	Random r = new Random();
	int choose = 0;
	int hp = 1500;
	boolean isMoving;
	int persuitTimer;
	private int timer = 200;

	private BufferedImage[] enemy_Image = new BufferedImage[2];

	public BOSS(int x, int y, ID id, Handler handler, SpriteSheet ss) {
		super(x, y, id, ss);
		this.handler = handler;
		isMoving = false;
		persuitTimer = 0;

		enemy_Image[0] = ss.grabImage(5, 3, 64, 64);
		enemy_Image[1] = ss.grabImage(7, 3, 64, 64);
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
		timer--;
		if (timer <= 0) {
			shoot();
			timer = 200;
		}
		if (this.hp <= 0) {
			this.handler.removeObject(this);
		}
		if (this.persuitTimer >= 20) {
			this.isMoving = true;
			this.persuitTimer = 0;
		}
	}

	private void shoot() {
		for (int i = 0; i < 8; i++) {
			double angle = Math.PI / 4 * i;
			double velX = 5 * Math.cos(angle);
			double velY = 5 * Math.sin(angle);
			handler.addObject(new BOSSBullet(this.x + 16, this.y + 24, ID.BOSSBullet, handler, (int) velX,
					(int) velY, ss));
		}
	}
	

	private void persuitPlayer(GameObject player) {
		if (this.x - player.x < 600 && this.y - player.y < 600) {
			if (this.x > player.getX()) {
				this.velX = -1;

			}
			if (this.x < player.getX()) {
				this.velX = 1;

			}
			if (this.y > player.getY()) {
				this.velY = -1;

			}
			if (this.y < player.getY()) {
				this.velY = 1;

			}
		} else {
			this.velX = 0;
			this.velY = 0;
		}

	}

	public void render(Graphics g) {
		if(velX>=0){
			g.drawImage(enemy_Image[1], x, y, null);
		}
		else if(velX<0){
			g.drawImage(enemy_Image[0], x, y, null);
		}

	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 64, 64);
	}

	public Rectangle getBoundsBig() {
		return new Rectangle(x - 16, y - 16, 64, 64);
	}
}
