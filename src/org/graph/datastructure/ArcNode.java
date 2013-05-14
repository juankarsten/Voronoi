package org.graph.datastructure;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class ArcNode {
    //anak kiri dan kanan
   public ArcNode left, right;
   
   public Arc lValue, rValue;
   public boolean lBreakPoint;
   
   public ArcNode parent;

   public Edge edge;

   public Event circleEvent;

   public ArcNode(Arc x, ArcNode parent){
      lValue = x;
      this.parent=parent;
   }
   public ArcNode(Arc x, Arc y, boolean lBreakPoint, ArcNode parent){
      lValue = x;
      rValue = y;
      this.lBreakPoint = true;
      this.parent=parent;
   }
   
   public boolean isLeaf(){
      return rValue == null;
   }
   public boolean isInternalNode(){
      return rValue != null;
   }
   
   public boolean isLeftOf(ArcNode x){
      //TODO hitung posisi terhadap arc x
       int ly=this.lValue.point.y;
       
       int py1=x.lValue.point.y;
       int px1=x.lValue.point.x;
       double a1=(double)1/(2*(py1-ly));
       double b1=(double)2*px1/(2*(py1-ly));
       double c1=(double)(px1*px1+py1*py1-ly*ly)/(2*(py1-ly));
       
       int py2=x.rValue.point.y;
       int px2=x.rValue.point.x;
       double a2=(double)1/(2*(py2-ly));
       double b2=(double)2*px2/(2*(py2-ly));
       double c2=(double)(px2*px2+py2*py2-ly*ly)/(2*(py2-ly));
       
       a1=a1-a2;
       b1=b1-b2;
       c1=c1-c2;
       
       double xx=(-b1-Math.sqrt(b1*b1-4*a1*c1))/(2*a1);
       
       return lValue.point.x<xx;
   }
   
   public Point getBreakPoint(int ly){
      //TODO hitung posisi terhadap arc x
       
       int py1=lValue.point.y;
       int px1=lValue.point.x;
       double a1=(double)1/(2*(py1-ly));
       double b1=(double)2*px1/(2*(py1-ly));
       double c1=(double)(px1*px1+py1*py1-ly*ly)/(2*(py1-ly));
       
       int py2=rValue.point.y;
       int px2=rValue.point.x;
       double a2=(double)1/(2*(py2-ly));
       double b2=(double)2*px2/(2*(py2-ly));
       double c2=(double)(px2*px2+py2*py2-ly*ly)/(2*(py2-ly));
       
       a1=a1-a2;
       b1=b1-b2;
       c1=c1-c2;
       
       double xx,yy;
       try{
          if(lBreakPoint){
             xx=(-b1-Math.sqrt(b1*b1-4*a1*c1))/(2*a1);
             yy=(1/(2*(py1-ly)))*(xx*xx-2*px1*xx+px1*px1+py1*py1-ly*ly);
          } else{
             xx=(-b1+Math.sqrt(b1*b1-4*a1*c1))/(2*a1);
             yy=(1/(2*(py2-ly)))*(xx*xx-2*px2*xx+px2*px2+py2*py2-ly*ly);
          }
          System.out.println("TidakNull: "+lValue.point+rValue.point+xx+","+yy+"ly:"+ly);
          return new Point((int) xx, (int) yy);
       }
       catch(ArithmeticException ae){
          System.out.println("Breakpoint Null:");
          System.out.println("ly="+ly+", "+lValue.point+", "+rValue.point);
          return null;
       }
       
   }

   
   
   
   public ArcNode getRightSibling(){
       //jika pjpi root ga mungkin ada sibling di kanan
       ArcNode pipj=this;
//       if(pipj.parent==null)return null;
//       while(pipj.parent.right==pipj){
//           pipj=pipj.parent;
//           if(pipj==null)return null;
//       }
//       pipj=pipj.parent;
//       ArcNode kanan=pipj.right;
//       while(!kanan.isLeaf()){
//           kanan=kanan.left;
//       }
//       return kanan;
       if(pipj.parent==null)return null;
       boolean finish=false;
       while(!finish){
           ArcNode parent=pipj.parent;
           if(parent==null){
               return null;
           }
           if(parent.lValue.equals(pipj.lValue)){
               finish=true;
           }
           pipj=parent;
       }
       ArcNode kanan=pipj.right;
         while(!kanan.isLeaf()){
           kanan=kanan.left;
       }
       return kanan;
   }
   
//   public ArcNode getLeftSibling(){
//       //jika pjpi root ga mungkin ada sibling di kanan
//       ArcNode pipj=this;
//       if(pipj.parent==null)return null;
//       while(pipj.parent.left==pipj){
//           pipj=pipj.parent;
//           if(pipj==null)return null;
//       }
//       pipj=pipj.parent;
//       ArcNode kiri=pipj;
//       while(!kiri.isLeaf()){
//           kiri=kiri.right;
//       }
//       return kiri;
//   }
   
public ArcNode getLeftSibling(){
       //jika pjpi root ga mungkin ada sibling di kanan
       ArcNode pipj=this;
       if(pipj.parent==null)return null;
       boolean finish=false;
       while(!finish){
           ArcNode parent=pipj.parent;
           if(parent==null){
               return null;
           }
           if(parent.rValue.equals(pipj.lValue)){
               finish=true;
           }
           pipj=parent;
       }
       ArcNode kiri=pipj.left;
         while(!kiri.isLeaf()){
           kiri=kiri.right;
       }
       return kiri;
   }   
}
