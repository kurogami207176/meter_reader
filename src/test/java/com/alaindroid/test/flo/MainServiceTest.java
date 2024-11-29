package com.alaindroid.test.flo;

import com.alaindroid.test.flo.reader.Nmi12ReaderGenerator;
import com.alaindroid.test.flo.service.NmiFileToNmiConverter;
import com.alaindroid.test.flo.service.NmiInsertGenerator;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.lang.System.out;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class MainServiceTest {
    MainService subject = new MainService(
            new Nmi12ReaderGenerator(),
            new NmiFileToNmiConverter(),
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
        long duration = System.currentTimeMillis() - start;
        out.println("Duration " + duration + "ms");
        return stmts;
    }

}