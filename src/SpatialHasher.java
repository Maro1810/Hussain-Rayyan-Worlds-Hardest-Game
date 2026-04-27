import java.util.List;

public class SpatialHasher {

    private List<Entity> entities;
    private int cellSize;
    private int numCells;
    
    public SpatialHasher(List<Entity> entities, int cellSize) {
        this.entities = entities;
        this.cellSize = cellSize;

        numCells = (int) (Math.pow(1000, 2)/Math.pow(cellSize, 2));

    }

    //Hash function from matthias-research.github.io/pages/tenMinutePhysics/11-hashing.pdf
    public int hash(int x, int y) {
        int hash = (x*92837111) ^ (y*689287499);  
        return hash % numCells;
    }
}
