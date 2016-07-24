package controller;


import KeyEventCanvas.KeyEventOnCanvas;
import com.esgi.ModuleAnnotations.model.InitView;
import com.esgi.ModuleAnnotations.model.PluginInfo;
import com.esgi.ModuleAnnotations.model.PluginView;
import drawing.Brush;
import drawing.Filtre;
import drawing.Line;
import drawing.Pencil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import liaisonmodel.ShapeBean;
import liaisonmodel.ShapeBeanContainer;
import model.Ellipse;
import model.Rectangle;
import model.Segment;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;


@PluginView(viewType = PluginView.ViewType.CENTER)
public class Controller {

    @FXML
    private GridPane mainGridPane;

    @FXML
    private Button openFileButton;

    @FXML
    private Button saveFileButton;

    @FXML
    private Button greyButton;

    @FXML
    private Slider opacitySlider;

    @FXML
    private Slider sepiaSlider;

    @FXML
    private Slider scalingSlider;

    @FXML
    private MenuItem NewItemMenu;

    @FXML
    private MenuItem SaveItemMenu;

    @FXML
    private MenuItem OpenItemMenu;

    @FXML
    private Canvas mainCanvas;

    @FXML
    private ColorPicker mainColorPicker;

    @FXML
    private Slider pencilScaleSlider;

    @FXML
    private ToggleButton toggleButtonPinceau;

    @FXML
    private ToggleButton toggleLineButton;

    @FXML
    private ToggleGroup LayerChooser;

    @FXML
    private ImageView mainImageView;



    @InitView
    public Pane getGetGridPaneForModule(){return this.mainGridPane;}

    private List<WritableImage> getSnapShotList(){return  snapShotList;}

    public ImageView getImageView() {return this.mainImageView;}

    public Canvas getCanvas() {
        return this.mainCanvas;
    }


    @FXML
    private void openPicture() throws MalformedURLException {

        FileChooserImage imageChose = new FileChooserImage(MainApp.getPrimaryStage(), mainCanvas);
        this.mainCanvas = imageChose.openBrowser();


    }

    @FXML
    private void savePicture() {
        FileChooserImage imageChose = new FileChooserImage(MainApp.getPrimaryStage(), mainCanvas);
        imageChose.saveFile();
    }

    @FXML
    private void createNewImage() {
            GraphicsContext gc;
            gc = this.mainCanvas.getGraphicsContext2D();
            if(gc!= null) {
                gc.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
                gc.restore();
            }

    }

    @FXML
    private void setSepia() {
        Filtre draw = new Filtre(mainCanvas, sepiaSlider);
        draw.setSepia();

    }

    @FXML
    private void setOpacity() {
        Filtre draw = new Filtre(opacitySlider, mainImageView);
        draw.setOpacity();

    }

    @FXML
    private void setScalingLevel() {
        Filtre draw = new Filtre(mainCanvas, opacitySlider);
        draw.setBloomEffect();

    }


    @FXML
    private void greyScale() {

        Filtre draw = new Filtre(mainImageView);
        draw.greyScaling();
    }

    @FXML
    private void drawing() {

        Line line = new Line(mainCanvas, mainColorPicker, pencilScaleSlider);
        Pencil pencil = new Pencil(mainCanvas, mainColorPicker, pencilScaleSlider);
        toggleLineButton.setUserData(line);
        toggleButtonPinceau.setUserData(pencil);
        LayerChooser.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle toggle, Toggle new_toggle) {
                Brush brush;
                if (new_toggle == null)
                    return;
                else {
                    brush = (Brush) LayerChooser.getSelectedToggle().getUserData();
                    if (brush instanceof Brush)
                        brush.Draw();
                }
                brush = null;
                return;
            }
        });
    }

    @FXML
    Pane canvas;

    @FXML
    ToggleGroup shapeChooser;

    /**
     * Controller For Key Controle
     */
    List<WritableImage> snapShotList = new ArrayList<WritableImage>();
    KeyEventOnCanvas keyController = new KeyEventOnCanvas(mainCanvas);

    /**************************************************************Shape Chooser*************************************************************/
    /**
     * Controller for moving shapes around. It subscribes to each shape
     */
    SelectionController selectionController = new SelectionController();

    /**
     * Controller to create new shapes. Subscribes to the canvas
     */
    ShapeDrawingController shapeDrawingController;

    /**
     * Read the model and bind it to the canvas children (shape) list
     */
    void setModel(final ShapeBeanContainer model) {

        shapeDrawingController = new ShapeDrawingController(model, canvas, shapeChooser);

        // read the current shapes and add them to the view
        for (ShapeBean sb : model.getShapeBeans()) {
            canvas.getChildren().add(makeNode(sb));
        }

        // keep the shape list in synch between model and view
        model.getShapeBeans().addListener(new ListChangeListener<ShapeBean>() {
            @Override
            public void onChanged(Change<? extends ShapeBean> chg) {
                while (chg.next()) {
                    if (chg.wasAdded()) {
                        canvas.getChildren().add(chg.getFrom(), makeNode(model.getShapeBeans().get(chg.getFrom())));
                    }
                    if (chg.wasRemoved()) {
                        canvas.getChildren().remove(chg.getFrom());
                    }
                }
            }
        });

    }

    ModelViewBinding mvBinder = new ModelViewBinding();

    /**
     * create a UI shape out of a model shape. The two objects (beans) will be bound to each other
     *
     * @param shapeBean
     * @return
     */
    protected Node makeNode(final ShapeBean shapeBean) {
        Shape ret = null;
        if (shapeBean.getShape() instanceof Segment) {
            javafx.scene.shape.Line line = new javafx.scene.shape.Line();
            line.layoutXProperty().bindBidirectional(shapeBean.xProperty());
            line.layoutYProperty().bindBidirectional(shapeBean.yProperty());
            line.endXProperty().bindBidirectional(shapeBean.wProperty());
            line.endYProperty().bindBidirectional(shapeBean.hProperty());

            ret = line;
        }
        if (shapeBean.getShape() instanceof Rectangle) {
            javafx.scene.shape.Rectangle rect = new javafx.scene.shape.Rectangle();
            rect.layoutXProperty().bindBidirectional(shapeBean.xProperty());
            rect.layoutYProperty().bindBidirectional(shapeBean.yProperty());
            rect.widthProperty().bindBidirectional(shapeBean.wProperty());
            rect.heightProperty().bindBidirectional(shapeBean.hProperty());

            ret = rect;
        } else if (shapeBean.getShape() instanceof Ellipse) {
            javafx.scene.shape.Ellipse ellipse = new javafx.scene.shape.Ellipse();

            // set the math relations between ellipse model and view using a ModelViewBinding
            // centerX=x+w/2
            mvBinder.bind(ellipse.layoutXProperty(), shapeBean.xProperty().add(shapeBean.wProperty().divide(2)));
            mvBinder.bind(ellipse.layoutYProperty(), shapeBean.yProperty().add(shapeBean.hProperty().divide(2)));

            // x=centerX-radiusX
            mvBinder.bind(shapeBean.xProperty(), ellipse.layoutXProperty().subtract(ellipse.radiusXProperty()));
            mvBinder.bind(shapeBean.yProperty(), ellipse.layoutYProperty().subtract(ellipse.radiusYProperty()));

            // radiusX= w/2
            mvBinder.bind(ellipse.radiusXProperty(), shapeBean.wProperty().divide(2));
            mvBinder.bind(ellipse.radiusYProperty(), shapeBean.hProperty().divide(2));

            // w= radius*2
            mvBinder.bind(shapeBean.wProperty(), ellipse.radiusXProperty().multiply(2));
            mvBinder.bind(shapeBean.hProperty(), ellipse.radiusYProperty().multiply(2));

            ret = ellipse;
        }

        selectionController.addTo(ret);

        return ret;
    }
}
