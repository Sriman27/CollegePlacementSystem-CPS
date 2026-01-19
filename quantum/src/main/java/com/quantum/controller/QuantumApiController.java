package com.quantum.controller;

import com.quantum.model.QuantumJob;
import com.quantum.service.QuantumComputingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/quantum")
@Tag(name = "Quantum Computing API", description = "Run quantum algorithms via REST API")
public class QuantumApiController {
    
    private final QuantumComputingService quantumService;

    public QuantumApiController(QuantumComputingService quantumService) {
        this.quantumService = quantumService;
    }
    
    @Operation(summary = "Submit a quantum computing job")
    @PostMapping("/jobs")
    public ResponseEntity<QuantumJob> submitJob(
            @RequestHeader("X-User-ID") String userId,
            @RequestParam String algorithm,
            @RequestParam(defaultValue = "SIMULATOR") QuantumJob.Backend backend,
            @RequestParam(defaultValue = "1024") int shots,
            @RequestBody Map<String, Object> parameters) {
        
        QuantumJob job = quantumService.submitJob(userId, algorithm, backend, parameters, shots);
        return ResponseEntity.ok(job);
    }
    
    @Operation(summary = "Get job status and results")
    @GetMapping("/jobs/{jobId}")
    public ResponseEntity<QuantumJob> getJob(@PathVariable String jobId) {
        QuantumJob job = quantumService.getJob(jobId);
        if (job != null) {
            return ResponseEntity.ok(job);
        }
        return ResponseEntity.notFound().build();
    }
    
    @Operation(summary = "Get user's job history")
    @GetMapping("/jobs/user/{userId}")
    public ResponseEntity<List<QuantumJob>> getUserJobs(@PathVariable String userId) {
        List<QuantumJob> jobs = quantumService.getUserJobs(userId);
        return ResponseEntity.ok(jobs);
    }
    
    @Operation(summary = "Run Grover's search algorithm")
    @PostMapping("/algorithms/grover")
    public ResponseEntity<Map<String, Object>> runGrover(
            @RequestParam(defaultValue = "5") int qubits,
            @RequestParam(defaultValue = "10101") String markedState,
            @RequestParam(defaultValue = "1024") int shots) {
        
        Map<String, Object> params = Map.of(
            "qubits", qubits,
            "marked_state", markedState
        );
        
        Map<String, Object> results = quantumService.runQuantumAlgorithm("GROVER", params, shots);
        return ResponseEntity.ok(results);
    }
    
    @Operation(summary = "Run Shor's factorization algorithm")
    @PostMapping("/algorithms/shor")
    public ResponseEntity<Map<String, Object>> runShor(
            @RequestParam int number) {
        
        Map<String, Object> params = Map.of("number", number);
        Map<String, Object> results = quantumService.runQuantumAlgorithm("SHOR", params, 1);
        return ResponseEntity.ok(results);
    }
    
    @Operation(summary = "Run quantum state vector simulation")
    @PostMapping("/algorithms/state-vector")
    public ResponseEntity<Map<String, Object>> runStateVector(
            @RequestParam(defaultValue = "8") int dimensions) {
        
        Map<String, Object> params = Map.of("dimensions", dimensions);
        Map<String, Object> results = quantumService.runQuantumAlgorithm("QVECTOR", params, 1);
        return ResponseEntity.ok(results);
    }
    
    @Operation(summary = "Run quantum teleportation protocol")
    @PostMapping("/algorithms/teleport")
    public ResponseEntity<Map<String, Object>> runTeleportation(
            @RequestParam(defaultValue = "1024") int shots) {
        
        Map<String, Object> results = quantumService.runQuantumAlgorithm("TELEPORT", Map.of(), shots);
        return ResponseEntity.ok(results);
    }
    
    @Operation(summary = "Generate and run a random quantum circuit")
    @PostMapping("/circuits/random")
    public ResponseEntity<Map<String, Object>> runRandomCircuit(
            @RequestParam(defaultValue = "5") int qubits,
            @RequestParam(defaultValue = "10") int depth,
            @RequestParam(defaultValue = "1024") int shots) {
        
        Map<String, Object> params = Map.of(
            "qubits", qubits,
            "depth", depth
        );
        
        Map<String, Object> results = quantumService.runQuantumAlgorithm("RANDOM", params, shots);
        return ResponseEntity.ok(results);
    }
    
    @Operation(summary = "Get all running and completed jobs")
    @GetMapping("/jobs")
    public ResponseEntity<List<QuantumJob>> getAllJobs() {
        List<QuantumJob> jobs = quantumService.getAllJobs();
        return ResponseEntity.ok(jobs);
    }
    
    @Operation(summary = "Health check with quantum metrics")
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> health = Map.of(
            "status", "UP",
            "service", "Quantum Computing API",
            "quantum_backend", "simulator",
            "max_qubits", 50,
            "algorithms_supported", List.of("Grover", "Shor", "QVECTOR", "Teleport", "Random"),
            "api_version", "1.0.0"
        );
        return ResponseEntity.ok(health);
    }
    
    @Operation(summary = "Get quantum system information")
    @GetMapping("/system/info")
    public ResponseEntity<Map<String, Object>> systemInfo() {
        Map<String, Object> info = Map.of(
            "quantum_processor", "Spring Boot Quantum Simulator",
            "max_qubits_supported", 1024,
            "gate_set", List.of("H", "X", "Y", "Z", "CNOT", "SWAP", "RX", "RY", "RZ"),
            "entanglement_capability", true,
            "quantum_volume", 2048,
            "coherence_time", "100ms (simulated)",
            "gate_fidelity", "0.999",
            "available_backends", List.of("SIMULATOR", "IBM_QUANTUM", "AWS_BRAKET")
        );
        return ResponseEntity.ok(info);
    }
}