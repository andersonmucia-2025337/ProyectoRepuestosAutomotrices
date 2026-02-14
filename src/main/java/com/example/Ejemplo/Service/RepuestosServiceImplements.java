package com.example.Ejemplo.Service;

import com.example.Ejemplo.Entity.Repuestos;
import com.example.Ejemplo.Repository.RepuestosRepository;
import com.example.Ejemplo.Repository.ProveedoresRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RepuestosServiceImplements implements RepuestosService {

    private final RepuestosRepository repuestosRepository;
    private final ProveedoresRepository proveedorRepository;

    public RepuestosServiceImplements(RepuestosRepository repuestosRepository,
                                      ProveedoresRepository proveedorRepository) {
        this.repuestosRepository = repuestosRepository;
        this.proveedorRepository = proveedorRepository;
    }

    @Override
    public List<Repuestos> getAllRepuestos() {
        return repuestosRepository.findAll();
    }

    @Override
    public Repuestos getRepuestoById(Integer id) {
        return repuestosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Repuesto con ID " + id + " no encontrado"));
    }

    @Override
    public Repuestos saveRepuesto(Repuestos repuesto) throws RuntimeException {
        if (!proveedorRepository.existsById(repuesto.getIdProveedor())) {
            throw new RuntimeException("El proveedor con ID " + repuesto.getIdProveedor() + " no existe");
        }

        validarRepuesto(repuesto);

        return repuestosRepository.save(repuesto);
    }

    @Override
    public Repuestos updateRepuesto(Integer id, Repuestos repuesto) throws RuntimeException {
        Repuestos repuestoExistente = repuestosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Repuesto con ID " + id + " no encontrado"));

        if (!proveedorRepository.existsById(repuesto.getIdProveedor())) {
            throw new RuntimeException("El proveedor con ID " + repuesto.getIdProveedor() + " no existe");
        }

        validarRepuesto(repuesto);

        repuestoExistente.setNombreRepuesto(repuesto.getNombreRepuesto());
        repuestoExistente.setCategoriaRepuesto(repuesto.getCategoriaRepuesto());
        repuestoExistente.setPrecioCompra(repuesto.getPrecioCompra());
        repuestoExistente.setPrecioVenta(repuesto.getPrecioVenta());
        repuestoExistente.setIdProveedor(repuesto.getIdProveedor());

        return repuestosRepository.save(repuestoExistente);
    }

    @Override
    public void deleteRepuesto(Integer id) throws RuntimeException {
        if (!repuestosRepository.existsById(id)) {
            throw new RuntimeException("Repuesto con ID " + id + " no encontrado");
        }
        repuestosRepository.deleteById(id);
    }

    private void validarRepuesto(Repuestos repuesto) {
        if (repuesto.getNombreRepuesto() == null || repuesto.getNombreRepuesto().trim().isEmpty()) {
            throw new RuntimeException("El nombre del repuesto no puede estar vacío");
        }
        repuesto.setNombreRepuesto(repuesto.getNombreRepuesto().trim());

        if (repuesto.getCategoriaRepuesto() == null || repuesto.getCategoriaRepuesto().trim().isEmpty()) {
            throw new RuntimeException("La categoría del repuesto no puede estar vacía");
        }
        repuesto.setCategoriaRepuesto(repuesto.getCategoriaRepuesto().trim());

        if (repuesto.getPrecioCompra() == null || repuesto.getPrecioCompra() <= 0) {
            throw new RuntimeException("El precio de compra debe ser mayor a 0");
        }

        if (repuesto.getPrecioVenta() == null || repuesto.getPrecioVenta() <= 0) {
            throw new RuntimeException("El precio de venta debe ser mayor a 0");
        }

        if (repuesto.getPrecioVenta() <= repuesto.getPrecioCompra()) {
            throw new RuntimeException("El precio de venta debe ser mayor al precio de compra");
        }

        if (repuesto.getIdProveedor() == null) {
            throw new RuntimeException("El ID del proveedor no puede estar vacío");
        }
    }
}