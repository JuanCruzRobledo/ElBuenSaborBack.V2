package org.mija.elbuensaborback.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.application.dto.request.insumo.ArticuloActualizarStockPrecioRequest;
import org.mija.elbuensaborback.application.dto.request.insumo.ArticuloInsumoCreatedRequest;
import org.mija.elbuensaborback.application.dto.request.insumo.ArticuloInsumoUpdateRequest;
import org.mija.elbuensaborback.application.dto.response.ArticuloInsumoResponse;
import org.mija.elbuensaborback.application.service.ArticuloInsumoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articulo-insumo")
@RequiredArgsConstructor
public class ArticuloInsumoController {

    private final ArticuloInsumoServiceImpl articuloInsumoService;

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<?> crearArticuloInsumo(@RequestBody ArticuloInsumoCreatedRequest articuloInsumoCreatedRequest) {
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(articuloInsumoService.crearArticuloInsumo(articuloInsumoCreatedRequest));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> editarArticuloInsumo(@RequestBody ArticuloInsumoUpdateRequest articuloManufacturadoCreatedRequest, @PathVariable Long id ){
        try{
            ArticuloInsumoResponse articuloManufacturado = articuloInsumoService.actualizarArticuloInsumo(id, articuloManufacturadoCreatedRequest);
            return ResponseEntity.ok(articuloManufacturado);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarArticuloInsumo(@PathVariable Long id){
        try{
            articuloInsumoService.eliminarArticuloInsumo(id);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    //@PreAuthorize("hasAnyRole('ADMIN', 'COCINERO')")
    @GetMapping("/get/{id}")
    public ResponseEntity<?> obtenerArticuloInsumo(@PathVariable Long id){
        try{
            return ResponseEntity.ok(articuloInsumoService.obtenerArticuloInsumo(id));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    //@PreAuthorize("hasAnyRole('ADMIN', 'COCINERO')")
    @GetMapping("/getAll")
    public ResponseEntity<?> obtenerTodosArticuloInsumo(){
        try{
            return ResponseEntity.ok(articuloInsumoService.listarArticulosInsumo());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping("bebidas/getAll")
    public ResponseEntity<?> obtenerBebidas(){
        try{
            return ResponseEntity.ok(articuloInsumoService.obtenerBebidas("bebidas"));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping("{categoria}/getAll")
    public ResponseEntity<?> obtenerBebidasPorCategoria(@PathVariable String categoria){
        try{
            return ResponseEntity.ok(articuloInsumoService.obtenerBebidas(categoria));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping("/basic/getAll")
    public ResponseEntity<?> obtenerTodosBasicArticuloInsumo(){
        try{
            return ResponseEntity.ok(articuloInsumoService.listarBasicArticulosInsumo());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @PatchMapping("/{id}/precio-stock")
    public ResponseEntity<?> actualizarPrecioYStock(
            @PathVariable Long id,
            @RequestBody ArticuloActualizarStockPrecioRequest request
    ) {
        try {
            ArticuloInsumoResponse actualizado = articuloInsumoService.actualizarPrecioYStock(id, request);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
