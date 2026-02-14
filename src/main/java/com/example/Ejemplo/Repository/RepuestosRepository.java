package com.example.Ejemplo.Repository;

import com.example.Ejemplo.Entity.Repuestos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepuestosRepository extends JpaRepository<Repuestos, Integer> {

}