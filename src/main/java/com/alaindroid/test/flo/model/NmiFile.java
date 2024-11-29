package com.alaindroid.test.flo.model;

import java.util.List;

public record NmiFile (String nmi, int intervalLengthInSeconds, List<IntervalData> intervalData){
}
