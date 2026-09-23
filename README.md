Build and run

From PowerShell in your project directory:

docker compose build
docker compose up


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
                    │ XMLStreamReader       │
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