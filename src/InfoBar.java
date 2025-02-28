import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class InfoBar extends HBox {

    private static String drawModeFormat ="Draw Mode: %s";
    private static String cursorPosFormat ="Cursor: (%d,%d)";

    private Label cursor;
    private Label editingTool;

    public InfoBar() {

        this.cursor = new Label();
        this.editingTool = new Label();

        Pane spacer = new Pane();
        spacer.setMinSize(0,0);
        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        HBox.setHgrow(spacer, Priority.ALWAYS);

        this.getChildren().addAll( this.editingTool, spacer, this.cursor);

    }

    public void setDrawMode(int drawMode) {
        String drawModeString;
        if (drawMode == Cell.ALIVE) {
            drawModeString = "Drawing";
        } else {
            drawModeString = "Erasing";
        }
        this.editingTool.setText(String.format(drawModeFormat, drawModeString));
    }

    public void setCursorPos(int x, int y) {
        this.cursor.setText(String.format(cursorPosFormat, x, y));
    }

}
