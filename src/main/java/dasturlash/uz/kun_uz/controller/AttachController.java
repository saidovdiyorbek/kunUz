package dasturlash.uz.kun_uz.controller;

import dasturlash.uz.kun_uz.dto.AttachDTO;
import dasturlash.uz.kun_uz.dto.response.FileResponse;
import dasturlash.uz.kun_uz.service.AttachService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/attach")
public class AttachController {
    @Autowired
    private AttachService attachService;


    /*@PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        String fileName = attachService.saveToSystem(file);
        return fileName;
    }*/

    @GetMapping("/open/{fileName}")
    public ResponseEntity<Resource> open(@PathVariable String fileName) {
        return attachService.open(fileName);
    }

    @PostMapping("/upload")
    public ResponseEntity<AttachDTO> upload(@RequestParam("file") MultipartFile file) {
        AttachDTO attachDTO = attachService.upload(file);
        return ResponseEntity.ok(attachDTO);
    }

    @GetMapping("/openGeneral/{fileName}")
    public ResponseEntity<Resource> openGeneral(@PathVariable String fileName) {
        return attachService.openGeneral(fileName);
    }
    @GetMapping("/download/{fineName}")
    public ResponseEntity<Resource> download(@PathVariable("fineName") String fileName) {
            return attachService.download(fileName);
    }


}
