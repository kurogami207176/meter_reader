package com.alaindroid.test.flo.service;

import com.alaindroid.test.flo.model.Nmi;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

class NmiInsertGeneratorTest {
    private final NmiInsertGenerator subject = new NmiInsertGenerator("meter_readings");

    @Test
    void testGenerate() {
        LocalDateTime timestamp = LocalDateTime.now();
        String strTs = timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Nmi nmi = new Nmi("alain-test", LocalDateTime.now(), BigDecimal.valueOf(3.141592553));
        String insertStatement = subject.generatedInsertStatement(nmi);
        assertThat(insertStatement)
                .isEqualToIgnoringCase("INSERT INTO meter_readings " +
                        "(nmi, timestamp, consumption) " +
                        "VALUES ('alain-test', '" + strTs + "', 3.141592553);");
    }
}