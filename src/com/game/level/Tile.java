package com.game.level;

import com.utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;

//Tile - произносится как «тайл», переводится как «плитка», а означает повторение. Повторяющуюся текстуру используют для текстурирования больших поверхностей: грунта, стен, домов, модулей и иногда мелких объектов. Такой вид текстур позволяет добиться высокого качества и при этом уменьшить количество применяемых материалов в игровой сцене.
public class Tile {

    private BufferedImage image;
    private TileType type;

    protected Tile(BufferedImage image, int scale, TileType type) {

        this.type = type;
        this.image = Utils.resize(image, image.getWidth() * scale, image.getHeight() * scale);
    }

    protected void render(Graphics2D g, int x, int y) {

        g.drawImage(image, x, y, null);
    }

    //Вернуть тип тайла
    protected TileType type() {

        return type;
    }
}
