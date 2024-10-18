package dasturlash.uz.kun_uz.controller;

import dasturlash.uz.kun_uz.dto.ProfileDTO;
import dasturlash.uz.kun_uz.service.ProfileService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.convert.PeriodUnit;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @PostMapping("")
    public ResponseEntity<ProfileDTO> add(@RequestBody ProfileDTO profileDTO) {
        return ResponseEntity.ok(profileService.add(profileDTO));
    }

    @GetMapping("")
    public ResponseEntity<List<ProfileDTO>> getAll() {
        return ResponseEntity.ok(profileService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(profileService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfileDTO> update(@PathVariable Integer id, @RequestBody ProfileDTO profileDTO) {
        return ResponseEntity.ok(profileService.update(id, profileDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProfileDTO> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(profileService.delete(id));
    }
}
