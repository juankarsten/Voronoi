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
import org.graph.datastructure.PointY;


public class VoronoiDiagram{
   ArrayList<Point> points;
   public VoronoiDiagram(ArrayList<Point> points){
      this.points = points;
   }
   
   //status arc
   BinarySearchTree T;
   //event queue
   PriorityQueue<Event> q;
   EdgeList edgeList = new EdgeList();
   
   public ArrayList<Point> fortuneAlgorithm(){
      //1. Initialize the event queue Q with all site events
      q= new PriorityQueue<Event>(11, new Comparator<Event>(){
         @Override
         public int compare(Event o1, Event o2) {
            return o1.point.compareTo(o2.point);
         }
      });
      for(Point p:points){
         q.add(new Event((PointY)p,null));
      }
      
      //Initialize an empty status structure T
      T = new BinarySearchTree();
      
      //TODO Initialize an empty DCEL D
      //??????????????????????????????
      
      //While ! is not empty
      while(!q.isEmpty()){
         //Remove the event with largest y-coordinate from Q
         Event top = q.poll();
         //if the event is a site event
         if(top.site){
            handleSiteEvent(top);
         }
         else{
            handleCircleEvent(top);
         }
      }
      
      //TODO Compute a bounding box containing all vertices
      //TODO attach the half infinite edges to the bounding box
      
      //TODO Traverse the half-edges of the DCEL to ad the cell records
      
      //TODO return masih asal
      return new ArrayList<Point>();
   }
   private void handleSiteEvent(Event top) {
      //1. if T is empty insert pi into it and return
      if(T.isEmpty()){
         //masih satu arc top.point bukan circle event jadi circle event point=null
//         T.insert(new ArcNode(new Arc(top.point)));
         T.root = new ArcNode(new Arc(top.point));
      }
      else{
         PointY pi=top.point;
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
         if(a.lValue.circleEvent == null && a.rValue.circleEvent == null){
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
            
            pjpi.left = new ArcNode(pjj);
            pjpi.right = pipj;
            pipj.left = new ArcNode(pii);
            pipj.right = new ArcNode(pjj);
            
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
   
   private void handleCircleEvent(Event top) {
      // TODO Auto-generated method stub
      
      //1. 
      
   }

   
}