package org.example;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.concurrent.atomic.AtomicLong;

public class FindDataInGoogleSheets {
    private static final String URL_FOR_DOWNLOAD = "https://docs.google.com/spreadsheets/d/1z4cavKmPKuvBVHkT8sA9XRAayUNZVYGNNAJMHcrvCo8/export?format=csv";
    private AtomicLong counter = new AtomicLong(0);

    public boolean isPresentInGoogleSheets(String data) {
        String fileName = counter.get() + ".csv";
        counter.incrementAndGet();
        try {
            downloadFile(new URL(URL_FOR_DOWNLOAD), fileName);
            return readFileDataAndFindText(data, fileName);
        } catch (Exception e) {

        }
        return false;
    }

    private boolean readFileDataAndFindText(String text, String fileName) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {

            String line = bufferedReader.readLine();
            while (line != null) {
                if (text.equals(line)) {
                    return true;
                }
                line = bufferedReader.readLine();
            }
        } catch (Exception e) {

        }
        return false;
    }

    private void downloadFile(URL url, String fileName) throws IOException {
        FileUtils.copyURLToFile(url, new File(fileName));
    }
}
