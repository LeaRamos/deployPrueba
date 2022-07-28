package com.gestion.empleado.controlador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.empleado.Modelo.EmpleadoModel;
import com.gestion.empleado.Repositorio.empleadoRepositorio;
import com.gestion.empleado.excepciones.ResourceNotFoundException;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "http://localhost:4200/")
public class empleadoControlador {
	
	private empleadoRepositorio empleadoRepositorio;
	
	public empleadoControlador(empleadoRepositorio empleadoRepositorio) {
		this.empleadoRepositorio = empleadoRepositorio;
	}
	
	@GetMapping("/empleados")
	public List<EmpleadoModel> listarTodosLosEmpleados(){
		return this.empleadoRepositorio.findAll();
	}
	
	@PostMapping("/empleados")
	public EmpleadoModel guardarEmpleados(@RequestBody EmpleadoModel empleado){
		return empleadoRepositorio.save(empleado);
	}
	
	@GetMapping("/empleados/{id}")
	public ResponseEntity<EmpleadoModel> buscarEmpleado(@PathVariable Long id){
		EmpleadoModel empleado = new EmpleadoModel();
		empleado = empleadoRepositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se encontro el empleado con el id "+id));
		return ResponseEntity.ok(empleado);
	}
	
	@PutMapping("/empleados/{id}")
	public ResponseEntity<EmpleadoModel> actualizarEmpleado(@PathVariable Long id, @RequestBody EmpleadoModel emp){
		EmpleadoModel empleado = new EmpleadoModel();
		empleado = empleadoRepositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se encontro el empleado con el id "+id));
		
		empleado.setApellido(emp.getApellido());
		empleado.setNombre(emp.getNombre());
		empleado.setMail(emp.getMail());
		
		EmpleadoModel empleadoGuardado =empleadoRepositorio.save(empleado);
		return ResponseEntity.ok().body(empleadoGuardado);
	}
/*	
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<MovieDetalleDto> updateMovie(@Valid @RequestBody MovieDetalleDto movie, @PathVariable Long id) throws Exception {
        MovieDetalleDto movieDetalleDto = movieService.updateMovie(movie, id);
        return ResponseEntity.ok().body(movieDetalleDto);
    }

	*/
	@DeleteMapping("/empleados/{id}")
	public ResponseEntity<Map<String,Boolean>> eliminarEmpleado(@PathVariable Long id){
		EmpleadoModel empleado = empleadoRepositorio.findById(id)
				            .orElseThrow(() -> new ResourceNotFoundException("No existe el empleado con el ID : " + id));
		
		empleadoRepositorio.delete(empleado);
		Map<String, Boolean> respuesta = new HashMap<>();
		respuesta.put("eliminar",Boolean.TRUE);
		return ResponseEntity.ok(respuesta);
    }
}
