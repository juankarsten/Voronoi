import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.awt.*;

public class ConvexHull{
	/* melihat contoh: http://code.google.com/p/dyn4j/source/browse/trunk/src/org/dyn4j/game2d/geometry/hull/DivideAndConquer.java?r=50 */
   ArrayList<Point> points;
   public ConvexHull(ArrayList<Point> points){
      this.points = points;
   }
   
	private class Vertex{
      Point point;
      Vertex next,prev;
      
      public Vertex(Point p, Vertex n, Vertex pr, int i){
         point = p; next = n; prev = pr;
      }
      public void setNP(Vertex n, Vertex p){
         next = n; prev = p;
      }
   }

   private class Hull{
      Vertex left, right;
      int size;

      public Hull( Vertex l, Vertex r, int s){
         left = l; right = r; size = s;
      }
   }

   private static class PointComparator implements Comparator<Point> {
      @Override
      public int compare(Point arg0, Point arg1) {
        //urutkan berdasarkan x
        int xx=(int) Math.signum(arg0.x - arg1.x);
        
        //jika x kolinear urutkan berdasarkan y
        return (xx==0)?((int) Math.signum(arg0.y - arg1.y)):xx;
         //return (int) Math.signum(arg0.x - arg1.x);
      }
   }

   public ArrayList<Point> calculate(){
      Collections.sort(points, new PointComparator() );
      Hull hull = divide(points, 0, points.size() - 1);

      ArrayList<Point> hulls = new ArrayList<Point> ();
      Vertex v = hull.left;
      //System.out.println(hull.left.point);
      
      //System.out.println(points);
      //Masukkan ke ArrayList berisi indeks vertex yang menjadi hull
      System.out.print("ConvexHull: ");
      for(int i=0; i<hull.size && i<1000; ++i){
         hulls.add(v.point);
         System.out.print(v.point);
         v = v.next;
      }
      System.out.println();
     // repaint();

		/*for(int i=0; i < points.size(); ++i)
			hulls.add(i);*/

      return hulls;
   }
	
   private  Hull divide(ArrayList<Point> points, int first, int last){
      //membagi titik-titik menjadi 2 bagian
      int size = last - first;
      if (size == 0){
         Vertex v = new Vertex(points.get(first), null, null, first);
         return new Hull(v,v,1);
      } else {
         int mid = (first+last)/2;
         Hull left = divide(points,first,mid);
         Hull right = divide(points,mid+1,last);
         return merge(left, right);
      }
   }

   private Hull merge(Hull left, Hull right){
	   	if(left.size == 1 && right.size == 1){ 
	         left.left.setNP(right.right, right.right); /* set next & prev left.root menjadi right.root */
	         right.right.setNP(left.left, left.left);
	         //System.out.println(left.left.point+" "+right.right.point);
	         return new Hull (left.left,right.right, 2);

	    } else if(left.size == 1 && right.size == 2) {
	         Hull hull = new Hull (left.left, right.right, 3);
	         mergeTriangle(left.left, right.left, hull);
	         //System.out.println(left.left.point+" "+right.right.point);
	         return hull;
	    } else if (left.size == 2 && right.size == 1){
	         Hull hull = new Hull (left.left, right.right, 3);
	         mergeTriangle(right.left, left.left, hull);
	         //System.out.println(left.left.point+" "+right.right.point);
	         return hull;
	    } 

        Hull hull = new Hull( left.left, right.right, 0);
         /* penghubung atas */
         Vertex lu = left.right;
         Vertex ru = right.left;
         System.out.println("upper"+lu.point+" "+ru.point);
         Point upper = new Point (ru.point.x -lu.point.x, ru.point.y - lu.point.y);
         for (int i=0; i<left.size*right.size; ++i){
            Point lv = new Point (lu.next.point.x - lu.point.x, lu.next.point.y - lu.point.y);
            Point rv = new Point (ru.prev.point.x - ru.point.x, ru.prev.point.y - ru.point.y);
            
            double crossR = rv.x * upper.y - rv.y * upper.x;
            double crossL = (-upper.x) * lv.y - (-upper.y) * lv.x; 
            
            if (crossR > 0.0 && crossL > 0.0) break; //both convex
            if (crossR <= 0) ru = ru.prev; //titik sebelah kanan digeser cw
            if (crossL <= 0) lu = lu.next; //titik sebelah kiri digeser ccw
            
            upper = new Point(ru.point.x - lu.point.x, ru.point.y - lu.point.y);
         }
         
         /* penghubung bawah */
         Vertex ll = left.right;
         Vertex rl = right.left;
         Point lower = new Point (rl.point.x - ll.point.x, rl.point.y - ll.point.y);
         for (int i=0; i<left.size*right.size; ++i){
            Point lv = new Point(ll.prev.point.x - ll.point.x, ll.prev.point.y - ll.point.y);
            Point rv = new Point(rl.next.point.x - rl.point.x, rl.next.point.y - rl.point.y);
            
            double crossR = lower.x * rv.y - lower.y * rv.x;
            double crossL = lv.x * (-lower.y)- lv.y * (-lower.x);
            
            if(crossR > 0.0 && crossL > 0.0) break; //both convex;
            
            if (crossR <= 0) rl = rl.next; //titik sebelah kanan digeser ccw
            if (crossL <= 0) ll = ll.prev; //titik sebelah kiri digeser cw
            
            lower = new Point(rl.point.x - ll.point.x, rl.point.y - ll.point.y);
         }
         
         lu.prev = ru;
         ru.next = lu;
         ll.next = rl;
         rl.prev = ll;
         
         
         Vertex v0 = hull.left;
         Vertex v = v0;
         int size = 0;
         int i=0;
         do{
            ++size; ++i;
            v = v.next;
         } while (v!=v0 || i>1000);
         hull.size = size;
         
         return hull;
   }

   private void mergeTriangle(Vertex one, Vertex two, Hull hull){
   		 // get the line segment points
      Vertex p1 = two;
      Vertex p2 = two.next;
      // get the point
      Vertex p = one;

      Point v1 = new Point(p1.point.x - p.point.x, p1.point.y - p.point.y);
      Point v2 = new Point(p2.point.x - p1.point.x, p2.point.y - p1.point.y);
      double area = Math.signum(v1.x * v2.y - v1.y * v2.x);  //cross product

      if(area==0){
      	hull.left.next=hull.right;
      	hull.left.prev=hull.right;
      	hull.right.next=hull.left;
      	hull.right.prev=hull.left;
	    hull.size=2;
      }else if(area<0){
      	p1.next=p;
         p.next=p2;
         p.prev=p1;
         p2.prev=p;
      }else{
        p.next=p1;
        p2.next=p;
        p1.prev=p;
        p.prev=p2;
      }

     
   }
}