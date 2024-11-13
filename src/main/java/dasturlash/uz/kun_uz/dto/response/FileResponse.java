package dasturlash.uz.kun_uz.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.Resource;
@Getter
@Setter
public class FileResponse {
    private String fileName;
    private String contentType;
    private long size;
    private Resource file;
}
