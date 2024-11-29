package com.alaindroid.test.flo;

import com.alaindroid.test.flo.model.IntervalData;
import com.alaindroid.test.flo.model.NmiFile;
import com.alaindroid.test.flo.reader.Nmi12ReaderGenerator;
import com.alaindroid.test.flo.service.NmiFileToNmi;
import com.alaindroid.test.flo.service.NmiInsertGenerator;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MainServiceTest {
    MainService subject = new MainService(
            new Nmi12ReaderGenerator(),
            new NmiFileToNmi(),
            new NmiInsertGenerator("meter_readings")
    );

    @Test
    @SneakyThrows
    void testReader() {
        List<String> stmts = ingest("src/test/resources/sample.csv");
        assertThat(stmts.size()).isEqualTo(384);
    }

    @Test
    @SneakyThrows
    void testReaderGiant() {
        List<String> stmts = ingest("src/test/resources/giant.csv");
        assertThat(stmts.size()).isEqualTo(6415104);
    }

    private List<String> ingest(String filePath) {
        long start = System.currentTimeMillis();
        List<String> stmts = subject.generateInsertSqls(filePath);
//        stmts.forEach(out::println);
        long duration = System.currentTimeMillis() - start;
        out.println("Duration " + duration + "ms");
        return stmts;
    }

}