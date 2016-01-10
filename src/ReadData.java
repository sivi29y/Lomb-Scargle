import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class ReadData {

    ArrayList<Double[]> input = new ArrayList<>();

      //testing
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
        String datSplitBy = " ";

        try {


            br = new BufferedReader(new FileReader(dataFile));
            while ((line = br.readLine()) != null) {

                // use blank as separator
                if (line != null) {

                    String clean = line.trim();
                    String[] attributes = clean.split(datSplitBy);

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



}