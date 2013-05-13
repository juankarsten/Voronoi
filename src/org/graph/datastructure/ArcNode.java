package org.graph.datastructure;

import java.awt.Point;
import java.awt.geom.Point2D;

public class ArcNode {
    //anak kiri dan kanan
   ArcNode left, right;
   
   Arc lValue, rValue;
   
   Edge edge;

   ArcNode(Arc x){
      lValue = x;
   }
   ArcNode(Arc x, Arc y){
      lValue = x;
      rValue = y;
   }
   
   public boolean isLeaf(){
      return rValue == null;
   }
   public boolean isInternalNode(){
      return lValue != null;
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
   
   public Pair getBreakPoint(int ly){
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
       
       double xx1=(-b1-Math.sqrt(b1*b1-4*a1*c1))/(2*a1);
       double yy1=(1/(2*(py1-ly)))*(xx1*xx1-2*px1*xx1+px1*px1+py1*py1-ly*ly);
       
       double xx2=(-b1+Math.sqrt(b1*b1-4*a1*c1))/(2*a1);
       double yy2=(1/(2*(py2-ly)))*(xx2*xx2-2*px2*xx2+px2*px2+py2*py2-ly*ly);
       
       
       return new Pair(new Point((int)xx1, (int)yy1), new Point((int)xx2, (int)yy2));
   }
}
