package org.mija.elbuensaborback.infrastructure.web.controller;

import org.mija.elbuensaborback.application.dto.global.categoria.CategoriaDto;
import org.mija.elbuensaborback.application.service.CategoriaServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categoria")
@CrossOrigin("*")
public class CategoriaController {

    private final CategoriaServiceImpl categoriaService;

    public CategoriaController(CategoriaServiceImpl categoriaService) {
        this.categoriaService = categoriaService;
    }


    @PostMapping("/crear")
    public ResponseEntity<?> crearCategoria(@RequestBody CategoriaDto categoriaDto) {
        return ResponseEntity.status(HttpStatus.OK).body(categoriaService.crearCategoria(categoriaDto));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarCategoria(@RequestBody CategoriaDto categoriaDto,@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(categoriaService.actualizarCategoria(id,categoriaDto));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> obtenerCategoria(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(categoriaService.obtenerCategoria(id));
    }

    @GetMapping("/getComplete/{id}")
    public ResponseEntity<?> obtenerCategoriaWithSubcategorias(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(categoriaService.obtenerCategoriaWithSubcategoria(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> listarCategorias() {
        return ResponseEntity.status(HttpStatus.OK).body(categoriaService.listarCategoria());
    }

    @GetMapping("padres/getAll")
    public ResponseEntity<?> listarCategoriasPadres() {
        return ResponseEntity.status(HttpStatus.OK).body(categoriaService.listarCategoriaPadres());
    }
}
