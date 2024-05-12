import model.IAnimatorModel;
import view.EditView;
import view.IView;
import view.SvgView;
import view.TextualView;
import view.VisualView;

/**
 * A class used solely as a factory for types of view to create.
 */
public class FactoryViews {

  private final IAnimatorModel model;
  private final Appendable output;
  private final int ticksPerSecond;

  /**
   * Constructs a FactoryViews Object.
   *
   * @param model          is the model passed in to pass into the created view
   * @param output         the name of the file the animation will output to if needed by the view
   * @param ticksPerSecond the speed of the animation if needed by the view
   * @throws IllegalArgumentException if the model or outputName are null
   */
  public FactoryViews(IAnimatorModel model, Appendable output, int ticksPerSecond)
          throws IllegalArgumentException {
    if (model == null || output == null) {
      throw new IllegalArgumentException("Null field!");
    }
    this.model = model;
    this.output = output;
    this.ticksPerSecond = ticksPerSecond;
  }

  /**
   * Used to construct a new view based on the passed in viewType.
   *
   * @param viewType the type of view that the factory will create. The three options are "text",
   *                 "svg", and "visual"
   * @return the IView associated with viewType
   * @throws IllegalArgumentException if the viewType isn't supported by the factory
   */
  public IView construct(String viewType) throws IllegalArgumentException {
    switch (viewType.toLowerCase()) {
      case "text":
        return new TextualView(this.model, this.output);
      case "svg":
        return new SvgView(this.model, this.output, this.ticksPerSecond);
      case "visual":
        return new VisualView(this.model, this.ticksPerSecond);
      case "edit":
        return new EditView(this.model, this.ticksPerSecond);
      default:
        throw new IllegalArgumentException("Unsupported view type!");
    }
  }
}
