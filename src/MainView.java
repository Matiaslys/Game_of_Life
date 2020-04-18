import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class MainView extends VBox {

    public static final int EDITING = 0;
    public static final int SIMULATING = 1;

    private InfoBar infoBar;
    private Canvas canvas;
    private Affine affine;

    private Cell cell;
    private Cell initialCell;

    private Simulator simulator;

    private int drawMode = Cell.ALIVE;

    private int applicationState = EDITING;

    public MainView() {

        this.canvas = new Canvas(400, 400);
        this.canvas.setOnMousePressed(this::handleDraw);
        this.canvas.setOnMouseDragged(this::handleDraw);
        this.canvas.setOnMouseMoved(this::handleMoved);

        this.setOnKeyPressed(this::onKeyPressed);

        Toolbar toolbar = new Toolbar(this);

        this.infoBar = new InfoBar();
        this.infoBar.setDrawMode(this.drawMode);
        this.infoBar.setCursorPos(0,0);

        Pane spacer = new Pane();
        spacer.setMinSize(0,0);
        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        VBox.setVgrow(spacer, Priority.ALWAYS);

        this.getChildren().addAll( toolbar, this.canvas, spacer, infoBar);

        this.affine = new Affine();
        this.affine.appendScale(400 / 10f, 400 / 10f);

        this.initialCell = new Cell(10,10);
        this.cell = Cell.copy(this.initialCell);
    }

    private void handleMoved(MouseEvent event) {
        Point2D simCoord = this.getCellCoordinates(event);

        this.infoBar.setCursorPos((int) simCoord.getX(), (int) simCoord.getY());

    }

    private void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.D){
            this.drawMode = Cell.ALIVE;
        }else if (keyEvent.getCode() == KeyCode.E){
            this.drawMode = Cell.DEAD;
        }
    }

    private void handleDraw(MouseEvent event) {

        if (this.applicationState == SIMULATING) {
            return;
        }

        Point2D simCoord = this.getCellCoordinates(event);

        int simX = (int) simCoord.getX();
        int simY = (int) simCoord.getY();

        this.initialCell.setState(simX,simY,drawMode);
        draw();
    }

    private Point2D getCellCoordinates(MouseEvent event) {
        double mouseX = event.getX();
        double mouseY = event.getY();


        try {
            Point2D simCoord = this.affine.inverseTransform(mouseX, mouseY);
            return simCoord;
        } catch (NonInvertibleTransformException e) {
            System.out.println("Could not invert transform");
            throw new RuntimeException("Non invertible transform");
        }
    }

    public void draw() {
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        graphics.setTransform(affine);

        graphics.setFill(Color.LIGHTGRAY);
        graphics.fillRect(0,0,400,400);

        if (this.applicationState == EDITING) {
            drawCell(this.initialCell);
        } else {
            drawCell(this.cell);
        }

        graphics.setStroke(Color.GRAY);
        graphics.setLineWidth(0.05f);
        for (int x = 0; x <= this.cell.width; x++) {
            graphics.strokeLine(x,0,x,this.cell.height);
        }

        for (int y = 0; y <= this.cell.height; y++) {
            graphics.strokeLine(0,y,this.cell.width,y);

        }

    }

    private void drawCell(Cell cellToDraw) {
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        graphics.setFill(Color.BLACK);
        for (int x = 0; x < cellToDraw.height; x++) {
            for (int y = 0; y < cellToDraw.width; y++) {
                if (cellToDraw.getState(x,y) == Cell.ALIVE) {
                    graphics.fillRect(x,y,1,1);
                }
            }
        }
    }

    public Cell getCell() {
        return this.cell;
    }

    public void setDrawMode(int newDrawMode) {
        this.drawMode = newDrawMode;
        this.infoBar.setDrawMode(newDrawMode);
    }

    public void setApplicationState(int applicationState) {
        if (applicationState == this.applicationState) {
            return;
        }

        if (applicationState == SIMULATING) {
            this.cell = Cell.copy(this.initialCell);
            this.simulator = new Simulator(this,this.cell);
        }

        this.applicationState = applicationState;
    }

    public Simulator getSimulator() {
        return simulator;
    }
}
