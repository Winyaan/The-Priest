import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener, MouseListener {

    private static final long serialVersionUID = 1L;

    private boolean isRunning = false;
    private Handler handler;
    private Camera camera;
    private SpriteSheet ss;
    private Timer gameTimer;
    private int Level = 0;
    private int time = 120;
    private Timer clock = new Timer(1000, e -> {time--;});

    private BufferedImage[] level = new BufferedImage[3];
    private BufferedImage sprite_sheet = null;
    private BufferedImage floor = null;

    public int hp = 100;

    private enum GameState {
        START_MENU,
        GAME,
        NEXT,
        GAMEOVER,
        VICTORY
    }

    private GameState gameState;

    public Game() {
        isRunning = true;
        new Window(1000, 563, "The Priest", this);
        gameState = GameState.START_MENU;

        handler = new Handler();
        camera = new Camera(0, 0);

        BufferedImageLoader loader = new BufferedImageLoader();
        level[0] = loader.loadImage("/res/Level1.png");
        level[1] = loader.loadImage("/res/Level2.png");
        level[2] = loader.loadImage("/res/Level3.png");
        sprite_sheet = loader.loadImage("/res/Projectp.png");
        ss = new SpriteSheet(sprite_sheet);
        floor = ss.grabImage(4, 2, 32, 32);

        LoadLevel(level[0]);

        this.addMouseListener(this);

        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(new KeyInput(handler));
        this.addMouseListener(new MouseInput(handler, camera, ss));
        this.start();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isRunning) {
            tick();
            if (gameState == GameState.START_MENU) {
                stop();
                repaint();
            } else if (gameState == GameState.GAME) {
                repaint();
            } else if (gameState == GameState.NEXT) {
                stop();
                repaint();
            } else if (gameState == GameState.VICTORY){
                stop();
                repaint();
            }
            if (hp <= 0) {
                stop();
                repaint();
            }
        }
    }

    private void start() {
        isRunning = true;
        gameTimer = new Timer(10, this);
        gameTimer.start();
        clock.start();
        time=60;
    }

    private void stop() {
        gameTimer.stop();
        clock.stop();
    }

    public void tick() {
        int enemy = 0;

        for (int i = 0; i < handler.object.size(); i++) {
            if (handler.object.get(i).getId() == ID.Priest) {
                camera.tick(handler.object.get(i));
            }
            if (handler.object.get(i).getId() == ID.Ghost) {
                enemy++;
            }
            if (handler.object.get(i).getId() == ID.Wraith) {
                enemy++;
            }
            if (handler.object.get(i).getId() == ID.Wisp) {
                enemy++;
            }
            if (handler.object.get(i).getId() == ID.BOSS) {
                enemy++;
            }
        }
        handler.tick();
        if (enemy <= 0 && gameState == GameState.GAME) {
            gameState = GameState.NEXT;
        }
        if (hp <= 0) {
            gameState = GameState.GAMEOVER;
        }
        if(time <= 0) {
            gameState = GameState.GAMEOVER;
        }
        if (enemy <= 0 && Level == 2) {
            gameState = GameState.VICTORY;
        }
        enemy = 0;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (gameState == GameState.START_MENU) {
            drawStartMenu(g);
        } else if (gameState == GameState.GAME) {
            drawGame(g);
        } else if (gameState == GameState.NEXT) {
            drawNextMenu(g);
        } else if (gameState == GameState.GAMEOVER) {
            drawGameover(g);
        } else if (gameState == GameState.VICTORY){
            drawVictory(g);
        }

        g.setColor(Color.WHITE);
        g.drawString("Time : " + time, 450, 20);
    }

    private void drawVictory(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1000, 563);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Georgia", Font.PLAIN, 50));
        g.drawString("You Won", 400, 180);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Main Menu", 460, 230);
        g.drawString("Exit", 480, 280);
    }

    private void drawGameover(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1000, 563);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Georgia", Font.PLAIN, 50));
        g.drawString("Current Level : "+(Level+1), 350, 180);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Retry", 480, 230);
        g.drawString("Main Menu", 460, 280);
        g.drawString("Exit", 480, 330);

    }

    private void drawNextMenu(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1000, 563);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Georgia", Font.PLAIN, 50));
        g.drawString("Next Level "+(Level+2), 350, 180);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Continue", 460, 230);
        g.drawString("Main Menu", 450, 280);

    }

    private void drawStartMenu(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1000, 563);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Georgia", Font.PLAIN, 50));
        g.drawString("The Priest", 400, 180);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Start Game", 450, 230);
        g.drawString("Exit", 480, 280);
    }

    private void drawGame(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.translate(-camera.getX(), -camera.getY());

        for (int xx = 0; xx < 30 * 72; xx += 32) {
            for (int yy = 0; yy < 30 * 72; yy += 32) {
                g.drawImage(floor, xx, yy, null);
            }
        }

        handler.render(g);

        g2d.translate(camera.getX(), camera.getY());

        g.setColor(Color.gray);
        g.fillRect(5, 5, 200, 32);
        g.setColor(Color.green);
        g.fillRect(5, 5, hp * 2, 32);
        g.setColor(Color.black);
        g.drawRect(5, 5, 200, 32);
        g.setColor(Color.black);
    }

    private void LoadLevel(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();

        for (int xx = 0; xx < w; xx++) {
            for (int yy = 0; yy < h; yy++) {
                int pixel = image.getRGB(xx, yy);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                if (red == 255)
                    handler.addObject(new Block(xx * 32, yy * 32, ID.Block, ss));

                if (blue == 255)
                    handler.addObject(new Priest(xx * 32, yy * 32, ID.Priest, handler, ss, this));

                if (green == 255)
                    handler.addObject(new Ghost(xx * 32, yy * 32, ID.Ghost, handler, ss));

                if (green == 155 && blue == 155)
                    handler.addObject(new Wraith(xx * 32, yy * 32, ID.Wraith, handler, ss));

                if (green == 155 && red == 155)
                    handler.addObject(new Wisp(xx * 32, yy * 32, ID.Wisp, handler, ss));

                if (green == 100 && red == 100 && blue == 100)
                    handler.addObject(new Holywater(xx * 32, yy * 32, ID.Holywater, handler, ss));

                if (green == 200 && red == 200)
                    handler.addObject(new BOSS(xx * 32, yy * 32, ID.BOSS, handler, ss));
            }
        }
    }

    private void resetlevel(BufferedImage image) {
        handler.object.clear();
        LoadLevel(image);
        hp = 100;
    }

    public static void main(String[] args) {
        new Game();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        if (gameState == GameState.START_MENU) {
            if (mouseX >= 450 && mouseX <= 550 && mouseY >= 200 && mouseY <= 250) {
                gameState = GameState.GAME;
                hp = 100;
                resetlevel(level[0]);
                start();
            }
            if (mouseX >= 450 && mouseX <= 550 && mouseY >= 260 && mouseY <= 300) {
                System.exit(0);
            }

        } else if (gameState == GameState.GAME) {
            repaint();

        } else if (gameState == GameState.NEXT) {
            if (mouseX >= 450 && mouseX <= 550 && mouseY >= 200 && mouseY <= 250) {
                Level++;
                if (Level <= 2) {
                    gameState = GameState.GAME;
                    System.out.print(Level);
                    resetlevel(level[Level]);
                    start();
                } else {
                    Level = 0;
                    gameState = GameState.START_MENU;
                }
            }
            if (mouseX >= 450 && mouseX <= 550 && mouseY >= 260 && mouseY <= 300){
                gameState = GameState.START_MENU;
            }

        } else if (gameState == GameState.GAMEOVER) {
            if (mouseX >= 450 && mouseX <= 550 && mouseY >= 200 && mouseY <= 250) {
                gameState = GameState.GAME;
                resetlevel(level[Level]);
                hp = 100;
                start();
                repaint();
            }
            if (mouseX >= 450 && mouseX <= 550 && mouseY >= 260 && mouseY <= 300) {
                time = 60;
                gameState = GameState.START_MENU;
                hp = 100;
                Level = 0;
                stop();
                repaint();
            }
            if (mouseX >= 450 && mouseX <= 550 && mouseY >= 310 && mouseY <= 350) {
                System.exit(0);
            }

        } else if (gameState == GameState.VICTORY) {
            if (mouseX >= 450 && mouseX <= 550 && mouseY >= 200 && mouseY <= 250) {
                gameState = GameState.START_MENU;
                time = 60;
                stop();
                hp = 100;
                Level = 0;
                repaint();
            }
            if (mouseX >= 450 && mouseX <= 550 && mouseY >= 260 && mouseY <= 300) {
                System.exit(0);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}
