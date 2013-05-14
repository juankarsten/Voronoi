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
public class Event implements Comparable<Event>{
      
      //event point
      public Point point;
      
      //jika circle event harus nyimpen pointer ke leaf tree.
      public ArcNode arcNode;
      
      
      public boolean site;
      public Event(Point point,ArcNode arcNode){
         this.point = point;
         this.arcNode=arcNode;
         site = false;
      }
      
      public Event(Point p) {
         this.point = p;
         site = true;
      }

      @Override
      public int compareTo(Event o) {
         int dy=(int)(this.point.y-o.point.y);
         return (dy==0)?(this.point.x - o.point.x):dy;
      }
  }
