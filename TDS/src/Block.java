import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Block extends GameObject {

    private BufferedImage block_Image;

    public Block(int x,int y,ID id,SpriteSheet ss){
        super(x, y, id, ss);

        block_Image = ss.grabImage(5, 2, 32, 32);
    }

    public void tick(){

    }

    public void render(Graphics g){
        g.drawImage(block_Image, x, y, null);
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, 32, 32);
    }
}
