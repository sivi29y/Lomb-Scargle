/* -------------------------
* XYErrorRendererDemo2.java
* -------------------------
* (C) Copyright 2007, by Object Refinery Limited.
*
*/


import java.awt.*;
import java.util.ArrayList;

import javax.swing.JPanel;

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
public class XYErrorRendererDemo2 extends ApplicationFrame {

    /**
     * Constructs the demo application.
     *
     * @param title  the frame title.
     */
    public XYErrorRendererDemo2(String title) {
        super(title);
        JPanel chartPanel = createDemoPanel();
        chartPanel.setPreferredSize(new java.awt.Dimension(1200, 600));

        setContentPane(chartPanel);
    }

    /**
     * Creates a chart.
     *
     * @param dataset  the dataset.
     *
     * @return The chart.
     */
    private static JFreeChart createChart(IntervalXYDataset dataset) {
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
        renderer.setSeriesStroke(0, new BasicStroke(1.5f,                      // Width
                BasicStroke.CAP_SQUARE,    // End cap
                BasicStroke.JOIN_MITER,    // Join style
                10.0f  //,                     // Miter limit
                //new float[]{21.0f, 9.0f, 1.0f, 9.0f}, // Dash pattern
               // 10

        ));         // Dash phase, true);

        renderer.setSeriesFillPaint(0, Color.black);


        renderer.setErrorStroke(new BasicStroke(2.5f,   // Width
                BasicStroke.CAP_SQUARE,    // End cap
                BasicStroke.JOIN_MITER));   // Dash phase


        JFreeChart chart = new JFreeChart("Mean Square with Scargle-Lomb Method", plot);
        chart.setBackgroundPaint(Color.white);

        return chart;
    }

    /**
     * Creates a sample dataset.
     */
    private static IntervalXYDataset createDataset(ArrayList<Double[]> samples)  {
        YIntervalSeriesCollection dataset = new YIntervalSeriesCollection();
        YIntervalSeries s1 = new YIntervalSeries("Samples");
        YIntervalSeries s2 = new YIntervalSeries("Sampling Error");


        //just checking data
        for (Double[] sample: samples){

            //  System.out.println("Input [time= " + sample[0] + " , sample= " + sample[1] + " , error= " + sample[2] + "]");\
            double positive_err = sample[1] +sample[2]*0.5;
            double negative_err = sample[1] - sample[2]*0.5;
           s1.add(sample[0], sample[1],negative_err ,positive_err );
        }

//
//        s1.add(1.0, 10.0, 9.0, 11.0);
//        s1.add(10.0, 6.1, 4.34, 7.54);
//        s1.add(17.8, 4.5, 3.1, 5.8);
//        YIntervalSeries s2 = new YIntervalSeries("Series 2");
//        s2.add(3.0, 7.0, 6.0, 8.0);
//        s2.add(13.0, 13.0, 11.5, 14.5);
//        s2.add(24.0, 16.1, 14.34, 17.54);
        dataset.addSeries(s1);
        dataset.addSeries(s2);
        return dataset;
    }

    /**
     * Creates a panel for the demo.
     *
     * @return A panel.
     */
    public static JPanel createDemoPanel() {

        ReadData dataObject = new ReadData();
        ArrayList<Double[]> samples = dataObject.run();


        return new ChartPanel(createChart(createDataset(samples)));
    }

    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(String[] args) {
        XYErrorRendererDemo2 chart = new XYErrorRendererDemo2(
                "Sivan Langer's Assignment: Scargle-Lomb chart");

        chart.pack();

        RefineryUtilities.centerFrameOnScreen(chart);

        chart.setVisible(true);
    }

}