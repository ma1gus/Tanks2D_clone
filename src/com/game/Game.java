package com.game;

import com.IO.Input;
import com.display.Display;
import com.game.level.Level;
import com.graphics.Sprite;
import com.graphics.SpriteSheet;
import com.graphics.TextureAtlas;
import com.utils.Time;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Game implements Runnable{

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final String TITLE = "Tanks";
    public static final int CLEAR_COLOR = 0xff000000;//черный цвет
    public static final int NUM_BUFFERS = 3;

    public static final float UPDATE_RATE = 60.0f;//сколько раз в секунду будет считаться физика
    public static final float UPDATE_INTERVAL = Time.SECOND / UPDATE_RATE;//сколько времени должно проходить между каждым апдейтом
    public static final long IDLE_TIME = 1;//дать подышать программе, чтобы другие потоки тоже могли что-то делать

    //название файла-картинки
    public static final String ATLAS_FILE_NAME = "texture_atlas.png";

    private boolean running;
    private Thread gameThread;//поток(процесс)
    private Graphics2D  graphics;//объект графики
    private Input input;
    private TextureAtlas atlas;
    private Player player;
    private Level level;

    public Game() {

        running = false;
        Display.create(WIDTH, HEIGHT, TITLE, CLEAR_COLOR, NUM_BUFFERS);
        graphics = Display.getGraphics();//рисовать изменения на окне
        //Регистрация класса Input внутри дисплея
        input = new Input();
        Display.addInputListener(input);
        atlas = new TextureAtlas(ATLAS_FILE_NAME);
        player = new Player(300, 300, 2, 2, atlas);
        level = new Level(atlas);
    }
    //Выполнять только один поток одновременно
    public synchronized void start() {

        if(running) {

            return;
        }

        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public synchronized void stop() {

        if(!running) {

            return;
        }

        running = false;

        try {

            gameThread.join();//ждет пока Thread закончит свою работу
        } catch (InterruptedException e) {

            e.printStackTrace();//распечатать, где произошел эксепшн
        }

        cleanUp();
    }
    //Считает всю физику и геометрию игры
    private void update() {

        player.update(input);
        level.update();
    }
    //Все местонахождения танков, пуль, объектов и т.д.
    private void render() {

        Display.clear();
        level.render(graphics);
        player.render(graphics);
        level.renderGrass(graphics);//для прохождения танка под травой
        Display.swapBuffers();
    }
    //Ядро игры
    public void run() {

        int fps = 0;
        int upd = 0;
        int updl = 0;//сколько раз пытался догнать апдейт

        long count = 0;//время, которое прошло по ходу игры

        float delta = 0;//для учета количества апдейтов

        long lastTime = Time.get();
        while(running) {

            long now = Time.get();
            long elapsedTime = now - lastTime;//количество времени, которое прошло, когда while работал в прошлый раз
            lastTime = now;

            count += elapsedTime;

            boolean render = false;
            delta += (elapsedTime / UPDATE_INTERVAL);
            //loop следит за тем, чтобы количество апдейтов всегда было 60
            while(delta > 1) {

                update();
                upd++;
                delta--;
                if(render) {

                    updl++;
                } else {

                    render = true;
                }
            }

            if(render) {

                render();
                fps++;
            } else {

                try {

                    Thread.sleep(IDLE_TIME);//ничего не делать, дать программе подышать
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
            }

            if(count >= Time.SECOND) {

                Display.setTitle(TITLE + " || Fps: " + fps + " | Upd: " + upd + " | Updl: " + updl);//мониторинг процессов
                upd = 0;
                fps = 0;
                updl = 0;
                count = 0;
            }
        }
    }
    //Закрывашка для создаваемых ресурсов, которые желательно закрывать
    private void cleanUp() {

        Display.destroy();
    }
}
