package org.mija.elbuensaborback.infrastructure.web.controller;

import org.mija.elbuensaborback.application.dto.request.ArticuloManufacturadoCreatedRequest;
import org.mija.elbuensaborback.application.dto.request.ArticuloManufacturadoUpdateRequest;
import org.mija.elbuensaborback.application.dto.response.ArticuloManufacturadoResponse;
import org.mija.elbuensaborback.application.service.ArticuloManufacturadoManagementServiceImpl;
import org.mija.elbuensaborback.application.service.ArticuloManufacturadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articulo-manufacturado")
public class ArticuloManufacturadoController {
    private final ArticuloManufacturadoManagementServiceImpl articuloManufacturadoService;

    public ArticuloManufacturadoController(ArticuloManufacturadoManagementServiceImpl articuloManufacturadoService) {
        this.articuloManufacturadoService = articuloManufacturadoService;
    }


    @PostMapping("")
    public ResponseEntity<?> crearArticuloManufacturada(@RequestBody ArticuloManufacturadoCreatedRequest articuloManufacturadoCreatedRequest) {
        try{
            ArticuloManufacturadoResponse articuloManufacturado = articuloManufacturadoService.crearArticulo(articuloManufacturadoCreatedRequest);
            return ResponseEntity.ok(articuloManufacturado);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarArticuloManufacturado (@RequestBody ArticuloManufacturadoUpdateRequest articuloManufacturadoCreatedRequest, @PathVariable Long id ){
        try{
            ArticuloManufacturadoResponse articuloManufacturado = articuloManufacturadoService.actualizarArticulo(id, articuloManufacturadoCreatedRequest);
            return ResponseEntity.ok(articuloManufacturado);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarArticuloManufacturado (@PathVariable Long id){
        try{
            articuloManufacturadoService.eliminarArticulo(id);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> obtenerArticuloManufacturado (@PathVariable Long id){
        try{
            return ResponseEntity.ok(articuloManufacturadoService.obtenerArticulo(id));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> obtenerTodosArticuloManufacturado (){
        try{
            return ResponseEntity.ok(articuloManufacturadoService.listarArticulos());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }


}
