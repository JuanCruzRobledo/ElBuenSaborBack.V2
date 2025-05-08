package org.mija.elbuensaborback.infrastructure.web.controller;

import org.mija.elbuensaborback.application.dto.request.ArticuloManufacturadoCreateRequest;
import org.mija.elbuensaborback.application.dto.response.ArticuloManufacturadoResponse;
import org.mija.elbuensaborback.application.service.ArticuloManufacturadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articulo-manufacturado")
public class ArticuloManufacturadoController {
    private final ArticuloManufacturadoService articuloManufacturadoService;

    public ArticuloManufacturadoController(ArticuloManufacturadoService articuloManufacturadoService) {
        this.articuloManufacturadoService = articuloManufacturadoService;
    }

    @PostMapping("")
    public ResponseEntity<?> crearArticuloManufacturada(@RequestBody ArticuloManufacturadoCreateRequest articuloManufacturadoCreateRequest) {
        try{
            System.out.println("INICIO CONTROLADOR");
            System.out.println(articuloManufacturadoCreateRequest);
            System.out.println("FIN CONTROLADOR");
            ArticuloManufacturadoResponse articuloManufacturado = articuloManufacturadoService.crearArticulo(articuloManufacturadoCreateRequest);
            return ResponseEntity.ok(articuloManufacturado);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarArticuloManufacturado (@RequestBody ArticuloManufacturadoCreateRequest articuloManufacturadoCreateRequest, @PathVariable Long id ){
        try{
            System.out.println("ENDPOINT EDITAR");
            ArticuloManufacturadoResponse articuloManufacturado = articuloManufacturadoService.actualizarArticulo(articuloManufacturadoCreateRequest);
            return ResponseEntity.ok(articuloManufacturado);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarArticuloManufacturado (@PathVariable Long id){
        try{
            System.out.println("ENDPOINT ELIMINAR");
            articuloManufacturadoService.eliminarArticulo(id);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

}
