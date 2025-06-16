package org.mija.elbuensaborback.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.application.dto.global.promocion.ArticuloPromocionDto;
import org.mija.elbuensaborback.application.dto.request.promocion.ArticuloPromocionCreatedRequest;
import org.mija.elbuensaborback.application.dto.request.promocion.ArticuloPromocionUpdateRequest;
import org.mija.elbuensaborback.application.service.ArticuloPromocionServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articulo-promocion")
@RequiredArgsConstructor
public class ArticuloPromocionController {

    private final ArticuloPromocionServiceImpl articuloPromocionService;

    // Obtener una promoción por ID
    @GetMapping("/{id}")
    public ResponseEntity<ArticuloPromocionDto> obtenerPromocion(@PathVariable Long id) {
        ArticuloPromocionDto dto = articuloPromocionService.obtenerArticuloPromocion(id);
        return ResponseEntity.ok(dto);
    }

    // Listar todas las promociones
    @GetMapping("/getAll")
    public ResponseEntity<List<ArticuloPromocionDto>> listarPromociones() {
        List<ArticuloPromocionDto> lista = articuloPromocionService.listarArticulosPromocion();
        return ResponseEntity.ok(lista);
    }

    // Crear una nueva promoción
    @PostMapping
    public ResponseEntity<ArticuloPromocionDto> crearPromocion(@RequestBody ArticuloPromocionCreatedRequest request) {
        ArticuloPromocionDto dto = articuloPromocionService.crearPromocion(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    // Actualizar una promoción existente
    @PutMapping("/{id}")
    public ResponseEntity<ArticuloPromocionDto> actualizarPromocion(
            @PathVariable Long id,
            @RequestBody ArticuloPromocionUpdateRequest dto
    ) {

        ArticuloPromocionDto actualizado = articuloPromocionService.actualizarPromocion(id,dto);
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar una promoción por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ArticuloPromocionDto> eliminarPromocion(@PathVariable Long id) {
        ArticuloPromocionDto eliminado = articuloPromocionService.eliminarPromocion(id);
        return ResponseEntity.ok(eliminado);
    }
}