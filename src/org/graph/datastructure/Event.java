/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.graph.datastructure;

/**
 *
 * @author juan.karsten
 */
public class Event{
      
      //event point
      public PointY point;
      
      //jika circle event harus nyimpen pointer ke leaf tree.
      public ArcNode arcNode;
      
      
      public boolean site;
      public Event(PointY point,ArcNode arcNode){
         this.point = point;
         this.arcNode=arcNode;
      }
  }
