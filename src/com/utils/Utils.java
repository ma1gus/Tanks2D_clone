package com.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class Utils {

    //Изменяет размер изображения
    public static BufferedImage resize(BufferedImage image, int width, int height) {

        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        newImage.getGraphics().drawImage(image, 0, 0, width, height, null);

        return newImage;
    }

    //Читать уровень из ресурса
    public static Integer[][] levelParser(String filePath) {

        Integer[][] result = null;

        try(BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)))){

            String line = null;
            ArrayList<Integer[]> levelLines = new ArrayList<Integer[]>();//динамически добавлять и убирать элементы
            while ((line = reader.readLine()) != null) {

                levelLines.add(str2int_arrays(line.split(" ")));//убрать пробелы
            }

            result = new Integer[levelLines.size()][levelLines.get(0).length];//создается array с правильным размером
            for(int i = 0; i < levelLines.size(); i++) {

                result[i] = levelLines.get(i);
            }
        } catch (IOException e) {

            e.printStackTrace();
        }

        return  result;
    }

    public static final Integer[] str2int_arrays(String[] stringArray) {

        Integer[] result = new Integer[stringArray.length];

        for (int i = 0; i < stringArray.length; i++) {

            result[i] = Integer.parseInt(stringArray[i]);
        }

        return result;
    }
}
