package com.example.demo.web;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import java.security.SecureRandom;
import java.util.Random;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/migration")
public class CustomMetricController {

  @Getter private final MeterRegistry meterRegistry;
  private final SecureRandom random;

  public CustomMetricController(MeterRegistry meterRegistry) {
    this.meterRegistry = meterRegistry;
    random = new SecureRandom();
  }

  @PostConstruct
  public void initializeMetrics() {
    Counter.builder("migration.success")
        .description("Number of successful migration")
        .tags("type", "demo")
        .register(meterRegistry);

    Counter.builder("migration.failure")
        .description("Number of failed migration")
        .tags("type", "demo")
        .register(meterRegistry);
  }

  @GetMapping(value = "/student", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> getCourse() throws InterruptedException {
    log.info("Got request for migrating students.");

    for (int i = 0; i < 100; i++) {
      int randomNumber = random.nextInt(100);
      long sleepTime = random.nextInt(10);
      if (randomNumber % 2 == 0) {
        Counter successCounter = meterRegistry.counter("migration.success", "type", "demo");
        successCounter.increment();
        System.out.println("Even - " + randomNumber);
      } else {
        Counter failureCounter = meterRegistry.counter("migration.failure", "type", "demo");
        failureCounter.increment();
        System.out.println("Odd - " + randomNumber);
      }
      Thread.sleep(sleepTime * 1000);
    }
    return ResponseEntity.ok("Migration has been done successfully.");
  }
}
