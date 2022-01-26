package com.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ResourceLoader {

    public static final String PATH = "Resources/";

    public static BufferedImage loadImage(String fileName) {

        BufferedImage image = null;
        //Зарузить картинку
        try {

            image = ImageIO.read(new File(PATH + fileName));
        } catch (IOException e) {

            e.printStackTrace();
        }
        return image;
    }
}
