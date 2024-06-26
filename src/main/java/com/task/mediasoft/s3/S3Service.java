package com.task.mediasoft.s3;

import com.task.mediasoft.product.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public interface S3Service {
    void getZip(OutputStream outputStream, Product product);

    UUID addFile(UUID productId, UUID imageId, MultipartFile file) throws IOException;
}
