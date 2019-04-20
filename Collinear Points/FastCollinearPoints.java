import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FastCollinearPoints {
	private final LineSegment[] lines;
	private int size;

	public FastCollinearPoints(Point[] points) // finds all line segments containing 4 or more points
	{
		validatePoints(points);
		int n_points = points.length;
		Point[] copy = points.clone();
		LineSegment[] temp = new LineSegment[n_points * n_points];
		for (int i = 0; i < n_points - 3; i++) {
			ArrayList<Point> linear = new ArrayList<Point>();
			Arrays.sort(points, copy[i].slopeOrder());
			int end = 0;

			while (end < n_points - 3) {
				int j = end + 1;
				if (points[end].compareTo(copy[i]) == 0) {
					end++;
					continue;
				}
				linear.add(copy[i]);
				linear.add(points[end]);
				double slope = copy[i].slopeTo(points[end]);

				while (j < n_points && copy[i].slopeTo(points[j]) == slope) {
					linear.add(points[j]);
					j++;
				}

				if (linear.size() > 3) {
					Collections.sort(linear);
					temp[size++] = new LineSegment(linear.get(0), linear.get(linear.size() - 1));
				}
				end = end + linear.size() - 1;
				linear.clear();

			}
		}
		lines = new LineSegment[size];
		for (int i = 0; i < this.size; i++)
			lines[i] = temp[i];

	}

	private void validatePoints(Point[] points) {
		if (points == null)
			throw new java.lang.IllegalArgumentException();
		for (Point p : points)
			if (p == null)
				throw new java.lang.IllegalArgumentException();
		Arrays.sort(points);
		int n_points = points.length;
		for (int i = 0; i < n_points - 1; i++)
			if (points[i].compareTo(points[i + 1]) == 0)
				throw new java.lang.IllegalArgumentException();
	}

	public int numberOfSegments() // the number of line segments
	{
		return size;
	}

	public LineSegment[] segments() // the line segments
	{
		return lines;
	}
}
