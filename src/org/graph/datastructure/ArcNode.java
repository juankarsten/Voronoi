package org.graph.datastructure;

public class ArcNode {
   ArcNode left, right;
   Arc lValue, rValue;

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
   
   public boolean isLeftOf(Arc x){
      //TODO hitung posisi terhadap arc x
   }
}
