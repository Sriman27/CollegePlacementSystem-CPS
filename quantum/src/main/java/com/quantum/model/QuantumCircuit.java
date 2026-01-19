package com.quantum.model;

import java.util.List;

public class QuantumCircuit {
    private String name;
    private int qubits;
    private List<String> gates; // ["H 0", "CNOT 0 1", "MEASURE 0"]
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQubits() {
        return qubits;
    }

    public void setQubits(int qubits) {
        this.qubits = qubits;
    }

    public List<String> getGates() {
        return gates;
    }

    public void setGates(List<String> gates) {
        this.gates = gates;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}