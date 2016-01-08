import java.util.ArrayList;
import java.util.Random;

/*
*
*    Lomb -Scargle Periodogram based on Python scipy library in Java
*
*    Sivan  January 2016
*
 */




public class LombScargle {

    static ArrayList<Double[]> samples;
    private static int normval;


    static ArrayList<Double[]> caluculateLombScargle(Integer nIn) {

        Double A = 1.2;
        Double w = 1.2;

        ArrayList<Double[]> samples_withS = new ArrayList<>();
        samples_withS = samples;


//      Double  Period = 3.0;
//      Double Frequency =  1.0/3.0;
        Double Phi = 0.5 * Math.PI;
        Integer nOut = nIn * 100;
        Double FracPoint = 0.9;
        Integer r = new Random().nextInt(nIn);


        Double[] X = linspace(0.01, 10 * Math.PI, nIn);
        normval = X.length; // For normalization of the periodogram


        Double[] Y = new Double[X.length];
        for (int i = 0; i < Y.length; i++) {
            Y[i] = A * Math.sin(w * X[i] + Phi);
        }
        //defines the array of frequencies
        Double[] F = linspace(0.01, 10, nOut);
        ArrayList<Double[]> Scargle_Lomb_Periodogram = Scargle(X, Y, F);

        return Scargle_Lomb_Periodogram;


    }

    private static ArrayList<Double[]> Scargle(Double[] X, Double[] Y, Double[] F) {


        Double[] Periodogram = new Double[F.length];

        ArrayList<Double[]> scargle = new ArrayList<>();

        double c, s, xc, xs, cc, ss, cs;
        double tau, c_tau, s_tau, c_tau2, s_tau2, cs_tau;


        for (int i = 0; i < F.length; i++) {

            xc = 0.0;
            xs = 0.0;
            cc = 0.0;
            ss = 0.0;
            cs = 0.0;


            for (int j = 0; j < X.length; j++) {

                c = Math.cos(F[i] * X[j]);
                s = Math.sin(F[i] * X[j]);

                xc += Y[j] * c;
                xs += Y[j] * s;
                cc += c * c;
                ss += s * s;
                cs += c * s;
            }

            tau = Math.atan2(2 * cs, cc - ss) / (2 * F[i]);
            c_tau = Math.cos(F[i] * tau);
            s_tau = Math.sin(F[i] * tau);
            c_tau2 = c_tau * c_tau;
            s_tau2 = s_tau * s_tau;
            cs_tau = 2 * c_tau * s_tau;


            Periodogram[i] = 0.5 * (
                    (Math.pow(c_tau * xc + s_tau * xs, 2) / (c_tau2 * cc + cs_tau * cs + s_tau2 * ss)) +
                            (Math.pow(c_tau * xs - s_tau * xc, 2) / (c_tau2 * ss - cs_tau * cs + s_tau2 * cc))
            );


            Double[] d;
            d = new Double[]{Periodogram[i], Math.sqrt(4 * Periodogram[i] / normval)};

            scargle.add(d);
        }

        return scargle;
    }


    // linstep identical to python's numpy default in java
    static Double[] linspace(double v, double v1, Integer nIn) {
        Double step = (v1 - v)/(nIn - 1);

        // System.out.println (step);
        Double[] lin = new Double[nIn];
        lin[0] = v;
        for (int i = 1; i < nIn; i++) {
            lin[i] = lin[i - 1] + step;
            //  System.out.println (lin[i]);
        }
        return lin;
    }


    public static void main(String[] args) {

        samples = readData();
        caluculateLombScargle(samples.size());

    }


    private static ArrayList<Double[]> readData() {
        //getting hold of data
        ReadData dataObject = new ReadData();
        samples = dataObject.run();
        return samples;
    }
}