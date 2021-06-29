package com.physicsEngine.rendering;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

import com.physicsEngine.Game;
import com.physicsEngine.components.rendering.*;

public class Displayer extends JFrame {

  private BufferedImage frame;

  public Displayer() {

    setTitle("physicsEnigne");
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setUndecorated(true);
    setVisible(true);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  public void display(Cam cam) {
    BufferStrategy bs = getBufferStrategy();
    if(bs == null){
      createBufferStrategy(3);
    return;
    }
    if (cam != null)
      frame = cam.render();
    else 
    return;

    setVisible(true);
    Graphics g = bs.getDrawGraphics();
    if (frame != null)
      g.drawImage(frame, 0, 0, null);

    Font myFont = new Font("Courier", Font.PLAIN,14);
    g.setFont(myFont);
    g.setColor(Color.yellow);
    g.drawString("ups : " + Game.game.getUpdates() ,5, 12);
    g.drawString("fps : " + Game.game.getFrames(), 5,31);
      g.dispose();  
    bs.show();




  }
}
