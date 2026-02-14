package com.example.Ejemplo.Service;

import com.example.Ejemplo.Entity.Repuestos;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface RepuestosService {
    List<Repuestos> getAllRepuestos();
    Repuestos getRepuestoById(Integer id);
    Repuestos saveRepuesto(Repuestos repuesto) throws RuntimeException;
    Repuestos updateRepuesto(Integer id, Repuestos repuesto) throws RuntimeException;
    void deleteRepuesto(Integer id) throws RuntimeException;
}