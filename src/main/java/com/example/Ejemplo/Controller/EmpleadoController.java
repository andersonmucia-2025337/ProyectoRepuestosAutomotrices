package com.example.Ejemplo.Controller;

import com.example.Ejemplo.Model.Empleado;
import com.example.Ejemplo.Service.EmpleadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {
    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping
    public List<Empleado> getAllEmpleados() {
        return empleadoService.getAllEmpleados();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEmpleadoById(@PathVariable Integer id) {
        try {
            Empleado empleado = empleadoService.getIdEmpleado(id);
            return ResponseEntity.ok(empleado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> createEmpleado(@RequestBody Empleado empleado) {
        try {
            Empleado createdEmpleado = empleadoService.saveEmpleado(empleado);
            return new ResponseEntity<>(createdEmpleado, HttpStatus.CREATED);
        } catch (Exception e) {  // Cambié de RuntimeException a Exception
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEmpleado(@PathVariable Integer id,
                                                 @RequestBody Empleado empleado) {
        try {
            Empleado updatedEmpleado = empleadoService.updateEmpleado(id, empleado);
            return ResponseEntity.ok(updatedEmpleado);
        } catch (Exception e) {  // Cambié de RuntimeException a Exception
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmpleado(@PathVariable Integer id) {
        try {
            empleadoService.deleteEmpleado(id);
            return ResponseEntity.ok("Empleado con ID " + id + " eliminado correctamente");
        } catch (Exception e) {  // Cambié de RuntimeException a Exception
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // MANEJADOR GLOBAL DE EXCEPCIONES - AÑADE ESTO
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex) {
        // Log del error en consola
        System.err.println("Error capturado en handleAllExceptions: " + ex.getMessage());
        ex.printStackTrace();

        // Devuelve el mensaje de error al cliente
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}