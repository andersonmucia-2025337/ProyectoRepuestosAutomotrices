package com.example.Ejemplo.Service;

import com.example.Ejemplo.Model.Empleado;
import com.example.Ejemplo.Repository.EmpleadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class EmpleadoServiceImplements implements EmpleadoService {
    private final EmpleadoRepository empleadoRepository;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@(gmail\\.com|email\\.com|yahoo\\.com)$"
    );

    public EmpleadoServiceImplements(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public List<Empleado> getAllEmpleados() {
        return empleadoRepository.findAll();
    }

    @Override
    public Empleado getIdEmpleado(Integer id) {
        return empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado con ID " + id + " no encontrado"));
    }

    @Override
    public Empleado saveEmpleado(Empleado empleado) throws RuntimeException {
        try {
            System.out.println("Empleado recibido para guardar: " + empleado);

            validarEmpleado(empleado);
            System.out.println("Validaciones pasadas correctamente");

            System.out.println("Buscando duplicados para email: " + empleado.getEmailEmpleado());
            List<Empleado> todosEmpleados = empleadoRepository.findAll();
            Optional<Empleado> empleadoExistente = todosEmpleados.stream()
                    .filter(e -> e.getEmailEmpleado() != null &&
                            e.getEmailEmpleado().equalsIgnoreCase(empleado.getEmailEmpleado()))
                    .findFirst();

            if (empleadoExistente.isPresent()) {
                System.out.println("ERROR: Email duplicado encontrado - " + empleado.getEmailEmpleado());
                throw new RuntimeException("Ya existe un empleado con el email: " + empleado.getEmailEmpleado());
            }

            System.out.println("No se encontraron duplicados, guardando...");
            Empleado empleadoGuardado = empleadoRepository.save(empleado);
            System.out.println("Empleado guardado exitosamente con ID: " + empleadoGuardado.getIdEmpleado());

            return empleadoGuardado;

        } catch(RuntimeException e) {
            System.out.println("ERROR en saveEmpleado: " + e.getMessage());
            throw e;
        } catch(Exception e) {
            System.out.println("ERROR inesperado en saveEmpleado: " + e.getMessage());
            throw new RuntimeException("Error al guardar empleado: " + e.getMessage());
        }
    }

    @Override
    public Empleado updateEmpleado(Integer id, Empleado empleado) throws RuntimeException {
        try {

            Empleado empleadoExistente = empleadoRepository.findById(id)
                    .orElseThrow(() -> {
                        System.out.println("ERROR: Empleado con ID " + id + " no encontrado");
                        return new RuntimeException("Empleado con ID " + id + " no encontrado");
                    });

            System.out.println("Empleado existente encontrado: " + empleadoExistente.getEmailEmpleado());

            validarEmpleado(empleado);
            System.out.println("Validaciones pasadas correctamente");

            String nuevoEmail = empleado.getEmailEmpleado();
            String emailActual = empleadoExistente.getEmailEmpleado();

            if (!emailActual.equalsIgnoreCase(nuevoEmail)) {
                System.out.println("Email cambiado de '" + emailActual + "' a '" + nuevoEmail + "'");
                System.out.println("Buscando si el nuevo email existe en otro empleado...");

                List<Empleado> todosEmpleados = empleadoRepository.findAll();
                Optional<Empleado> otroEmpleadoConEmail = todosEmpleados.stream()
                        .filter(e -> e.getIdEmpleado() != id &&
                                e.getEmailEmpleado() != null &&
                                e.getEmailEmpleado().equalsIgnoreCase(nuevoEmail))
                        .findFirst();

                if (otroEmpleadoConEmail.isPresent()) {
                    System.out.println("ERROR: Otro empleado ya tiene el email: " + nuevoEmail);
                    throw new RuntimeException("Ya existe otro empleado con el email: " + nuevoEmail);
                }
            } else {
                System.out.println("Email no cambiado, manteniendo: " + emailActual);
            }

            System.out.println("Actualizando campos del empleado...");
            empleadoExistente.setNombreEmpleado(empleado.getNombreEmpleado());
            empleadoExistente.setApellidoEmpleado(empleado.getApellidoEmpleado());
            empleadoExistente.setPuestoEmpleado(empleado.getPuestoEmpleado());
            empleadoExistente.setEmailEmpleado(empleado.getEmailEmpleado());

            Empleado empleadoActualizado = empleadoRepository.save(empleadoExistente);
            System.out.println("Empleado actualizado exitosamente");

            return empleadoActualizado;

        } catch(RuntimeException e) {
            System.out.println("ERROR en updateEmpleado: " + e.getMessage());
            throw e;
        } catch(Exception e) {
            System.out.println("ERROR inesperado en updateEmpleado: " + e.getMessage());
            throw new RuntimeException("Error al actualizar empleado: " + e.getMessage());
        }
    }

    @Override
    public void deleteEmpleado(Integer id) throws RuntimeException {
        try {

            if (!empleadoRepository.existsById(id)) {
                throw new RuntimeException("Empleado con ID " + id + " no encontrado");
            }

            System.out.println("Empleado encontrado, procediendo a eliminar...");
            empleadoRepository.deleteById(id);
            System.out.println("Empleado eliminado exitosamente");

        } catch(RuntimeException e) {
            System.out.println("ERROR en deleteEmpleado: " + e.getMessage());
            throw e;
        }
    }

    private void validarEmpleado(Empleado empleado) {

        if (empleado == null) {
            System.out.println("ERROR: El empleado es null");
            throw new RuntimeException("El empleado no puede ser nulo");
        }

        String nombre = empleado.getNombreEmpleado();
        String apellido = empleado.getApellidoEmpleado();
        String puesto = empleado.getPuestoEmpleado();
        String email = empleado.getEmailEmpleado();

        System.out.println("Valores recibidos:");
        System.out.println("  Nombre: '" + nombre + "'");
        System.out.println("  Apellido: '" + apellido + "'");
        System.out.println("  Puesto: '" + puesto + "'");
        System.out.println("  Email: '" + email + "'");

        if (nombre == null) {
            System.out.println("ERROR: nombre es null");
            throw new RuntimeException("El nombre no puede estar vacío");
        }

        if (apellido == null) {
            System.out.println("ERROR: apellido es null");
            throw new RuntimeException("El apellido no puede estar vacío");
        }

        if (puesto == null) {
            System.out.println("ERROR: puesto es null");
            throw new RuntimeException("El puesto no puede estar vacío");
        }

        if (email == null) {
            System.out.println("ERROR: email es null");
            throw new RuntimeException("El email no puede estar vacío");
        }

        String nombreTrim = nombre.trim();
        String apellidoTrim = apellido.trim();
        String puestoTrim = puesto.trim();
        String emailTrim = email.trim();

        System.out.println("Valores después de trim:");
        System.out.println("  Nombre: '" + nombreTrim + "'");
        System.out.println("  Apellido: '" + apellidoTrim + "'");
        System.out.println("  Puesto: '" + puestoTrim + "'");
        System.out.println("  Email: '" + emailTrim + "'");

        if (nombreTrim.isEmpty()) {
            System.out.println("ERROR: nombre está vacío después de trim");
            throw new RuntimeException("El nombre no puede estar vacío o contener solo espacios");
        }

        if (apellidoTrim.isEmpty()) {
            System.out.println("ERROR: apellido está vacío después de trim");
            throw new RuntimeException("El apellido no puede estar vacío o contener solo espacios");
        }

        if (puestoTrim.isEmpty()) {
            System.out.println("ERROR: puesto está vacío después de trim");
            throw new RuntimeException("El puesto no puede estar vacío o contener solo espacios");
        }

        if (emailTrim.isEmpty()) {
            System.out.println("ERROR: email está vacío después de trim");
            throw new RuntimeException("El email no puede estar vacío");
        }

        if (!EMAIL_PATTERN.matcher(emailTrim).matches()) {
            System.out.println("ERROR: email no cumple el patrón - " + emailTrim);
            throw new RuntimeException("El email debe tener un formato válido (@gmail.com, @email.com o @yahoo.com)");
        }

        if (nombreTrim.length() < 2) {
            System.out.println("ERROR: nombre tiene menos de 2 caracteres");
            throw new RuntimeException("El nombre debe tener al menos 2 caracteres");
        }

        if (apellidoTrim.length() < 2) {
            System.out.println("ERROR: apellido tiene menos de 2 caracteres");
            throw new RuntimeException("El apellido debe tener al menos 2 caracteres");
        }
        empleado.setNombreEmpleado(nombreTrim);
        empleado.setApellidoEmpleado(apellidoTrim);
        empleado.setPuestoEmpleado(puestoTrim);
        empleado.setEmailEmpleado(emailTrim);
    }
}