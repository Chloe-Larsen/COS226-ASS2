# COS226 Assignment 2
| Name | Student Number |
| -------- | ------- | 
|Anchen Kruger| u25073703|
|Caleb Jennings| u25173805|
|Chloe Larsen| u25004141|

## Results of the Experiment
### 4 Producers & 4 Consumers
| Queue Type | Producer Count | Consumer Count | Time (seconds) | Throughput jobs/s | Produced Count | Processed Count |
| -------- | ------- | -------- | ------- | -------- | ------- | ------- |
| bounded  | 4 | 4 | 0.047s | 255114.2 | 12000 | 12000 | 
| lockfree | 4 | 4 | 0.031s | 386837.3 | 12000 | 12000 | 

---

### 6 Producers & 2 Consumers
| Queue Type | Producer Count | Consumer Count | Time (seconds) | Throughput jobs/s | Produced Count | Processed Count |
| -------- | ------- | -------- | ------- | -------- | ------- | ------- |
| bounded  | 6 | 2 | 0.014s | 867453.3 | 12000 | 12000 | 
| lockfree | 6 | 2 | 0.006s | 2139794.6 | 12000 | 12000 | 

---

### 2 Producers & 6 Consumers
| Queue Type | Producer Count | Consumer Count | Time (seconds) | Throughput jobs/s | Produced Count | Processed Count |
| -------- | ------- | -------- | ------- | -------- | ------- | ------- |
| bounded  | 2 | 6 | 0.015s | 812939.0 | 12000 | 12000 | 
| lockfree | 2 | 6 | 0.009s | 1289168.6 | 12000 | 12000 | 