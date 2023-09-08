import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener
{
//All instance variables
static final int SCREEN_WIDTH = 600;
static final int SCREEN_HEIGHT = 600;
static final int UNIT_SIZE = 25; //How big we want objects in game (ex. apple)
static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
static final int DELAY = 75;
final int x [] = new int [GAME_UNITS];
final int y [] = new int [GAME_UNITS];
int bodyParts = 6; //length of snake
int applesEaten;
int appleX; //x-coordinate of apple
int appleY; //y-coordinate of apple
char direction = 'R'; //inital direction of snake
boolean running = false;
Timer timer;
Random random;


//Constructor
GamePanel()
{
random = new Random();
this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
this.setBackground(Color.black);
this.setFocusable(true);
this.addKeyListener(new MyKeyAdapter());
startGame();
}

public void startGame()
{
newApple();
running = true;
timer = new Timer(DELAY, this);
timer.start();
}

public void paintComponent(Graphics g)
{
super.paintComponent(g);
draw(g);

}

public void draw(Graphics g)
{
if (running)
{
//Drawing a grid (fix later)
for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
}
g.setColor(Color.red);
g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

//snake
for (int i = 0; i < bodyParts; i++)
{
if (i == 0) //head of snake
{
g.setColor(Color.green);
g.fillOval(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
}
else
{
g.setColor(Color.blue);
g.fillOval(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
}

}
//Score
g.setColor(Color.red);
g.setFont( new Font("Ink Free",Font.BOLD, 40));
FontMetrics metrics = getFontMetrics(g.getFont());
g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());

}

else
{
gameOver(g);
}

}

//Moving snake
public void move()
{
for(int i = bodyParts; i>0; i--)
{
x[i] = x[i-1];
y[i] = y[i-1];
}

//TODO: What exactly does switch do?
switch(direction)
{
case 'U':
y[0] = y[0] - UNIT_SIZE; //y[0] and x[0] are head of snake
break;
case 'D':
y[0] = y[0] + UNIT_SIZE;
break;
case 'L':
x[0] = x[0] - UNIT_SIZE;
break;
case 'R':
x[0] = x[0] + UNIT_SIZE;
break;
}

}

//New apple
public void newApple()
{
appleX = random.nextInt((int) (SCREEN_WIDTH/UNIT_SIZE)) * UNIT_SIZE;
appleY = random.nextInt((int) (SCREEN_HEIGHT/UNIT_SIZE)) * UNIT_SIZE;


}

//Checking score
public void checkApple()
{
if ((x[0] == appleX) && (y[0] == appleY))
{
bodyParts++;
applesEaten++;
newApple();
}

}


//Check collisions
public void checkCollisions()
{
//Snake running into itself
for (int i = bodyParts; i > 0; i--)
{
if ((x[0] == x[i]) && (y[0] == y[i]))
{
running = false;
}
}
//touching left border
if (x[0] < 0)
{
running = false;
}
//touching right border
if (x[0] > SCREEN_WIDTH)
{
running = false;
}
//touching top border
if (y[0] < 0)
{
running = false;
}
//touching bottom border
{
if (y[0] > SCREEN_HEIGHT)
{
running = false;
}
}

if (!running)
{
timer.stop();
}
}

public void gameOver(Graphics g)
{
//Score after gameOver
g.setColor(Color.red);
g.setFont( new Font("Ink Free",Font.BOLD, 40));
FontMetrics metrics = getFontMetrics(g.getFont());
g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
//Game over text
g.setColor(Color.red);
g.setFont(new Font(("Ink Free"), Font.BOLD, 75));
FontMetrics metrics2 = getFontMetrics(g.getFont()); //This is displayed in center
g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
}
@Override
public void actionPerformed(ActionEvent e) {

if(running)
{
move();
checkApple();
checkCollisions();
}
repaint(); //TODO: What's this?
}

public class MyKeyAdapter extends KeyAdapter
{
@Override
public void keyPressed(KeyEvent e)
{
switch(e.getKeyCode())
{
case KeyEvent.VK_LEFT: //left key
if(direction != 'R') //this limits the user to only 90 degree turns
{
direction = 'L';
}
break;
case KeyEvent.VK_RIGHT:
if(direction != 'L')
{
direction = 'R';
}
break;
case KeyEvent.VK_UP:
if(direction != 'D')
{
direction = 'U';
}
break;
case KeyEvent.VK_DOWN:
if(direction != 'U')
{
direction = 'D';
}
break;
}

}
}

}
