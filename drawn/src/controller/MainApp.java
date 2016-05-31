package controller;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private static Stage primaryStage;
    public GraphicsContext gc;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;

        initStage();
    }

    public void initStage() throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        Scene scene = new Scene(root, 1500, 850);
        primaryStage.setMaxHeight(1000);
        primaryStage.setMinHeight(680);
        primaryStage.setMinWidth(1000);
        primaryStage.setMaxWidth(1250);
        primaryStage.setTitle("Paint");
        primaryStage.setScene(scene);

        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "sample.fxml"));        //récuperer l'instance de mon controlleur afin d'avoir accès au objet tel que ImageView
        Parent myparent = (Parent) loader.load();
        Controller ctrl = loader.getController();
        //ctrl.getImageView().setOnMouseMoved(mouseHandler); //ne recupere pas la position de la souris sur l'imageView à creuser



        //ImageView imageview = ctrl.getImageView();


        //scene.setOnMouseMoved(mouseHandler); //recupere la position de la souris sur toute la scene
        primaryStage.show();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
     //permet de récuperer et d'afficher la position de la souris
    EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent mouseEvent) {
            System.out.println(mouseEvent.getEventType() + "\n"
                    + "X : Y - " + mouseEvent.getX() + " : " + mouseEvent.getY() + "\n"
                    + "SceneX : SceneY - " + mouseEvent.getSceneX() + " : " + mouseEvent.getSceneY() + "\n"
                    + "ScreenX : ScreenY - " + mouseEvent.getScreenX() + " : " + mouseEvent.getScreenY());

        }
    };

    public static void main(String[] args) {
        launch(args);
    }
}
