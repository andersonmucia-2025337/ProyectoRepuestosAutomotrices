package com.example.Ejemplo.Controller;

import com.example.Ejemplo.Entity.Repuestos;
import com.example.Ejemplo.Service.RepuestosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/repuestos")
public class RepuestosController {

    private final RepuestosService repuestosService;

    public RepuestosController(RepuestosService repuestosService) {
        this.repuestosService = repuestosService;
    }

    @GetMapping
    public List<Repuestos> getAllRepuestos() {
        return repuestosService.getAllRepuestos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRepuestoById(@PathVariable Integer id) {
        try {
            Repuestos repuesto = repuestosService.getRepuestoById(id);
            return ResponseEntity.ok(repuesto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> createRepuesto(@RequestBody Repuestos repuesto) {
        try {
            Repuestos createdRepuesto = repuestosService.saveRepuesto(repuesto);
            return new ResponseEntity<>(createdRepuesto, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRepuesto(@PathVariable Integer id,
                                                 @RequestBody Repuestos repuesto) {
        try {
            Repuestos updatedRepuesto = repuestosService.updateRepuesto(id, repuesto);
            return ResponseEntity.ok(updatedRepuesto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRepuesto(@PathVariable Integer id) {
        try {
            repuestosService.deleteRepuesto(id);
            return ResponseEntity.ok("Repuesto con ID " + id + " eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
