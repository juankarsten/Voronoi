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
public class PointY extends Point implements Comparable<PointY>{
      private static final long serialVersionUID = 1701444726214635657L;

      @Override
      public int compareTo(PointY o) {
         int dy=(int)(this.y-o.y);
         return (dy==0)?(this.x - o.x):dy;
      }
 }
