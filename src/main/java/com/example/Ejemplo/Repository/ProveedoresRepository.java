package com.example.Ejemplo.Repository;

import org.springframework.stereotype.Repository;
import com.example.Ejemplo.Entity.Proveedores;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProveedoresRepository extends JpaRepository<Proveedores, Integer>{

}
