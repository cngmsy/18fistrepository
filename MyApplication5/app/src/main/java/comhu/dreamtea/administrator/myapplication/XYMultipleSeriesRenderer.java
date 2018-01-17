package comhu.dreamtea.administrator.myapplication;

import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2018/1/14.
 */

public class XYMultipleSeriesRenderer implements AttributeSet {
    private String chartTitle;
    private String XTitle;
    private String YTitle;
    private double XAxisMin;
    private double XAxisMax;
    private double YAxisMin;
    private double YAxisMax;
    private int axesColor;
    private int labelsColor;
    private boolean showGrid;
    private int gridColor;
    private int XLabels;
    private int YLabels;
    private float pointSize;
    private boolean showLegend;
    private Paint.Align yLabelsAlign;
    private XYSeriesRenderer xy;

    public String getChartTitle() {
        return chartTitle;
    }

    public void setChartTitle(String chartTitle) {
        this.chartTitle = chartTitle;
    }

    public String getXTitle() {
        return XTitle;
    }

    public void setXTitle(String XTitle) {
        this.XTitle = XTitle;
    }

    public String getYTitle() {
        return YTitle;
    }

    public void setYTitle(String YTitle) {
        this.YTitle = YTitle;
    }

    public double getXAxisMin() {
        return XAxisMin;
    }

    public void setXAxisMin(double XAxisMin) {
        this.XAxisMin = XAxisMin;
    }

    public double getXAxisMax() {
        return XAxisMax;
    }

    public void setXAxisMax(double XAxisMax) {
        this.XAxisMax = XAxisMax;
    }

    public double getYAxisMin() {
        return YAxisMin;
    }

    public void setYAxisMin(double YAxisMin) {
        this.YAxisMin = YAxisMin;
    }

    public double getYAxisMax() {
        return YAxisMax;
    }

    public void setYAxisMax(double YAxisMax) {
        this.YAxisMax = YAxisMax;
    }

    public int getAxesColor() {
        return axesColor;
    }

    public void setAxesColor(int axesColor) {
        this.axesColor = axesColor;
    }

    public int getLabelsColor() {
        return labelsColor;
    }

    public void setLabelsColor(int labelsColor) {
        this.labelsColor = labelsColor;
    }

    public boolean isShowGrid() {
        return showGrid;
    }

    public void setShowGrid(boolean showGrid) {
        this.showGrid = showGrid;
    }

    public int getGridColor() {
        return gridColor;
    }

    public void setGridColor(int gridColor) {
        this.gridColor = gridColor;
    }

    public int getXLabels() {
        return XLabels;
    }

    public void setXLabels(int XLabels) {
        this.XLabels = XLabels;
    }

    public int getYLabels() {
        return YLabels;
    }

    public void setYLabels(int YLabels) {
        this.YLabels = YLabels;
    }

    public float getPointSize() {
        return pointSize;
    }

    public void setPointSize(float pointSize) {
        this.pointSize = pointSize;
    }

    public Boolean getShowLegend() {
        return showLegend;
    }

    public void setShowLegend(boolean showLegend) {
        this.showLegend = showLegend;
    }

    public Paint.Align getyLabelsAlign() {
        return yLabelsAlign;
    }

    public void setyLabelsAlign(Paint.Align yLabelsAlign) {
        this.yLabelsAlign = yLabelsAlign;
    }

    public void addSeriesRenderer(XYSeriesRenderer r) {
        this.xy=r;
    }

    @Override
    public int getAttributeCount() {
        return 0;
    }

    @Override
    public String getAttributeName(int index) {
        return null;
    }

    @Override
    public String getAttributeValue(int index) {
        return null;
    }

    @Override
    public String getAttributeValue(String namespace, String name) {
        return null;
    }

    @Override
    public String getPositionDescription() {
        return null;
    }

    @Override
    public int getAttributeNameResource(int index) {
        return 0;
    }

    @Override
    public int getAttributeListValue(String namespace, String attribute, String[] options, int defaultValue) {
        return 0;
    }

    @Override
    public boolean getAttributeBooleanValue(String namespace, String attribute, boolean defaultValue) {
        return false;
    }

    @Override
    public int getAttributeResourceValue(String namespace, String attribute, int defaultValue) {
        return 0;
    }

    @Override
    public int getAttributeIntValue(String namespace, String attribute, int defaultValue) {
        return 0;
    }

    @Override
    public int getAttributeUnsignedIntValue(String namespace, String attribute, int defaultValue) {
        return 0;
    }

    @Override
    public float getAttributeFloatValue(String namespace, String attribute, float defaultValue) {
        return 0;
    }

    @Override
    public int getAttributeListValue(int index, String[] options, int defaultValue) {
        return 0;
    }

    @Override
    public boolean getAttributeBooleanValue(int index, boolean defaultValue) {
        return false;
    }

    @Override
    public int getAttributeResourceValue(int index, int defaultValue) {
        return 0;
    }

    @Override
    public int getAttributeIntValue(int index, int defaultValue) {
        return 0;
    }

    @Override
    public int getAttributeUnsignedIntValue(int index, int defaultValue) {
        return 0;
    }

    @Override
    public float getAttributeFloatValue(int index, float defaultValue) {
        return 0;
    }

    @Override
    public String getIdAttribute() {
        return null;
    }

    @Override
    public String getClassAttribute() {
        return null;
    }

    @Override
    public int getIdAttributeResourceValue(int defaultValue) {
        return 0;
    }

    @Override
    public int getStyleAttribute() {
        return 0;
    }
}
