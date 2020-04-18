import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class Simulator {

    private Timeline timeline;
    private MainView mainView;
    private Cell cell;

    public Simulator(MainView mainView, Cell cell) {
        this.mainView = mainView;
        this.cell = cell;
        this.timeline =  new Timeline(new KeyFrame(Duration.millis(500), this::doStep));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void doStep(ActionEvent actionEvent) {
        this.cell.step();
        this.mainView.draw();
    }

    public void start() {
        this.timeline.play();
    }

    public void stop() {
        this.timeline.stop();
    }

}
