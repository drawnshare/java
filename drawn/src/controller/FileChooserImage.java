package controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;


public final class FileChooserImage{

    private Stage stage;
    private Canvas canvas;
    final FileChooser fileChooser = new FileChooser();


    public FileChooserImage(Stage stage, Canvas mainCanvas){
        this.stage = stage;
        this.canvas = mainCanvas;
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Images", "*.*"),new ExtensionFilter("JPG", "*.jpg"),new ExtensionFilter("PNG", "*.png"));
    }


    public Canvas openBrowser() throws MalformedURLException {

        File file = fileChooser.showOpenDialog(stage);
        if(file!=null){

            Image myImage = new Image(String.valueOf(file.toURI().toURL()),canvas.getWidth(),canvas.getHeight(),false,false);
            if (myImage != null) {
               return openFile(myImage);
            }
        }
        return null;
    }

    public void saveFile() {

        WritableImage wim = new WritableImage((int)canvas.getWidth(),(int)canvas.getHeight());
        canvas.snapshot(null,wim);
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                    ImageIO.write(SwingFXUtils.fromFXImage(wim, null), "png", file);
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    private Canvas openFile(Image image) {
        GraphicsContext gc;
        if(image!= null){
            gc = this.canvas.getGraphicsContext2D();
            if(gc!= null) {
                gc.drawImage(image,1,1,image.getWidth(),image.getHeight());
                gc.restore();
            }
        }
        return this.canvas;
    }
}