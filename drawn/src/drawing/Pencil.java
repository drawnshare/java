package drawing;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    public boolean cancelled;


    public Pencil(Canvas canvas, ColorPicker colorPicker, Slider pencilSlider) {
        this.canvas = canvas;
        this.colorPicker = colorPicker;
        this.pencilSlider = pencilSlider;
        gc = canvas.getGraphicsContext2D();
        canvas.setOnKeyPressed(keyHandler);

    }

    @Override
    public void Draw() {

        if (!cancelled) {
            canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    pencilSlider.valueProperty().addListener(new ChangeListener<Number>() {
                        public void changed(ObservableValue<? extends Number> ov,
                                            Number old_val, Number new_val) {
                            pencilSize = new_val.doubleValue();
                        }
                    });
                    gc.fillOval(mouseEvent.getX(), mouseEvent.getY(), pencilSize + 5, pencilSize + 5);
                    gc.setFill(colorPicker.getValue());
                    if (cancelled) {
                        mouseEvent.consume();
                        return;
                    }
                }

            });

            canvas.setOnMouseReleased(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent mouseEvent) {
                    mouseEvent.consume();
                    cancelled = true;
                    return;
                }
            });
        }
    }
   EventHandler<KeyEvent> keyHandler = new EventHandler<KeyEvent>() {

        @Override
        public void handle(KeyEvent key) {
            if (key.getCode().equals(KeyCode.ESCAPE)) {
                cancelled = true;
                key.consume();
            }
        }
    };

}