package dasturlash.uz.kun_uz.controller;

import dasturlash.uz.kun_uz.dto.RegionDTO;
import dasturlash.uz.kun_uz.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.convert.PeriodUnit;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/region")
public class RegionController {

    @Autowired
    RegionService regionService;

    @PostMapping("")
    public ResponseEntity<RegionDTO> add(@RequestBody RegionDTO regionDTO) {
        return ResponseEntity.ok(regionService.add(regionDTO));
    }

    @GetMapping("")
    public ResponseEntity<List<RegionDTO>> getAll() {
        return ResponseEntity.ok(regionService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegionDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(regionService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegionDTO> update(@PathVariable Integer id, @RequestBody RegionDTO regionDTO) {
        return ResponseEntity.ok(regionService.update(id, regionDTO));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(regionService.delete(id));
    }
}
