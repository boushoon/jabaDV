package ru.ac.uniyar.databasescourse.utils;

import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class SomeCsvDataLoader {
        public static ArrayList<CsvRow> load(Path path) throws IOException {
            try (CsvReader csvReader = CsvReader.builder().build(path)) {
                return csvReader.stream().skip(1).collect(Collectors.toCollection(ArrayList::new));
            }
        }
}
