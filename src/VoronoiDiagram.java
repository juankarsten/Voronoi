import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.PriorityQueue;
import java.util.TreeMap;
import org.graph.datastructure.Arc;
import org.graph.datastructure.ArcNode;
import org.graph.datastructure.BinarySearchTree;
import org.graph.datastructure.Edge;
import org.graph.datastructure.EdgeList;
import org.graph.datastructure.Event;
import org.graph.datastructure.Pair;


public class VoronoiDiagram{
   ArrayList<Point> points;
   public VoronoiDiagram(ArrayList<Point> points){
      this.points = points;
   }
   
   //status arc
   BinarySearchTree T;
   //event queue
   PriorityQueue<Event> q;
   EdgeList edgeList;
   
   public ArrayList<Edge> fortuneAlgorithm(){
      //1. Initialize the event queue Q with all site events
      q= new PriorityQueue<Event>();
      for(Point p:points){
         q.add(new Event(p));
      }
      //Initialize an empty status structure T
      T = new BinarySearchTree();
      //Initialize an empty DCEL D
      edgeList = new EdgeList();
      
      //While ! is not empty
      while(!q.isEmpty()){
         //Remove the event with largest y-coordinate from Q
         Event top = q.poll();
         //if the event is a site event
         if(top.site){
            handleSiteEvent(top);
         }
         else{
            //handle circle event y where y is the leaf of T
            //representing the arc that will disappear
            handleCircleEvent(top);
         }
      }
      
      //TODO Compute a bounding box containing all vertices
      //TODO attach the half infinite edges to the bounding box
      
      //TODO Traverse the half-edges of the DCEL to ad the cell records
      
      //TODO return masih asal
      return edgeList.edges;
   }
   private void handleSiteEvent(Event top) {
      //1. if T is empty insert pi into it and return
      if(T.isEmpty()){
         //masih satu arc top.point bukan circle event jadi circle event point=null
//         T.insert(new ArcNode(new Arc(top.point)));
         T.root = new ArcNode(new Arc(top.point));
      }
      else{
         Point pi=top.point;
        //2. Search in T for the arc a vertically above pi.
         ArcNode a = T.root;
         // penyimpanan parent a dan posisi a terhadap pa
         ArcNode pa = T.root;
         boolean left = true;
               
         while(a.isInternalNode()){
            // jika di titik pi di kanan breakpoint kanan: 
            Point breakpoint = a.getBreakPoint(pi.y);
            pa = a;
            if (pi.x < breakpoint.x){
               a = a.left;
               left = true;
            }
            else{
               a = a.right;
               left = false;
            }
         }
         
         //If the leaf representing a has a pointer to a circle event in Q delete the circle event from Q
         //jika ada pointer ke circleEvent, event ini diabaikan
         if(a.circleEvent == null){
            //3. Replace the leaf of T that represents a with a subtree 
            //having three leaves. The middle leaf stores the new site pi               
            //the other two leaves store the site pj that was originally
            //stored with a.
            
            Arc pjj = a.lValue;
            Arc pii = new Arc(pi);
            
            //Store the tuples <pj,pi> and <pi,pj> representing the new
            //breakpoints at the two new internal nodes.
            
            ArcNode pjpi = new ArcNode(pjj, pii, true);
            ArcNode pipj = new ArcNode(pii, pjj, false);
            
            pjpi.left = new ArcNode(pjj, pjpi);
            pjpi.right = pipj;
            pipj.left = new ArcNode(pii, pipj);
            pipj.right = new ArcNode(pjj, pipj);
            
            if(pa == a){
               T.root = pjpi;
            }
            else if(left){
               pa.left = pjpi;
            }
            else{
               pa.right = pjpi;
            }
            //4.create new half edge in voronoi diagram
            //which will be traced out by the two new breakpoints
            Edge edge = new Edge(pjpi.getBreakPoint(pi.y), pipj.getBreakPoint(pi.y));
            pipj.edge = edge;
            pjpi.edge = edge;
            edgeList.addEdge(edge);
            
            //5.check the triple consecutive arcs where pi is left
            //to see if the breakpoints converge. If so, insert the
            //circle event into Q and add pointer between the node
            //in T and the node in Q.
            
            
            //do the same for the triple where the new arch is right
            
         }
      }
   }
   
   private void handleCircleEvent(Event event) {

      ArcNode y = event.arcNode;
      
      //1. Delete the leaf y that represents the disappearing
      // arc a from T. Delete all circle events involving a from
      // Q; these can be found using the pointers from the 
      // predecessor and successor of y in T
      // (The circle event where a is the middle arc is currently
      // being handled, and has been deleted from Q.)
      
      T.delete(y);
      q.remove(y.circleEvent);
      
      //2. Add the center of the circle causing the event as a 
      //vertex record to the DCEL. 
      //Create two half-edge records corresponding to the new
      //breakpoint of the beach line
      //Set the pointers between them appropriately.
      //Attach the three new records to the half-edge records
      //that end at the vertex.
      
      //TODO apakah penyebab circle adalah parent & grandparent?
      ArcNode py = y.parent;
      ArcNode gpy = y.parent.parent;
      py.edge.end = event.point;
      gpy.edge.end = event.point;
      
      //3. Check the new triple of consecutive arcs that has the
      //former left neighbor of a as its middle arc to see if
      //the two breakpoints of the triple converge.
      //If so, insert the corresponding circle event into Q and
      //set pointers between the new circle event in Q and the
      //corresponding leaf of T.
      Point leftPoint = T.getTripleLeftPoint(y);
      if(leftPoint != null) q.add(new Event(leftPoint, T.leftOf(y)));
      
      //Do the same for the triple where the former right
      //neighbor is the middle arc.
      
   }

   
}