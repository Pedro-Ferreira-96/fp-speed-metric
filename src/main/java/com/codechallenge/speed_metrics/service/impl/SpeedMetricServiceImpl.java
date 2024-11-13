package com.codechallenge.speed_metrics.service.impl;

import com.codechallenge.speed_metrics.service.SpeedMetricService;
import com.codechallenge.speed_metrics.service.config.CsvFileConfig;
import com.codechallenge.speed_metrics.service.model.LineSpeedMetricModel;
import com.codechallenge.speed_metrics.service.model.request.LineSpeedRequestModel;
import com.codechallenge.speed_metrics.service.model.response.LineSpeedResponseModel;
import com.codechallenge.speed_metrics.service.model.response.MetricResponseModel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import lombok.AllArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SpeedMetricServiceImpl implements SpeedMetricService {

    private static final String[] HEADERS = {"line_id", "speed", "timestamp"};
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static final ConcurrentHashMap<Long, LineSpeedMetricModel> METRICS_MAP = new ConcurrentHashMap<>();

    private final CsvFileConfig csvFileConfig;

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

        METRICS_MAP.clear();
    }

    @Override
    public List<LineSpeedResponseModel> fetchLineMetrics(final Long lineId, final Long timeInterval) {

        if (!fileExists(csvFileConfig.getPath())) {

            throw new RuntimeException("File with line metrics does not exist");
        }

        lock.readLock().lock();

        final List<List<String>> metrics = readFromCsv();

        lock.readLock().unlock();

        return computeMetrics(metrics, lineId, timeInterval);
    }

    private void writeToCsv(final ConcurrentHashMap<Long, LineSpeedMetricModel> speedMetricRecord) throws IOException {

        final CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
            .setHeader(HEADERS)
            .setSkipHeaderRecord(fileExists(csvFileConfig.getPath()))
            .setIgnoreHeaderCase(true)
            .build();

        final BufferedWriter writer = Files.newBufferedWriter(
            Paths.get(csvFileConfig.getPath()),
            StandardOpenOption.APPEND,
            StandardOpenOption.CREATE);

        final CSVPrinter csvPrinter = new CSVPrinter(writer, csvFormat);

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

        final List<List<String>> metrics = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(csvFileConfig.getPath()))) {

            String line;

            while ((line = reader.readLine()) != null) {

                final String[] values = line.split(",");
                metrics.add(Arrays.asList(values));
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return metrics;
    }

    private List<LineSpeedResponseModel> computeMetrics(final List<List<String>> readMetrics, final Long lineId, final Long timeInterval) {

        final List<LineSpeedResponseModel> result = new ArrayList<>();

        final List<List<String>> metricsWithinTheThreshold = readMetrics.stream()
            .skip(1)
            .filter(metric -> !recordOlderThan(Long.valueOf(metric.get(2)), timeInterval))
            .filter(metric -> lineId == null || lineId.equals(Long.valueOf(metric.get(0))))
            .toList();

        final Map<Long, List<Float>> groupedByLineId = new HashMap<>();

        for (List<String> row : metricsWithinTheThreshold) {

            final Long currentLineId = Long.parseLong(row.get(0));
            final Float speed = Float.parseFloat(row.get(1));

            groupedByLineId
                .computeIfAbsent(currentLineId, k -> new ArrayList<>())
                .add(speed);
        }

        for (Map.Entry<Long, List<Float>> map : groupedByLineId.entrySet()) {

            final Long lineIdentifier = map.getKey();
            final List<Float> speedValues = map.getValue();

            final Long numberOfRecords = speedValues.stream().count();
            final Float average = (float) speedValues.stream().mapToDouble(x -> x).average().getAsDouble();
            final Float max = (float) speedValues.stream().mapToDouble(x -> x).max().getAsDouble();
            final Float min = (float) speedValues.stream().mapToDouble(x -> x).min().getAsDouble();

            final LineSpeedResponseModel model = appendResultToModel(lineIdentifier, numberOfRecords, average, max, min);

            result.add(model);
        }

        return result;
    }

    private static boolean fileExists(final String path) {

        final Path convertedPath = Paths.get(path);

        return Files.exists(convertedPath);
    }

    private boolean recordOlderThan(final Long timestamp ,final Long thresholdInMinutes) {

        final long nowInstant = Instant.now().toEpochMilli();

        Duration timeBetween = Duration.between(Instant.ofEpochMilli(timestamp), Instant.ofEpochMilli(nowInstant));

        return timeBetween.toMinutes() > thresholdInMinutes;
    }

    private LineSpeedResponseModel appendResultToModel(final Long lineId, final Long numberOfRecords, final Float avg, final Float max,
        final Float min) {

        return LineSpeedResponseModel.builder()
            .line_id(lineId)
            .numberOfRecords(numberOfRecords)
            .metricResponse(appendResultToMetricModel(avg, max, min))
            .build();
    }

    private MetricResponseModel appendResultToMetricModel(final Float avg, final Float max, final Float min) {

        return MetricResponseModel.builder()
            .avg(avg)
            .max(max)
            .min(min)
            .build();
    }

}
