/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.graph.datastructure;

import java.awt.Point;

/**
 *
 * @author juan.karsten
 */
public class Edge {
    public Point start;
    public Point end;
    
    public Edge(Point a, Point b){
       start = a;
       end = b;
    }
}
