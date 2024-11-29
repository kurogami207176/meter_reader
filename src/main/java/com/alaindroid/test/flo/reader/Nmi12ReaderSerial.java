package com.alaindroid.test.flo.reader;

import com.alaindroid.test.flo.model.IntervalData;
import com.alaindroid.test.flo.model.NmiFile;
import com.alaindroid.test.flo.service.Nmi12Reader;
import lombok.SneakyThrows;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.System.out;

@Service
public class Nmi12ReaderSerial implements Nmi12Reader {

    @SneakyThrows
    public List<NmiFile> readFile(String fileName) {
        List<NmiFile> returnList = new ArrayList<>();
        Reader in = new FileReader(fileName);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);
        NmiFile currentNmiFile = null;
        for (CSVRecord record : records) {
            switch (record.get(0)) {
                case "200" -> {
                    if (currentNmiFile != null) {
                        returnList.add(currentNmiFile);
                    }
                    currentNmiFile = readNmiDetail(record);
                }
                case "300" -> {
                    assert currentNmiFile != null;
                    currentNmiFile = readIntervalData(currentNmiFile, record);
                }
            }
        }
        if (currentNmiFile != null) {
            returnList.add(currentNmiFile);
        }
        return returnList;
    }
}
