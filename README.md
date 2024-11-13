# Speed Metrics Service 

Speed Metrics calculation challenge

## Installation

Use the following to build the project:

```bash
./gradlew build
```

Use the following to run the application:

```bash
./gradlew bootRun
```

## Usage

The application persists the records in an CSV file. In the application file, we are able to choose the path for it and also define the existing lines, in order to post and get metrics.

**Post a new metric:**

```java
/api/metrics/line-speed
```
```javascript
{
 "line_id": "",
 "speed": "",
 "timestamp": ""
}
```
A metric with a timestamp older that 60 minutes will be return a HTTP 204 but the record will be persisted.

A metric with an invalid line will be discarded.

During the write operation, we will lock the CSV file in order to block writing operations in parallel.

**Get the metrics:**

```java
/api/metrics/line-speed
```
With the optional query parameters: **lineId** and **timeInterval**.

It will return the a List of the following:

```javascript
{
 "line_id": "",
 "numberOfRecords": "",
 "metrics": [
    "averageSpeed":"",
    "maxSpeed":"",
    "minSpeed":"",
  ]
}
```

The optional query parameters will filter the metrics by the line or by the timeInterval or by both.

During the read operation, we will lock the CSV file in order to block writing operations on it. Several read operations can work in parallel.

## Next Steps

* Implement throughout testing in order to test business logic.
* Review metrics calculation according to the timestamps.
* The sampling frequency needs to be known in order to calculate a meaningful metric. Without it, we can't really characterize the machine's behavior between the timestamps.

## License

[MIT](https://choosealicense.com/licenses/mit/)
