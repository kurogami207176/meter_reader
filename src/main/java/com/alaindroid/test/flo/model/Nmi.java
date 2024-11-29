package com.alaindroid.test.flo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Nmi(String nmi, LocalDateTime timestamp, BigDecimal consumption) {
}
