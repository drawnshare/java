package sample;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;


public final class FileChooserImage{

    private Button button;
    private Stage stage;
    private ImageView imageView;
    final FileChooser fileChooser = new FileChooser();


    public FileChooserImage(Stage stage, Button button, ImageView imageView){
        this.stage = stage;
        this.button = button;
        this.imageView = imageView;
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Images", "*.*"),new ExtensionFilter("JPG", "*.jpg"),new ExtensionFilter("PNG", "*.png"));
    }


    public ImageView openBrowser() throws MalformedURLException {

        File file = fileChooser.showOpenDialog(stage);
        if(file!=null){

            Image myImage = new Image(String.valueOf(file.toURI().toURL()));
            if (myImage != null) {
               return openFile(myImage);
            }
        }
        return null;
    }

    public void saveFile() {

        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                    ImageIO.write(SwingFXUtils.fromFXImage(imageView.getImage(), null), "png", file);
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    private ImageView openFile(Image image) {
        if(image!= null){
            imageView.setImage(image);
        }
        return imageView;
    }
}