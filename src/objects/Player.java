package objects;

import core.Window;
import level.SoundPlayer;

import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Iterator;

public class Player {
   public Window w; // Tham chiếu tới cửa sổ của trò chơi
   public int width; // Chiều rộng của người chơi
   public int height; // Chiều cao của người chơi
   public double x; // Vị trí theo trục x của người chơi trên màn hình
   public double y; // Vị trí theo trục y của người chơi trên màn hình
   public double velx; // Vận tốc theo trục x của người chơi
   public double vely; // Vận tốc theo trục y của người chơi
   public double JumpVelocity = 30; // Độ lớn vận tốc khi nhảy
   public int speed = 5; // Tốc độ di chuyển của người chơi
   public boolean Falling = false; // Biến boolean để xác định xem người chơi có đang rơi không
   public boolean IsCollisionL = false;
   public boolean IsCollisionR = false;
   public boolean preL;
   public boolean preR;
   
   public BufferedImage upright1,upright2,upleft1,upleft2,downleft,downright,left1,left2,right1,right2,standright,standleft,stand,jump;
   public Image characterImage;
   public int spriteCounter=0;
   public int spriteNum=1;
   
   public SoundPlayer soundPlayer;
   
   
   
   // Constructor của lớp Player
   public Player(Window w, int x, int y, int width, int height,String imagePath) {
      this.w = w; // Khởi tạo cửa sổ của trò chơi
      this.x = (double)x; // Khởi tạo vị trí theo trục x
      this.y = (double)y; // Khởi tạo vị trí theo trục y
      this.width = width; // Khởi tạo chiều rộng
      this.height = height; // Khởi tạo chiều cao
      soundPlayer = new SoundPlayer();
      getplayerimage();
      try {
	   	    characterImage=ImageIO.read(getClass().getResourceAsStream(imagePath));
	   	}catch(IOException e) {
	   		e.printStackTrace();
	   	}
   }
   public void jump() {
       // Logic for jumping;
       soundPlayer.playSound("/soundeffects/boi_jump.wav");
       //soundPlayer.setVolume(-10.0f);
   }

   public void collide() {
       // Logic for colliding
       soundPlayer.playSound("/soundeffects/boi_bump.wav");
       //soundPlayer.setVolume(-10.0f);
   }

   public void land() {
       // Logic for landing
       soundPlayer.playSound("/soundeffects/boi_land.wav");
       //soundPlayer.setVolume(-10.0f);
   }
   public void splat() {
       // Logic for landing
       soundPlayer.playSound("/soundeffects/boi_splat.wav");
       soundPlayer.setVolume(-30.0f);
   }

   // Phương thức tick để cập nhật trạng thái của người chơi
   public void tick() {
      // Cập nhật vị trí của người chơi dựa trên vận tốc
      this.x += this.velx;
      this.y += this.vely;
      
      // Xử lý trọng lực
      if (this.vely < this.w.level.Gravity*10 && this.Falling) {
         this.vely += 0.18; // Tăng vận tốc theo trục y nếu đang rơi và vận tốc vẫn nhỏ hơn trọng lực
      } else if (!this.Falling && this.vely > 0.0) {
         this.vely = 0.0; // Đặt vận tốc theo trục y về 0 nếu không đang rơi và vận tốc vẫn dương
      }
      if(velx>0&&vely==0) {
    	  //splat();
    	  jump=upright1;
    	  if(spriteNum==1) {
    			characterImage=right1;
    		}
    		if(spriteNum==2) {
    			characterImage=right2;
    		}
      }
      else if(velx<0&&vely==0) {
    	  //splat();
    	  jump=upleft1;
    	  if(spriteNum==1) {
  			characterImage=left1;
  		}
  		if(spriteNum==2) {
  			characterImage=left2;
  		}
      }
      else if(velx>0&&vely<0) {
    	  characterImage=upright2;
      }
      else if(velx>0&&vely>0) {
    	  characterImage=downright;
    	  stand=standright;
      }
      else if(velx<0&&vely<0) {
    	  characterImage=upleft2;  
      }
      
      else if(velx<0&&vely>0) {
    	  characterImage=downleft;
    	  stand=standleft;
      }
      else {
    	  characterImage=stand;
      }
      spriteCounter++;
		if(spriteCounter>10) {
			if(spriteNum==1) {
				spriteNum=2;
			}
			else if(spriteNum==2) {
				spriteNum=1;
			}
			spriteCounter=0;
		}
      // Xử lý va chạm
      this.Collisions();
   }
   public void getplayerimage() {
	   	try {
	   		left1=ImageIO.read(getClass().getResourceAsStream("/map/leftwalk1.png"));
	   		left2=ImageIO.read(getClass().getResourceAsStream("/map/leftwalk2.png"));
	   		right1=ImageIO.read(getClass().getResourceAsStream("/map/rightwalk1.png"));
	   		right2=ImageIO.read(getClass().getResourceAsStream("/map/rightwalk2.png"));
	   		upright1=ImageIO.read(getClass().getResourceAsStream("/map/newup.png"));
	   		upright2=ImageIO.read(getClass().getResourceAsStream("/map/uppingright2.png"));
	   		upleft1=ImageIO.read(getClass().getResourceAsStream("/map/newup.png"));
	   		upleft2=ImageIO.read(getClass().getResourceAsStream("/map/uppingleft2.png"));
	   		downleft=ImageIO.read(getClass().getResourceAsStream("/map/downleft1.png"));
	   		downright=ImageIO.read(getClass().getResourceAsStream("/map/downright1.png"));
	   		standright=ImageIO.read(getClass().getResourceAsStream("/map/standright.png"));
	   		standleft=ImageIO.read(getClass().getResourceAsStream("/map/standleft.png"));
	   		stand=standright;
	   	}catch(IOException e) {
	   		e.printStackTrace();
	   	}
}	
   // Phương thức để xử lý va chạm
   public void Collisions() {
	  this.IsCollisionL = false;
	  this.IsCollisionR = false;
      this.Falling = true; // Ban đầu, giả sử người chơi đang rơi
      Iterator var2 = this.w.level.items.iterator();

      while(var2.hasNext()) {
         Item i = (Item)var2.next();
         if (i.ID == ObjectIDS.Platform) {
            Platform p = (Platform)i;
            Rectangle playerRect = new Rectangle((int)(this.x + this.velx), (int)(this.y + this.vely), this.width, this.height);
            if (playerRect.intersects((double)p.x, (double)p.y, (double)p.width, (double)p.height)) {
               // Xử lý va chạm với platform
               if (this.y + (double)this.height <= (double)(p.y + 1)) {
                  this.Falling = false; // Nếu đang chạm đất, đặt trạng thái rơi về false
                  if (this.vely > 0.0) {
                     this.vely = 0.0;
                     this.y = (double)(p.y - this.height + 1); // Đặt vị trí y để ngăn người chơi "đè" vào nền
                     this.velx = 0.0; // Dừng người chơi khi chạm đất
                     land();
                  }
               } else {//
            	   if (this.velx > 0)	{
               		   this.IsCollisionR = true;
               		   this.w.Klistener.direction=-1;
               		   this.IsCollisionL = false;
               	   }
            	   else if(this.velx<0) 	{
                		   this.IsCollisionL = true;
                		   this.w.Klistener.direction=1;
                		   this.IsCollisionR = false;
                	   }
            	   else {
            		   if (this.preL)   {
            			   this.IsCollisionR = false;
                		   this.IsCollisionL = true;

            		   }
            		   if (this.preR) {
            			   this.IsCollisionL = false;
                		   this.IsCollisionR = true;

            		   }
            	   }
                    
            	  if(this.Falling==false) {
            		  this.velx=0;
            	  }
            	  else {
            		  collide();
            		  this.velx *= -0.5; // Đảo ngược hướng di chuyển nếu va chạm với platform từ bên trái hoặc phải
            	  }
                  
               }
               //else this.velx=0;

               if (this.vely < 0.0 && this.x + (double)this.width > (double)(p.x + 1) && this.x < (double)(p.x + p.width - 1)) {
            	   collide();
                  this.Falling = true; // Nếu va chạm phía trên, đặt trạng thái rơi về true
                  this.velx *= -0.4; // Đảo ngược hướng di chuyển
                  this.y -= this.vely + 1.0; // Đặt lại vị trí y
                  this.vely *= -0.7; // Đảo ngược vận tốc theo trục y
               }
            }
         }
      }
   }
   
   // Phương thức để vẽ người chơi lên màn hình
  public void Render(Graphics g) {
	   g.drawImage(this.characterImage, (int)this.x, (int)this.y, this.width, this.height, null);// Vẽ hình chữ nhật đại diện cho người chơi
   }
}
