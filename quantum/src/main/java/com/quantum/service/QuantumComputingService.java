package com.quantum.service;

import com.quantum.model.QuantumJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class QuantumComputingService {
    
    private static final Logger log = LoggerFactory.getLogger(QuantumComputingService.class);
    
    private final Random random = new Random();
    private final Map<String, QuantumJob> jobStore = new ConcurrentHashMap<>();
    private final ExecutorService executorService = Executors.newFixedThreadPool(4);
    private int totalJobsProcessed = 0;
    
    public QuantumJob submitJob(String userId, String algorithm, QuantumJob.Backend backend, 
                               Map<String, Object> parameters, int shots) {
        
        String jobId = UUID.randomUUID().toString();
        QuantumJob job = new QuantumJob();
        job.setId(jobId);
        job.setUserId(userId);
        job.setAlgorithm(algorithm);
        job.setBackend(backend);
        job.setParameters(parameters != null ? parameters : new HashMap<>());
        job.setShots(shots);
        job.setStatus("QUEUED");
        job.setCreatedAt(LocalDateTime.now());
        
        jobStore.put(jobId, job);
        
        log.info("📨 Quantum job submitted: {} | Algorithm: {} | User: {} | Backend: {}", 
                jobId.substring(0, 8) + "...", algorithm.toUpperCase(), userId, backend);
        
        // Process in background (async)
        processJobAsync(jobId);
        
        return job;
    }
    
    private void processJobAsync(String jobId) {
        executorService.submit(() -> {
            try {
                QuantumJob job = jobStore.get(jobId);
                if (job == null) return;
                
                // Simulate queue delay
                Thread.sleep(500 + random.nextInt(1000));
                job.setStatus("RUNNING");
                log.debug("▶️  Processing job: {}", jobId.substring(0, 8));
                
                // Simulate computation time
                Thread.sleep(1000 + random.nextInt(2000));
                
                // Run the quantum algorithm
                Map<String, Object> results = runQuantumAlgorithm(
                    job.getAlgorithm(), 
                    job.getParameters(),
                    job.getShots()
                );
                
                job.setResults(results);
                job.setStatus("COMPLETED");
                job.setExecutionTime(1.5 + random.nextDouble() * 3.5);
                job.setCompletedAt(LocalDateTime.now());
                
                totalJobsProcessed++;
                log.info("✓ Job completed: {} | Time: {:.2f}s | Algorithm: {}", 
                        jobId.substring(0, 8) + "...", job.getExecutionTime(), job.getAlgorithm().toUpperCase());
                
            } catch (InterruptedException e) {
                QuantumJob job = jobStore.get(jobId);
                if (job != null) {
                    job.setStatus("CANCELLED");
                }
                Thread.currentThread().interrupt();
                log.warn("❌ Job cancelled: {}", jobId.substring(0, 8));
            } catch (Exception e) {
                QuantumJob job = jobStore.get(jobId);
                if (job != null) {
                    job.setStatus("FAILED");
                }
                log.error("✗ Error processing job {}: {}", jobId.substring(0, 8), e.getMessage(), e);
            }
        });
    }
    
    public Map<String, Object> runQuantumAlgorithm(String algorithm, 
                                                   Map<String, Object> parameters,
                                                   int shots) {
        
        switch (algorithm.toUpperCase()) {
            case "GROVER":
                return runGroverAlgorithm(parameters, shots);
            case "SHOR":
                return runShorAlgorithm(parameters);
            case "QVECTOR":
                return runQuantumVectorAlgorithm(parameters, shots);
            case "TELEPORT":
                return runQuantumTeleportation(parameters, shots);
            default:
                return runRandomCircuit(parameters, shots);
        }
    }
    
    private Map<String, Object> runGroverAlgorithm(Map<String, Object> params, int shots) {
        int qubits = (int) params.getOrDefault("qubits", 5);
        String markedState = params.getOrDefault("marked_state", "10101").toString();
        
        Map<String, Object> results = new HashMap<>();
        Map<String, Integer> counts = new HashMap<>();
        
        // Grover's algorithm: finds marked state with ~80% probability
        for (int i = 0; i < shots; i++) {
            if (random.nextDouble() < 0.8) {
                counts.put(markedState, counts.getOrDefault(markedState, 0) + 1);
            } else {
                String randomState = generateRandomState(qubits);
                counts.put(randomState, counts.getOrDefault(randomState, 0) + 1);
            }
        }
        
        results.put("algorithm", "Grover");
        results.put("qubits", qubits);
        results.put("marked_state", markedState);
        results.put("counts", counts);
        results.put("success_probability", 0.8);
        results.put("oracle_calls", (int) Math.sqrt(Math.pow(2, qubits)));
        results.put("quantum_speedup", "quadratic");
        
        return results;
    }
    
    private Map<String, Object> runShorAlgorithm(Map<String, Object> params) {
        int number = (int) params.getOrDefault("number", 15);
        
        Map<String, Object> results = new HashMap<>();
        List<Integer> factors = new ArrayList<>();
        
        // Simulate Shor's factorization
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                factors.add(i);
                factors.add(number / i);
                break;
            }
        }
        
        results.put("algorithm", "Shor");
        results.put("input_number", number);
        results.put("factors", factors);
        results.put("qubits_required", (int) Math.ceil(Math.log(number) / Math.log(2)) * 2);
        results.put("quantum_speedup", "exponential");
        results.put("classical_complexity", "O(exp(n^(1/3)))");
        results.put("quantum_complexity", "O(n^2 log n)");
        
        return results;
    }
    
    private Map<String, Object> runQuantumVectorAlgorithm(Map<String, Object> params, int shots) {
        int dimensions = (int) params.getOrDefault("dimensions", 8);
        
        Map<String, Object> results = new HashMap<>();
        Map<String, Double> vector = new HashMap<>();
        
        // Generate quantum state vector
        double sum = 0;
        for (int i = 0; i < dimensions; i++) {
            String state = Integer.toBinaryString(i);
            // Pad with zeros
            while (state.length() < (int) Math.ceil(Math.log(dimensions) / Math.log(2))) {
                state = "0" + state;
            }
            double amplitude = random.nextDouble();
            vector.put(state, amplitude);
            sum += amplitude * amplitude;
        }
        
        // Normalize
        final double norm = Math.sqrt(sum);
        vector.replaceAll((k, v) -> v / norm);
        
        results.put("algorithm", "Quantum State Vector");
        results.put("dimensions", dimensions);
        results.put("state_vector", vector);
        results.put("entanglement_entropy", random.nextDouble());
        results.put("state_fidelity", 0.95 + random.nextDouble() * 0.05);
        
        return results;
    }
    
    private Map<String, Object> runQuantumTeleportation(Map<String, Object> params, int shots) {
        Map<String, Object> results = new HashMap<>();
        Map<String, Integer> counts = new HashMap<>();
        
        // Quantum teleportation simulation
        int success = 0;
        for (int i = 0; i < shots; i++) {
            if (random.nextDouble() < 0.75) { // 75% success rate
                counts.put("111", counts.getOrDefault("111", 0) + 1);
                success++;
            } else {
                counts.put("000", counts.getOrDefault("000", 0) + 1);
            }
        }
        
        results.put("algorithm", "Quantum Teleportation");
        results.put("qubits_used", 3);
        results.put("counts", counts);
        results.put("success_rate", (double) success / shots);
        results.put("classical_bits_sent", 2);
        results.put("entanglement_used", true);
        
        return results;
    }
    
    private Map<String, Object> runRandomCircuit(Map<String, Object> params, int shots) {
        int qubits = (int) params.getOrDefault("qubits", 5);
        int depth = (int) params.getOrDefault("depth", 10);
        
        Map<String, Object> results = new HashMap<>();
        Map<String, Integer> counts = new HashMap<>();
        
        // Generate random measurement outcomes
        for (int i = 0; i < shots; i++) {
            String state = generateRandomState(qubits);
            counts.put(state, counts.getOrDefault(state, 0) + 1);
        }
        
        results.put("algorithm", "Random Circuit");
        results.put("qubits", qubits);
        results.put("depth", depth);
        results.put("counts", counts);
        results.put("expected_hamming_weight", qubits / 2.0);
        results.put("actual_hamming_weight", calculateAverageHammingWeight(counts));
        results.put("entanglement", random.nextDouble());
        
        return results;
    }
    
    private String generateRandomState(int qubits) {
        StringBuilder state = new StringBuilder();
        for (int i = 0; i < qubits; i++) {
            state.append(random.nextInt(2));
        }
        return state.toString();
    }
    
    private double calculateAverageHammingWeight(Map<String, Integer> counts) {
        int total = counts.values().stream().mapToInt(Integer::intValue).sum();
        double sum = 0;
        
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            String state = entry.getKey();
            int count = entry.getValue();
            int ones = (int) state.chars().filter(ch -> ch == '1').count();
            sum += ones * count;
        }
        
        return sum / total;
    }
    
    public QuantumJob getJob(String jobId) {
        return jobStore.get(jobId);
    }
    
    public List<QuantumJob> getUserJobs(String userId) {
        return jobStore.values().stream()
                .filter(job -> job.getUserId().equals(userId))
                .toList();
    }
    
    public List<QuantumJob> getAllJobs() {
        return new ArrayList<>(jobStore.values());
    }
    
    public boolean cancelJob(String jobId) {
        QuantumJob job = jobStore.get(jobId);
        if (job != null && "QUEUED".equals(job.getStatus())) {
            job.setStatus("CANCELLED");
            log.info("Job cancelled: {}", jobId);
            return true;
        }
        return false;
    }
    
    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("total_jobs_submitted", jobStore.size());
        stats.put("total_jobs_completed", totalJobsProcessed);
        
        long queued = jobStore.values().stream().filter(j -> "QUEUED".equals(j.getStatus())).count();
        long running = jobStore.values().stream().filter(j -> "RUNNING".equals(j.getStatus())).count();
        long completed = jobStore.values().stream().filter(j -> "COMPLETED".equals(j.getStatus())).count();
        long failed = jobStore.values().stream().filter(j -> "FAILED".equals(j.getStatus())).count();
        long cancelled = jobStore.values().stream().filter(j -> "CANCELLED".equals(j.getStatus())).count();
        
        stats.put("queued", queued);
        stats.put("running", running);
        stats.put("completed", completed);
        stats.put("failed", failed);
        stats.put("cancelled", cancelled);
        
        double avgTime = jobStore.values().stream()
                .filter(j -> j.getExecutionTime() > 0)
                .mapToDouble(QuantumJob::getExecutionTime)
                .average()
                .orElse(0.0);
        
        stats.put("average_execution_time_seconds", String.format("%.2f", avgTime));
        
        return stats;
    }
    
    public void shutdown() {
        executorService.shutdown();
    }
}