package com.graphics;

import com.utils.ResourceLoader;

import java.awt.image.BufferedImage;

//Грузим картинку
public class TextureAtlas {

    BufferedImage image;

    public TextureAtlas(String imageName) {

        image = ResourceLoader.loadImage(imageName);
    }

    //Способо вырезать из картинки
    public BufferedImage cut(int x, int y, int w, int h) {

        return image.getSubimage(x, y, w, h);
    }
}
