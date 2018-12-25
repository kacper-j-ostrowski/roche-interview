# Project for Roche Interview

Requirements for the service
    • The service has to be written in a JVM language of your choice
    • You can use any frameworks of your choice
    • The service should have ability to be started with a single Gradle or Maven command
    • The service should run on port 8888

Requirements for the service API:
    • Service should expose one endpoint for a GET request
    • The endpoint should accept list of IP addresses passed as request parameters
    • The endpoint should accept at least 1 and maximum 5 ip addresses
    • If among the passed IP addresses there are addresses from countries from the northern hemisphere, service should return these country names.
    • Response should contain list of unique names (no repetitions of names) sorted alphabetically

### Installing

Windows

```
./gradlew.bat clean bootRun
```

Linux

```
./gradlew clean bootRun
```

### Example use case

```
curl "http://localhost:8888/northcountries?ip=8.8.8.8&ip=8.8.0.0&ip=177.0.0.0&ip=180.0.0.0&ip=190.0.0.0"
```

```
Response:

{
   "northcountries":[
      "Colombia",
      "Japan",
      "United States"
   ]
}
```