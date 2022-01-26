package com.graphics;

import java.awt.image.BufferedImage;

public class SpriteSheet {

    private BufferedImage sheet;
    private int spriteCount;
    private int scale;
    private int spritesInWidth;

    public SpriteSheet(BufferedImage sheet, int spriteCount, int scale) {

        this.sheet = sheet;
        this.spriteCount = spriteCount;
        this.scale = scale;

        this.spritesInWidth = sheet.getWidth() / scale;
    }
    //Запрос изображения по очереди с помощью переменной
    public BufferedImage getSprite(int index) {

        index = index % spriteCount;//нужно для того, чтобы загружать спрайты по очереди

        int x = index % spritesInWidth * scale;//для того чтобы двигаться не по пикселю, а сразу на следующий спрайт
        int y = index / spritesInWidth * scale;

        return sheet.getSubimage(x, y, scale, scale);
    }
}
