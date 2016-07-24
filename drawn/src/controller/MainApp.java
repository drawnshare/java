package controller;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import liaisonmodel.ShapeBeanContainer;
import sun.applet.Main;

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

        String fxmlFileString = "sample.fxml";

        Controller controller = new Controller();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileString));        //récuperer l'instance de mon controlleur afin d'avoir accès au objet tel que ImageView

        loader.setController(controller);
        Parent root = loader.load(MainApp.class.getResourceAsStream(fxmlFileString));

        Controller ctrl = loader.getController();
        //loader.setRoot(ctrl.getGetGridPaneForModule());



        Scene scene = new Scene(root, 1500, 850);
        primaryStage.setMaxHeight(1080);
        primaryStage.setMinHeight(680);
        primaryStage.setMinWidth(1000);
        primaryStage.setMaxWidth(1920);
        primaryStage.setTitle("Paint");
        primaryStage.setScene(scene);



        ShapeBeanContainer model = new ShapeBeanContainer();
        ctrl.setModel(model);

        primaryStage.show();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
     //permet de récuperer et d'afficher la position de la souris

    public static void main(String[] args) {
        launch(args);
    }
}
