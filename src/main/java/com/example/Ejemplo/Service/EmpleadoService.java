package com.example.Ejemplo.Service;


import com.example.Ejemplo.Entity.Empleado;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmpleadoService {
    List<Empleado> getAllEmpleados();
    Empleado getIdEmpleado(Integer id);
    Empleado saveEmpleado(Empleado empleado) throws RuntimeException;
    Empleado updateEmpleado(Integer id, Empleado empleado) throws RuntimeException;
    void deleteEmpleado(Integer id) throws RuntimeException;
}