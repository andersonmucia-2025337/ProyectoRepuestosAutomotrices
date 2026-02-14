package com.example.Ejemplo.Service;

import com.example.Ejemplo.Entity.Proveedores;
import com.example.Ejemplo.Repository.ProveedoresRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class ProveedoresServiceImplements implements ProveedoresService {

    private final ProveedoresRepository proveedorRepository;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@(gmail\\.com|email\\.com|yahoo\\.com)$"
    );
    private static final Pattern TELEFONO_PATTERN = Pattern.compile("^[0-9]{8}$");

    public ProveedoresServiceImplements(ProveedoresRepository proveedoresRepository) {
        this.proveedorRepository = proveedoresRepository;
    }

    @Override
    public List<Proveedores> getAllProveedores() {
        return proveedorRepository.findAll();
    }

    @Override
    public Proveedores getIdProveedores(Integer id) {
        return proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor con ID " + id + " no encontrado"));
    }

    @Override
    public Proveedores saveProveedores(Proveedores proveedores) throws RuntimeException {
        try {
            validarProveedor(proveedores);

            List<Proveedores> todos = proveedorRepository.findAll();
            for (Proveedores p : todos) {
                if (p.getEmailProveedor().equalsIgnoreCase(proveedores.getEmailProveedor())) {
                    throw new RuntimeException("Ya existe un proveedor con el email: " + proveedores.getEmailProveedor());
                }
            }

            for (Proveedores p : todos) {
                if (p.getTelefonoProveedor().equals(proveedores.getTelefonoProveedor())) {
                    throw new RuntimeException("Ya existe un proveedor con el teléfono: " + proveedores.getTelefonoProveedor());
                }
            }

            return proveedorRepository.save(proveedores);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar proveedor: " + e.getMessage());
        }
    }

    @Override
    public Proveedores updateProveedor(Integer id, Proveedores proveedor) throws RuntimeException {
        try {
            Proveedores proveedorExistente = proveedorRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Proveedor con ID " + id + " no encontrado"));

            validarProveedor(proveedor);

            List<Proveedores> todos = proveedorRepository.findAll();
            for (Proveedores p : todos) {
                if (!p.getIdProveedor().equals(id) &&
                        p.getEmailProveedor().equalsIgnoreCase(proveedor.getEmailProveedor())) {
                    throw new RuntimeException("Ya existe otro proveedor con el email: " + proveedor.getEmailProveedor());
                }
            }

            for (Proveedores p : todos) {
                if (!p.getIdProveedor().equals(id) &&
                        p.getTelefonoProveedor().equals(proveedor.getTelefonoProveedor())) {
                    throw new RuntimeException("Ya existe otro proveedor con el teléfono: " + proveedor.getTelefonoProveedor());
                }
            }

            proveedorExistente.setNombreProveedor(proveedor.getNombreProveedor());
            proveedorExistente.setTelefonoProveedor(proveedor.getTelefonoProveedor());
            proveedorExistente.setDireccion(proveedor.getDireccion());
            proveedorExistente.setEmailProveedor(proveedor.getEmailProveedor());

            return proveedorRepository.save(proveedorExistente);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar proveedor: " + e.getMessage());
        }
    }

    @Override
    public void deleteProveedor(Integer id) throws RuntimeException {
        try {
            if (!proveedorRepository.existsById(id)) {
                throw new RuntimeException("Proveedor con ID " + id + " no encontrado");
            }
            proveedorRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar proveedor: " + e.getMessage());
        }
    }

    private void validarProveedor(Proveedores proveedor) {
        if (proveedor.getNombreProveedor() == null ||
                proveedor.getNombreProveedor().trim().isEmpty()) {
            throw new RuntimeException("El nombre del proveedor no puede estar vacío");
        }
        proveedor.setNombreProveedor(proveedor.getNombreProveedor().trim());

        if (proveedor.getNombreProveedor().length() < 3) {
            throw new RuntimeException("El nombre del proveedor debe tener al menos 3 caracteres");
        }

        if (proveedor.getTelefonoProveedor() == null) {
            throw new RuntimeException("El teléfono no puede estar vacío");
        }
        String telefonoStr = String.valueOf(proveedor.getTelefonoProveedor());
        if (!TELEFONO_PATTERN.matcher(telefonoStr).matches()) {
            throw new RuntimeException("El teléfono debe tener 8 dígitos numéricos");
        }

        if (proveedor.getDireccion() == null ||
                proveedor.getDireccion().trim().isEmpty()) {
            throw new RuntimeException("La dirección no puede estar vacía");
        }
        proveedor.setDireccion(proveedor.getDireccion().trim());

        if (proveedor.getDireccion().length() < 5) {
            throw new RuntimeException("La dirección debe tener al menos 5 caracteres");
        }

        if (proveedor.getEmailProveedor() == null ||
                proveedor.getEmailProveedor().trim().isEmpty()) {
            throw new RuntimeException("El email no puede estar vacío");
        }
        String email = proveedor.getEmailProveedor().trim().toLowerCase();
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new RuntimeException("El email debe ser @gmail.com, @email.com o @yahoo.com");
        }
        proveedor.setEmailProveedor(email);
    }
}