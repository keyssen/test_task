package com.task.mediasoft.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.task.mediasoft.configuration.properties.S3Properties;
import com.task.mediasoft.image.ImageEntity;
import com.task.mediasoft.image.service.ImageService;
import com.task.mediasoft.product.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {
    private final ImageService imageService;
    private final S3Properties s3Properties;
    private final AmazonS3 s3Client;


    public void getZip(OutputStream outputStream, Product product) {
        try (ZipOutputStream zipOut = new ZipOutputStream(outputStream)) {
            final Map<String, Integer> fileNamesInZip = new HashMap<>();
            for (ImageEntity imageEntity : imageService.getImagesByProductId(product.getId())) {
                final S3Object s3Object = s3Client.getObject(s3Properties.getBucket(), String.format("%s/%s", product.getId(), imageEntity.getId()));
                final ObjectMetadata objectMetadata = s3Object.getObjectMetadata();
                final String name = objectMetadata.getUserMetaDataOf("name");
                final String extension = objectMetadata.getUserMetaDataOf("extension");
                final String fullName = String.format("%s.%s", name, extension);
                final Integer count = fileNamesInZip.get(fullName);
                final ZipEntry entry;
                if (count == null) {
                    fileNamesInZip.put(fullName, 1);
                    entry = new ZipEntry(fullName);
                } else {
                    fileNamesInZip.put(fullName, count + 1);
                    entry = new ZipEntry(String.format("%s (%s).%s", name, count, extension));
                }
                zipOut.putNextEntry(entry);
                final byte[] buffer = IOUtils.toByteArray(s3Object.getObjectContent());
                zipOut.write(buffer);

                zipOut.closeEntry();
            }
        } catch (IOException e) {
            log.error("Failed to upload file: ", e);
            throw new RuntimeException("Failed to upload file.");
        }
    }

    public UUID addFile(UUID productId, UUID imageId, MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("Please select a file to upload.");
        }

        final byte[] fileContent = file.getBytes();
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(fileContent)) {
            final ObjectMetadata metadata = new ObjectMetadata();

            metadata.setContentLength(fileContent.length);

            final String fileName = file.getOriginalFilename();
            final int dotIndex = fileName.lastIndexOf('.');

            metadata.addUserMetadata("name", fileName.substring(0, dotIndex));
            metadata.addUserMetadata("extension", fileName.substring(dotIndex + 1));

            s3Client.putObject(s3Properties.getBucket(), String.format("%s/%s", productId, imageId), inputStream, metadata);
            return imageId;
        }
    }
}
