package com.task.mediasoft.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.task.mediasoft.configuration.properties.S3Properties;
import com.task.mediasoft.image.Image;
import com.task.mediasoft.image.service.ImageService;
import com.task.mediasoft.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {
    private final ImageService imageService;
    private final S3Properties s3Properties;
    private final AmazonS3 s3Client;

    public Pair<byte[], String> getZip(Product product) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ZipOutputStream zipOut = new ZipOutputStream(byteArrayOutputStream);) {

            for (Image image : imageService.getImagesByProductId(product.getId())) {
                S3Object s3Object = s3Client.getObject(s3Properties.getBucket(), String.format("%s/%s", product.getId(), image.getFileName()));

                ZipEntry entry = new ZipEntry(image.getFileName());
                zipOut.putNextEntry(entry);

                byte[] buffer = IOUtils.toByteArray(s3Object.getObjectContent());
                zipOut.write(buffer);

                zipOut.closeEntry();
            }
            return Pair.of(byteArrayOutputStream.toByteArray(), product.getArticle());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to upload file.");
        }
    }

    public void addFile(String fullFileName, MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("Please select a file to upload.");
        }

        byte[] fileContent = file.getBytes();
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(fileContent)) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(fileContent.length);
            s3Client.putObject(s3Properties.getBucket(), fullFileName, inputStream, metadata);
        }
    }
}
