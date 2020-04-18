
public class Cell {
    int width, height;
    int[][] world;

    public static int DEAD = 0;
    public static int ALIVE = 1;

    public Cell(int width, int height) {
        this.width = width;
        this.height = height;

        this.world = new int[width][height];
    }

    public static Cell copy(Cell cell) {
        Cell copy = new Cell(cell.width, cell.height);

        for (int y = 0; y < cell.height; y++) {
            for (int x = 0; x < cell.width; x++) {
                copy.setState(x, y, cell.getState(x, y));
            }
        }

        return copy;
    }

    public void printBoard() {
        System.out.println("___");
        for (int y = 0; y < height; y++) {
            String line = "|";
            for (int x = 0; x < width; x++) {
                if (this.world[x][y] == DEAD){
                    line += ".";
                }else {
                    line += "*";
                }
            }
            line += "|";
            System.out.println(line);
        }
        System.out.println("___ \n");
    }

    public void setAlive(int x, int y) {
        this.setState(x,y,ALIVE);
    }

    public void setDead(int x, int y) {
        this.setState(x,y,DEAD);
    }

    public void setState(int x, int y, int state){
        if (x < 0 || x >= width) {
            return;
        }

        if (y < 0 || y >= height) {
            return;
        }

        this.world[x][y] = state;
    }

    public int countAliveNeighbours(int x, int y) {
        int count = 0;
        count += getState(x - 1,y - 1);
        count += getState(x,y - 1);
        count += getState(x + 1,y - 1);

        count += getState(x - 1,y);
        count += getState(x + 1,y);

        count += getState(x - 1,y + 1);
        count += getState(x,y + 1);
        count += getState(x + 1,y + 1);

        return count;
    }

    public int getState(int x, int y){
        if (x < 0 || x >= width) {
            return DEAD;
        }

        if (y < 0 || y >= height) {
            return DEAD;
        }

        return this.world[x][y];
    }

    public void step() {
        int[][] newBoard = new int[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int aliveNeighbours = countAliveNeighbours(x, y);


                if (getState(x, y) == ALIVE) {
                    if(aliveNeighbours < 2){
                        newBoard[x][y] = DEAD;
                    }else if (aliveNeighbours == 2 || aliveNeighbours == 3){
                        newBoard[x][y] = ALIVE;
                    }else if (aliveNeighbours > 3){
                        newBoard[x][y] = DEAD;
                    }
                } else {
                    if (aliveNeighbours == 3) {
                        newBoard[x][y] = ALIVE;
                    }
                }
            }
        }
        this.world = newBoard;
    }
}
