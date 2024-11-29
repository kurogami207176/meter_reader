package com.alaindroid.test.flo.reader;

import com.alaindroid.test.flo.model.IntervalData;
import com.alaindroid.test.flo.model.NmiFile;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static java.lang.System.out;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Nmi12ReaderSerialTest {
    private final Nmi12ReaderSerial subject = new Nmi12ReaderSerial();

    @Test
    @SneakyThrows
    void testReader() {
        List<NmiFile> returnValue = readNmiFiles("src/test/resources/sample.csv");
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
        List<NmiFile> returnValue = readNmiFiles("src/test/resources/giant.csv");
        assertThat(returnValue.size()).isEqualTo(33412);
    }

    private List<NmiFile> readNmiFiles(String filePath) {
        long start = System.currentTimeMillis();
        File file = new File(filePath);
        List<NmiFile> returnValue = subject.readFile(file.getAbsolutePath());
        long duration = System.currentTimeMillis() - start;
        out.println("Duration " + duration + "ms");
        return returnValue;
    }

}