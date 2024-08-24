package inputs;

import core.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.imageio.ImageIO;


public class KeyboardHandler implements KeyListener {
   private Window w;
   private long start_time;
   private long stop_time;
   private long pressed_time;
   private boolean MovingLeft = false;
   public boolean holding_key = false;
   public int direction = 1;
   private boolean Jumping;
   public KeyboardHandler(Window w) {
      this.w = w;
      w.addKeyListener(this);
   }

   public void keyTyped(KeyEvent e) {
   }

   public void keyPressed(KeyEvent e) {
      int key = e.getKeyCode();
      if (this.w.level.player.vely == 0.0) {
         if (key == 32 && !this.holding_key) {
            this.start_time = System.currentTimeMillis();
            this.holding_key = true;
            this.w.level.player.characterImage=this.w.level.player.upright1;
            this.w.level.player.stand=this.w.level.player.upright1;
         }

         else if (key == 68) {
        	 this.w.level.player.preR = true;
        	 this.w.level.player.preL = false;
            if (this.w.level.player.IsCollisionR && !this.w.level.player.Falling) {
            	this.w.level.player.velx = 0;
            }
            
            else {
                this.w.level.player.velx = (double)this.w.level.player.speed;
                this.MovingLeft = false;
                this.direction = 1;
            }

         }

         else if (key == 65) {
        	this.w.level.player.preL = true;
        	this.w.level.player.preR = false;
            if (this.w.level.player.IsCollisionL && !this.w.level.player.Falling) {
            	this.w.level.player.velx = 0;
            }
            else {
                this.w.level.player.velx = (double)(-this.w.level.player.speed);
                this.MovingLeft = true;
                this.direction = -1;
            }
         }

         // if (key == 87 && this.w.level.player.vely == 0.0) {
         //    this.w.level.player.vely = -this.w.level.player.JumpVelocity;
         // }
      }

   }
   public void keyReleased(KeyEvent e) {
      int key = e.getKeyCode();
      if (this.w.level.player.vely == 0.0) {
         if (key == 68 && !this.MovingLeft) {
            this.w.level.player.velx = 0.0;
            this.w.level.player.stand=this.w.level.player.standright;
            this.w.level.player.characterImage=this.w.level.player.stand;
         }

         if (key == 65 && this.MovingLeft) {
        	 this.w.level.player.velx = 0.0;
        	 this.w.level.player.stand=this.w.level.player.standleft;
             this.w.level.player.characterImage=this.w.level.player.stand;
         }

         if (key == 32 && this.holding_key) {
        	 this.w.level.player.jump();////
            this.stop_time = System.currentTimeMillis();
            this.pressed_time = Math.min(this.stop_time - this.start_time, 1000L);
            this.w.level.player.vely = -((double)this.pressed_time) * 0.0105;
            this.w.level.player.velx = (double)((long)this.direction * this.pressed_time) * 0.0055;
            this.Jumping = true;
            this.holding_key = false;
         }
      }

   }
}


