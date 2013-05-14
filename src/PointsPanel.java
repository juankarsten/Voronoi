//**************************************************************
//  PointsPanel.java       
//
//  Represents the primary panel for user to enter points.
//*************************************************************

import java.util.ArrayList;
import javax.swing.JPanel;

import org.graph.datastructure.Edge;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class PointsPanel extends JPanel {
   private static final long serialVersionUID = 1L;
   private ArrayList<Point> pointList;
   private ArrayList<Point> hull;
   private ArrayList<Point> closestPair;
   private ArrayList<Edge> edgeList;
   
   int autoCalculate = 0; 
   /* 1. incremental convex hull
      2. divide & conquer convex hull
      3. divide & conquer closest pair of points
      4. voronoi diagram
   */

   //------------------------------------------------------------
   //  Constructor: 
   //  Sets up this panel to listen for mouse events.       
   //-----------------------------------------------------------
   public PointsPanel() {
      pointList = new ArrayList<Point>();
      hull= new ArrayList<Point>();

      addMouseListener (new PointsListener());

      setBackground (Color.black);
      setPreferredSize (new Dimension(300, 200));
   }

   //------------------------------------------------------------
   //  Draws all of the points stored in the list.
   //-----------------------------------------------------------
   public void paintComponent (Graphics page) {
      super.paintComponent(page);

      page.setColor (Color.green);

      for (Point spot : pointList)
         page.fillOval (spot.x-3, spot.y-3, 7, 7);
 
      if(autoCalculate == 1 || autoCalculate == 2){ 
         // Convex Hull
        for(int ii=0; ii<hull.size()-1; ii++){
           page.drawLine( (int)hull.get(ii).getX() , (int)hull.get(ii).getY(), 
                 (int)hull.get(ii+1).getX(), (int)hull.get(ii+1).getY());
        }
        page.drawLine( (int)hull.get(hull.size()-1).getX() , (int)hull.get(hull.size()-1).getY(), 
              (int)hull.get(0).getX(), (int)hull.get(0).getY());
        page.drawString ("Area: " + area(), 5, 30);
      }
      else if(autoCalculate == 3){ 
         // Closest Pair
         page.drawLine( (int)(closestPair.get(0).getX()), (int)closestPair.get(0).getY(),
               (int)closestPair.get(1).getX(), (int)closestPair.get(1).getY());
         double jarak = closestPair.get(0).distance(closestPair.get(1));
         page.drawString("Distance: "+jarak, 5, 30);
         
      }
      else if(autoCalculate == 4){
         for(Edge edge: edgeList) if(edge.start!= null && edge.end != null){
            int x1 = edge.start.x;
            int y1 = edge.start.y;
            int x2 = edge.end.x;
            int y2 = edge.end.y;
            page.drawLine(x1, y1, x2, y2);
         }
      }
      page.drawString ("Count: " + pointList.size(), 5, 15);
      
   }

   public double area(){
      double area=0.0;
      int sz=hull.size();
      for(int ii=0; ii<sz; ii++){
         area+=hull.get(ii).x*hull.get((ii+1>=sz)?0:ii+1).y;
      }
      for(int ii=0; ii<sz; ii++){
         area-=hull.get(ii).y*hull.get((ii+1>=sz)?0:ii+1).x;
      }
      area*=0.5;
      return Math.abs(area);
   }

   public void clear(){
      pointList.clear();
      hull.clear();
      autoCalculate = 0;
      repaint();
   }

   //***********************************************************
   //  Represents the listener for mouse events.
   //***********************************************************
   private class PointsListener implements MouseListener {
      //-------------------------------------------------------
      //  Adds the current point to the list of points 
      //  and redraws
      //  the panel whenever the mouse button is pressed.
      //------------------------------------------------------
      public void mousePressed (MouseEvent event) {
         pointList.add(event.getPoint());
         if (autoCalculate == 1)
            createIncrement();
         else if(autoCalculate == 2)
            calculateConvexHullDC();
         else if(autoCalculate == 3)
            findClosestPair();
         repaint();
      }

      //-----------------------------------------------------
      //  Provide empty definitions for unused event methods.
      //-----------------------------------------------------
      public void mouseClicked (MouseEvent event) {}
      public void mouseReleased (MouseEvent event) {}
      public void mouseEntered (MouseEvent event) {}
      public void mouseExited (MouseEvent event) {}

           
   }

   public void calculateConvexHullDC(){
      
      if(!pointList.isEmpty()){
         hull=new ConvexHull(pointList).calculate();
         autoCalculate = 2;
         repaint();
      }
   }
   public void findClosestPair(){
      closestPair = new ClosestPair(pointList).findclosest();
      autoCalculate = 3;
      repaint();
      
   }
   
   public void fortune() {
      edgeList = new VoronoiDiagram(pointList).fortuneAlgorithm();
      autoCalculate = 4;
      repaint();
   }
   
   //**************incremental********************
   public void createIncrement(){
      Collections.sort(this.pointList, new PointComparator());
      System.out.println("list: "+pointList);

      autoCalculate = 1;
            
      hull=new ArrayList<Point>();
      if(pointList.isEmpty())return;
      hull.add(pointList.get(0));
      if(pointList.size()<2)return;
      hull.add(pointList.get(1));

      for(int ii=2; ii<pointList.size(); ii++){
         hull.add(pointList.get(ii));
         int jj=hull.size()-1;
         while(hull.size()>2 && isLeftTurn(hull.get(jj-2),hull.get(jj-1),hull.get(jj))){
            hull.remove(jj-1);
            jj--;
         }
      }

      //System.out.println("\nconvex hull upper: "+points);
      
      //point lower
      ArrayList<Point> pointsLower=new ArrayList<Point>();
      pointsLower.add(pointList.get(pointList.size()-1));
      pointsLower.add(pointList.get(pointList.size()-2));
      
      for(int ii=pointList.size()-3; ii>=0; ii--){
          pointsLower.add(pointList.get(ii));
          int jj=pointsLower.size()-1;
          while(pointsLower.size()>2 && isLeftTurn(pointsLower.get(jj-2),
              pointsLower.get(jj-1),pointsLower.get(jj))){
             //System.out.println("remove"+points.get(jj-1)+"\n\n");
             pointsLower.remove(jj-1);
             jj--;
             //jj=points.size()-1;
          }
       }
      
      //System.out.println("\nconvex hull lower: "+pointsLower);
      
      for(int ii=1; ii<pointsLower.size()-1; ii++){
        hull.add(pointsLower.get(ii));
      }
      
      System.out.println("\nconvex hull: "+hull);
      repaint();
   }

   public boolean isLeftTurn(Point a, Point b, Point c){
       double area=((double)(b.getX()-a.getX())*(c.getY()-a.getY()))-((double)(c.getX()-a.getX())*(b.getY()-a.getY()));
      //System.out.println(area+""+a+b+c);
      if (area>=0) return true;
      return false;
   }

   private class PointComparator implements Comparator<Point>{
      public int compare(Point a, Point b){
         int x=(int)Math.signum(a.getX()-b.getX());
         return (x==0)?((int) Math.signum(a.y - b.y)):x;
      }
   }
   
   
}