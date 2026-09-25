# StAX Payment Processor

A Java/Spring Boot application for processing payment XML files using **StAX (Streaming API for XML)**.

The project is designed as a learning project to demonstrate how large XML payment files can be processed using event-based XML parsing, validation, persistence, and XML generation.

---

## Overview

The application exposes a REST API that allows users to upload a payment XML file.

The processing flow is:

```text
                ┌─────────────────┐
                │   Swagger UI    │
                │  Upload XML     │
                └────────┬────────┘
                         │
                         ▼
                ┌─────────────────┐
                │ REST Controller  │
                └────────┬────────┘
                         │
                         ▼
                ┌─────────────────┐
                │ Payment File    │
                │ Service         │
                └────────┬────────┘
                         │
              ┌──────────┴──────────┐
              │                     │
              ▼                     ▼
      ┌──────────────┐      ┌───────────────┐
      │ XML          │      │ StAX XML      │
      │ Validation   │      │ Parser        │
      └──────────────┘      └───────┬───────┘
                                    │
                                    ▼
                            ┌───────────────┐
                            │ Payment       │
                            │ Validation   │
                            └───────┬───────┘
                                    │
                                    ▼
                            ┌───────────────┐
                            │ PostgreSQL    │
                            └───────┬───────┘
                                    │
                                    ▼
                            ┌───────────────┐
                            │ XML Writer    │
                            └───────┬───────┘
                                    │
                                    ▼
                         processed-payments.xml
                         
                         
                     
Build and run

From PowerShell in your project directory:

docker compose build
docker compose up

If Docker Compose configuration change rebuild the container is needed:

docker compose down

docker compose build --no-cache
docker compose up -d

docker compose up -d --build




verify the JVM debugger
docker logs payment-xml-processor
docker logs -f payment-xml-processor


docker ps

You should eventually see:

payment-postgres
payment-xml-processor



                    ┌──────────────────────┐
                    │   REST API / Upload  │
                    │  POST /payments/file │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │ Payment File Service │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │    StAX Parser       │
                    │ XMLStreamReader      │
                    └──────────┬───────────┘
                               │
                     Payment by Payment
                               │
                               ▼
                    ┌──────────────────────┐
                    │ Payment Validator    │
                    │                      │
                    │ IBAN                 │
                    │ Amount               │
                    │ Currency             │
                    │ Required fields      │
                    └──────────┬───────────┘
                               │
                     ┌─────────┴─────────┐
                     ▼                   ▼
              Valid Payment       Invalid Payment
                     │                   │
                     ▼                   ▼
                Repository          Error Repository
                     │
                     ▼
                  Database