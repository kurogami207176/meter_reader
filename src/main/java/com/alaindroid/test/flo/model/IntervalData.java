package com.alaindroid.test.flo.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record IntervalData(LocalDate intervalDate, List<BigDecimal> intervalValue) {
}
