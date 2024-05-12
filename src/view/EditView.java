package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.IAnimatorModelView;

/**
 * A class to represent a visual view form of the animator model. Add functionality to start, stop,
 * and restart the animation. It also allows the user to type in commands that edit the existing
 * animation. These editing abilities include adding and removing shapes and motions and changing an
 * existing motion.
 */
public class EditView extends JFrame implements IView {

  private AnimatorPanel panel;
  private final IAnimatorModelView model;
  private int tick;
  private int ticksPerSecond;
  private Timer time;
  private boolean loop;
  private boolean started;
  private JButton pause;
  private JButton start;
  private JButton resume;
  private JButton restart;
  private JButton loopingOn;
  private JButton loopingOff;
  private JButton increaseSpeed;
  private JButton decreaseSpeed;
  private JTextField input;
  private JButton inputString;
  private JSlider slider;

  /**
   * Constructs an edit view when given a model and the speed at which it runs.
   *
   * @param model          the instance of the model it takes in and is able to modify
   * @param ticksPerSecond the speed at which the animation moves
   * @throws IllegalArgumentException if the model is a null or the speed is not positive
   */
  public EditView(IAnimatorModelView model, int ticksPerSecond) throws IllegalArgumentException {
    super();
    if (model == null) {
      throw new IllegalArgumentException("model is a null!");
    }
    if (ticksPerSecond <= 0) {
      throw new IllegalArgumentException("rate must be positive");
    }

    this.model = model;
    this.ticksPerSecond = ticksPerSecond;
    this.loop = false;
    this.started = false;
    this.tick = 0;

    this.setLayout(new BorderLayout());
    this.setTitle("Animation");
    this.setSize(model.getWidth(), model.getHeight());
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    panel = new AnimatorPanel(model, tick);
    panel.setPreferredSize(new Dimension(model.getWidth(), model.getHeight()));

    JScrollPane scrollBars = new JScrollPane(panel);
    this.add(scrollBars);
    this.add(scrollBars, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setPreferredSize(new Dimension(model.getWidth(), 150));
    buttonPanel.setLayout(new FlowLayout());

    JScrollPane buttonScroll = new JScrollPane(buttonPanel);
    this.add(buttonScroll, BorderLayout.SOUTH);

    slider = new JSlider(0, model.getLastMotionTick(), 0);
    buttonPanel.add(slider);

    input = new JTextField(30);
    buttonPanel.add(input);

    // Making buttons.
    inputString = new JButton("Input");
    start = new JButton("Start");
    pause = new JButton("Pause");
    resume = new JButton("Resume");
    restart = new JButton("Restart");
    loopingOn = new JButton("Loop On");
    loopingOff = new JButton("Loop Off");
    increaseSpeed = new JButton("Increase Speed");
    decreaseSpeed = new JButton("Decrease Speed");

    // Adding command actions to the buttons.
    inputString.setActionCommand("Input");
    pause.setActionCommand("Pause");
    start.setActionCommand("Start");
    resume.setActionCommand("Resume");
    restart.setActionCommand("Restart");
    loopingOn.setActionCommand("Loop On");
    loopingOff.setActionCommand("Loop Off");
    increaseSpeed.setActionCommand("Increase Speed");
    decreaseSpeed.setActionCommand("Decrease Speed");

    // Adding the buttons to the button panel.
    buttonPanel.add(inputString);
    buttonPanel.add(pause);
    buttonPanel.add(start);
    buttonPanel.add(resume);
    buttonPanel.add(restart);
    buttonPanel.add(loopingOn);
    buttonPanel.add(loopingOff);
    buttonPanel.add(increaseSpeed);
    buttonPanel.add(decreaseSpeed);

    slider.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        setTick(slider.getValue());
      }
    });

    pack();
    initializeTimer();
  }

  @Override
  public void draw() {
    this.setVisible(true);
  }

  // Initializes the time field to a Timer object that, at every tick, tells the panel what to draw,
  // draws it, and then increases the tick by one. Also allows for looping the animation.
  private void initializeTimer() {
    ActionListener animate = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        panel.setFrames(tick);
        panel.repaint();
        tick++;
        slider.setValue(tick);
        if ((model.getLastMotionTick() + 1 <= tick) && loop) {
          time.restart();
          tick = 0;
        }
      }
    };
    this.time = new Timer(1000 / ticksPerSecond, animate);
  }

  @Override
  public void setListener(ActionListener listener) {
    inputString.addActionListener(listener);
    pause.addActionListener(listener);
    start.addActionListener(listener);
    resume.addActionListener(listener);
    restart.addActionListener(listener);
    loopingOn.addActionListener(listener);
    loopingOff.addActionListener(listener);
    increaseSpeed.addActionListener(listener);
    decreaseSpeed.addActionListener(listener);
  }

  @Override
  public String getEditCommand() {
    String command = this.input.getText();
    this.input.setText("");
    return command;
  }

  @Override
  public Timer getTimer() {
    return this.time;
  }

  @Override
  public int getTicksPerSecond() {
    return this.ticksPerSecond;
  }

  @Override
  public void setTicksPerSecond(int tps) {
    if (tps <= 0) {
      throw new IllegalArgumentException("Ticks per second is not positive!");
    }
    this.ticksPerSecond = tps;
    this.time.setDelay(1000 / tps);
  }

  @Override
  public void setTick(int tick) {
    if (tick < 0) {
      throw new IllegalArgumentException("Tick is negative!");
    }
    this.tick = tick;
  }

  @Override
  public void setLoop(boolean loop) {
    this.loop = loop;
  }

  @Override
  public void setStarted(boolean started) {
    this.started = started;
  }

  @Override
  public boolean getStarted() {
    return this.started;
  }

  @Override
  public void errorMessage(String error) {
    JOptionPane.showMessageDialog(this, error, "Error!",
            JOptionPane.ERROR_MESSAGE);
  }
}
