import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import controller.Controller;
import model.AnimatorModelImpl;
import model.IAnimatorModel;
import util.AnimationBuilder;
import util.AnimationReader;
import view.IView;

/**
 * A class that used solely for the main function for the animator program.
 */
public class Excellence {

  /**
   * Main function for the animator program that gets arguments from the command line. These include
   * the file from which the animation model reads data, the view type that will be created, the
   * file name to output a view's contents to, and the speed of the animations.
   *
   * @param args the values from the command lines that will determine characteristics of the
   *             program
   */
  public static void main(String[] args) {
    JFrame frame = new JFrame();
    int tps = 1;
    String inString = "";
    String viewString = "";
    String outString = "out";
    boolean inArg = false;
    boolean viewArg = false;
    AnimationBuilder<IAnimatorModel> builder = AnimatorModelImpl.builder();

    for (int i = 0; i < args.length - 1; i++) {
      if (args[i].equalsIgnoreCase("-view")) {
        if (args[i + 1].equalsIgnoreCase("visual") ||
                args[i + 1].equalsIgnoreCase("svg") ||
                args[i + 1].equalsIgnoreCase("edit") ||
                args[i + 1].equalsIgnoreCase("text")) {
          viewString = args[i + 1].toLowerCase();
          viewArg = true;
        } else {
          JOptionPane.showMessageDialog(frame, "Invalid view arg!",
                  "Error", JOptionPane.ERROR_MESSAGE);
          System.exit(0);
        }
      }
      if (args[i].equalsIgnoreCase("-in")) {
        if (args[i + 1] == null) {
          JOptionPane.showMessageDialog(frame, "null in arg!",
                  "Error", JOptionPane.ERROR_MESSAGE);
          System.exit(0);
        } else {
          inString = args[i + 1];
          inArg = true;
        }
      }
      if (args[i].equalsIgnoreCase("-out")) {
        if (args[i + 1] == null ||
                args[i + 1].equalsIgnoreCase("-in") ||
                args[i + 1].equalsIgnoreCase("-out") ||
                args[i + 1].equalsIgnoreCase("-view") ||
                args[i + 1].equalsIgnoreCase("-speed")) {
          JOptionPane.showMessageDialog(frame, "Invalid out arg!",
                  "Error", JOptionPane.ERROR_MESSAGE);
          System.exit(0);
        } else {
          outString = args[i + 1];
        }
      }
      if (args[i].equalsIgnoreCase("-speed")) {
        try {
          tps = Integer.parseInt(args[i + 1]);
        } catch (NumberFormatException e) {
          JOptionPane.showMessageDialog(frame, "Invalid speed arg!",
                  "Error", JOptionPane.ERROR_MESSAGE);
          System.exit(0);
        }
      }
    }

    if (!inArg) {
      JOptionPane.showMessageDialog(frame, "No in argument!",
              "Error", JOptionPane.ERROR_MESSAGE);
      System.exit(0);
    }
    if (!viewArg) {
      JOptionPane.showMessageDialog(frame, "No view argument!",
              "Error", JOptionPane.ERROR_MESSAGE);
      System.exit(0);
    }
    IAnimatorModel model = null;
    AnimationReader reader = new AnimationReader();
    try {
      model = reader.parseFile(new FileReader(inString), builder);
    } catch (FileNotFoundException e) {
      JOptionPane.showMessageDialog(frame, "File not found!",
              "Error", JOptionPane.ERROR_MESSAGE);
      System.exit(0);
    }


    Appendable out = System.out;
    if (viewString.equalsIgnoreCase("visual") ||
            viewString.equalsIgnoreCase("edit")) {
      IView view = new FactoryViews(model, out, tps).construct(viewString);
      new Controller(model, view).start();
      return;
    }
    if (outString.equalsIgnoreCase("out")) {
      IView view = new FactoryViews(model, out, tps).construct(viewString);
      new Controller(model, view).start();
    } else {
      File fileOut = new File(outString);
      if (fileOut.exists()) {
        fileOut.delete();
      }
      try {
        if (fileOut.createNewFile()) {
          FileWriter fileWrite = new FileWriter(fileOut);
          IView view = new FactoryViews(model, fileWrite, tps).construct(viewString);
          fileWrite.flush();
          new Controller(model, view).start();
          fileWrite.close();
        } else {
          System.out.println("Cannot create new File!");
        }
      } catch (IOException e) {
        JOptionPane.showMessageDialog(frame, "Couldn't create new file",
                "Error", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
      }
    }
  }
}
