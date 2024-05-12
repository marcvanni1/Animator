package view;

import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * This interface represents the methods offered by a view. It allows the view to make a file, draw
 * to the system, or override toString for an output.
 */
public interface IView {

  /**
   * Draws the view or makes the view visible.
   */
  public void draw();

  /**
   * Sets an ActionListener for the View to use.
   *
   * @param listener the ActionListener to be set
   */
  public void setListener(ActionListener listener);

  public String getEditCommand();

  /**
   * Gets the View's timer object.
   *
   * @return the View's timer object
   */
  public Timer getTimer();

  /**
   * Gets the ticks per second the View is currently set to run at.
   *
   * @return the current ticks per second
   */
  public int getTicksPerSecond();

  /**
   * Sets a new ticks per second for the View to run at.
   *
   * @param tps the new ticks per second
   */
  public void setTicksPerSecond(int tps);

  /**
   * Sets the current tick for the View, usually back to 0 for a restart.
   *
   * @param tick the tick to set the View's tick value to
   */
  public void setTick(int tick);

  /**
   * Set whether or not the View's animation will loop. True if it does.
   *
   * @param loop will the View's animation loop?
   */
  public void setLoop(boolean loop);

  /**
   * Set whether or not the View's animation has started, in order to differentiate between a start
   * and a resume from a pause.
   *
   * @param started set whether or not the View's animation has started
   */
  public void setStarted(boolean started);

  /**
   * Get whether or not the View's animation has started.
   *
   * @return has the View's animation started?
   */
  public boolean getStarted();

  public void errorMessage(String error);
}
