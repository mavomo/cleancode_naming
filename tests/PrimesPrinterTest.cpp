#include "ApprovalTests.hpp"
#include "catch2/catch.hpp"
#include <ostream>
#include <iomanip>

using namespace ApprovalTests;
using namespace std;

class PrimesPrinter
{
public:
   void runApp()
    {
       const int M = 121;
       const int RR = 30;
       const int CC = 4;
       int ROFFSET;
       int C;
       bool JPRIME;
       int N;
       const int ORDMAX = 9;
       int MULT[ORDMAX + 1];
       int J = 1;
       int K = 1;
       int ORD = 2;
       int SQUARE = 9;
       int P[M + 1];
       P[1] = 2;
       while (K < M)
       {
           do {
               J += 2;
               if (J == SQUARE) {
                   ORD = ORD + 1;
                   SQUARE = P[ORD] * P[ORD];
                   MULT[ORD - 1] = J;
               }
               N = 2;
               JPRIME = true;
               while (N < ORD && JPRIME){
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
           cout << "---------------------------------------------" << endl;
           cout << "**** The First " << M << " Prime numbers # Page " << PNBR << " **** "<< endl;
           cout << "---------------------------------------------" << endl;
           for (ROFFSET = POFFSET; ROFFSET < POFFSET + RR; ROFFSET++) {
               for (C = 0; C < CC; C++)
                   if (ROFFSET + C * RR <= M)
                       cout << setw(12) << P[ROFFSET + C * RR];
               cout << "" <<endl;
           }
           cout << "" << endl;
           PNBR += 1;
           POFFSET += (RR * CC);
       }
   }
};


TEST_CASE("Primes printer - output matches gold")
{

    std::ostringstream oss;
    std::streambuf* p_cout_streambuf = std::cout.rdbuf();
    std::cout.rdbuf(oss.rdbuf());

    PrimesPrinter primesPrinter;
    primesPrinter.runApp();

    std::cout.rdbuf(p_cout_streambuf);	
    
	Approvals::verify(oss.str());
}


