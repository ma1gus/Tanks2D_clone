package com.display;

import com.IO.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

public abstract class Display {

    private static boolean created = false;//чтобы создавалось только одно окно
    private static JFrame window;//главное окно
    private static Canvas content;//создание растрового двухмерного изображения

    private static BufferedImage buffer;//сожержит изображения
    private static int[] bufferData;//вся информация об image
    private static Graphics bufferGraphics;
    private static int clearColor;

    private static BufferStrategy bufferStrategy;//для имплементации бафферов

//    //tmp
//    private static float delta = 0;
//    //tmp end

    public static void create(int width, int height, String title, int _clearColor, int numBuffers) {

        if (created) {

            return;
        }

        window = new JFrame(title);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//чтобы при нажатии на крестик закрывалось окно и вместе с тем останавливался процесс работы программы
        content = new Canvas();
        //Один из примеров создания графики
        /*{

            public void paint(Graphics g) {

                super.paint(g);
                render(g);
            }
        };*/
        Dimension size = new Dimension(width, height);
        content.setPreferredSize(size);

        window.setResizable(false);//юзер не сможет менять размер
        window.getContentPane().add(content);//добавить контент в окно
        window.pack();//изменит размер окна под размер контента
        window.setLocationRelativeTo(null);//чтобы окно появилось по середине
        window.setVisible(true);//видеть окно, которое создал

        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);//информация хранится с помощью одного большого массива BufferedImage.TYPE_INT_ARGB
        bufferData = ((DataBufferInt) buffer.getRaster().getDataBuffer()).getData();//возвращает тот самый длинный Integer, который сидит в buffer
        bufferGraphics = buffer.getGraphics();//получаем объект типа Graphics, оторый умеет рисовать фигуры и делать множество дургих операций с графикой
        ((Graphics2D)bufferGraphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        clearColor = _clearColor;

        content.createBufferStrategy(numBuffers);
        bufferStrategy = content.getBufferStrategy();//вытаскиваем bufferStrategy, который относится к контенту


        created = true;
    }

    public static void clear() {

        Arrays.fill(bufferData, clearColor);//берет любой Array и заполняет его
    }

//    public static void render() {
//
//        bufferGraphics.setColor(new Color(0xff0000ff));
//        bufferGraphics.fillOval((int)(350 + (Math.sin(delta) * 200)), 250, 100, 100);
//
//        ((Graphics2D)bufferGraphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);//включили сглаживание фигур
//        bufferGraphics.fillOval((int)(500 + (Math.sin(delta) * 200)), 250, 100, 100);//создаем вторую фигуру для сравнения
//        ((Graphics2D)bufferGraphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);//выключили подсказку для сглаживания
//
//        //delta += 0.02f;
//    }

    public static void swapBuffers() {

        //Graphics g = content.getGraphics();//вытащили графику из канваса
        Graphics g = bufferStrategy.getDrawGraphics();//грамотный вариант создания граффического объекта
        g.drawImage(buffer, 0, 0, null);
        bufferStrategy.show();
    }

    public static Graphics2D getGraphics() {

        return (Graphics2D)bufferGraphics;
    }
    //Уничтожить окно
    public  static void destroy() {

        if(!created) {

            return;
        }
        window.dispose();
    }

    public static void setTitle(String title) {

        window.setTitle(title);
    }

    public static void addInputListener(Input inputListener) {

        window.add(inputListener);
    }
    //Один из примеров создания фигуры в окне
    /*public static void render() {

        content.repaint();
    }

    private static void render(Graphics g) {

        g.setColor(Color.white);
        g.fillOval(400 - 50, 300 - 50, 100, 100);
    }*/
}
