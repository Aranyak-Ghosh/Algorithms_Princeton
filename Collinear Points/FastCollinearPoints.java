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
		Point[] copy = points.clone();
		Point[][] pairs = new Point[n_points * n_points][2];
		int count = 0;
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
					boolean include = true;
					for (int x = 0; x < count; x++) {
						if (pairs[x][0].compareTo(linear.get(0)) == 0
								&& pairs[x][1].compareTo(linear.get(linear.size() - 1)) == 0) {
							include = false;
							break;
						}
					}
					if (include) {
						pairs[count][0] = linear.get(0);
						pairs[count][1] = linear.get(linear.size() - 1);
						temp[count++] = new LineSegment(pairs[count][0], pairs[count][1]);
					}
				}

				end = end + linear.size() - 1;
				linear.clear();

			}
		}
		size = count;
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
		return lines.clone();
	}
}
