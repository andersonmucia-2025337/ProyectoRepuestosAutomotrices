package com.example.Ejemplo.Service;

import com.example.Ejemplo.Entity.Ventas;
import com.example.Ejemplo.Repository.VentasRepository;
import com.example.Ejemplo.Repository.EmpleadoRepository;
import com.example.Ejemplo.Repository.RepuestosRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VentasServiceImplements implements VentasService {

    private final VentasRepository ventasRepository;
    private final EmpleadoRepository empleadoRepository;
    private final RepuestosRepository repuestosRepository;

    public VentasServiceImplements(VentasRepository ventasRepository,
                                   EmpleadoRepository empleadoRepository,
                                   RepuestosRepository repuestosRepository) {
        this.ventasRepository = ventasRepository;
        this.empleadoRepository = empleadoRepository;
        this.repuestosRepository = repuestosRepository;
    }

    @Override
    public List<Ventas> getAllVentas() {
        return ventasRepository.findAll();
    }

    @Override
    public Ventas getVentaById(Integer id) {
        return ventasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta con ID " + id + " no encontrada"));
    }

    @Override
    public Ventas saveVenta(Ventas venta) throws RuntimeException {
        if (!empleadoRepository.existsById(venta.getIdEmpleado())) {
            throw new RuntimeException("El empleado con ID " + venta.getIdEmpleado() + " no existe");
        }

        if (!repuestosRepository.existsById(venta.getIdRepuesto())) {
            throw new RuntimeException("El repuesto con ID " + venta.getIdRepuesto() + " no existe");
        }

        validarVenta(venta);

        return ventasRepository.save(venta);
    }

    @Override
    public Ventas updateVenta(Integer id, Ventas venta) throws RuntimeException {
        Ventas ventaExistente = ventasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta con ID " + id + " no encontrada"));

        if (!empleadoRepository.existsById(venta.getIdEmpleado())) {
            throw new RuntimeException("El empleado con ID " + venta.getIdEmpleado() + " no existe");
        }

        if (!repuestosRepository.existsById(venta.getIdRepuesto())) {
            throw new RuntimeException("El repuesto con ID " + venta.getIdRepuesto() + " no existe");
        }

        validarVenta(venta);

        ventaExistente.setFechaVenta(venta.getFechaVenta());
        ventaExistente.setCantidad(venta.getCantidad());
        ventaExistente.setTotal(venta.getTotal());
        ventaExistente.setIdEmpleado(venta.getIdEmpleado());
        ventaExistente.setIdRepuesto(venta.getIdRepuesto());

        return ventasRepository.save(ventaExistente);
    }

    @Override
    public void deleteVenta(Integer id) throws RuntimeException {
        if (!ventasRepository.existsById(id)) {
            throw new RuntimeException("Venta con ID " + id + " no encontrada");
        }
        ventasRepository.deleteById(id);
    }

    private void validarVenta(Ventas venta) {
        if (venta.getFechaVenta() == null) {
            throw new RuntimeException("La fecha de venta no puede estar vacía");
        }

        if (venta.getCantidad() == null || venta.getCantidad() <= 0) {
            throw new RuntimeException("La cantidad debe ser mayor a 0");
        }

        if (venta.getTotal() == null || venta.getTotal() <= 0) {
            throw new RuntimeException("El total debe ser mayor a 0");
        }

        if (venta.getIdEmpleado() == null) {
            throw new RuntimeException("El ID del empleado no puede estar vacío");
        }

        if (venta.getIdRepuesto() == null) {
            throw new RuntimeException("El ID del repuesto no puede estar vacío");
        }
    }
}