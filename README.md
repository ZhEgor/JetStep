## Compose compiler metrics

Run the following command to get and analyse compose compiler metrics:

```bash
./gradlew assembleRelease -PenableComposeCompilerMetrics=true -PenableComposeCompilerReports=true
```

The reports files will be added to [build/compose-reports](build/compose-reports). The metrics files will also be
added to [build/compose-metrics](build/compose-metrics).

For more information on Compose compiler metrics, see [this blog post](https://medium.com/androiddevelopers/jetpack-compose-stability-explained-79c10db270c8).