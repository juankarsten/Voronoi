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
public class Arc{
    public Point point;
    //pointer ke half edge ??????
    

    public Arc(Point point) {
       this.point = point;
    }

    @Override
    public boolean equals(Object obj) {
        Arc arc=(Arc)obj;
        if(arc.point.x==point.x&&arc.point.y==point.y) return true;
        return false;
//        return super.equals(obj); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
