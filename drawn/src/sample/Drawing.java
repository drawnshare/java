package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by kokoghlanian on 17/04/2016.
 */
public class Drawing {

    private Slider slider;
    private ImageView imageView;
    private SepiaTone sepiaEffect = new SepiaTone();

    public Drawing(Slider slider, ImageView imageView){
        this.slider = slider;
        this.imageView = imageView;
    }

    public Drawing(ImageView imageView){
        this.imageView = imageView;
    }

    public void setOpacity(){
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                imageView.setOpacity(new_val.doubleValue()/50);
            }
        });
    }


    public void setSepia(){
        imageView.setEffect(sepiaEffect);
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                sepiaEffect.setLevel(new_val.doubleValue()/50);
            }
        });
    }


    public void setScallingLevel(){
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                imageView.setScaleX(new_val.doubleValue()/50);
                imageView.setScaleY(new_val.doubleValue()/50);
            }
        });
    }

    public void greyScaling(){

        PixelReader pr = imageView.getImage().getPixelReader();
        WritableImage myImage = (WritableImage) imageView.getImage();
        PixelWriter pw = myImage.getPixelWriter();
        for (int x = 0; x < imageView.getImage().getWidth(); x++) {
            for (int y = 0; y < imageView.getImage().getHeight(); y++) {
                Color c = pr.getColor(x, y);
                double avg = (c.getRed() + c.getGreen() + c.getBlue()) / 3.;
                Color grey = new Color(avg, avg, avg, c.getOpacity());
                pw.setColor(x, y, grey);
            }
        }
    }

    //Penser au CANVAS!!!!!!
    //zone geometrique.
    // GraphiqueContext gc = Canvas.getGraphicsContext2D(); (stylo)
    // gc.setFill(Color.blue);
    //gc.setStroke(Color.Red);
    //gc.StrokeLine(x1,y1,x2,y2);






}
