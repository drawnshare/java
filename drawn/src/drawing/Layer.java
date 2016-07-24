package drawing;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * Created by kokoghlanian on 31/05/2016.
 */
public class Layer implements Brush {

    private Canvas canvas;
    private ColorPicker colorPicker;
    public GraphicsContext gc;
    public double pencilSize;
    public boolean cancelled;


    public Layer(Canvas canvas, ColorPicker colorPicker, double pencilSize){
        this.canvas = canvas;
        this.colorPicker = colorPicker;
        this.pencilSize = pencilSize;
        gc = canvas.getGraphicsContext2D();

    }


    public void Draw() {

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println(mouseEvent.getEventType() + "\n"
                        + "X : Y - " + mouseEvent.getX() + " : " + mouseEvent.getY() + "\n"
                        + "SceneX : SceneY - " + mouseEvent.getSceneX() + " : " + mouseEvent.getSceneY() + "\n"
                        + "ScreenX : ScreenY - " + mouseEvent.getScreenX() + " : " + mouseEvent.getScreenY());
                gc.strokeOval(mouseEvent.getX(), mouseEvent.getY(), pencilSize, pencilSize);
                gc.setStroke(colorPicker.getValue());
                if (cancelled)
                {
                    mouseEvent.consume();
                    return;
                }
            }
        });

        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>(){

                @Override
                public void handle(MouseEvent mouseEvent) {
                    cancelled = true;
                    return;
                }

        });

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

