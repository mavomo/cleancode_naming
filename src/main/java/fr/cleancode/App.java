package fr.cleancode;


import static fr.cleancode.App.AppHelper.runApp;

/**
 * This class generates prime numbers up to a user specified maximum.
 * The algorithm used is the Sieve of Erastosthenes.
 */

public class App 
{
    public static void main( String[] args )
    {
        runApp();
    }


    static class AppHelper {
        static void runApp() {
            final int M = 121;
            final int RR = 30;
            final int CC = 4;
            int ROFFSET;
            int C;
            boolean JPRIME;
            int N;
            final int ORDMAX = 9;
            int MULT[] = new int[ORDMAX + 1];
            int J = 1;
            int K = 1;
            int ORD = 2;
            int SQUARE = 9;
            int P[] = new int[M + 1];
            P[1] = 2;
            while (K < M) {
                do {
                    J += 2;
                    if (J == SQUARE) {
                        ORD = ORD + 1;
                        SQUARE = P[ORD] * P[ORD];
                        MULT[ORD - 1] = J;
                    }
                    N = 2;
                    JPRIME = true;
                    while (N < ORD && JPRIME) {
                        while (MULT[N] < J)
                            MULT[N] += P[N] + P[N];
                        if (MULT[N] == J)
                            JPRIME = false;
                        N++;
                    }
                } while (!JPRIME);
                K++;
                P[K] = J;
            }

            int PNBR = 1;
            int POFFSET = 1;
            while (POFFSET <= M) {
                System.out.println("----------------------------------------------------");
                System.out.println("**** The First " + M + " Prime numbers # Page " + PNBR);
                System.out.println("----------------------------------------------------");
                for (ROFFSET = POFFSET; ROFFSET < POFFSET + RR; ROFFSET++) {
                    for (C = 0; C < CC; C++)
                        if (ROFFSET + C * RR <= M)
                            System.out.format("%10d", P[ROFFSET + C * RR]);
                    System.out.println("");
                }
                System.out.println("\f");
                PNBR += 1;
                POFFSET += (RR * CC);
            }
        }
    }
}
