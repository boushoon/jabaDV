package ru.ac.uniyar.databasescourse.utils;

import de.siegmar.fastcsv.reader.CsvRow;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import static ru.ac.uniyar.databasescourse.utils.SomeCsvDataLoader.load;

public class DataReader {
    public static void csvRead(Path path) throws IOException {
        ArrayList<CsvRow> arrayOfRow = load(path);
        arrayOfRow.forEach(csvRow -> System.out.println(csvRow.));

    }
}
