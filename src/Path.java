import java.awt.Point;
import java.util.ArrayList;

public class Path {
	private ArrayList<Point> points = new ArrayList<Point>();
	private String PathType;
	
	public Path(String type) {
		this.PathType = type;
	}
	public Path addPoints(int x, int y) {
		points.add(new Point(x,y));
		return this;
	}
	public Point getPoint(int i) {
		return points.get(i);
	}
	public String getType() {
		return PathType;
	}
	public void setType(String s) {
		PathType = s;
	}
	public ArrayList<Point> getPath(){
		return points;
	}

}
