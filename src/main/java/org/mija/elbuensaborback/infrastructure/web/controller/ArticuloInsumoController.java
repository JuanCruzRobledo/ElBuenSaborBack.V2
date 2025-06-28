package org.mija.elbuensaborback.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.application.dto.request.insumo.ArticuloActualizarStockPrecioRequest;
import org.mija.elbuensaborback.application.dto.request.insumo.ArticuloInsumoCreatedRequest;
import org.mija.elbuensaborback.application.dto.request.insumo.ArticuloInsumoUpdateRequest;
import org.mija.elbuensaborback.application.dto.response.ArticuloInsumoResponse;
import org.mija.elbuensaborback.application.service.ArticuloInsumoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articulo-insumo")
@RequiredArgsConstructor
public class ArticuloInsumoController {

    private final ArticuloInsumoServiceImpl articuloInsumoService;

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<?> crearArticuloInsumo(@RequestBody ArticuloInsumoCreatedRequest articuloInsumoCreatedRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(articuloInsumoService.crearArticuloInsumo(articuloInsumoCreatedRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarArticuloInsumo(@RequestBody ArticuloInsumoUpdateRequest articuloManufacturadoCreatedRequest, @PathVariable Long id ){
        ArticuloInsumoResponse articuloManufacturado = articuloInsumoService.actualizarArticuloInsumo(id, articuloManufacturadoCreatedRequest);
        return ResponseEntity.ok(articuloManufacturado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarArticuloInsumo(@PathVariable Long id){
        articuloInsumoService.eliminarArticuloInsumo(id);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerArticuloInsumo(@PathVariable Long id){
        return ResponseEntity.ok(articuloInsumoService.obtenerArticuloInsumo(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> obtenerTodosArticuloInsumo(){
        return ResponseEntity.ok(articuloInsumoService.listarArticulosInsumo());
    }

    @GetMapping("/vendibles/basic/getAll")
    public ResponseEntity<?> obtenerInsumosVendibles(){
        return ResponseEntity.ok(articuloInsumoService.obtenerInsumosVendibles());
    }

    @GetMapping("/{categoria}/basic/getAll")
    public ResponseEntity<?> obtenerBebidasPorCategoria(@PathVariable String categoria){
        return ResponseEntity.ok(articuloInsumoService.obtenerBebidas(categoria));
    }

    @GetMapping("/basic/getAll")
    public ResponseEntity<?> obtenerTodosBasicArticuloInsumo(){
        return ResponseEntity.ok(articuloInsumoService.listarBasicArticulosInsumo());
    }

    @GetMapping("/elaboracion/basic/getAll")
    public ResponseEntity<?> obtenerTodosBasicArticuloInsumoParaPreparar(){
        return ResponseEntity.ok(articuloInsumoService.listarBasicArticulosInsumoParaPreparar());
    }

    @PatchMapping("/{id}/precio-stock")
    public ResponseEntity<?> actualizarPrecioYStock(
            @PathVariable Long id,
            @RequestBody ArticuloActualizarStockPrecioRequest request
    ) {
        ArticuloInsumoResponse actualizado = articuloInsumoService.actualizarPrecioYStock(id, request);
        return ResponseEntity.ok(actualizado);
    }

    @GetMapping("/bajo-stock/getAll")
    public ResponseEntity<?> obtenerBajoStock(){
        return ResponseEntity.ok(articuloInsumoService.obtenerInsumosBajoDeStock());
    }

}
