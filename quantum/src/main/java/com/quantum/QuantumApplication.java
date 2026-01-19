package com.quantum;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class QuantumApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(QuantumApplication.class, args);
        printStartupBanner();
    }
    
    private static void printStartupBanner() {
        String banner = """
            
            ╔═══════════════════════════════════════════════════════════╗
            ║                                                           ║
            ║     ⚛️  QUANTUM COMPUTING SIMULATOR API ⚛️                ║
            ║                                                           ║
            ║          Simulating quantum algorithms in real-time      ║
            ║                                                           ║
            ╚═══════════════════════════════════════════════════════════╝
            
            🌐 API Documentation:
               • OpenAPI/Swagger: http://localhost:8080/swagger-ui.html
               • API Docs JSON:   http://localhost:8080/v3/api-docs
            
            🎯 Supported Algorithms:
               • Grover's Search      - Quantum search with amplitude amplification
               • Shor's Factoring     - Quantum period-finding for factorization
               • Quantum Teleport     - Bell state measurement and quantum state transfer
               • Quantum Vector Ops   - Vector operations on quantum states
            
            📊 Quick Start:
               curl -X POST "http://localhost:8080/api/quantum/jobs" \\
                 -H "Content-Type: application/json" \\
                 -H "X-User-ID: user123" \\
                 -d '{
                   "algorithm": "grover",
                   "backend": "SIMULATOR",
                   "parameters": {"qubits": 5, "marked_state": "10101"},
                   "shots": 1024
                 }'
            
            ✨ Status: Ready for quantum computation!
            """.stripIndent();
        
        System.out.println(banner);
    }
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Quantum Computing Simulator API")
                        .version("1.0.0")
                        .description("A comprehensive REST API for simulating quantum algorithms including Grover's search, Shor's factoring, and quantum teleportation.")
                        .contact(new Contact()
                                .name("Quantum Team")
                                .url("https://github.com/quantum-simulator")
                                .email("team@quantum-sim.dev"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}