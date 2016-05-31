package drawing;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;

/**
 * Created by kokoghlanian on 31/05/2016.
 */
public class Pencil implements Brush {

    private Canvas canvas;
    private ColorPicker colorPicker;
    public GraphicsContext gc;
    public double pencilSize;


    public Pencil(Canvas canvas, ColorPicker colorPicker, double pencilSize){
        this.canvas = canvas;
        this.colorPicker = colorPicker;
        this.pencilSize = pencilSize;
        gc = canvas.getGraphicsContext2D();
    }

    @Override
    public void Draw() {

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                gc.fillOval(e.getX(), e.getY(), pencilSize, pencilSize);
                gc.setFill(colorPicker.getValue());
            }
        });
    }
}
