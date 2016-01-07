import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadData {

    ArrayList<Double[]> input = new ArrayList<>();
    ArrayList<Sample> sampleLists = new ArrayList<>();


//    public static void main(String[] args) {
//
//        ReadData obj = new ReadData();
//        obj.run();
//
//    }

    public ArrayList<Double[]> run() {

        String dataFile = "/Users/user/IdeaProjects/Lomb-Scargle/data/sample_for_sivan.dat";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = " ";

        try {


            br = new BufferedReader(new FileReader(dataFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                // String[] country = line.split(cvsSplitBy);
                if (line != null) {

                    String clean = line.trim();
                    String[] attributes = clean.split(cvsSplitBy);


                  // Sample sample_row = createSample(attributes);

                   //sampleLists.add(sample_row);

                    Double[] doubles = {Double.parseDouble(attributes[0]), Double.parseDouble(attributes[1]), Double.parseDouble(attributes[2])};
                    input.add(doubles);
                   // System.out.println("Input [time= " + attributes[0] + " , sample= " + attributes[1] + " , error= " + attributes[2] + "]");

                }


            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Reading data done ...");

        return input;
    }

    private static Sample createSample(String[] metadata) {
        double time = Double.parseDouble(metadata[0]);
        double sample = Double.parseDouble(metadata[1]);
        double err =Double.parseDouble(metadata[2]);

        // create and return book of this metadata
        return new Sample(time, sample, err);


    }

    static class Sample {
        private double time;
        private double sample;
        private double err;

        public Sample(double time, double sample, double err) {
            this.time = time;
            this.sample = sample;
            this.err = err;
        }

        public double getTime() {
            return time;
        }


        public double getSamplw() {
            return sample;
        }


        public double getErrValue() {
            return err;
        }


        @Override
        public String toString() {
            return "Sample [Time=" + time + ", Sample=" + sample + ", Error=" + err + "]";
        }


    }

}