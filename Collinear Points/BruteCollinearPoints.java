import java.util.Arrays;

public class BruteCollinearPoints {

	private final LineSegment[] lines;
	private int size;

	public BruteCollinearPoints(Point[] points) // finds all line segments containing 4 points
	{
		validatePoints(points);
		int n_points = points.length;
		LineSegment[] temp = new LineSegment[n_points * n_points];
		size = 0;
		for (int i = 0; i < n_points - 3; i++) {

			for (int j = i + 1; j < n_points - 2; j++) {

				double slope = points[i].slopeTo(points[j]);

				for (int k = j + 1; k < n_points - 1; k++) {

					if (slope == points[i].slopeTo(points[k])) {
						for (int l = k + 1; l < n_points; l++) {
							if (slope == points[i].slopeTo(points[l])) {
								Point[] pt = { points[i], points[j], points[k], points[l] };
								Arrays.sort(pt);
								temp[size] = new LineSegment(pt[0], pt[3]);
								size++;
							}
						}
					}
				}
			}

		}
		lines = new LineSegment[size];
		for (int i = 0; i < this.size; i++)
			lines[i] = temp[i];

	}

	public int numberOfSegments() // the number of line segments
	{
		return size;
	}

	public LineSegment[] segments() // the line segments
	{
		return lines;
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
}