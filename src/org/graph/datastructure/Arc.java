/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.graph.datastructure;

/**
 *
 * @author juan.karsten
 */
public class Arc implements Comparable<Arc>{
    public PointY point;
    public Event circleEvent;
    //pointer ke half edge ??????
    

    public Arc(PointY point, Event circleEvent) {
        this.point = point;
        this.circleEvent = circleEvent;
    }
    
    public Arc(PointY point) {
       this.point = point;
    }


    //arc diurutkan berdasarkan x
    @Override
    public int compareTo(Arc o) {
        int dx=(int)(point.x-o.point.y);
         return (dx==0)?(this.point.y - o.point.y):dx;
    }

    
}
