/**************************************
*
*   -------------------------
*    Renderer.java
*   -------------------------
*
*  This class will diplay the Lomb-Scargle perdiogram and the input data using java swing and jfreechart libraries.
*  @author to display multiple panels by Sivan Jan 2016
*
*/


import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYErrorRenderer;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.YIntervalSeries;
import org.jfree.data.xy.YIntervalSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * A line chart with error bars.
 */
public class Renderer extends ApplicationFrame {

    private static ArrayList<Double[]> samples;
    private static ArrayList<Double[]> results;

    /**
     * Constructs the renderer class.
     *
     * @uses  Top and Bottom pannels.
     *
     * @param title  the frame title.
     */
    public Renderer(String title) {
        super(title);
        JPanel chartPanel = createDemoPanel();

        JPanel chartPanelModeled = createDemoPanel2();

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        getContentPane().add(topPanel);

        chartPanel.setPreferredSize(new java.awt.Dimension(1200, 600));


        // Create a splitter pane
        JSplitPane splitPaneV = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        topPanel.add(splitPaneV, BorderLayout.CENTER);

        splitPaneV.setBottomComponent(chartPanel);
        splitPaneV.setTopComponent(chartPanelModeled);


        setContentPane(topPanel);
    }

    /**
     * Creates the data input chart.
     *
     * @param dataset  the dataset.
     *
     * @return Input chart.
     */
    private static JFreeChart createChartA(IntervalXYDataset dataset) {
        NumberAxis xAxis = new NumberAxis("time");
        NumberAxis yAxis = new NumberAxis("samples");
        XYErrorRenderer renderer = new XYErrorRenderer();
        renderer.setBaseLinesVisible(true);
        renderer.setBaseShapesVisible(false);
        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer);

        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);
        renderer.setErrorPaint(Color.blue);
        renderer.setBasePaint(Color.black);
        renderer.setSeriesPaint(0, Color.black);
        renderer.setSeriesPaint(1, Color.blue);
        renderer.setSeriesStroke(0, new BasicStroke(1.5f,   // Width
                BasicStroke.CAP_SQUARE,     // End cap
                BasicStroke.JOIN_MITER,     // Join style
                10.0f                      // Miter limit
        ));

        renderer.setSeriesFillPaint(0, Color.black);


        renderer.setErrorStroke(new BasicStroke(2.5f,   // Width
                BasicStroke.CAP_SQUARE,    // End cap
                BasicStroke.JOIN_MITER));


        JFreeChart chart = new JFreeChart("Input Data", plot);
        chart.setBackgroundPaint(Color.white);

        return chart;
    }

    /**
     * Creates Scargle chart.
     *
     * @param dataset  the dataset.
     *
     * @return A chart.
     */
    private static JFreeChart createChartB(IntervalXYDataset dataset) {
        NumberAxis xAxis = new NumberAxis("time");
        NumberAxis yAxis = new NumberAxis("samples");
        XYErrorRenderer renderer = new XYErrorRenderer();
        Color transparent  = new Color(0,0,0,0);
        renderer.setBaseLinesVisible(true);
        renderer.setBaseShapesVisible(false);
        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer);
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);
        renderer.setErrorPaint(transparent);
        renderer.setBasePaint(Color.black);
        renderer.setSeriesPaint(0, Color.black);
        renderer.setSeriesStroke(0, new BasicStroke(2.5f,   // Width
                BasicStroke.CAP_ROUND,     // End cap
                BasicStroke.JOIN_ROUND,     // Join style
                2.0f                      // Miter limit
        ));
        renderer.setSeriesFillPaint(0, Color.black);
        JFreeChart chart = new JFreeChart("Mean Square with Scargle-Lomb Method", plot);
        chart.setBackgroundPaint(Color.white);

        return chart;
    }

    /**
     * Creates input data  dataset.
     */
    private static IntervalXYDataset createDataset1(ArrayList<Double[]> samples, ArrayList<Double[]> results)  {
        YIntervalSeriesCollection dataset = new YIntervalSeriesCollection();
        YIntervalSeries s1 = new YIntervalSeries("Samples");;
        YIntervalSeries s3 = new YIntervalSeries("Sampling Error");

        //creating dataset with error values
        for (Double[] sample: samples){
            double positive_err = sample[1] + sample[2]*0.5;
            double negative_err = sample[1] - sample[2]*0.5;
            s1.add(sample[0], sample[1],negative_err ,positive_err);
        }


        dataset.addSeries(s1);
        dataset.addSeries(s3);
        return dataset;
    }

    /**
     * Creates a Scargle's dataset.
     */
    private static IntervalXYDataset createDataset2(ArrayList<Double[]> samples, ArrayList<Double[]> results)  {
        YIntervalSeriesCollection dataset = new YIntervalSeriesCollection();

        YIntervalSeries s2 = new YIntervalSeries("Scargle");

        for (int i=0; i< results.size(); i++){
            double positive_err = results.get(i)[1];
            double negative_err = results.get(i)[1];
            s2.add(i, results.get(i)[1],negative_err ,positive_err);
        }

        dataset.addSeries(s2);

        return dataset;
    }

    /**
     * Creates first panel for display.
     *
     * @return A panel.
     */
    public static JPanel createDemoPanel() {

        return new ChartPanel(createChartA(createDataset1(samples, results)));

    }

    /**
     * Creates second panel for display.
     *
     * @return A panel.
     */
    public static JPanel createDemoPanel2() {


        return new ChartPanel(createChartB(createDataset2(samples, results)));

    }

    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(String[] args) {


        LombScargle lombScargle = new LombScargle();

        samples = lombScargle.readData();

        results = lombScargle.caluculateLombScargle(samples.size());


        Renderer chart = new Renderer(
                "Sivan's Assignment: Scargle-Lomb chart");

        chart.pack();

        RefineryUtilities.centerFrameOnScreen(chart);

        chart.setVisible(true);
    }

}