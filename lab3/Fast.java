import javax.swing.*;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Brute force solution. To run: java brute.java < input.txt
 *
 * @author Magnus Nielsen Largely based on existing C++-laborations by Tommy
 *         Olsson and Filip Strömbäck.
 */
public class Fast {
	/**
	 * Clear the window and paint all the Points in the plane.
	 *
	 * @param frame  - The window / frame.
	 * @param points - The points to render.
	 */
	private static void render(JFrame frame, ArrayList<Point> points) {
		frame.removeAll();
		frame.setVisible(true);

		for (Point p : points) {
			p.paintComponent(frame.getGraphics(), frame.getWidth(), frame.getHeight());
		}
	}

	/**
	 * Draw a line between two points in the window / frame.
	 *
	 * @param frame - The frame / window in which you wish to draw the line.
	 * @param p1    - The first Point.
	 * @param p2    - The second Point.
	 */
	private static void renderLine(JFrame frame, Point p1, Point p2) {
		p1.lineTo(p2, frame.getGraphics(), frame.getWidth(), frame.getHeight());
	}

	/**
	 * Read all the points from the buffer in the input scanner.
	 *
	 * @param input - Scanner containing a buffer from which to read the points.
	 * @return ArrayList<Point> containing all points defined in the file / buffer.
	 */
	private static ArrayList<Point> getPoints(Scanner input) {
		int count = input.nextInt();
		ArrayList<Point> res = new ArrayList<>();
		for (int i = 0; i < count; ++i) {
			res.add(new Point(input.nextInt(), input.nextInt()));
		}

		return res;
	}

	public static void main(String[] args) throws InterruptedException {
		JFrame frame;
		Scanner input = null;
		File f;
		ArrayList<Point> points;

		if (args.length != 1) {
			System.out.println("Usage: java Brute <input.txt>\n"
					+ "Replace <input.txt> with your input file of preference, and possibly the path.\n"
					+ "Ex: java Brute data/input1000.txt");
			System.exit(0);
		}

		// Opening the file containing the points.
		f = new File(args[0]);
		try {
			input = new Scanner(f);
		} catch (FileNotFoundException e) {
			System.err.println("Failed to open file. Try giving a correct file / file path.");
		}

		// Creating frame for painting.
		frame = new JFrame();
		frame.setMinimumSize(new Dimension(512, 512));
		frame.setPreferredSize(new Dimension(512, 512));

		// Getting the points and painting them in the window.
		points = getPoints(input);
		render(frame, points);

		// Sorting points by natural order (lexicographic order). Makes finding end
		// points of line segments easy.
		// Collections.sort(points, new NaturalOrderComparator());

		long start = System.currentTimeMillis();

		ArrayList<Point> sortedList = new ArrayList<>();
		ArrayList<Point> pointList = new ArrayList<>();
		int size = points.size();
		Point startingPoint;

		for (int i = 0; i < size; ++i) {
			startingPoint = points.get(i);
			sortedList = points;
			Collections.sort(sortedList, new CompareSlopes(startingPoint));

			for (int j = 0; j < size; ++j) {
				if (pointList.isEmpty()) {
					pointList.add(startingPoint);
					pointList.add(sortedList.get(j));
				}
				if (sortedList.size() > j + 1) {
					if (startingPoint.slopeTo(sortedList.get(j)) == startingPoint.slopeTo(sortedList.get(j + 1))) {
						pointList.add(points.get(j + 1));
					} else {
						pointList.clear();
					}
				}
				if (pointList.size() > 3) {
					Collections.sort(pointList, new NaturalOrderComparator());
					renderLine(frame, pointList.get(0), pointList.get(pointList.size() - 1));
				}
			}
			pointList.clear();
		}

		long end = System.currentTimeMillis();
		System.out.println("Computing all the line segments took: " + (end - start) + " milliseconds.");
	}
}

/**
 * Comparator class. Used to tell Collections.sort how to compare objects of a
 * non standard class.
 */
class NaturalOrderComparator implements Comparator<Point> {
	public int compare(Point a, Point b) {
		if (a.greaterThan(b)) {
			return 1;
		}
		return -1;
	}
}

class CompareSlopes implements Comparator<Point> {
	Point startingPoint;

	public CompareSlopes(Point point) {
		this.startingPoint = point;
	}

	@Override
	public int compare(Point o1, Point o2) {
		if (startingPoint.slopeTo(o1) > startingPoint.slopeTo(o2)) {
			return 1;
		} else if (startingPoint.slopeTo(o2) > startingPoint.slopeTo(o1)) {
			return -1;
		}
		return 0;
	}

}
