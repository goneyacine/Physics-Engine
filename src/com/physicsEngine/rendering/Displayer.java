package com.physicsEngine.rendering;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
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
    if (cam != null)
      frame = cam.render();

    ImageCapabilities imgBackBufCap = new ImageCapabilities(true);
    ImageCapabilities imgFrontBufCap = new ImageCapabilities(true);
    BufferCapabilities bufCap =
      new BufferCapabilities(imgFrontBufCap, imgBackBufCap, BufferCapabilities.FlipContents.COPIED);
    try {

      createBufferStrategy(2, bufCap);
    } catch (AWTException ex) {
      createBufferStrategy(2);
    }

    BufferStrategy bs = getBufferStrategy();
    Graphics g =  bs.getDrawGraphics();
    if (frame != null)
      g.drawImage(frame, 0, 0, null);
    g.dispose();
    bs.show();



  }
}
