import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {
    
    public LinkedList<GameObject> object = new LinkedList<GameObject>();
	public LinkedList<GameObject> object2 = new LinkedList<GameObject>();

	private boolean up = false, down = false, left = false, right = false;
	
	public void tick() {
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);

			tempObject.tick();
		}
	}

	public void render(Graphics g) {
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);

			tempObject.render(g);
		}
	}

	public GameObject findPriest() {
		for (int i = 0; i < object.size(); i++) {
			GameObject obj = object.get(i);
			if (obj.getId() == ID.Priest) {
				return obj;
			}
		}
		return null;
	}

	public void addObject(GameObject gameObject) {
		this.object.add(gameObject);
	}

	public void removeObject(GameObject gameObject) {
		this.object.remove(gameObject);
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

}
