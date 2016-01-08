

import org.jfree.chart.ChartPanel; import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultKeyedValues2DDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


public class LineChart_AWT extends ApplicationFrame {
    public LineChart_AWT( String applicationTitle , String chartTitle ) {
        super(applicationTitle);
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "time","samples value", createDataset(), PlotOrientation.VERTICAL, true,true,false
        );
        ChartPanel chartPanel = new ChartPanel( lineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        setContentPane( chartPanel );
    }
    private DefaultCategoryDataset createDataset( ) {
        DefaultCategoryDataset dataset = new DefaultKeyedValues2DDataset();
        dataset.addValue( 15 , "samples", "1.4" );
        dataset.addValue( 30 , "samples" , "2.3");
        dataset.addValue( 60 , "samples" , "20.7" );
        dataset.addValue( 120 , "samples" , "29.4" );
        dataset.addValue( 240 , "samples" , "30.5" );
        dataset.addValue( 300 , "samples" , "50.7" );
        return dataset; }

    public static void main( String[ ] args ) {
        LineChart_AWT chart = new LineChart_AWT(
                "Scargle" ,
                "Numer of Samples vs time"
        );
        chart.pack( ); RefineryUtilities.centerFrameOnScreen( chart ); chart.setVisible( true );
    } }

