package fr.cleancode;

public class PrimePrinter {

    private static final int MAXIMUM = 121;
    private static final int NB_ROWS = 30;
    private static final int NB_COLUMNS = 4;
    private static final int ORDMAX = 9;

    public static void main(String[] args) {
        int[] primesNumbers = generatePrimesUpTo(MAXIMUM);
        printPrimes(MAXIMUM, primesNumbers);
    }

    private static void printPrimes(int maximum, int[] primesNumbers) {
        int pageNumber = 1;
        int pageOffset = 1;

        while (maxPrintableNotReached(maximum, pageOffset)) {
            printPageHeader(maximum, pageNumber);
            printPrimesInPage(maximum, primesNumbers, pageOffset);
            pageOffset += nextPageOffset();
            pageNumber++;
        }
    }

    private static int[] generatePrimesUpTo(int maxPrimes) {
        int eligiblePrime = 1;
        int numberOfPrimes = 1;
        int primesNumberOrder = 2;
        int squareOfNextEligiblePrime = 9;

        int[] primesNumbers = initializeSieve(maxPrimes);
        int[] multiplesOfPrimes = initializeMultiplesOfPrimes();

        while (maxPrimesNotReached(maxPrimes, numberOfPrimes)) {
            boolean isPrime;
            do {
                eligiblePrime += 2;
                if (eligiblePrime == squareOfNextEligiblePrime) {
                    primesNumberOrder++;
                    squareOfNextEligiblePrime = nextMinSquare(primesNumbers[primesNumberOrder]);
                    multiplesOfPrimes[primesNumberOrder - 1] = eligiblePrime;
                }

                isPrime = hasFoundNextPrime(eligiblePrime, primesNumberOrder, primesNumbers, multiplesOfPrimes);
            } while (!isPrime);
            numberOfPrimes++;
            primesNumbers[numberOfPrimes] = eligiblePrime;
        }
        return primesNumbers;
    }

    private static int[] initializeMultiplesOfPrimes() {
        return new int[ORDMAX + 1];
    }

    private static boolean hasFoundNextPrime(int eligiblePrime, int ORD, int[] primesNumbers, int[] multiplesOfPrimes) {
        int currentPrime = 2;
        boolean isPrime = true;
        while (currentPrime < ORD && isPrime) {
            while (multiplesOfPrimes[currentPrime] < eligiblePrime) {
                multiplesOfPrimes[currentPrime] += nextMultipleOfPrime(primesNumbers[currentPrime]);
            }
            if (isAFactorOfPrime(eligiblePrime, multiplesOfPrimes[currentPrime])) {
                isPrime = false;
            }
            currentPrime++;
        }
        return isPrime;
    }

    private static int nextMultipleOfPrime(int firstPrime) {
        return firstPrime + firstPrime;
    }

    private static boolean isAFactorOfPrime(int eligiblePrime, int multiplesOfPrime) {
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
