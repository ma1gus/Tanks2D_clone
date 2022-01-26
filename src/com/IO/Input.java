package com.IO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

//JComponent — базовый класс не только для Swing-компонентов, но также и для пользовательских компонентов.
//Он обеспечивает инфраструктуру окрашивания для всех компонентов — нечто, становящееся удобным для пользовательских компонентов.
//Он знает, как обрабатывать все нажатия клавиш на клавиатуре. Подклассы, следовательно, должны только прослушивать определенные клавиши.
//Он содержит метод add(), который позволяет вам добавить другие JComponent. Этим способом вы можете добавить любой Swing-компонент к любому другому Swing-компоненту для создания вложенных компонентов (например, JPanel, содержащую JButton, или даже более причудливые комбинации, например JMenu, содержащее JButton).

public class Input extends JComponent {

    private boolean[] map;//ASCII-значения для кей-кодов

    public Input() {

        map = new boolean[256];

        for (int i = 0; i < map.length; i++) {

            final int KEY_CODE = i;
            //Случай, когда кнопка нажата
            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(i, 0, false), i * 2);//говорит о том, что нажатие кнопок нужно ловить только когда окно игры в фокусе. i * 2 - даем уникальное имя
            getActionMap().put(i * 2, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    map[KEY_CODE] = true;
                }
            });
            //Случай,когда кнопка отпускается
            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(i, 0, true), i * 2 + 1);//i * 2 + 1 - используем нечетные цифры
            getActionMap().put(i * 2 + 1, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    map[KEY_CODE] = false;
                }
            });
        }
    }

    //Получить нажатые кнопки
    public boolean[] getMap() {

        return Arrays.copyOf(map, map.length);
    }

    //Нажата кнопка или нет
    public boolean getKey(int keyCode) {

        return map[keyCode];
    }
}
