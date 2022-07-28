package com.gestion.empleado.Repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.empleado.Modelo.EmpleadoModel;

public interface empleadoRepositorio extends JpaRepository<EmpleadoModel, Long>{

}
