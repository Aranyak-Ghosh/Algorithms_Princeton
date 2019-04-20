import java.util.Arrays;

public class BruteCollinearPoints {

	LineSegment[] lines;
	int size;

	public BruteCollinearPoints(Point[] points) // finds all line segments containing 4 points
	{
		if (points == null)
			throw new java.lang.IllegalArgumentException();
		int n_points = points.length;
		LineSegment[] temp = new LineSegment[n_points * n_points];
		size = 0;
		for (int i = 0; i < n_points - 3; i++) {

			for (int j = i + 1; j < n_points - 2; j++) {
				if (points[i] == null || points[j] == null)
					throw new java.lang.IllegalArgumentException();
				double slope = points[i].slopeTo(points[j]);
				if (points[i].compareTo(points[j]) == 0)
					throw new java.lang.IllegalArgumentException();

				for (int k = j + 1; k < n_points - 1; k++) {

					if (points[k] == null)
						throw new java.lang.IllegalArgumentException();

					if (points[i].slopeTo(points[k]) == Double.NEGATIVE_INFINITY
							|| points[k].slopeTo(points[j]) == Double.NEGATIVE_INFINITY)
						throw new java.lang.IllegalArgumentException();

					if (slope == points[i].slopeTo(points[k])) {
						for (int l = k + 1; l < n_points; l++) {
							if (points[l] == null)
								throw new java.lang.IllegalArgumentException();
							Point[] pt = { points[i], points[j], points[k], points[l] };
							Arrays.sort(pt);

							if (points[i].slopeTo(points[l]) == Double.NEGATIVE_INFINITY
									|| points[l].slopeTo(points[j]) == Double.NEGATIVE_INFINITY
									|| points[l].slopeTo(points[k]) == Double.NEGATIVE_INFINITY)
								throw new java.lang.IllegalArgumentException();
							if (slope == points[i].slopeTo(points[l])) {
								temp[size] = new LineSegment(pt[0], pt[3]);
								size++;
							}
						}
					}
				}
			}

		}
		resize(temp);
	}

	public int numberOfSegments() // the number of line segments
	{
		return size;
	}

	public LineSegment[] segments() // the line segments
	{
		return lines;
	}

	private void resize(LineSegment[] temp) {
		lines = new LineSegment[size];
		for (int i = 0; i < this.size; i++)
			lines[i] = temp[i];

	}
}