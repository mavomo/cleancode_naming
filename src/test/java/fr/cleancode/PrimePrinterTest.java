package fr.cleancode;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;


public class PrimePrinterTest {
    private PrintStream out;
    private static final String GOLD = "gold_n_121.txt";
    private static final String LEAD = "lead_n_121.txt";

    @Before
    public void setup() throws FileNotFoundException {
        out = System.out;
        System.setOut(new PrintStream(new FileOutputStream(LEAD)));
    }

    @After
    public void teardown() {
        System.setOut(out);
    }

    @Test
    public void makeSureOutputMatchesGold() throws Exception {
        PrimePrinter.main(new String[0]);

        BufferedReader goldReader = new BufferedReader(new FileReader(getFileToRead(GOLD)));
        BufferedReader leadReader = new BufferedReader(new FileReader(LEAD));

        String line;
        while ((line = goldReader.readLine()) != null) {
            String leadLine = leadReader.readLine();
            Assert.assertEquals(line, leadLine);
        }
        Assert.assertNull(leadReader.readLine());
    }

    private File getFileToRead(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(fileName).getFile());
    }

}
