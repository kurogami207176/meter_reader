package com.alaindroid.test.flo.reader;

import com.alaindroid.test.flo.model.NmiFile;
import com.alaindroid.test.flo.service.Nmi12Reader;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class Nmi12ReaderGenerator implements Nmi12Reader {

    @SneakyThrows
    public Iterable<NmiFile> readFile(String fileName) {
        return getIterableFromIterator(new GeneratorList(fileName, 10).start());
    }

    @Data
    public class GeneratorList implements Iterator<NmiFile> {

        private final String fileName;
        private final int threadPoolCount;
        private ConcurrentLinkedQueue<NmiFile> queue = new ConcurrentLinkedQueue<>();
        private Future f = null;

        public GeneratorList start() {
            ExecutorService executorService = Executors.newFixedThreadPool(threadPoolCount);
            f = executorService.submit(this::readFile);
            return this;
        }

        @Override
        @SneakyThrows
        public boolean hasNext() {
            while (queue.isEmpty() && (f == null || !f.isDone())) {
                Thread.sleep(50);
            }
            return !queue.isEmpty();
        }

        @Override
        @SneakyThrows
        public NmiFile next() {
            while (queue.isEmpty() && (f == null || !f.isDone())) {
                Thread.sleep(50);
            }
            return queue.poll();
        }

        @SneakyThrows
        private void readFile() {
            Reader in = new FileReader(fileName);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);
            NmiFile currentNmiFile = null;
            for (CSVRecord record : records) {
                switch (record.get(0)) {
                    case "200" -> {
                        if (currentNmiFile != null) {
                            queue.add(currentNmiFile);
                        }
                        currentNmiFile = readNmiDetail(record);
                    }
                    case "300" -> {
                        assert currentNmiFile != null;
                        currentNmiFile = readIntervalData(currentNmiFile, record);
                    }
                }
            }
            if (currentNmiFile != null) {
                queue.add(currentNmiFile);
            }
        }

    }

    private static <T> Iterable<T> getIterableFromIterator(Iterator<T> iterator)
    {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator()
            {
                return iterator;
            }
        };
    }

}
