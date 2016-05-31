package drawing;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;

/**
 * Created by kokoghlanian on 31/05/2016.
 */
public class Line implements Brush {

    private Canvas canvas;
    private ColorPicker colorPicker;
    public GraphicsContext gc;
    public double pencilSize;

    public double coordonneX;
    public double coordonneY;


    public Line(Canvas canvas, ColorPicker colorPicker, double pencilSize){
        this.canvas = canvas;
        this.colorPicker = colorPicker;
        this.pencilSize = pencilSize;
        this.gc = canvas.getGraphicsContext2D();
        this.coordonneX = 0.0;
        this.coordonneY = 0.0;
    }

    @Override
    public void Draw() {

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

                if(coordonneX != 0.0 && coordonneY != 0.0) {
                    gc.fillOval(e.getX(), e.getY(), pencilSize, pencilSize);
                    gc.setFill(colorPicker.getValue());
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
