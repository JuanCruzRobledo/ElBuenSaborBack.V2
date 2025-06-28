package org.mija.elbuensaborback.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.application.dto.request.manufacturado.ArticuloManufacturadoCreatedRequest;
import org.mija.elbuensaborback.application.dto.request.manufacturado.ArticuloManufacturadoUpdateRequest;
import org.mija.elbuensaborback.application.dto.response.ArticuloManufacturadoResponse;
import org.mija.elbuensaborback.application.service.ArticuloManufacturadoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articulo-manufacturado")
@RequiredArgsConstructor
public class ArticuloManufacturadoController {
    private final ArticuloManufacturadoServiceImpl articuloManufacturadoService;

    @PostMapping("")
    public ResponseEntity<?> crearArticuloManufacturada(@RequestBody ArticuloManufacturadoCreatedRequest articuloManufacturadoCreatedRequest) {
        ArticuloManufacturadoResponse articuloManufacturado = articuloManufacturadoService.crearArticulo(articuloManufacturadoCreatedRequest);
        return ResponseEntity.ok(articuloManufacturado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarArticuloManufacturado (@RequestBody ArticuloManufacturadoUpdateRequest articuloManufacturadoCreatedRequest, @PathVariable Long id ){
        ArticuloManufacturadoResponse articuloManufacturado = articuloManufacturadoService.actualizarArticulo(id, articuloManufacturadoCreatedRequest);
        return ResponseEntity.ok(articuloManufacturado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarArticuloManufacturado (@PathVariable Long id){
        articuloManufacturadoService.eliminarArticulo(id);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerArticuloManufacturado (@PathVariable Long id){
        return ResponseEntity.ok(articuloManufacturadoService.obtenerArticulo(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> obtenerTodosArticuloManufacturado (){
        return ResponseEntity.ok(articuloManufacturadoService.listarArticulos());
    }

    @GetMapping("/basic/getAll")
    public ResponseEntity<?> obtenerTodosBasicArticulosManufacturados (){
        return ResponseEntity.ok(articuloManufacturadoService.listarBasicArticulosManufacturados());
    }
}