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
         T.insert(new ArcNode(new Arc(top.point)));
      }
      else{
            PointY pi=top.point;
           //2. Search in T for the arc a vertically above pi.
            ArcNode a = T.root;
            // penyimpanan parent a dan posisi a terhadap pa
            ArcNode pa = null;
            boolean left = true;
                  
            while(a.isInternalNode()){
               // jika di titik pi di kanan breakpoint kanan: 
               Pair breakpoint = a.getBreakPoint(pi.y);
               pa = a;
               if (pi.x < breakpoint.p2.x){
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
             //3. Replace the leaf of T that represents a with a subtree having three leaves.
             //TODO
               
            }
            
            //4.create new half edge in voronoi diagram
           //?????????????belum ada DCEL
            
            //5.check 
            //
            
      }
      
   }
   
   private void handleCircleEvent(Event top) {
      // TODO Auto-generated method stub
      
      //1. 
      
   }

   
}