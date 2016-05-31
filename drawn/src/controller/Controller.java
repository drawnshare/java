package controller;


import drawing.Filtre;
import drawing.Line;
import drawing.Pencil;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
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
    private MenuItem NewItemMenu;

    @FXML
    private MenuItem SaveItemMenu;

    @FXML
    private MenuItem OpenItemMenu;

    @FXML
    private Canvas mainCanvas;

    @FXML
    private ColorPicker mainColorPicker;

    @FXML
    private Slider pencilScaleSlider;

    @FXML
    private ToggleButton toggleButtonPinceau;

    @FXML
    private ToggleButton toggleLineButton;




    @FXML
    private void openPicture() throws MalformedURLException {

        FileChooserImage imageChose = new FileChooserImage(MainApp.getPrimaryStage(),mainCanvas);
        this.mainCanvas = imageChose.openBrowser();



    }

    @FXML
    private void savePicture(){
        FileChooserImage imageChose = new FileChooserImage(MainApp.getPrimaryStage(),mainCanvas);
        imageChose.saveFile();
    }

    @FXML
    private void createNewImage() {
        WritableImage newImage = new WritableImage((int)mainImageView.getFitWidth(),(int)mainImageView.getFitHeight());
        mainImageView.setImage(newImage);
    }


    @FXML
    private void setSepia(){
        Filtre draw = new Filtre(sepiaSlider,mainImageView);
        draw.setSepia();

    }

    @FXML
    private void setOpacity(){
        Filtre draw = new Filtre(opacitySlider,mainImageView);
        draw.setOpacity();

    }

    @FXML
    private void setScalingLevel(){
        Filtre draw = new Filtre(scalingSlider,mainImageView);
        draw.setScallingLevel();

    }



    @FXML
    private void greyScale() {

        Filtre draw = new Filtre(mainImageView);
        draw.greyScaling();
    }

    @FXML
    private void pencilDraw()
    {
        Pencil pencil = new Pencil(mainCanvas,mainColorPicker,pencilScaleSlider,toggleButtonPinceau);
        pencil.Draw();
    }

    @FXML
    private void lineDraw()
    {
        Line Line = new Line(mainCanvas,mainColorPicker,pencilScaleSlider,toggleButtonPinceau);
        Line.Draw();
    }




    public ImageView getImageView(){
        return this.mainImageView;
    }

    public Canvas getCanvas(){
        return this.mainCanvas;
    }



}
