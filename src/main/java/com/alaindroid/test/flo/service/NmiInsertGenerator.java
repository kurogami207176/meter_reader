package com.alaindroid.test.flo.service;

import com.alaindroid.test.flo.model.Nmi;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
@Data
public class NmiInsertGenerator {
    private final String tableName;
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @SneakyThrows
    public String generatedInsertStatement(Nmi nmi) {
    // We should be using prepared statements for SQL ops regardless
        return String.format("INSERT INTO %s (nmi, timestamp, consumption) VALUES ('%s', '%s', %s);",
                tableName,
                nmi.nmi(),
                nmi.timestamp().format(FORMATTER),
                nmi.consumption());
    }
}
