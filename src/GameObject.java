import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {
    
    protected int x,y;
    protected float velX=0,velY=0;
    protected ID id;
    protected SpriteSheet ss;
    
    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract Rectangle getBounds();
    
    public GameObject(int x,int y,ID id,SpriteSheet ss){
        this.x=x;
        this.y=y;
        this.id=id;
        this.ss = ss;
    }


    public ID getId(){
        return id;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
    
    public float getvelX(){
        return velX;
    }
    
    public float getvelY(){
        return velY;
    }

    public void setId(ID id){
        this.id=id;
    }

    public void setX(int x){
        this.x=x;
    }

    public void setY(int y){
        this.y=y;
    }

    public void setvelX(float velX){
        this.velX=velX;
    }

    public void setvelY(float velY){
        this.velY=velY;
    }

}
