package launcher;

import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {
    private static void main(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        ComponentFactory componentFactory = ComponentFactory.getInstance(false,primaryStage);
    }
}
