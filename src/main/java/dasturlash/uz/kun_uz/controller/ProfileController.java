package dasturlash.uz.kun_uz.controller;

import dasturlash.uz.kun_uz.dto.profile.ProfileDTO;
import dasturlash.uz.kun_uz.dto.UpdateProfileDetailDTO;
import dasturlash.uz.kun_uz.dto.profile.ProfileFilterDTO;
import dasturlash.uz.kun_uz.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProfileDTO> add(@RequestBody ProfileDTO profileDTO) {
        return ResponseEntity.ok(profileService.add(profileDTO));
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProfileDTO>> getAll() {
        return ResponseEntity.ok(profileService.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProfileDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(profileService.getById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProfileDTO> update(@PathVariable Integer id, @RequestBody ProfileDTO profileDTO) {
        return ResponseEntity.ok(profileService.update(id, profileDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProfileDTO> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(profileService.delete(id));
    }

    @GetMapping("/getPagination")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<ProfileDTO>> getPagination(@RequestParam(value = "page", defaultValue = "1") int page,
                                                          @RequestParam(value = "size", defaultValue = "10")int size){
        return ResponseEntity.ok(profileService.pagination(page, size));
    }

    @PutMapping("/detail")
    public ResponseEntity<Boolean> updateDetail(@RequestBody @Valid UpdateProfileDetailDTO requestDTO) {
        return ResponseEntity.ok().body(profileService.updateDetail(requestDTO));
    }

    @PostMapping("/getFilter")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProfileFilterDTO> getProfileFilter(@RequestBody ProfileFilterDTO DTO) {
        return ResponseEntity.ok(profileService.getFilter(DTO));
    }


}
