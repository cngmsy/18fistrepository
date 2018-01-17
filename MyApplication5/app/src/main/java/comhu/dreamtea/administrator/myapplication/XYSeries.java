package comhu.dreamtea.administrator.myapplication;

/**
 * Created by Administrator on 2018/1/14.
 */

public class XYSeries {
    private String  xin;
    private int itemCount=100;
    private int x =0;
    private int  y=0;

    public XYSeries(String xin) {

    }

    public String getXin() {
        return xin;
    }

    public void setXin(String xin) {
        this.xin = xin;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void clear() {

    }

    public void add(int addX, double addY) {
    }
}
