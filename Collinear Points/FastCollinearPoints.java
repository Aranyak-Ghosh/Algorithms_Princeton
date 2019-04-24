import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FastCollinearPoints {
	private final LineSegment[] lines;
	private final int size;

	public FastCollinearPoints(Point[] points) // finds all line segments containing 4 or more points
	{
		validatePoints(points);
		int n_points = points.length;
		Point[] copy = new Point[n_points];
		System.arraycopy(points, 0, copy, 0, points.length);
		Point[][] pairs = new Point[n_points * n_points][2];
		int count = 0;
		for (int i = 0; i < n_points - 3; i++) {
			ArrayList<Point> linear = new ArrayList<Point>();

			// Sort the array based on slope
			Arrays.sort(copy, points[i].slopeOrder());
			int end = 0;

			while (end < n_points - 2) {
				int j = end + 1;

				// If the end point is the same as initial point ignore it
				if (copy[end].compareTo(points[i]) == 0) {
					end++;
					continue;
				}

				// Add the initial two points to the list
				linear.add(points[i]);
				linear.add(copy[end]);

				// Calculate slope
				double slope = points[i].slopeTo(copy[end]);

				// Find all points with the same slope and add to the list
				while (j < n_points && points[i].slopeTo(copy[j]) == slope) {
					linear.add(copy[j]);
					j++;
				}

				if (linear.size() > 3) {
					// Sort the points so that line is drawn between two extreme points
					Collections.sort(linear);
					boolean include = true;
					// Check if line already included in list
					for (int x = 0; x < count; x++) {
						if (pairs[x][0].compareTo(linear.get(0)) == 0
								&& pairs[x][1].compareTo(linear.get(linear.size() - 1)) == 0) {
							include = false;
							break;
						}
					}
					// If line not included in list then add the points
					if (include) {
						pairs[count][0] = linear.get(0);
						pairs[count][1] = linear.get(linear.size() - 1);
						count++;
					}
				}

				end = end + linear.size() - 1;
				linear.clear();

			}
		}
		size = count;
		lines = new LineSegment[size];
		for (int i = 0; i < this.size; i++)
			lines[i] = new LineSegment(pairs[i][0], pairs[i][1]);

	}

	private void validatePoints(Point[] points) {
		if (points == null)
			throw new java.lang.IllegalArgumentException();
		for (Point p : points)
			if (p == null)
				throw new java.lang.IllegalArgumentException();
		Point[] copy = new Point[points.length];
		System.arraycopy(points, 0, copy, 0, points.length);
		Arrays.sort(copy);
		int n_points = points.length;
		for (int i = 0; i < n_points - 1; i++)
			if (copy[i].compareTo(copy[i + 1]) == 0)
				throw new java.lang.IllegalArgumentException();
	}

	public int numberOfSegments() // the number of line segments
	{
		return size;
	}

	public LineSegment[] segments() // the line segments
	{
		return lines.clone();
	}
}
