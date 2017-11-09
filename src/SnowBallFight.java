
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;

public class SnowBallFight
  extends MIDlet
{
  public void startApp()
  {
    Display localDisplay = Display.getDisplay(this);
    localDisplay.setCurrent(new GameScreen(this));
  }
  
  public void destroyApp(boolean paramBoolean) {}
  
  public void pauseApp() {}
}
