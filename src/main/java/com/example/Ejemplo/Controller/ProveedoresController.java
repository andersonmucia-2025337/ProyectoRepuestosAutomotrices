package com.example.Ejemplo.Controller;

import com.example.Ejemplo.Entity.Proveedores;
import com.example.Ejemplo.Service.ProveedoresService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedoresController {
    private final ProveedoresService proveedoresService;

    public ProveedoresController(ProveedoresService proveedoresService) {
        this.proveedoresService = proveedoresService;
    }

    @GetMapping
    public List<Proveedores> getAllProveedores() {
        return proveedoresService.getAllProveedores();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getIdProveedores (@PathVariable Integer id) {
        try {
            Proveedores proveedores = proveedoresService.getIdProveedores(id);
            return ResponseEntity.ok(proveedores);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
        @PostMapping
        public ResponseEntity<Object> createProveedores (@RequestBody Proveedores proveedores){
            try {
                Proveedores createProveedores = proveedoresService.saveProveedores(proveedores);
                return new ResponseEntity < > (createProveedores,HttpStatus.CREATED);
            }catch(RuntimeException e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProveedor(@PathVariable Integer id,
                                                  @RequestBody Proveedores proveedores) {
        try {
            Proveedores updatedProveedor = proveedoresService.updateProveedor(id, proveedores);
            return ResponseEntity.ok(updatedProveedor);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProveedor(@PathVariable Integer id) {
        try {
            proveedoresService.deleteProveedor(id);
            return ResponseEntity.ok("Proveedor con ID " + id + " eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

