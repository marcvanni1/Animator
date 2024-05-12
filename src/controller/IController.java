package controller;

/**
 * This interface represents the capabilities of the Controller. It can start a program with a view
 * and a model and process commands from the view that affect the model.
 */
public interface IController {

  /**
   * Run the view given the model.
   */
  public void start();

  /**
   * Process a given string command and return status or error as a string.
   *
   * @param command the command given, including any parameters (e.g. "move 3")
   * @return status or error message
   * @throws IllegalArgumentException if the command string is null
   */
  String processCommand(String command) throws IllegalArgumentException;
}
