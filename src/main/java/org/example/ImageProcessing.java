package org.example;

import boofcv.struct.image.GrayF32;
import java.awt.image.BufferedImage;
import java.beans.SimpleBeanInfo;

public class ImageProcessing {

    public static GrayF32 bufferedImageToGrayF32(BufferedImage bufferedImage) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        GrayF32 grayImage = new GrayF32(width, height);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = bufferedImage.getRGB(x, y);
                // Chuyển đổi RGB sang giá trị grayscale
                int gray = (int) ((0.299 * ((rgb >> 16) & 0xff)) + (0.587 * ((rgb >> 8) & 0xff)) + (0.114 * (rgb & 0xff)));
                grayImage.set(x, y, gray);
            }
        }
        return grayImage;
    }

    // Phương thức đọc ảnh và trích xuất đặc trưng
    public static float[] extractImageFeatures(String imagePath) {
        // Đọc ảnh dưới dạng BufferedImage
        SimpleBeanInfo UtilImageIO = null;
        BufferedImage bufferedImage = (BufferedImage) UtilImageIO.loadImage(imagePath);

        if (bufferedImage == null) {
            throw new RuntimeException("Không thể đọc ảnh");
        }

        // Sử dụng phương thức chuyển đổi từ BufferedImage sang GrayF32
        GrayF32 grayImage = bufferedImageToGrayF32(bufferedImage);

        int width = grayImage.getWidth();
        int height = grayImage.getHeight();

        // Chuyển ảnh thành vector đặc trưng
        float[] features = new float[width * height];
        int index = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                features[index++] = grayImage.get(x, y);
            }
        }
        return features;
    }
}
