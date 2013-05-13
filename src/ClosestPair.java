
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ristek1
 */
public class ClosestPair {

    ArrayList<Point> pointList;

    public ClosestPair(ArrayList<Point> points) {
        this.pointList = points;
    }

    public ArrayList<Point> findclosest() {
        ArrayList<Point> sortX = new ArrayList<Point>();
        ArrayList<Point> sortY = new ArrayList<Point>();

        for(int ii=0;ii<pointList.size(); ii++){
            sortX.add(pointList.get(ii));
            sortY.add(pointList.get(ii));     
        }
        
        
        //sorting berdasarkan x dan y
        Collections.sort(sortX, new ComparatorX());
        Collections.sort(sortY, new ComparatorY());
        
        //System.out.println();
        //System.out.println(sortX);
        //System.out.println(sortY);
        
//        System.out.println(findBruteForce(pointList, 0, pointList.size()-1));
        Result dac=divideandconquer(sortX, sortY, 0, pointList.size()-1);
        System.out.println(dac);
        
        ArrayList<Point >res = new ArrayList<Point>();
        res.add(dac.a);
        res.add(dac.b);
        return res;
    }

    public Result findBruteForce(ArrayList<Point> points, int awal, int akhir) {
        double min = Double.MAX_VALUE;
        Point a = null, b = null;
        for (int ii = awal; ii <= akhir-1; ii++) {
            for (int jj = ii + 1; jj <= akhir; jj++) {
                double temp = findDistance(points.get(ii), points.get(jj));
                if (min > temp) {
                    min = temp;
                    a = points.get(ii);
                    b = points.get(jj);
                }
            }
        }
        return new Result(min, a, b);
    }

    private Result divideandconquer(ArrayList<Point> xx, ArrayList<Point> yy, int awal, int akhir) {
        //BASE CASE
        //if(akhir<=awal)return new Result(Double.MAX_VALUE, null, null);
        if (akhir - awal <= 2) {
            return findBruteForce(xx, awal, akhir);
        }

        //CARI TENGAH
        int tengah = (awal + akhir) / 2;

        //BREAK Y INTO YL AND YR
        double mid=xx.get(tengah).x;
        ArrayList<Point> yyL=new ArrayList<Point>();
        ArrayList<Point> yyR= new ArrayList<Point>();
        for(int ii=0; ii<yy.size(); ii++){
            Point temp=yy.get(ii);
            if(mid<temp.x){
                yyR.add(temp);
            }else{
                yyL.add(temp);
            }
        }
        
        //System.out.println(yyL+" "+yyR+" "+xx);
        //CARI MINIMUM KIRI DAN KANAN
        Result left = divideandconquer(xx, yyL, awal, tengah);
        Result right = divideandconquer(xx, yyR, tengah + 1, akhir);
        Result min = null;
        if (left.distance <= right.distance) {
            min = left;
        } else {
            min = right;
        }

        //POINT TENGAH
        Point median = xx.get(tengah + 1);
        
        //CARI YANG DALAM DELTA
        ArrayList<Point> delta = new ArrayList<Point>();
        for (int kk = 0; kk < yy.size(); kk++) {
            if (Math.abs(median.x - yy.get(kk).x) < min.distance ){
                 delta.add(yy.get(kk));
            }
        }

        //CARI APAKAH ADA YG KECIL DARI KIRI DAN KANAN
        
        for(int ii=0; ii<delta.size()-1; ii++){
            for(int jj=ii+1; jj<delta.size(); jj++){
                if(Math.abs(delta.get(ii).y -delta.get(jj).y) >= min.distance)break;
                double distance=findDistance(delta.get(ii), delta.get(jj));
                if(min.distance>distance){
                    min.distance=distance;
                    min.a=delta.get(ii);
                    min.b=delta.get(jj);
                }
            }
        }

        return min;
    }


    public double findDistance(Point a, Point b) {
        return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
    }

    class ComparatorX implements Comparator<Point> {

        @Override
        public int compare(Point o1, Point o2) {
            int temp = o1.x - o2.x;
            return (temp != 0) ? temp : o1.y - o2.y;
        }
    }

    class ComparatorY implements Comparator<Point> {

        @Override
        public int compare(Point o1, Point o2) {
            int temp = o1.y - o2.y;
            return (temp != 0) ? temp : o1.x - o2.x;
        }
    }
    
    public class Result{
       public double distance;
       public Point a;
       public Point b;

   public Result(double d, Point a, Point b) {
       distance=d;
       this.a=a;
       this.b=b;
   }

   @Override
   public String toString() {
       return "jarak= "+distance+"; A= "+a+"; B= "+b; //To change body of generated methods, choose Tools | Templates.
   }
}
}
