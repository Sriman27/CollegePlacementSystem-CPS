# Quantum-Simulator
# ⚛️ Quantum Computing Simulator API

A comprehensive **Spring Boot REST API** for simulating quantum algorithms in real-time. Run famous quantum algorithms like Grover's search, Shor's factoring, quantum teleportation, and more - all via simple HTTP endpoints.

![Java Version](https://img.shields.io/badge/Java-21%20LTS-blue?style=flat-square)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.0-green?style=flat-square)
![Build Status](https://img.shields.io/badge/Build-Passing-brightgreen?style=flat-square)

## 🎯 Features

- **⚡ Quantum Algorithm Simulation**
  - Grover's Search Algorithm
  - Shor's Factorization Algorithm
  - Quantum Teleportation Protocol
  - Quantum State Vector Operations
  - Random Quantum Circuits

- **🚀 RESTful API**
  - Submit quantum jobs asynchronously
  - Track job status in real-time
  - Retrieve detailed results
  - Cancel queued jobs
  - Get system statistics

- **📊 Job Management**
  - Unique job IDs with UUID
  - Status tracking (QUEUED, RUNNING, COMPLETED, FAILED, CANCELLED)
  - Execution time metrics
  - User-based job history
  - Concurrent job processing

- **🔧 Multiple Quantum Backends**
  - SIMULATOR (default)
  - IBM_QUANTUM
  - AWS_BRAKET
  - GOOGLE_SYCAMORE

- **📚 Interactive Documentation**
  - OpenAPI 3.0 specification
  - Swagger UI for API exploration
  - Request/response schemas
  - Live endpoint testing

## 🛠️ Tech Stack

- **Java 21 LTS** - Latest long-term support version
- **Spring Boot 3.3.0** - Modern framework
- **SpringDoc OpenAPI** - Auto-generated API documentation
- **Jackson** - JSON processing
- **Concurrent APIs** - Async job processing

## 🚀 Getting Started

### Prerequisites

- **Java 21 LTS** or higher
- **Maven 3.9.x**

### Installation

```bash
# Clone the repository
git clone https://github.com/quantum-simulator/api.git
cd api

# Build the project
mvn clean package

# Run the application
mvn spring-boot:run
```

### Quick Start

```bash
# Check API health
curl http://localhost:8080/api/quantum/health

# List available algorithms
curl http://localhost:8080/api/quantum/algorithms

# Submit a Grover's search job
curl -X POST http://localhost:8080/api/quantum/jobs \
  -H "Content-Type: application/json" \
  -H "X-User-ID: user-123" \
  -d '{
    "algorithm": "grover",
    "backend": "SIMULATOR",
    "parameters": {
      "qubits": 5,
      "marked_state": "10101"
    },
    "shots": 1024
  }'

# Get job results
curl http://localhost:8080/api/quantum/jobs/{jobId}
```

## 📡 API Endpoints

### Job Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/quantum/jobs` | Submit a quantum job |
| `GET` | `/api/quantum/jobs/{jobId}` | Get job details |
| `GET` | `/api/quantum/users/{userId}/jobs` | Get user's jobs |
| `DELETE` | `/api/quantum/jobs/{jobId}` | Cancel a job |
| `GET` | `/api/quantum/jobs` | List all jobs |

### Algorithms

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/quantum/algorithms` | List available algorithms |
| `POST` | `/api/quantum/algorithms/grover` | Run Grover's search |
| `POST` | `/api/quantum/algorithms/shor` | Run Shor's factoring |
| `POST` | `/api/quantum/algorithms/teleport` | Run quantum teleportation |
| `POST` | `/api/quantum/algorithms/state-vector` | Run state vector simulation |
| `POST` | `/api/quantum/circuits/random` | Generate random circuit |

### System

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/quantum/health` | Health check |
| `GET` | `/api/quantum/system/info` | System information |
| `GET` | `/api/quantum/stats` | Compute statistics |

## 📖 Documentation

### OpenAPI/Swagger UI
```
http://localhost:8080/swagger-ui.html
```

### API Documentation
```
http://localhost:8080/v3/api-docs
```

## 🧮 Algorithm Details

### Grover's Search
Quantum search algorithm with amplitude amplification. Provides ~80% probability of finding the marked state.

```json
{
  "algorithm": "grover",
  "parameters": {
    "qubits": 5,
    "marked_state": "10101"
  },
  "shots": 1024
}
```

### Shor's Factoring
Quantum period-finding algorithm for factorizing large numbers efficiently.

```json
{
  "algorithm": "shor",
  "parameters": {
    "number": 15
  }
}
```

### Quantum Teleportation
Bell state measurement and quantum state transfer protocol.

```json
{
  "algorithm": "teleport",
  "shots": 1024
}
```

## 📊 Response Examples

### Job Submission
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "userId": "user-123",
  "algorithm": "grover",
  "backend": "SIMULATOR",
  "status": "QUEUED",
  "shots": 1024,
  "createdAt": "2026-01-19T15:30:00",
  "parameters": {
    "qubits": 5,
    "marked_state": "10101"
  }
}
```

### Completed Job
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "userId": "user-123",
  "algorithm": "grover",
  "backend": "SIMULATOR",
  "status": "COMPLETED",
  "shots": 1024,
  "executionTime": 2.34,
  "createdAt": "2026-01-19T15:30:00",
  "completedAt": "2026-01-19T15:30:05",
  "parameters": {
    "qubits": 5,
    "marked_state": "10101"
  },
  "results": {
    "counts": {
      "10101": 800,
      "01010": 224
    },
    "most_likely": "10101",
    "success_probability": 0.85,
    "qubits_used": 5
  }
}
```

## ⚙️ Configuration

### application.properties
```properties
server.port=8080
spring.application.name=quantum-simulator
logging.level.com.quantum=INFO
```

### Thread Pool Configuration
- **Thread Pool Size**: 4 concurrent workers
- **Max Queue**: Unlimited
- **Timeout**: 30 seconds default

## 🧪 Testing

```bash
# Run all tests
mvn test

# Run specific test
mvn test -Dtest=QuantumControllerTest

# Generate coverage report
mvn jacoco:report
```

## 🔬 Performance

- **Job Submission**: ~10ms
- **Algorithm Execution**: 1.5s - 5.5s (simulated)
- **Max Concurrent Jobs**: 4
- **Max Qubits Supported**: 50 (simulator)

## 🎓 Learning Resources

### Quantum Computing Basics
- [IBM Quantum Learning](https://learning.quantum.ibm.com/)
- [Qiskit Documentation](https://qiskit.org/)
- [Quantum Computing in 90 Minutes](https://www.nature.com/articles/s41567-021-01201-7)

### Algorithms
- **Grover's Algorithm**: O(√N) search vs O(N) classical
- **Shor's Algorithm**: Exponential speedup for factorization
- **Quantum Teleportation**: State transfer via entanglement

## 🚧 Roadmap

- [ ] Real quantum hardware integration (IBM, AWS)
- [ ] Advanced circuit optimization
- [ ] Quantum error correction
- [ ] Variational quantum algorithms
- [ ] Machine learning integration
- [ ] WebSocket support for real-time updates
- [ ] Database persistence
- [ ] Rate limiting and authentication

## 🤝 Contributing

Contributions welcome! Please:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see [LICENSE](LICENSE) file for details.

## 👥 Authors

**Quantum Team** - Initial work and continuous development

## 🙏 Acknowledgments

- IBM Quantum for algorithm inspiration
- Qiskit community for reference implementations
- Spring Boot team for excellent framework

## 📞 Support

For issues, questions, or suggestions:
- Email: ksrimannarayanan@gmail.com
- Check documentation: http://localhost:8080/swagger-ui.html

---

**⚛️ Simulating the quantum future, one job at a time!**


