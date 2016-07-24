package KeyEventCanvas;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Created by kokoghlanian on 24/07/2016.
 */
public class KeyEventOnCanvas {

    private Canvas canvas;

    public KeyEventOnCanvas(Canvas canvas) {
        this.canvas = canvas;

        if (canvas != null) {
            canvas.setOnKeyPressed(new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent key) {
                    if (key.getCode().equals(KeyCode.CONTROL) && key.getCode().equals(KeyCode.Z)) {


                    }
                }

            });
        }
    }

}
