package dasturlash.uz.kun_uz.controller;

import dasturlash.uz.kun_uz.dto.JwtDTO;
import dasturlash.uz.kun_uz.dto.ProfileDTO;
import dasturlash.uz.kun_uz.dto.UpdateProfileDetailDTO;
import dasturlash.uz.kun_uz.service.ProfileService;
import dasturlash.uz.kun_uz.util.JWTUtil;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.convert.PeriodUnit;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @PostMapping("")
    public ResponseEntity<ProfileDTO> add(@RequestBody ProfileDTO profileDTO,
                                          @RequestHeader("Authorization") String token) {
        JwtDTO decode = JWTUtil.decode(token.substring(7));
        return ResponseEntity.ok(profileService.add(profileDTO, decode));
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

    @GetMapping("/getPagination")
    public ResponseEntity<Page<ProfileDTO>> getPagination(@RequestParam(value = "page", defaultValue = "1") int page,
                                                          @RequestParam(value = "size", defaultValue = "10")int size){
        return ResponseEntity.ok(profileService.pagination(page, size));
    }

    @PutMapping("/detail")
    public ResponseEntity<Boolean> updateDetail(@RequestBody @Valid UpdateProfileDetailDTO requestDTO,
                                                @RequestHeader("Authorization") String token) {
        JwtDTO dto = JWTUtil.decode(token.substring(7));
        return ResponseEntity.ok().body(profileService.updateDetail(requestDTO, dto.getUsername()));
    }

}
