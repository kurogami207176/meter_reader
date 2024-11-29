package com.alaindroid.test.flo.service;

import com.alaindroid.test.flo.model.IntervalData;
import com.alaindroid.test.flo.model.Nmi;
import com.alaindroid.test.flo.model.NmiFile;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class NmiFileToNmiTest {
    private final NmiFileToNmi subject = new NmiFileToNmi();

    @Test
    void testConvert() {
        NmiFile nmiFile = testData();
        List<Nmi> nmi = subject.convert(nmiFile);
        Nmi first = nmi.get(0);
        Nmi second = nmi.get(1);
        Nmi third = nmi.get(2);
        nmi.forEach(nm -> assertThat(nm.nmi()).isEqualToIgnoringCase("alain-test"));

        assertThat(first.timestamp()).isEqualToIgnoringNanos(LocalDateTime.of(2024, 11, 30, 0, 0, 0));
        assertThat(first.consumption()).isEqualByComparingTo(BigDecimal.ZERO);

        assertThat(second.timestamp()).isEqualToIgnoringNanos(LocalDateTime.of(2024, 11, 30, 0, 0, 30));
        assertThat(second.consumption()).isEqualByComparingTo(BigDecimal.ONE);

        assertThat(third.timestamp()).isEqualToIgnoringNanos(LocalDateTime.of(2024, 11, 30, 0, 1, 0));
        assertThat(third.consumption()).isEqualByComparingTo(BigDecimal.TEN);
    }

    private NmiFile testData() {
        return new NmiFile(
                "alain-test",
                30,
                List.of(
                        new IntervalData(
                                LocalDate.of(2024, 11, 30),
                                List.of(
                                        BigDecimal.ZERO,
                                        BigDecimal.ONE,
                                        BigDecimal.TEN
                                )
                        )
                )
        );
    }
}