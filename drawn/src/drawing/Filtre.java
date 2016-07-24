package drawing;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.effect.*;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * Created by kokoghlanian on 17/04/2016.
 */
public class Filtre {

    private Slider slider;
    private ImageView imageView;
    private SepiaTone sepiaEffect = new SepiaTone();
    private Bloom bloom = new Bloom();
    private Canvas canvas;
    private double opacityValue;
    private double sepiaValue;
    public GraphicsContext gc;


    public Filtre(Slider slider, ImageView imageView) {
        this.slider = slider;
        this.imageView = imageView;
    }

    public Filtre(ImageView imageView) {
        this.imageView = imageView;
    }

    public Filtre(Canvas canvas, Slider slider) {
        this.canvas = canvas;
        this.slider = slider;
        gc = canvas.getGraphicsContext2D();

    }

    public void setOpacity() {
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                opacityValue = new_val.doubleValue();
                canvas.setOpacity(opacityValue / 50);

            }
        });
    }


    public void setSepia() {
        gc.setEffect(sepiaEffect);
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {

                sepiaValue = new_val.doubleValue();
                sepiaEffect.setLevel(sepiaValue / 50);
            }
        });
        gc.applyEffect(sepiaEffect);
    }

    public void setBloomEffect() {

        gc.setEffect(bloom);
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                bloom.setThreshold(new_val.doubleValue());
            }
        });
        gc.applyEffect(bloom);
    }

    public void setScallingLevel() {
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                canvas.setScaleX(new_val.doubleValue() / 50);
                canvas.setScaleY(new_val.doubleValue() / 50);
            }
        });
    }

    public void greyScaling() {

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

}
