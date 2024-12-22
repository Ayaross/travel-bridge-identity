package com.coolminds.travelbridge.config;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import javax.imageio.ImageIO;

public class ImageProcessorJava2D {

    // Décoder Base64 en BufferedImage
    public static BufferedImage decodeBase64ToImage(String base64) throws Exception {
        byte[] decodedBytes = Base64.getDecoder().decode(base64);
        return ImageIO.read(new ByteArrayInputStream(decodedBytes));
    }

    // Encoder BufferedImage en Base64
    public static String encodeImageToBase64(BufferedImage image) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    // Appliquer un filtre de netteté simple
    public static BufferedImage applySharpeningFilter(BufferedImage image) {
        BufferedImage sharpenedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = sharpenedImage.createGraphics();

        // Dessiner l'image d'origine
        graphics.drawImage(image, 0, 0, null);

        // Appliquer un effet de netteté (simulé avec une bordure)
        graphics.setColor(new Color(255, 255, 255, 50)); // Blanc translucide
        graphics.drawRect(10, 10, image.getWidth() - 20, image.getHeight() - 20);
        graphics.dispose();

        return sharpenedImage;
    }

    // Exemple d'utilisation
    public static String processImage(String base64Image) throws Exception {
        BufferedImage image = decodeBase64ToImage(base64Image);
        BufferedImage sharpenedImage = applySharpeningFilter(image);
        return encodeImageToBase64(sharpenedImage);
    }
}
