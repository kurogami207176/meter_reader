package com.alaindroid.test.flo.service;

import com.alaindroid.test.flo.model.IntervalData;
import com.alaindroid.test.flo.model.Nmi;
import com.alaindroid.test.flo.model.NmiFile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NmiFileToNmi {
    public List<Nmi> convert(NmiFile nmiFile) {
        List<Nmi> nmiList = new ArrayList<>();
        for(int i = 0; i < nmiFile.intervalData().size(); i++) {
            IntervalData intervalData = nmiFile.intervalData().get(i);
            for (int j = 0; j < intervalData.intervalValue().size(); j++) {
                int timeFromMidnight = nmiFile.intervalLengthInSeconds() * j;
                LocalDateTime timestamp = intervalData.intervalDate()
                        .atTime(0, 0)
                        .plusSeconds(timeFromMidnight);
                nmiList.add(
                        new Nmi(nmiFile.nmi(),
                        timestamp,
                        intervalData.intervalValue().get(j))
                );
            }
        }
        return nmiList;
    }
}
