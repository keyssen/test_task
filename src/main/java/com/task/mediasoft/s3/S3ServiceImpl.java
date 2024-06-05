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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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
             ZipOutputStream zipOut = new ZipOutputStream(byteArrayOutputStream)) {
            Map<String, Integer> fileNamesInZip = new HashMap<>();
            for (Image image : imageService.getImagesByProductId(product.getId())) {
                System.out.println(image.getId());
                S3Object s3Object = s3Client.getObject(s3Properties.getBucket(), String.format("%s/%s", product.getId(), image.getId()));
                ObjectMetadata objectMetadata = s3Object.getObjectMetadata();
                String name = objectMetadata.getUserMetaDataOf("name");
                String extension = objectMetadata.getUserMetaDataOf("extension");
                String fullName = String.format("%s.%s", name, extension);
                Integer count = fileNamesInZip.get(fullName);
                ZipEntry entry;
                if (count == null) {
                    fileNamesInZip.put(fullName, 1);
                    entry = new ZipEntry(fullName);
                } else {
                    fileNamesInZip.put(fullName, count + 1);
                    entry = new ZipEntry(String.format("%s (%s).%s", name, count, extension));
                }
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

    public void addFile(UUID productId, UUID imageId, MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("Please select a file to upload.");
        }

        byte[] fileContent = file.getBytes();
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(fileContent)) {
            ObjectMetadata metadata = new ObjectMetadata();

            metadata.setContentLength(fileContent.length);

            String fileName = file.getOriginalFilename();
            int dotIndex = fileName.lastIndexOf('.');

            metadata.addUserMetadata("name", fileName.substring(0, dotIndex));
            metadata.addUserMetadata("extension", fileName.substring(dotIndex + 1));

            s3Client.putObject(s3Properties.getBucket(), String.format("%s/%s", productId, imageId), inputStream, metadata);
        }
    }
}
