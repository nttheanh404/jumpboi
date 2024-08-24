// Source code is decompiled from a .class file using FernFlower decompiler.
package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spike extends Item {
   public int x;
   public int y;
   public int width;
   public int height;
   public Image image;
   public Spike(byte ID, int x, int y, int width, int height, String imagePath) {
      super(ID);
      try {
          this.image = ImageIO.read(getClass().getResourceAsStream(imagePath));
      } catch (IOException e) {
          e.printStackTrace();
      }
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
   }

   public void Render(Graphics g) {
	   g.drawImage(this.image, this.x, this.y, this.width, this.height, null);
   }

   public void tick() {
   }
}


