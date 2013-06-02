import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

import org.graph.datastructure.Edge;

public class VoronoiDiagram{
	
	class Dpoint{
		double x, y;
		
		Dpoint(double x, double y){
			this.x = x;
			this.y = y;
		}
		
		Dpoint(){
			x = 0; y = 0;
		}
		
		public Dpoint(Point p) {
			this.x = p.x;
			this.y = p.y;
		}

		Point toPoint(){
			return new Point((int) x, (int)y);
		}
		
		void setLocation(double x, double y){
			this.x = x;
			this.y = y;
		}
	}
	class Event {
		double x;
		Dpoint p;
		Arc a;
		boolean valid;
		
		Event(double x,Dpoint p, Arc a){
			this.x = x;
			this.p = p;
			this.a = a;
			valid = true;
		}
	}
	
	class Arc {
		Dpoint p;
		Arc prev, next;
		Event e;
		
		Seg s0, s1;
		
		Arc(Dpoint p, Arc a, Arc b){
			this.p = p;
			prev = a;
			next = b;
			e = null;
			s0 = null;
			s1 = null;
		}
		
		Arc(Dpoint p, Arc a){
			this.p = p;
			prev = a;
			next = null;
			e = null;
			s0 = null;
			s1 = null;
		}
		
		Arc(Dpoint p){
			this(p, null, null);
		}

	}

	ArrayList<Seg> output;
	
	class Seg{
		Dpoint start, end;
		boolean done;
		
		Seg(Dpoint p){
			start = p;
			end = new Dpoint(0,0);
			done = false;
			output.add(this);
		}
		
		void finish(Dpoint p){
//			System.out.println("FINISH SEGMENT");
			if(done)
				return;
			end = p;
			done = true;
//			System.out.println("DONE!");
		}
	}
	
	Arc root;
	double X0 = 0, X1 = 0, Y0 = 0, Y1 = 0;
	
	ArrayList<Point> pointList;
	
	VoronoiDiagram(ArrayList<Point> pointList){
		this.pointList = pointList;
	}
	
	PriorityQueue<Dpoint> points;
	PriorityQueue<Event> events;
	
	public ArrayList<Edge> fortuneAlgorithm(){

		points = new PriorityQueue<Dpoint>(11, new Comparator<Dpoint>(){
			@Override
			public int compare(Dpoint o1, Dpoint o2) {
				int dx=(int)(o1.x-o2.x);
				return (int) ((dx==0)?(o1.y - o2.y):dx);
			}
		});

		events = new PriorityQueue<Event>(11, new Comparator<Event>(){
			@Override
			public int compare(Event o1, Event o2) {
				return (int)(o1.x - o2.x);
			}
		});

		
		HashSet<Point> hs = new HashSet<Point>();
		for(Point p: pointList){
			if(hs.contains(p)) continue;
			// Keep track of bounding box size.
			if (p.x < X0) X0 = p.x;
			if (p.y < Y0) Y0 = p.y;
			if (p.x > X1) X1 = p.x;
			if (p.y > Y1) Y1 = p.y;
			points.add(new Dpoint(p));
			hs.add(p);
			System.out.println(p);
		}
		
		
		System.out.println("Boundary: (" + X0 + "," + Y0 + ")-(" + X1 + "," + Y1 + ")");

		output = new ArrayList<Seg> ();
		
		// Process the queues; select the top element with smaller x coordinate.
		while (!points.isEmpty())
			if (!events.isEmpty() && events.peek().x <= points.peek().x)
				process_event();
			else{
				//Process Point
				// Add a new arc to the parabolic front.
				front_insert(points.poll());
			}

		// After all points are processed, do the remaining circle events.
		while (!events.isEmpty())
			process_event();

		finish_edges(); // Clean up dangling edges.

		//-----------------
		// Output the voronoi diagram.

		ArrayList<Edge> result = new ArrayList<Edge>();
		for(Seg seg: output) {
			Point start = seg.start.toPoint();
			Point end = seg.end.toPoint();
			if(end.x == 0 && end.y == 0) continue;
			result.add(new Edge(start, end));
			System.out.println("Garis: " + "("+start.x + "," + start.y + "),(" + end.x + "," + end.y + ")");
		}
		return result;

	}

	private void process_event()
	{
	   // Get the next event from the queue.
	   Event e = events.poll();

	   System.out.println("Proses event: " + e.p);
	   
	   if (e.valid) {
	      // Start a new edge.
	      Seg s = new Seg(e.p);

	      // Remove the associated arc from the front.
	      Arc a = e.a;
	      if (a.prev != null) {
	         a.prev.next = a.next;
	         a.prev.s1 = s;
	      }
	      if (a.next != null) {
	         a.next.prev = a.prev;
	         a.next.s0 = s;
	      }

	      // Finish the edges before and after a.
	      if (a.s0 != null) a.s0.finish(e.p);
	      if (a.s1 != null) a.s1.finish(e.p);

	      // Recheck circle events on either side of p:
	      if (a.prev != null) check_circle_event(a.prev, e.x);
	      if (a.next != null) check_circle_event(a.next, e.x);
	   }
	}

	private void front_insert(Dpoint p) {
		System.out.println("Masukkan titik ke priority queue: " + p);
		if (root == null) {
			root = new Arc(p);
			return;
		}

		// Find the current arc(s) at height p.y (if there are any).
		for (Arc i = root; i != null ; i = i.next) {
			Dpoint z = new Dpoint(), zz = new Dpoint();
			if (intersect(p,i,z)) {
				// New parabola intersects arc i.  If necessary, duplicate i.
				if (i.next != null && !intersect(p,i.next, zz)) {
					i.next.prev = new Arc(i.p,i,i.next);
					i.next = i.next.prev;
				}
				else i.next = new Arc(i.p,i);
				i.next.s1 = i.s1;

				// Add p between i and i.next.
				i.next.prev = new Arc(p,i,i.next);
				i.next = i.next.prev;

				i = i.next; // Now i points to the new arc.

				// Add new half-edges connected to i's endpoints.
				i.prev.s1 = i.s0 = new Seg(z);
				i.next.s0 = i.s1 = new Seg(z);

				// Check for new circle events around the new arc:
				check_circle_event(i, p.x);
				check_circle_event(i.prev, p.x);
				check_circle_event(i.next, p.x);

				return;
			}
		}

		// Special case: If p never intersects an arc, append it to the list.
		Arc i;
		for (i = root; i.next != null; i=i.next) ; // Find the last node.

		i.next = new Arc(p,i);  
		// Insert segment between p and i
		Dpoint start = new Dpoint(X0, (i.next.p.y + i.p.y) / 2);
//		start.x = X0;
//		start.y = (i.next.p.y + i.p.y) / 2;
		i.s1 = i.next.s0 = new Seg(start);

	}

	private void check_circle_event(Arc i, double x0) {
		System.out.println("Cek circle event: "+i+ "x0="+x0);
		// Invalidate any old event.
		if (i.e!= null && i.e.x != x0)
			i.e.valid = false;
		i.e = null;

		if (i.prev == null || i.next == null){
			System.out.println("next & prev = null");
			return;
		}

		Dpoint o = new Dpoint();
		double x = circle(i.prev.p, i.p, i.next.p, o);
		
		System.out.println(" circle X=" + x);
		if (x > x0) {
			// Create new event.
			System.out.println("tambah event" + i.e);
			i.e = new Event(x, o, i);
			events.add(i.e);
		}
	}

	// Find the rightmost point on the circle through a,b,c.
//	private boolean circle(Point a, Point b, Point c, Double x, Point o)
	private double circle(Dpoint a, Dpoint b, Dpoint c, Dpoint o)
	{
		// Check that bc is a "right turn" from ab.
		if ((b.x-a.x)*(c.y-a.y) - (c.x-a.x)*(b.y-a.y) > 0)
			return -1;

		// Algorithm from O'Rourke 2ed p. 189.
		double A = b.x - a.x,  B = b.y - a.y,
				C = c.x - a.x,  D = c.y - a.y,
				E = A*(a.x+b.x) + B*(a.y+b.y),
				F = C*(a.x+c.x) + D*(a.y+c.y),
				G = 2*(A*(c.y-b.y) - B*(c.x-b.x));

		if (G == 0) return -1;  // Points are co-linear.

		// Point o is the center of the circle.
		o.setLocation((D*E-B*F)/G, (A*F-C*E)/G);

		// o.x plus radius equals max x coordinate.
	
		return (o.x + Math.sqrt( Math.pow((double) a.x - o.x, 2) + Math.pow((double) a.y - o.y, 2) ));
//		System.out.println("circle x = " + x);
	}

	private boolean intersect(Dpoint p, Arc i, Dpoint result)
	{
		if (i.p.x == p.x) return false;

		double a=0, b=0;
		if (i.prev != null) // Get the intersection of i.prev, i.
			a = intersection(i.prev.p, i.p, p.x).y;
		if (i.next != null) // Get the intersection of i.next, i.
			b = intersection(i.p, i.next.p, p.x).y;

		if ((i.prev == null || a <= p.y) && (i.next == null || p.y <= b)) {
			result.y = p.y;

			result.x = (i.p.x*i.p.x + (i.p.y-result.y)*(i.p.y-result.y) - p.x*p.x)
					/ (2*i.p.x - 2*p.x);

			return true;
		}
		return false;
	}

	private Dpoint intersection(Dpoint p0, Dpoint p1, double l)
	{
		Dpoint res = new Dpoint(), p = p0;

		double z0 = 2*(p0.x - l);
		double z1 = 2*(p1.x - l);

		if (p0.x == p1.x)
			res.y = (p0.y + p1.y) / 2;
		else if (p1.x == l)
			res.y = p1.y;
		else if (p0.x == l) {
			res.y = p0.y;
			p = p1;
		} else {
			// Use the quadratic formula.
			double a = 1/z0 - 1/z1;
			double b = -2*(p0.y/z0 - p1.y/z1);
			double c = (p0.y*p0.y + p0.x*p0.x - l*l)/z0
					- (p1.y*p1.y + p1.x*p1.x - l*l)/z1;

			res.y = (( -b - Math.sqrt(b*b - 4*a*c) ) / (2*a));
		}
		// Plug back into one of the parabola equations.
		res.x = (p.x*p.x + (p.y-res.y)*(p.y-res.y) - l*l)/(2*p.x-2*l);
		return res;
	}


	private void finish_edges() {
		// Advance the sweep line so no parabolas can cross the bounding box.
		double l = X1 + (X1-X0) + (Y1-Y0);

		// Extend each remaining segment to the new parabola intersections.
		for (Arc i = root; i.next != null; i = i.next)
			if (i.s1 != null)
				i.s1.finish(intersection(i.p, i.next.p, l*2));
	}

}