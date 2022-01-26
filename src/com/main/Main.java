package com.main;

import com.game.Game;

public class Main {

    public static void main(String[] args) {

        Game tanks = new Game();
        tanks.start();

        /*Display.create(800, 600, "Tanks", 0xff00ff00, 3);//цвет передается в хексо-децимальном значении. 0123456789abcdef (int 0xffffffff)

        Timer t = new Timer(1000/60, new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {

                Display.clear();
                Display.render();
                Display.swapBuffers();
            }
        });

        t.setRepeats(true);
        t.start();*/
    }
}
