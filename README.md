# BeachPlanner

BeachPlanner is an event-driven application that collects beach and weather information from external APIs, processes it through a business unit and generates recommendations for users.


## Class Diagram: weather-module

The following diagram illustrates the internal structure of the weather module:

```mermaid
classDiagram

class Main {
    +main(String[] args)
}

class WeatherController {
    -WeatherFeeder feeder
    -WeatherEventPublisher publisher
    +execute()
}

class WeatherFeeder {
    <<interface>>
    +fetch()
}

class OpenMeteoFeeder {
    -BeachProvider beachProvider
    -OpenMeteoUrlBuilder urlBuilder
    -OpenMeteoResponseParser parser
    -WeatherMapper mapper
    +fetch()
}

class BeachProvider {
    +getBeaches()
}

class WeatherEventPublisher {
    <<interface>>
    +publish()
}

class WeatherPublisher {
    -WeatherEventBuilder builder
    +publish()
}

class WeatherEventBuilder {
    +buildEvent()
}

class OpenMeteoUrlBuilder {
    +buildUrl()
}

class OpenMeteoResponseParser {
    +parse()
}

class WeatherMapper {
    +toWeatherRecord()
}

class WeatherRepository {
    <<interface>>
    +saveAll()
}

class SQLiteWeatherRepository {
    -WeatherDatabase database
    +saveAll()
}

class WeatherDatabase {
    +initialize()
    +connect()
}

class Beach {
    <<record>>
    +name
    +latitude
    +longitude
}

class WeatherRecord {
    <<record>>
    +beachName
    +forecastTime
    +temperature
    +windSpeed
    +capturedAt
}

Main --> WeatherController

WeatherController --> WeatherFeeder
WeatherController --> WeatherEventPublisher

OpenMeteoFeeder ..|> WeatherFeeder
WeatherPublisher ..|> WeatherEventPublisher
SQLiteWeatherRepository ..|> WeatherRepository

OpenMeteoFeeder --> BeachProvider
OpenMeteoFeeder --> OpenMeteoUrlBuilder
OpenMeteoFeeder --> OpenMeteoResponseParser
OpenMeteoFeeder --> WeatherMapper

OpenMeteoFeeder --> Beach
WeatherMapper --> WeatherRecord

SQLiteWeatherRepository --> WeatherDatabase
SQLiteWeatherRepository --> WeatherRecord

WeatherPublisher --> WeatherEventBuilder
WeatherEventBuilder --> WeatherRecord
```

## Class Diagram beachInfo module

The beachinfo module is responsible for retrieving beach information from the external API, mapping it into domain records, storing it and publishing beach information events.

```mermaid
classDiagram

class Main {
    +main(String[] args)
}

class BeachInfoController {
    -BeachInfoFeeder feeder
    -BeachInfoSerializer serializer
    -BeachInfoEventPublisher publisher
    +execute()
}

class BeachInfoFeeder {
    <<interface>>
    +fetch()
}

class BeachInfoApiFeeder {
    -BeachInfoApiClient client
    -BeachInfoMapper mapper
    +fetch()
}

class BeachInfoApiClient {
    +fetchBeachInfo()
}

class BeachInfoMapper {
    +toBeachInfoRecord()
}

class BeachInfoSerializer {
    <<interface>>
    +save()
}

class SQLiteBeachInfoSerializer {
    -BeachInfoDatabaseManager databaseManager
    +save()
}

class BeachInfoDatabaseManager {
    +initialize()
    +connect()
}

class BeachInfoEventPublisher {
    +publish()
}

class BeachInfoEventFactory {
    +createEvent()
}

class BeachInfoRecord {
    <<record>>
    +id
    +name
    +municipality
    +island
    +latitude
    +longitude
}

Main --> BeachInfoController

BeachInfoController --> BeachInfoFeeder
BeachInfoController --> BeachInfoSerializer
BeachInfoController --> BeachInfoEventPublisher

BeachInfoApiFeeder ..|> BeachInfoFeeder
SQLiteBeachInfoSerializer ..|> BeachInfoSerializer

BeachInfoApiFeeder --> BeachInfoApiClient
BeachInfoApiFeeder --> BeachInfoMapper
BeachInfoMapper --> BeachInfoRecord

SQLiteBeachInfoSerializer --> BeachInfoDatabaseManager
SQLiteBeachInfoSerializer --> BeachInfoRecord

BeachInfoEventPublisher --> BeachInfoEventFactory
BeachInfoEventFactory --> BeachInfoRecord
BeachInfoEventPublisher --> BeachInfoRecord
```