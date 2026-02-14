package com.example.Ejemplo.Service;

import com.example.Ejemplo.Entity.Ventas;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface VentasService {
    List<Ventas> getAllVentas();
    Ventas getVentaById(Integer id);
    Ventas saveVenta(Ventas venta) throws RuntimeException;
    Ventas updateVenta(Integer id, Ventas venta) throws RuntimeException;
    void deleteVenta(Integer id) throws RuntimeException;
}