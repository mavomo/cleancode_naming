package fr.cleancode;

public class PrimePrinter {

    private static final int LIMIT = 121;
    private static final int NB_ROWS = 30;
    private static final int NB_COLUMNS = 4;
    private static final int ORD_MAX = 9;

    public static void main(String[] args) {
        int[] primesNumbers = generateFirstPrimesFor(LIMIT);
        printPrimes(primesNumbers, LIMIT);
    }

    private static void printPrimes(int[] primesNumbers, int maximum) {
        int pageNumber = 1;
        int pageOffset = 1;

        while (maxPrintableNotReached(maximum, pageOffset)) {
            printPageHeader(maximum, pageNumber);
            printPrimesInPage(maximum, primesNumbers, pageOffset);
            pageOffset += nextPageOffset();
            pageNumber++;
        }
    }

    private static int[] generateFirstPrimesFor(int totalPrimes) {
        int nextEligiblePrime = 1;
        int primesCounter = 1;
        int squareOfNextEligiblePrime = 9;
        int ord = 2;

        int[] primesNumbers = initializeSieve(totalPrimes);
        int[] multiplesOfPrimes = initializeMultiplesOfPrimes();

        while (maxPrimesNotReached(totalPrimes, primesCounter)) {
            boolean isPrime;
            do {
                nextEligiblePrime += 2;
                if (nextEligiblePrime == squareOfNextEligiblePrime) {
                    ord++;
                    squareOfNextEligiblePrime = nextMinSquare(primesNumbers[ord]);
                    multiplesOfPrimes[ord - 1] = nextEligiblePrime;
                }

                isPrime = hasFoundNextPrime(nextEligiblePrime, ord, primesNumbers, multiplesOfPrimes);
            } while (!isPrime);
            primesCounter++;
            primesNumbers[primesCounter] = nextEligiblePrime;
        }
        return primesNumbers;
    }

    private static int[] initializeMultiplesOfPrimes() {
        return new int[ORD_MAX + 1];
    }

    private static boolean hasFoundNextPrime(int eligiblePrime, int ORD, int[] primesNumbers, int[] multiplesOfPrimes) {
        int nextPrime = 2;
        boolean isPrime = true;
        while (nextPrime < ORD && isPrime) {
            while (isAMultipleofPrime(eligiblePrime, multiplesOfPrimes[nextPrime])) {
                multiplesOfPrimes[nextPrime] += nextMultipleOfPrime(primesNumbers[nextPrime]);
            }
            if (isDivisibleByPrime(eligiblePrime, multiplesOfPrimes[nextPrime])) {
                isPrime = false;
            }
            nextPrime++;
        }
        return isPrime;
    }

    private static boolean isAMultipleofPrime(int eligiblePrime, int multiplesOfPrime) {
        return multiplesOfPrime < eligiblePrime;
    }

    private static int nextMultipleOfPrime(int firstPrime) {
        return firstPrime + firstPrime;
    }

    private static boolean isDivisibleByPrime(int eligiblePrime, int multiplesOfPrime) {
        return multiplesOfPrime == eligiblePrime;
    }

    private static int[] initializeSieve(int maxPrimes) {
        int primesNumbers[] = new int[maxPrimes + 1];
        primesNumbers[1] = 2;
        return primesNumbers;
    }

    private static boolean maxPrimesNotReached(int maxPrimes, int numberOfPrimes) {
        return numberOfPrimes < maxPrimes;
    }

    private static int nextMinSquare(int primesNumber) {
        return primesNumber * primesNumber;
    }

    private static void printPrimesInPage(int maximum, int[] primesNumbers, int pageOffset) {
        for (int rowOffset = pageOffset; rowOffset < maxRowOffset(pageOffset); rowOffset++) {
            printPrimesInColumns(maximum, primesNumbers, rowOffset);
        }
        System.out.println("\f");
    }

    private static int maxRowOffset(int pageOffset) {
        return pageOffset + NB_ROWS;
    }

    private static boolean maxPrintableNotReached(int maximum, int pageOffset) {
        return pageOffset <= maximum;
    }

    private static int nextPageOffset() {
        return NB_COLUMNS * NB_ROWS;
    }

    private static void printPageHeader(int MAXIMUM, int pageNumber) {
        System.out.println("----------------------------------------------------");
        System.out.println("**** The First " + MAXIMUM + " Prime numbers # Page " + pageNumber);
        System.out.println("----------------------------------------------------");
    }

    private static void printPrimesInColumns(int maxPrimes, int[] primesNumbers, int rowOffset) {
        for (int columnIndex = 0; columnIndex < NB_COLUMNS; columnIndex++)
            if (getColumnIndexOfRow(rowOffset, columnIndex) <= maxPrimes) {
                int columnIndexInRow = getColumnIndexOfRow(rowOffset, columnIndex);
                System.out.format("%10d", primesNumbers[columnIndexInRow]);
            }
        System.out.println("");
    }

    private static int getColumnIndexOfRow(int rowOffset, int columnIndex) {
        return rowOffset + columnIndex * NB_ROWS;
    }
}
