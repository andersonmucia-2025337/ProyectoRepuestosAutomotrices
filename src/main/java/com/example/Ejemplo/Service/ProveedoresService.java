package com.example.Ejemplo.Service;

import com.example.Ejemplo.Entity.Proveedores;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProveedoresService {
    List<Proveedores> getAllProveedores();
    Proveedores getIdProveedores(Integer id) throws RuntimeException;
    Proveedores saveProveedores(Proveedores proveedores) throws RuntimeException;
    Proveedores updateProveedor(Integer id, Proveedores proveedor) throws RuntimeException;
    void deleteProveedor(Integer id) throws RuntimeException;
}
