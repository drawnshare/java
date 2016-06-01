package drawing;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;

/**
 * Created by kokoghlanian
 */
public class Pencil implements Brush {

    private Canvas canvas;
    private ColorPicker colorPicker;
    public GraphicsContext gc;
    public double pencilSize;
    public Slider pencilSlider;
    public ToggleButton toggleButton;

    public Pencil(Canvas canvas, ColorPicker colorPicker, Slider pencilSlider, ToggleButton toggleButtonPinceau) {
        this.canvas = canvas;
        this.colorPicker = colorPicker;
        this.pencilSlider = pencilSlider;
        gc = canvas.getGraphicsContext2D();
        this.toggleButton = toggleButtonPinceau;
    }

    @Override
    public void Draw() {

            canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    pencilSlider.valueProperty().addListener(new ChangeListener<Number>() {
                        public void changed(ObservableValue<? extends Number> ov,
                                            Number old_val, Number new_val) {
                            pencilSize = new_val.doubleValue();
                        }
                    });

                    gc.fillOval(e.getX(), e.getY(), pencilSize + 5, pencilSize + 5);
                    gc.setFill(colorPicker.getValue());
                }
            });

    }
}