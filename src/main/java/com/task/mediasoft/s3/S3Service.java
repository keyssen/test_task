package com.task.mediasoft.s3;

import com.task.mediasoft.product.model.Product;
import org.springframework.data.util.Pair;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface S3Service {
    Pair<byte[], String> getZip(Product product);

    void addFile(UUID id, MultipartFile file) throws IOException;
}
