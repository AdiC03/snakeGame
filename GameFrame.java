import javax.swing.JFrame;

public class GameFrame extends JFrame{

//Constructor
GameFrame()
{
GamePanel panel = new GamePanel(); //Instantiates the GamePanel class
this.add(panel); //why do we put this?
this.setTitle("Snake");
this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
this.setResizable(false);
this.pack();
this.setVisible(true);
this.setLocationRelativeTo(null);


}

}