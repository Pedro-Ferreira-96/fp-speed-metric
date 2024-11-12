package com.codechallenge.speed_metrics.service.impl;

import com.codechallenge.speed_metrics.service.SpeedMetricService;
import com.codechallenge.speed_metrics.service.model.LineSpeedMetricModel;
import com.codechallenge.speed_metrics.service.model.request.LineSpeedRequestModel;
import com.codechallenge.speed_metrics.service.model.response.LineSpeedResponseModel;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

@Service
public class SpeedMetricServiceImpl implements SpeedMetricService {

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

        try {

            writeToCsv(METRICS_MAP);
        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }

    @Override
    public List<LineSpeedResponseModel> fetchLineMetrics(final Long lineId) {

        return List.of();
    }

    private void writeToCsv(final ConcurrentHashMap<Long, LineSpeedMetricModel> speedMetricRecord) throws IOException {

        try {
            BufferedWriter writer = Files.newBufferedWriter(
                Paths.get(CSV_FILE),
                StandardOpenOption.APPEND,
                StandardOpenOption.CREATE);

            final CSVPrinter csvPrinter = new CSVPrinter(writer, CSV_FORMAT);

            for (ConcurrentHashMap.Entry<Long, LineSpeedMetricModel> entry : speedMetricRecord.entrySet()) {

                Long lineId = entry.getKey();
                LineSpeedMetricModel model = entry.getValue();

                csvPrinter.printRecord(lineId, model.getSpeed(), model.getTimestamp());
            }

            csvPrinter.close(true);
            writer.close();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    private static boolean fileExists(final String path) {

        final Path convertedPath = Paths.get(path);

        return Files.exists(convertedPath);
    }

}
