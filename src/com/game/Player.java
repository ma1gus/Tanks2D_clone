package com.game;

import com.IO.Input;
import com.graphics.Sprite;
import com.graphics.SpriteSheet;
import com.graphics.TextureAtlas;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Player extends Entity {

    public static final int spriteScale = 16;
    public static final int SPRITES_PER_HEADING = 1;

    //Для разворота танка по направлению движения
    private enum Heading {

        NORTH(0 * spriteScale, 0 * spriteScale, 1 * spriteScale, 1 * spriteScale),//координаты танка
        EAST(6 * spriteScale, 0 * spriteScale, 1 * spriteScale, 1 * spriteScale),
        SOUTH(4 * spriteScale, 0 * spriteScale, 1 * spriteScale, 1 * spriteScale),
        WEST(2 * spriteScale, 0 * spriteScale, 1 * spriteScale, 1 * spriteScale);

        private int x, y, h, w;

        Heading(int x, int y, int h, int w) {

            this.x = x;
            this.y = y;
            this.h = h;
            this.w = w;
        }
        //Вырезать изображение
        protected BufferedImage texture(TextureAtlas atlas) {

            return atlas.cut(x, y, w, h);
        }
    }

    private Heading heading;//хранилище для стороны, в которую смотрит танк
    private Map<Heading, Sprite> spriteMap;//вытаскивает правильный спрайт, когда танк меняет направление движения
    private float scale;
    private float speed;

    public Player(float x, float y,float scale, float speed, TextureAtlas atlas) {

        super(EntityType.Player, x, y);

        heading = Heading.NORTH;
        spriteMap = new HashMap<Heading, Sprite>();
        this.scale = scale;
        this.speed = speed;

        //Заполнить Map
        for(Heading h : Heading.values()) {

            SpriteSheet sheet = new SpriteSheet(h.texture(atlas), SPRITES_PER_HEADING, spriteScale);
            Sprite sprite = new Sprite(sheet, scale);
            spriteMap.put(h, sprite);//связать направление с каким-то спрайтом
        }
    }

    //Движение по направлению вектора
    @Override
    public void update(Input input) {

        float newX = x;
        float newY = y;
        //else if для того, чтобы танк не мог двигаться наискосок
        if(input.getKey(KeyEvent.VK_UP)) {

            newY -= speed;
            heading = Heading.NORTH;
        } else if(input.getKey(KeyEvent.VK_RIGHT)) {

            newX += speed;
            heading = Heading.EAST;
        } else  if(input.getKey(KeyEvent.VK_LEFT)) {

            newX -= speed;
            heading = Heading.WEST;
        } else if(input.getKey(KeyEvent.VK_DOWN)) {

            newY += speed;
            heading = Heading.SOUTH;
        }
        //Проверка
        if(newX < 0) {//если танк за экраном

            newX = 0;
        } else if(newX >= Game.WIDTH - spriteScale * scale) {

            newX = Game.WIDTH - spriteScale * scale;
        }

        if(newY < 0) {//если танк за экраном

            newY = 0;
        } else if(newY >= Game.HEIGHT - spriteScale * scale) {

            newY = Game.HEIGHT - spriteScale * scale;
        }

        x = newX;
        y = newY;
    }

    //Проверить, куда смотрит танк и вытащить правильный спрайт
    @Override
    public void render(Graphics2D g) {

        spriteMap.get(heading).render(g, x, y);
    }
}
