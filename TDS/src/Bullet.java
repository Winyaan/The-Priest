import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Bullet extends GameObject{

    private Handler handler;
    
    public Bullet(int x, int y, ID id,Handler handler,int mx, int my,SpriteSheet ss){
        super(x, y, id,ss);
        this.handler = handler;

        velX = (mx - x) / 10;
        velY = (my - y) / 10;
    }

    public void tick() {
        x += velX;
        y += velY;

        for(int i = 0; i < handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);

            if(tempObject.getId() == ID.Block){
                if(getBounds().intersects(tempObject.getBounds())){
                    handler.removeObject(this);
                }
            }
        }
    }


    public void render(Graphics g) {
        int newx = x+1;
        int newy = y+3;
        g.setColor(Color.white);
        g.fillRect(newx, y, 2, 8);
        g.fillRect(x, newy, 4, 2);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 4, 8);
    }
    
}
