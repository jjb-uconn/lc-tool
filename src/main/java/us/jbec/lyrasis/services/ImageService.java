package us.jbec.lyrasis.services;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import us.jbec.lyrasis.io.ImageCropsIO;
import us.jbec.lyrasis.io.PrimaryImageIO;
import us.jbec.lyrasis.models.ImageJob;
import us.jbec.lyrasis.models.ImageJobFile;
import us.jbec.lyrasis.models.LabeledImageCrop;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Component
public class ImageService {

    Logger LOG = LoggerFactory.getLogger(ImageService.class);

    private final ImageCropsIO imageCropsIO;
    private final PrimaryImageIO primaryImageIO;


    public ImageService(ImageCropsIO imageCropsIO, PrimaryImageIO primaryImageIO) {
        this.imageCropsIO = imageCropsIO;
        this.primaryImageIO = primaryImageIO;
    }

    public String getImageById(String id) throws IOException {
        List<ImageJobFile> imageJobFiles = primaryImageIO.getImageJobFiles();
        for (ImageJobFile imageJobFile : imageJobFiles) {
            if (id.equals(imageJobFile.getImageFileName())){
                InputStream in = new FileInputStream(imageJobFile.getImageFile().getPath());
                return Base64.getEncoder().encodeToString(IOUtils.toByteArray(in));
            }
        }
        return null;
    }
}
