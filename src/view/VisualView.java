package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import model.IAnimatorModelView;

/**
 * A class to represent a visual view form of the animator model. Draws the motions specified in the
 * model.
 */
public class VisualView extends JFrame implements IView {

  private AnimatorPanel panel;
  private int tick;
  private int ticksPerSecond;
  private Timer time;


  /**
   * Constructor for a VisualView, taking in a model and a ticks per second to run at.
   *
   * @param model          an AnimatorModel to translate to the View
   * @param ticksPerSecond the ticks per second for the animation to run at
   * @throws IllegalArgumentException if model is null or ticksPerSecond isn't positive
   */
  public VisualView(IAnimatorModelView model, int ticksPerSecond)
          throws IllegalArgumentException {
    super();
    if (model == null) {
      throw new IllegalArgumentException("model is a null!");
    }
    if (ticksPerSecond <= 0) {
      throw new IllegalArgumentException("rate must be positive");
    }

    this.tick = 0;
    this.ticksPerSecond = ticksPerSecond;

    this.setLayout(new BorderLayout());
    this.setTitle("Animation");
    this.setSize(model.getWidth(), model.getHeight());
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    panel = new AnimatorPanel(model, this.tick);
    panel.setPreferredSize(new Dimension(model.getWidth(), model.getHeight()));



    JScrollPane scrollBars = new JScrollPane(panel);
    this.add(scrollBars);
    this.add(scrollBars, BorderLayout.CENTER);

    pack();

    initializeTimer();
  }

  @Override
  public void draw() {
    this.time.start();
    this.setVisible(true);
  }

  // Initializes the time field to a Timer object that, at every tick, tells the panel what to draw,
  // draws it, and then increases the tick by one.
  private void initializeTimer() {
    ActionListener animate = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        panel.setFrames(tick);
        panel.repaint();
        tick++;
      }
    };
    time = new Timer(1000 / ticksPerSecond, animate);
  }

  @Override
  public void setListener(ActionListener listener) {
    // Set listener is not supported but is used in the controller.
  }

  @Override
  public String getEditCommand() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Timer getTimer() {
    throw new UnsupportedOperationException();
  }

  @Override
  public int getTicksPerSecond() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setTicksPerSecond(int tps) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setTick(int tick) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setLoop(boolean loop) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setStarted(boolean started) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean getStarted() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void errorMessage(String error) {
    throw new UnsupportedOperationException();
  }
}
