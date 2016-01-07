import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.util.ArrayList;
import javax.swing.*;

public class LombScargle extends JPanel {


    final int PAD = 20;
     ArrayList<Double> data;



    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        // Draw ordinate.
        g2.draw(new Line2D.Double(PAD, PAD, PAD, h-PAD));
        // Draw abcissa.
        g2.draw(new Line2D.Double(PAD, h-PAD, w-PAD, h-PAD));
        // Draw labels.
        Font font = g2.getFont();
        FontRenderContext frc = g2.getFontRenderContext();
        LineMetrics lm = font.getLineMetrics("0", frc);
        float sh = lm.getAscent() + lm.getDescent();
        // Ordinate label.
        String s = "data";
        float sy = PAD + ((h - 2*PAD) - s.length()*sh)/2 + lm.getAscent();
        for(int i = 0; i < s.length(); i++) {
            String letter = String.valueOf(s.charAt(i));
            float sw = (float)font.getStringBounds(letter, frc).getWidth();
            float sx = (PAD - sw)/2;
            g2.drawString(letter, sx, sy);
            sy += sh;
        }
        // Abcissa label.
        s = "x axis";
        sy = h - PAD + (PAD - sh)/2 + lm.getAscent();
        float sw = (float)font.getStringBounds(s, frc).getWidth();
        float sx = (w - sw)/2;
        g2.drawString(s, sx, sy);
        // Draw lines.
        double xInc = (double)(w - 2*PAD)/(data.size()-1);
        double scale = (double)(h - 2*PAD)/getMax();
        g2.setPaint(Color.green.darker());
        for(int i = 0; i < data.size()-1; i++) {
            double x1 = PAD + i*xInc;
            double y1 = h - PAD - scale*data.get(i);
            double x2 = PAD + (i+1)*xInc;
            double y2 = h - PAD - scale*data.get(i+1);
            g2.draw(new Line2D.Double(x1, y1, x2, y2));
        }
        // Mark data points.
        g2.setPaint(Color.red);
        for(int i = 0; i < data.size(); i++) {
            double x = PAD + i*xInc;
            double y = h - PAD - scale*data.get(i);
            g2.fill(new Ellipse2D.Double(x-2, y-2, 4, 4));
        }
    }

    private Double getMax() {
        Double max = -Double.MAX_VALUE;
        for(int i = 0; i < data.size(); i++) {
            if(data.get(i) > max)
                max = data.get(i);
        }
        return max;
    }

    public static void main(String[] args) {


        //getting hold of data
        ReadData dataObject = new ReadData();
        ArrayList<Double[]> samples = dataObject.run();
        ArrayList<Double> data = new ArrayList<>();


        //just checking data
        for (Double[] sample: samples){

          //  System.out.println("Input [time= " + sample[0] + " , sample= " + sample[1] + " , error= " + sample[2] + "]");
            data.add(sample[0]);
        }


        for (Double dat : data){

            System.out.println("Input [time= " + dat + "]");

        }



       // XYSeriesCollection ds = new XYSeriesCollection();












//
//
//        JFrame f = new JFrame();
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        f.add(new LombScargle());
//        f.setSize(400,400);
//        f.setLocation(200,200);
//        f.setVisible(true);
    }
}