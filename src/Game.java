import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class Game extends Application {
    private static int width = 15, height = 15;
    private Cell[][] world = new Cell[width][height];
    private Affine affine;
    private Canvas canvas;
    Cell cell = new Cell();
    Button button = new Button("Play Again");

    private Game() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Random randomGenerator = new Random();
                int r = randomGenerator.nextInt(2);

                if (r == 0) {
                    cell.setAlive(false);
                } else {
                    cell.setAlive(true);
                }
                world[i][j] = cell;

                System.out.println(r);
            }
        }
    }
    public static Game intialize() {
        return new Game();
    }
    @Override
    public void start(Stage primaryStage){
        VBox root = new VBox();
        primaryStage.setTitle("Game of Life");
        button.setOnAction(actionEvent -> {
            update();
        });

        canvas = new Canvas(680, 480);
        Scene scene = new Scene(root, 680,520);
        root.getChildren().addAll(canvas, button);

        affine = new Affine();
        affine.appendScale(680 / 15f, 480 / 15f);


        primaryStage.setScene(scene);
        primaryStage.show();
        Game World = Game.intialize();
        draw();

    }


    public void draw() {

        GraphicsContext graphics = canvas.getGraphicsContext2D();
        graphics.setTransform(affine);

        graphics.setFill(Color.LIGHTGRAY);
        graphics.fillRect(0,0,400,400);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
//                Random randomGenerator = new Random();
//                int r = randomGenerator.nextInt(2);
//
//                if (r == 0) {
//                    cell.setAlive(false);
//                } else {
//                    cell.setAlive(true);
//                }
//                world[i][j] = cell;
                if (world[i][j].alive){
                    graphics.setFill(Color.BLACK);
                    graphics.fillRect(i,j,1,1);
                }
                if (!world[i][j].alive){
                    graphics.setFill(Color.LIGHTGRAY);
                    graphics.fillRect(i,j,1,1);
                }
//                System.out.println(r);
            }
        }

        graphics.setStroke(Color.GRAY);
        graphics.setLineWidth(0.05f);
        for (int x = 0; x <= width; x++) {
            graphics.strokeLine(x,0,x,height);
        }

        for (int y = 0; y <= height; y++) {
            graphics.strokeLine(0,y,width,y);
            
        }
    }

    public void update() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                ArrayList<String> neighborList = new ArrayList<>();
                if (y < height - 1 && x > 0) {
                    if (world[x - 1][y + 1].isAlive()) {
                        neighborList.add("SW");
//                        cell.setLivingNeighbours(+1);
                    }
                }
                if (y < height - 1) {
                    if (world[x][y + 1].isAlive()) {
                        neighborList.add("S");
//                        cell.setLivingNeighbours(+1);
                    }
                }
                if (x < width - 1 && y < height - 1) {
                    if (world[x + 1][y + 1].isAlive()) {
                        neighborList.add("SE");
//                        cell.setLivingNeighbours(+1);
                    }
                }

                if (x > 0) {
                    if (world[x - 1][y].isAlive()) {
                        neighborList.add("W");
//                        cell.setLivingNeighbours(+1);

                    }
                }

                if (x < width - 1) {
                    if (world[x + 1][y].isAlive()) {
                        neighborList.add("E");
//                        cell.setLivingNeighbours(+1);
                    }
                }

                if (x > 0 && y > 0) {
                    if (world[x - 1][y - 1].isAlive()) {
                        neighborList.add("NW");
//                        cell.setLivingNeighbours(+1);
                    }
                }
                if (y > 0) {
                    if (world[x][y - 1].isAlive()) {
                        neighborList.add("N");
//                        cell.setLivingNeighbours(+1);
                    }
                }
                if (x < width - 1 && y > 0) {
                    if (world[x + 1][y - 1].isAlive()) {
                        neighborList.add("NE");
//                        cell.setLivingNeighbours(+1);
                    }
                }
                world[x][y] = cell;
                world[x][y].setLivingNeighbours(neighborList.size());
                cell.update();
            }
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                world[x][y] = cell;
                world[x][y].update();
            }
        }

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
