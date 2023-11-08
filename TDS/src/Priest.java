import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Priest extends GameObject {

	Handler handler;
	Game game;
	private int speed = 3;

	private BufferedImage[] lPriest_images = new BufferedImage[3];
	private BufferedImage[] rPriest_images = new BufferedImage[3];

	Animation leftanim;
	Animation rightanim;
	int side = 0;

	public Priest(int x, int y, ID id, Handler handler, SpriteSheet ss, Game game) {
		super(x, y, id, ss);
		this.handler = handler;
		this.game = game;

		lPriest_images[0] = ss.grabImage(1, 3, 32, 64);
		lPriest_images[1] = ss.grabImage(2, 3, 32, 64);
		lPriest_images[2] = ss.grabImage(3, 3, 32, 64);

		rPriest_images[0] = ss.grabImage(1, 1, 32, 64);
		rPriest_images[1] = ss.grabImage(2, 1, 32, 64);
		rPriest_images[2] = ss.grabImage(3, 1, 32, 64);
		

		leftanim = new Animation(3, lPriest_images[0], lPriest_images[1],lPriest_images[2]);

		rightanim = new Animation(3, rPriest_images[0], rPriest_images[1],rPriest_images[2]);

	}

	@Override
	public void tick() {
		this.x += velX;
		this.y += velY;

		collision();
		movement();

		leftanim.runAnimation();
		rightanim.runAnimation();
	}

	private void collision() {
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);

			if (tempObject.getId() == ID.Block) {
				if (this.getBounds().intersects(tempObject.getBounds())) {
					this.x += this.velX * -1;
					this.y += this.velY * -1;
				}
			}
			if (tempObject.getId() == ID.Ghost) {
				if (this.getBounds().intersects(tempObject.getBounds())) {
					game.hp--;
				}
			}
			if (tempObject.getId() == ID.Wraith) {
				if (this.getBounds().intersects(tempObject.getBounds())) {
					game.hp-=3;
				}
			}
			if (tempObject.getId() == ID.Wisp) {
				if (this.getBounds().intersects(tempObject.getBounds())) {
					game.hp-=10;
				}
			}
			if (tempObject.getId() == ID.BOSS) {
				if (this.getBounds().intersects(tempObject.getBounds())) {
					game.hp-=50;
				}
			}
			if (tempObject.getId() == ID.BOSSBullet) {
				if (this.getBounds().intersects(tempObject.getBounds())) {
					game.hp-=5;
				}
			}
			if (tempObject.getId() == ID.Holywater) {
				if (this.getBounds().intersects(tempObject.getBounds())) {
					if(game.hp<=50){
						game.hp+=50;	
					}
					else{
						game.hp = 100;
					}
				}
			}
		}
	}

	private void movement() {
		if (handler.isUp()) {
			this.velY = -speed;
		} else if (!handler.isDown()) {
			this.velY = 0;
		}
		if (handler.isDown()) {
			this.velY = speed;
		} else if (!handler.isUp()) {
			this.velY = 0;
		}
		if (handler.isLeft()) {
			this.velX = -speed;
		} else if (!handler.isRight()) {
			this.velX = 0;
		}
		if (handler.isRight()) {
			this.velX = speed;
		} else if (!handler.isLeft()) {
			this.velX = 0;
		}
	}

	public void render(Graphics g) {
		if( velX == 0 && side == 0){
			g.drawImage(rPriest_images[0], x, y, null);
		}
		else if( velX == 0 && side == 1){
			g.drawImage(lPriest_images[0], x, y, null);
		}
		else if(velX >= 0){
			rightanim.drawAnimation(g, x, y, 0);
			side = 0;
		}
		else if(velX <= 0){
			leftanim.drawAnimation(g, x, y, 0);
			side = 1;
		}
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 48);
	}

}