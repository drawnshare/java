package drawing;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;

/**
 * Created by kokoghlanian on 31/05/2016.
 */
public class Line implements Brush {

    private Canvas canvas;
    private ColorPicker colorPicker;
    public GraphicsContext gc;
    public double pencilSize;
    public Slider pencilSlider;
    public ToggleButton toggleButton;

    public double coordonneX;
    public double coordonneY;

    public Line(Canvas canvas, ColorPicker colorPicker, Slider pencilSlider, ToggleButton toggleButtonPinceau) {
        this.canvas = canvas;
        this.colorPicker = colorPicker;
        this.pencilSlider = pencilSlider;
        gc = canvas.getGraphicsContext2D();
        this.toggleButton = toggleButtonPinceau;
        this.coordonneX = 0.0;
        this.coordonneY = 0.0;
    }

    @Override
    public void Draw() {

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

                pencilSlider.valueProperty().addListener(new ChangeListener<Number>() {
                    public void changed(ObservableValue<? extends Number> ov,
                                        Number old_val, Number new_val) {
                            pencilSize = new_val.doubleValue();
                        }});

                if(coordonneX != 0.0 && coordonneY != 0.0) {
                    gc.setStroke(colorPicker.getValue());
                    gc.setLineWidth(pencilSize);
                    gc.setFill(colorPicker.getValue());
                    gc.fillOval(e.getX(), e.getY(), 1, 1);
                    gc.strokeLine(coordonneX, coordonneY, e.getX(), e.getY());
                    coordonneX = e.getX();
                    coordonneY = e.getY();
                }
                else
                {
                    coordonneX = e.getX();
                    coordonneY = e.getY();
                }

            }
        });
    }

}
