import java.util.Arrays;

public class BruteCollinearPoints {

	private final LineSegment[] lines;
	private final int size;

	public BruteCollinearPoints(Point[] points) // finds all line segments containing 4 points
	{
		checkNull(points);
		int n_points = points.length;
		LineSegment[] temp = new LineSegment[n_points * n_points];
		int count = 0;
		Point[] copy = new Point[points.length];
		System.arraycopy(points, 0, copy, 0, points.length);
		checkDuplicates(copy);
		for (int i = 0; i < n_points - 3; i++) {

			for (int j = i + 1; j < n_points - 2; j++) {

				double slope = copy[i].slopeTo(points[j]);

				for (int k = j + 1; k < n_points - 1; k++) {

					if (slope == copy[i].slopeTo(points[k])) {
						for (int l = k + 1; l < n_points; l++) {
							if (slope == copy[i].slopeTo(copy[l])) {
								Point[] pt = { copy[i], copy[j], copy[k], copy[l] };
								Arrays.sort(pt);
								temp[count] = new LineSegment(pt[0], pt[3]);
								count++;
							}
						}
					}
				}
			}

		}
		size = count;
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
		return lines.clone();
	}

	private void checkNull(Point[] points) {
		if (points == null)
			throw new java.lang.IllegalArgumentException();
		for (Point p : points)
			if (p == null)
				throw new java.lang.IllegalArgumentException();
	}
	
	private void checkDuplicates(Point[] points) {
		Arrays.sort(points);
		int n_points = points.length;
		for (int i = 0; i < n_points - 1; i++)
			if (points[i].compareTo(points[i + 1]) == 0)
				throw new java.lang.IllegalArgumentException();

	}
}