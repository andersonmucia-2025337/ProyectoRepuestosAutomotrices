package com.example.Ejemplo.Controller;

import com.example.Ejemplo.Entity.Ventas;
import com.example.Ejemplo.Service.VentasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentasController {

    private final VentasService ventasService;

    public VentasController(VentasService ventasService) {
        this.ventasService = ventasService;
    }

    @GetMapping
    public List<Ventas> getAllVentas() {
        return ventasService.getAllVentas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getVentaById(@PathVariable Integer id) {
        try {
            Ventas venta = ventasService.getVentaById(id);
            return ResponseEntity.ok(venta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> createVenta(@RequestBody Ventas venta) {
        try {
            Ventas createdVenta = ventasService.saveVenta(venta);
            return new ResponseEntity<>(createdVenta, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateVenta(@PathVariable Integer id,
                                              @RequestBody Ventas venta) {
        try {
            Ventas updatedVenta = ventasService.updateVenta(id, venta);
            return ResponseEntity.ok(updatedVenta);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteVenta(@PathVariable Integer id) {
        try {
            ventasService.deleteVenta(id);
            return ResponseEntity.ok("Venta con ID " + id + " eliminada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}