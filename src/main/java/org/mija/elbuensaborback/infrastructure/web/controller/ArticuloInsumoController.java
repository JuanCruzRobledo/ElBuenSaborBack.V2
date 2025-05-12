package org.mija.elbuensaborback.infrastructure.web.controller;

import org.mija.elbuensaborback.application.dto.request.insumo.ArticuloInsumoCreatedRequest;
import org.mija.elbuensaborback.application.dto.request.insumo.ArticuloInsumoUpdateRequest;
import org.mija.elbuensaborback.application.dto.response.ArticuloInsumoResponse;
import org.mija.elbuensaborback.application.service.ArticuloInsumoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articulo-insumo")
//@CrossOrigin("*")
public class ArticuloInsumoController {

    private final ArticuloInsumoServiceImpl articuloInsumoService;

    public ArticuloInsumoController(ArticuloInsumoServiceImpl articuloInsumoService) {
        this.articuloInsumoService = articuloInsumoService;
    }


    @PostMapping()
    public ResponseEntity<?> crearArticuloInsumo(@RequestBody ArticuloInsumoCreatedRequest articuloInsumoCreatedRequest) {
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(articuloInsumoService.crearArticuloInsumo(articuloInsumoCreatedRequest));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarArticuloInsumo(@RequestBody ArticuloInsumoUpdateRequest articuloManufacturadoCreatedRequest, @PathVariable Long id ){
        try{
            ArticuloInsumoResponse articuloManufacturado = articuloInsumoService.actualizarArticuloInsumo(id, articuloManufacturadoCreatedRequest);
            return ResponseEntity.ok(articuloManufacturado);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarArticuloInsumo(@PathVariable Long id){
        try{
            articuloInsumoService.eliminarArticuloInsumo(id);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> obtenerArticuloInsumo(@PathVariable Long id){
        try{
            return ResponseEntity.ok(articuloInsumoService.obtenerArticuloInsumo(id));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> obtenerTodosArticuloInsumo(){
        try{
            return ResponseEntity.ok(articuloInsumoService.listarArticulosInsumo());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping("basic/getAll")
    public ResponseEntity<?> obtenerTodosBasicArticuloInsumo(){
        try{
            return ResponseEntity.ok(articuloInsumoService.listarBasicArticulosInsumo());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
}
