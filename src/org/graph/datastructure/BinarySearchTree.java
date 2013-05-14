package org.graph.datastructure;

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
   
   public void insert(ArcNode x){
      if (root == null){
         root = x;
      }
      else{
         ArcNode parent = root;
         ArcNode grandparent=root;
         boolean left=true;
         while(!parent.isLeaf()){
            grandparent=parent;
             if(x.isLeftOf(parent)){
               parent = parent.left;
               left=true;
             }
             else{
               parent = parent.right;
               left=false;
             }
         }
         //TODO x dijadikan anak dari parent
         //jika x di di kiri parent:
//            parent.rValue = parent.lValue;
//            parent.lValue = x.lValue;
//            parent.left = x;
//            parent.right = new ArcNode(parent.rValue);
            
         //TODO jika x di kanan parent
         
        
         Arc pi=parent.lValue;
         Arc pk=x.lValue;
         ArcNode pipk=new ArcNode(pi,pk);
         ArcNode pkpi=new ArcNode(pk,pi);
         pipk.left=parent;
         pipk.right=pkpi;
         pkpi.left=x;
         pkpi.right=parent;
         if(parent==root){
             root=pipk;
         }
         else if(left){
             grandparent.left=pipk;
         }else{
             grandparent.right=pipk;
         }
         
         Pair pair=pipk.getBreakPoint(x.lValue.point.y);
         
      }
   }
   
}
