package com.quantum.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class QuantumJob {
    private String id;
    private String userId;
    private String algorithm;
    
    public enum Backend {
        SIMULATOR, IBM_QUANTUM, AWS_BRAKET, GOOGLE_SYCAMORE
    }
    
    private Backend backend = Backend.SIMULATOR;
    private String status = "QUEUED"; // QUEUED, RUNNING, COMPLETED, FAILED
    private Map<String, Object> parameters = new HashMap<>();
    private Map<String, Object> results = new HashMap<>();
    private int shots = 1024;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime completedAt;
    private double executionTime;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public Backend getBackend() {
        return backend;
    }

    public void setBackend(Backend backend) {
        this.backend = backend;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public Map<String, Object> getResults() {
        return results;
    }

    public void setResults(Map<String, Object> results) {
        this.results = results;
    }

    public int getShots() {
        return shots;
    }

    public void setShots(int shots) {
        this.shots = shots;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public double getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(double executionTime) {
        this.executionTime = executionTime;
    }
}