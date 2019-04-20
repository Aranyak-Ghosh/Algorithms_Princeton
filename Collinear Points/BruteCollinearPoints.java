import java.util.ArrayList;

public class BruteCollinearPoints {

	ArrayList<LineSegment> lines;

	public BruteCollinearPoints(Point[] points) // finds all line segments containing 4 points
	{
		lines = new ArrayList<LineSegment>();
		if (points == null)
			throw new java.lang.IllegalArgumentException();
		int n_points = points.length;
		for (int i = 0; i < n_points - 3; i++) {
			for (int j = i + 1; j < n_points - 2; j++) {
				double slope = points[i].slopeTo(points[j]);
				if (slope == Double.NEGATIVE_INFINITY)
					throw new java.lang.IllegalArgumentException();
				for (int k = j + 1; k < n_points - 1; k++) {
					if (points[i] == null || points[j] == null || points[k] == null)
						throw new java.lang.IllegalArgumentException();
					if (points[i].slopeTo(points[k]) == Double.NEGATIVE_INFINITY
							|| points[k].slopeTo(points[j]) == Double.NEGATIVE_INFINITY)
						throw new java.lang.IllegalArgumentException();

					if (slope == points[i].slopeTo(points[k])) {
						for (int l = k + 1; l < n_points; l++) {
							if (points[l] == null)
								throw new java.lang.IllegalArgumentException();
							if (points[i].slopeTo(points[l]) == Double.NEGATIVE_INFINITY
									|| points[l].slopeTo(points[j]) == Double.NEGATIVE_INFINITY
									|| points[l].slopeTo(points[k]) == Double.NEGATIVE_INFINITY)
								throw new java.lang.IllegalArgumentException();
							if (slope == points[i].slopeTo(points[l]))
								lines.add(new LineSegment(points[i], points[l]));
						}
					}
				}
			}

		}
	}

	public int numberOfSegments() // the number of line segments
	{
		return lines.size();
	}

	public LineSegment[] segments() // the line segments
	{
		return (LineSegment[]) lines.toArray();
	}
}