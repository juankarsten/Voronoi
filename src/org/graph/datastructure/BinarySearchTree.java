package org.graph.datastructure;

import java.awt.Point;

public class BinarySearchTree {
   public ArcNode root;
   
   public BinarySearchTree(){
      
   }
   public BinarySearchTree(ArcNode root) {
      this.root = root;
   }
   
   public boolean isEmpty(){
      return root == null;
   }
   public void delete(ArcNode y) {
      // TODO Auto-generated method stub
      
   }
   public Point getTripleLeftPoint(ArcNode y) {
      // TODO Auto-generated method stub
      return null;
   }
   public ArcNode leftOf(ArcNode y) {
      // TODO Auto-generated method stub
      return null;
   }
   
   public Point getRightCirclePoint(ArcNode pi){
       ArcNode pj=pi.getRightSibling();
       if(pj==null)return null;
       ArcNode pk=pj.getRightSibling();
       if(pk==null)return null;
       Point p2=circleCenter(pi.lValue.point, pj.lValue.point, pk.lValue.point);
       return p2;
   }
   
   public Point getLeftCirclePoint(ArcNode pi){
       ArcNode ph=pi.getLeftSibling();
       if(ph==null)return null;
       ArcNode pg=ph.getLeftSibling();
       if(pg==null)return null;
       Point p2=circleCenter(pg.lValue.point, ph.lValue.point, pi.lValue.point);
       return p2;
   }
   
   public Pair getCirclePoint(ArcNode pi){
       return new Pair(getRightCirclePoint(pi),getLeftCirclePoint(pi));
   }
   
   private Point circleCenter(Point A, Point B, Point C) {

    double yDelta_a = B.y - A.y;
    double xDelta_a = B.x - A.x;
    double yDelta_b = C.y - B.y;
    double xDelta_b = C.x - B.x;
    Point center = new Point();

    double aSlope = yDelta_a/xDelta_a;
    double bSlope = yDelta_b/xDelta_b;  
    double xcenter = (aSlope*bSlope*(A.y - C.y) + bSlope*(A.x + B.x)
        - aSlope*(B.x+C.x) )/(2* (bSlope-aSlope) );
    double ycenter = -1*(center.x - (A.x+B.x)/2)/aSlope +  (A.y+B.y)/2;

    ycenter+=Math.sqrt(Math.pow(xcenter-B.x,2)+Math.pow(ycenter-B.y,2));
    return new Point((int)xcenter, (int)ycenter);
  }
   
//   public void insert(ArcNode x){
//      if (root == null){
//         root = x;
//      }
//      else{
//         ArcNode parent = root;
//         ArcNode grandparent=root;
//         boolean left=true;
//         while(!parent.isLeaf()){
//            grandparent=parent;
//             if(x.isLeftOf(parent)){
//               parent = parent.left;
//               left=true;
//             }
//             else{
//               parent = parent.right;
//               left=false;
//             }
//         }
//         //TODO x dijadikan anak dari parent
//         //jika x di di kiri parent:
////            parent.rValue = parent.lValue;
////            parent.lValue = x.lValue;
////            parent.left = x;
////            parent.right = new ArcNode(parent.rValue);
//            
//         //TODO jika x di kanan parent
//         
//        
//         Arc pi=parent.lValue;
//         Arc pk=x.lValue;
//         ArcNode pipk=new ArcNode(pi,pk,true);
//         ArcNode pkpi=new ArcNode(pk,pi, false);
//         pipk.left=parent;
//         pipk.right=pkpi;
//         pkpi.left=x;
//         pkpi.right=parent;
//         if(parent==root){
//             root=pipk;
//         }
//         else if(left){
//             grandparent.left=pipk;
//         }else{
//             grandparent.right=pipk;
//         }
//         
//         Pair pair=pipk.getBreakPoint(x.lValue.point.y);
//         
//      }
//   }
   
}
