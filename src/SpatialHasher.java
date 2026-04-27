import java.util.ArrayList;
import java.util.List;

public class SpatialHasher {

    private List<Entity> entities;
    private double cellSize;
    private int numCells;

    private List<Entity>[] hashTable;
    
    public SpatialHasher(List<Entity> entities, double cellSize) {
        this.entities = entities;
        this.cellSize = cellSize;

        numCells = (int) (Math.pow(1000, 2)/Math.pow(cellSize, 2));

        hashTable = new ArrayList[numCells];

        for (int i = 0; i < numCells; i++) {
            hashTable[i] = new ArrayList<>();
        }
    }

    public void update() {
        for (int i = 0; i < numCells; i++) {
            hashTable[i].clear();
        }

        for (Entity e : entities) {
            int minCellX = (int)Math.floor(e.getHitbox().getX() / cellSize);
            int maxCellX = (int) Math.floor((e.getHitbox().getX() + e.getHitbox().getWidth()) / cellSize);

            int minCellY = (int) Math.floor(e.getHitbox().getY() / cellSize);
            int maxCellY = (int) Math.floor((e.getHitbox().getY() + e.getHitbox().getHeight()) / cellSize);

            for (int i = minCellX; i <= maxCellX; i++) {
                for (int j = minCellY; j <= maxCellY; j++) {
                    int hashIndex = hash(i, j);

                    hashTable[hashIndex].add(e);
                }
            }
            
        }

        handleCollisions();
    }

    private void handleCollisions() {
        for (int i = 0; i < numCells; i++) {
            List<Entity> cellEntities = hashTable[i];

            for (int j = 0; j < cellEntities.size(); j++) {
                Entity e1 = cellEntities.get(j);

                for (int k = j+1; k < cellEntities.size(); k++) {
                    Entity e2 = cellEntities.get(k);

                    if(e1.getHitbox().intersects(e2.getHitbox())) {
                        e1.collision(e2);
                        e2.collision(e1);
                    }
                }
            }
        }
    }
    
    //Hash function from matthias-research.github.io/pages/tenMinutePhysics/11-hashing.pdf
    public int hash(int x, int y) {
        int hash = (x*92837111) ^ (y*689287499);  
        return Math.abs(hash) % numCells;
    }
}
