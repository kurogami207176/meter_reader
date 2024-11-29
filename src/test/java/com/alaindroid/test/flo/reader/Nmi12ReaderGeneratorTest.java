package com.alaindroid.test.flo.reader;

import com.alaindroid.test.flo.model.IntervalData;
import com.alaindroid.test.flo.model.NmiFile;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Nmi12ReaderGeneratorTest {

    @Test
    @SneakyThrows
    void testReader() {
        Nmi12ReaderGenerator subject = new Nmi12ReaderGenerator();

        List<NmiFile> returnValue = readNmiFiles(subject, "src/test/resources/sample.csv");

        assertThat(returnValue.size()).isEqualTo(2);
        assertThat(returnValue.get(0).nmi()).isEqualToIgnoringCase("NEM1201009");
        assertThat(returnValue.get(1).nmi()).isEqualToIgnoringCase("NEM1201010");
        for (NmiFile nmiFile: returnValue) {
            assertThat(nmiFile.intervalLengthInSeconds()).isEqualTo(30);
            for (IntervalData ids: nmiFile.intervalData()) {
                assertThat(ids.intervalValue().size()).isEqualTo(48);
            }
        }
    }

    @Test
    @SneakyThrows
    void testReaderGiant() {
        Nmi12ReaderGenerator subject = new Nmi12ReaderGenerator();

        List<NmiFile> returnValue = readNmiFiles(subject, "src/test/resources/giant.csv");
        assertThat(returnValue.size()).isEqualTo(33412);
    }

    private List<NmiFile> readNmiFiles(Nmi12ReaderGenerator subject, String filePath) {
        long start = System.currentTimeMillis();
        File file = new File(filePath);
        Iterable<NmiFile> returnValue = subject.readFile(file.getAbsolutePath());
        long duration = System.currentTimeMillis() - start;
        out.println("Duration " + duration + "ms");
        List<NmiFile> rv = new ArrayList<>();
        for (NmiFile f: returnValue) {
            rv.add(f);
        }
        return rv;
    }

}