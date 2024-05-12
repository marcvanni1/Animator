package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controller.commands.AddMotion;
import controller.commands.AddShape;
import controller.commands.ChangeMotion;
import controller.commands.RemoveMotion;
import controller.commands.RemoveShape;
import model.IAnimatorModel;
import view.IView;

/**
 * A class to represent a controller for the View of the animation.
 */
public class Controller implements ActionListener, IController {

  private final IAnimatorModel model;
  private final IView view;

  /**
   * Constructor for a Controller that takes in the Model and View to connect.
   *
   * @param model the model to read in data from
   * @param view  the view to output the information from the model to
   * @throws IllegalArgumentException if the model or view are null
   */
  public Controller(IAnimatorModel model, IView view) throws IllegalArgumentException {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Null was passed into controller!");
    }
    this.model = model;
    this.view = view;
  }

  @Override
  public void start() {
    view.setListener(this);
    view.draw();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Input":
        try {
          processCommand(view.getEditCommand());
        } catch (Exception exception) {
          view.errorMessage(exception.getMessage());
        }
        break;
      case "Start":
        if (!(view.getStarted())) {
          view.setStarted(true);
          view.getTimer().start();
        }
        break;
      case "Pause":
        view.getTimer().stop();
        break;
      case "Resume":
        if (view.getStarted()) {
          view.getTimer().start();
        }
        break;
      case "Restart":
        view.getTimer().restart();
        view.setTick(0);
        break;
      case "Loop On":
        view.setLoop(true);
        break;
      case "Loop Off":
        view.setLoop(false);
        break;
      case "Increase Speed":
        view.setTicksPerSecond(view.getTicksPerSecond() + 1);
        break;
      case "Decrease Speed":
        if (view.getTicksPerSecond() > 1) {
          view.setTicksPerSecond(view.getTicksPerSecond() - 1);
        }
        break;
      default:
        break;
    }
  }

  @Override
  public String processCommand(String command) throws IllegalArgumentException {
    if (command == null) {
      throw new IllegalArgumentException("command was null");
    }
    StringBuilder output = new StringBuilder();
    Scanner s = new Scanner(command);
    ShapeCommand cmd = null;

    while (s.hasNext()) {
      String in = s.next();
      String shape;
      List<Integer> fields = new ArrayList<Integer>();

      switch (in.toLowerCase()) {
        case "rshape":
          cmd = new RemoveShape(s.next());
          break;
        case "rmotion":
          shape = s.next();
          int index = s.nextInt();
          cmd = new RemoveMotion(shape, index);
          break;
        case "ashape":
          String name = s.next();
          String type = s.next();
          cmd = new AddShape(name + " " + type);
          break;
        case "amotion":
          shape = s.next();
          for (int i = 1; i <= 8; i++) {
            fields.add(s.nextInt());
          }
          cmd = new AddMotion(shape, fields);
          fields.clear();
          break;
        case "change":
          shape = s.next();
          for (int i = 1; i <= 8; i++) {
            fields.add(s.nextInt());
          }
          cmd = new ChangeMotion(shape, fields);
          break;
        default:
          output.append(String.format("Unknown command %s", in));
          cmd = null;
          break;
      }
      if (cmd != null) {
        cmd.execute(model);
        output.append("Successfully executed: " + command);
      }
    }
    return output.toString();
  }
}
