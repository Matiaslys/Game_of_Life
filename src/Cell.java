
public class Cell {
    int livingNeighbours = 0;
    boolean alive = false;

    public void update(){
        if (!alive && livingNeighbours == 3) {
            alive = true;
        }

        if (alive && livingNeighbours > 1 && livingNeighbours < 4) {
            alive = true;
        }

        if (alive && livingNeighbours < 1 || alive && livingNeighbours > 4){
            alive = false;
        }
    }

    public int getLivingNeighbours() {
        return livingNeighbours;
    }

    public void setLivingNeighbours(int livingNeighbours) {
        this.livingNeighbours = livingNeighbours;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }


}
