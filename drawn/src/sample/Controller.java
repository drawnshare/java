package sample;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

import java.net.MalformedURLException;


public class Controller {


    @FXML
    private Button openFileButton;

    @FXML
    private Button saveFileButton;

    @FXML
    private Button greyButton;

    @FXML
    private ImageView mainImageView;

    @FXML
    private Slider opacitySlider;

    @FXML
    private Slider sepiaSlider;

    @FXML
    private Slider scalingSlider;





    @FXML
    private void openPicture() throws MalformedURLException {

        FileChooserImage imageChose = new FileChooserImage(MainApp.getPrimaryStage(),openFileButton,mainImageView);
        this.mainImageView = imageChose.openBrowser();

    }

    @FXML
    private void savePicture(){
        FileChooserImage imageChose = new FileChooserImage(MainApp.getPrimaryStage(),saveFileButton,mainImageView);
        imageChose.saveFile();
    }

    @FXML
    private void setSepia(){
        Drawing draw = new Drawing(sepiaSlider,mainImageView);
        draw.setSepia();

    }

    @FXML
    private void setOpacity(){
        Drawing draw = new Drawing(opacitySlider,mainImageView);
        draw.setOpacity();

    }

    @FXML
    private void setScalingLevel(){
        Drawing draw = new Drawing(scalingSlider,mainImageView);
        draw.setScallingLevel();

    }

    @FXML
    private void createNewImage() {
        WritableImage newImage = new WritableImage((int)mainImageView.getFitWidth(),(int)mainImageView.getFitHeight());
        mainImageView.setImage(newImage);
    }

    @FXML
    private void greyScale() {

        Drawing draw = new Drawing(mainImageView);
        draw.greyScaling();
    }

    public ImageView getImageView(){
        return this.mainImageView;
    }



}
