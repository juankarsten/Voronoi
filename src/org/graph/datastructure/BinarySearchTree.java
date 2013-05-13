package org.graph.datastructure;

public class BinarySearchTree {
   ArcNode root;
   
   public BinarySearchTree(ArcNode root) {
      this.root = root;
   }
   
   public void insert(ArcNode x){
      if (root == null){
         root = x;
      }
      else{
         ArcNode parent = root;
         
         while(!parent.isLeaf()){
            if(x.isLeftOf(parent))
               parent = parent.left;
            else
               parent = parent.right;
         }
         //TODO x dijadikan anak dari parent
         //jika x di di kiri parent:
            parent.rValue = parent.lValue;
            parent.lValue = x.lValue;
            parent.left = x;
            parent.right = new ArcNode(parent.rValue);
            
         //TODO jika x di kanan parent
      }
   }
   
}
