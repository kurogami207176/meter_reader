package com.alaindroid.test.flo;

import com.alaindroid.test.flo.service.Nmi12Reader;
import com.alaindroid.test.flo.service.NmiFileToNmi;
import com.alaindroid.test.flo.service.NmiInsertGenerator;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Data
@Service
public class MainService {

    private final Nmi12Reader reader;
    private final NmiFileToNmi nmiFileToNmi;
    private final NmiInsertGenerator insertGenerator;

    public List<String> generateInsertSqls(String fileName) {
        return
                StreamSupport.stream(reader.readFile(fileName).spliterator(), false)
                        .map(nmiFileToNmi::convert)
                        .flatMap(List::stream)
                        .map(insertGenerator::generatedInsertStatement)
                        .collect(Collectors.toList());
    }
}