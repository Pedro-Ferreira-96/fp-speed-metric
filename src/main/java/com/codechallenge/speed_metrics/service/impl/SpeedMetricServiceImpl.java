package com.codechallenge.speed_metrics.service.impl;

import com.codechallenge.speed_metrics.service.SpeedMetricService;
import com.codechallenge.speed_metrics.service.model.LineSpeedMetricModel;
import com.codechallenge.speed_metrics.service.model.request.LineSpeedRequestModel;
import com.codechallenge.speed_metrics.service.model.response.LineSpeedResponseModel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

@Service
public class SpeedMetricServiceImpl implements SpeedMetricService {

    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static final String CSV_FILE = "src/main/resources/line_metrics.csv";
    private static final String[] HEADERS = { "line_id", "speed", "timestamp"};
    private static final ConcurrentHashMap<Long, LineSpeedMetricModel> METRICS_MAP = new ConcurrentHashMap<>();
    private static final CSVFormat CSV_FORMAT = CSVFormat.DEFAULT.builder()
        .setHeader(HEADERS)
        .setSkipHeaderRecord(fileExists(CSV_FILE))
        .setIgnoreHeaderCase(true)
        .build();

    @Override
    public void submitLineSpeed(final LineSpeedRequestModel lineSpeedRequestModel) {

        METRICS_MAP.put(lineSpeedRequestModel.getLine_id(), LineSpeedMetricModel.from(lineSpeedRequestModel));

        lock.writeLock().lock();

        try {

            writeToCsv(METRICS_MAP);
        } catch (IOException e) {

            throw new RuntimeException(e);
        } finally {

            lock.writeLock().unlock();
        }
    }

    @Override
    public List<LineSpeedResponseModel> fetchLineMetrics(final Long lineId) {

        if (!fileExists(CSV_FILE)) {

            throw new RuntimeException("File with line metrics does not exist");
        }

        lock.readLock().lock();

        List<List<String>> metrics = readFromCsv();

        lock.readLock().unlock();

        return List.of();
    }

    private void writeToCsv(final ConcurrentHashMap<Long, LineSpeedMetricModel> speedMetricRecord) throws IOException {

        BufferedWriter writer = Files.newBufferedWriter(
            Paths.get(CSV_FILE),
            StandardOpenOption.APPEND,
            StandardOpenOption.CREATE);

        final CSVPrinter csvPrinter = new CSVPrinter(writer, CSV_FORMAT);

        try {

            for (ConcurrentHashMap.Entry<Long, LineSpeedMetricModel> entry : speedMetricRecord.entrySet()) {

                Long lineId = entry.getKey();
                LineSpeedMetricModel model = entry.getValue();

                csvPrinter.printRecord(lineId, model.getSpeed(), model.getTimestamp());
            }
        } catch (IOException e) {

            e.printStackTrace();
        } finally {

            csvPrinter.close(true);
        }
    }

    private List<List<String>> readFromCsv() {

        List<List<String>> metrics = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(CSV_FILE))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] values = line.split(",");
                metrics.add(Arrays.asList(values));
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return metrics;
    }

    private static boolean fileExists(final String path) {

        final Path convertedPath = Paths.get(path);

        return Files.exists(convertedPath);
    }

}
