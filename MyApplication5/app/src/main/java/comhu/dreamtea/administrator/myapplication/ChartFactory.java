package comhu.dreamtea.administrator.myapplication;

/**
 * Created by Administrator on 2018/1/14.
 */

public class ChartFactory {


    public static GraphicalView getLineChartView(MainActivity mainActivity, XYMultipleSeriesDataset mDataset, XYMultipleSeriesRenderer renderer) {
        XYChart  xy= new XYChart(mainActivity);
        return  new GraphicalView(mainActivity);
    }
}
