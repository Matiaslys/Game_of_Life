import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Game extends Application {

    @Override
    public void start(Stage primaryStage){

        primaryStage.setTitle("Game of Life");
        MainView mainView = new MainView();
        Scene scene = new Scene(mainView, 640,480);
        primaryStage.setScene(scene);
        primaryStage.show();

        mainView.draw();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
