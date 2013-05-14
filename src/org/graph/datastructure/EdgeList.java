/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.graph.datastructure;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author juan.karsten
 */
public class EdgeList {
    public ArrayList<Edge> edges=new ArrayList<Edge>();
    
    public void addEdge(Point point, Point point2){
       edges.add(new Edge(point,point2));
    }

   public void addEdge(Edge edge) {
      edges.add(edge);
   }
}
