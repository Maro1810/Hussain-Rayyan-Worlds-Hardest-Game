import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Path {
	private ArrayList<Point> points = new ArrayList<Point>();
	
	public Path(Point p) {
		addPoints(0, p);
	}
	public Path addPoints(int i, Point p) {
		points.add(i, p);
		return this;
	}
	public Path addPoints( Point p) {
		points.add( p);
		return this;
	}
	public Point getPoint(int i) {
		return points.get(i);
	}
	public ArrayList<Point> getPath(){
		return points;
	}

}
