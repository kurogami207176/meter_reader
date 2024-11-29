package com.alaindroid.test.flo.service;

import com.alaindroid.test.flo.model.IntervalData;
import com.alaindroid.test.flo.model.NmiFile;
import lombok.SneakyThrows;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.System.out;

@Service
public interface Nmi12Reader {

    @SneakyThrows
    Iterable<NmiFile> readFile(String fileName);


    default NmiFile readNmiDetail(CSVRecord record) {
        return new NmiFile(record.get(1),
                Integer.parseInt(record.get(8)),
                new ArrayList<>());
    }

    default NmiFile readIntervalData(NmiFile nmiFile, CSVRecord record) {
        assert nmiFile != null;
        assert 1440 % nmiFile.intervalLengthInSeconds() == 0;
        int recordsToRead = (1440 / nmiFile.intervalLengthInSeconds());
        LocalDate timestamp = LocalDate.parse(record.get(1), DateTimeFormatter.ofPattern("yyyyMMdd"));
        List<BigDecimal> records = IntStream.rangeClosed(1, recordsToRead).
                mapToObj(it ->
                        new BigDecimal(record.get(1 + it))
                )
                .collect(Collectors.toList());
        assert recordsToRead == records.size();
        IntervalData intervalData = new IntervalData(timestamp, records);
        nmiFile.intervalData().add(intervalData);
        return nmiFile;
    }

}
