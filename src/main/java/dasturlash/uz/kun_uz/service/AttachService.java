package dasturlash.uz.kun_uz.service;

import dasturlash.uz.kun_uz.dto.AttachDTO;
import dasturlash.uz.kun_uz.dto.response.FileResponse;
import dasturlash.uz.kun_uz.entity.Attach;
import dasturlash.uz.kun_uz.exp.AppBadException;
import dasturlash.uz.kun_uz.repository.AttachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
public class AttachService {
    @Autowired
    AttachRepository attachRepository;
    private  String folderName = "attaches";

    @Value("${server.domain}")
    private String domainName;

    public String saveToSystem(MultipartFile file) {

        try { // mazgi.png
            File folder = new File("attaches");
            if (!folder.exists()) {
                folder.mkdir();
            }

            byte[] bytes = file.getBytes();
            Path path = Paths.get("attaches/" + file.getOriginalFilename());
            Files.write(path, bytes);
            return file.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResponseEntity<Resource> open(String id) {
        Attach entity = getEntity(id);
        String path = folderName + "/" + entity.getPath() + "/" + entity.getId();
        // zari.jpg
        Path filePath = Paths.get(path).normalize();
        Resource resource = null;
        try {
            resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                throw new RuntimeException("File not found: " + entity.getId());
            }
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream"; // Fallback content type
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    public AttachDTO upload(MultipartFile file) {

        //  attaches/2024/09/27/dasdasd-dasdasda-asdasda-asdasd.jpg
        // attaches/2024/11/2
        String pathFolder = getYmDString(); // 2024/09/27
        String key = UUID.randomUUID().toString(); // dasdasd-dasdasda-asdasda-asdasd
        String extension = getExtension(file.getOriginalFilename()); // .jpg, .png, .mp4

        // create folder if not exists
        File folder = new File(folderName + "/" + pathFolder); // attaches/2024/09/27
        if (!folder.exists()) {
            folder.mkdirs();
        }

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(folderName + "/" + pathFolder + "/" + key + "." + extension);
            // attaches/2024/09/27/dasdasd-dasdasda-asdasda-asdasd.jpg
            Files.write(path, bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // save to db
        Attach entity = new Attach();
        entity.setId(key + "." + extension);
        entity.setPath(pathFolder);
        entity.setSize(file.getSize());
        entity.setOrigenName(file.getOriginalFilename());
        entity.setExtension(extension);
        entity.setVisible(true);
        attachRepository.save(entity);

        return toDTO(entity);
    }

    public ResponseEntity<Resource> openGeneral(String id) {
        Attach entity = getEntity(id);
        String path = folderName + "/" + entity.getPath() + "/" + entity.getId();
        // zari.jpg
        Path filePath = Paths.get(path).normalize();
        Resource resource = null;
        try {
            resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                throw new RuntimeException("File not found: " + entity.getId());
            }
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream"; // Fallback content type
            }
            FileResponse fileResponse = new FileResponse();
            fileResponse.setFileName(filePath.getFileName().toString());
            fileResponse.setContentType(contentType);
            fileResponse.setSize(Files.size(filePath));
            fileResponse.setFile(resource);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
/*

    public ResponseEntity<AttachDTO> download(String id) {
        Attach entity = getEntity(id);
        String path = folderName + "/" + entity.getPath() + "/" + entity.getId();

        Path filePath = Paths.get(path).normalize();
        Resource resource = null;

        String fileName = "download";
        Path sourcePath = Paths.get(path).normalize();
        Path targetPath = Paths.get(fileName);





        try {
            byte[] bytes = Files.readAllBytes(sourcePath);
            Files.write(targetPath, bytes);
            resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                throw new RuntimeException("File not found: " + entity.getId());
            }



        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        }


    }
*/


    private String getYmDString () {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
            int day = Calendar.getInstance().get(Calendar.DATE);
            return year + "/" + month + "/" + day;
        }
        private String getExtension (String fileName){
            int lastIndex = fileName.lastIndexOf("."); // mazgi.latta.jpg
            return fileName.substring(lastIndex + 1);
        }

        private AttachDTO toDTO (Attach entity){
            AttachDTO attachDTO = new AttachDTO();
            attachDTO.setId(entity.getId());
            attachDTO.setOriginName(entity.getOrigenName());
            attachDTO.setSize(entity.getSize());
            attachDTO.setExtension(entity.getExtension());
            attachDTO.setCreatedData(entity.getCreatedDate());
            attachDTO.setUrl(getUrl(entity.getId()));
            return attachDTO;
        }
    public Attach getEntity(String id) {
        Optional<Attach> optional = attachRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException("File not found");
        }
        return optional.get();
    }

    public ResponseEntity<Resource> download(String id) {
        try {
            Attach entity = getEntity(id);
            String path = folderName + "/" + entity.getPath() + "/" + entity.getId();

            Path file = Paths.get(path);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + entity.getOrigenName()+"\"").body(resource);
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not read the file!");
        }


    }

   public String getUrl(String id) {
        return domainName + "/attach/open/" + id;
   }

    public AttachDTO getDTO(String id) {
        if(id == null) return null;

        AttachDTO dto = new AttachDTO();
        dto.setId(id);
        dto.setUrl(getUrl(id));
        return dto;
    }
}


