package level;

import core.Window;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;

import objects.Item;
import objects.ObjectIDS;
import objects.Platform;
import objects.Player;
import objects.Spike;

public class LevelHandler {
   public Graphics2D g2;
   private Window w;
   public double Gravity = 15.0;
   public LinkedList<Item> items = new LinkedList();
   public int seed;
   public int CameraX = 0;
   public int CameraY = 0;
   public Player player = null;
   Tile[] tilesquare;//
   Tile[] tilesflat;
   Tile[] tilesback;
   int mapTileNumsquare[][];
   int mapTileNumflat[][];
   int mapTileNumback[][];
   public LevelHandler(Window w) {
	   tilesquare=new Tile[20];
	   tilesflat=new Tile[20];
	   tilesback=new Tile[30];
   	   getTileImage();
   	   mapTileNumsquare=new int[32][192]; 
   	   mapTileNumflat=new int[32][192];
   	   mapTileNumback=new int[32][192];
       this.w = w;
       this.loadMap();//
       Random r = new Random();
       this.seed = r.nextInt();
       this.player = new Player(w, 48+300, 700-40-176*48, 72, 44,"/map/standright.png");
       this.loadtile();
   }
   public void getTileImage() {
   		tilesquare[0]=new Tile();
   		tilesquare[0].imagename="/tiles/floor1.png";
   		//tile[0].image=ImageIO.read(getClass().getResourceAsStream("/tiles/floor1.png"));
   		tilesquare[1]=new Tile();
   		tilesquare[1].imagename="/tiles/floor1.png";
   		//tile[1].image=ImageIO.read(getClass().getResourceAsStream("/tiles/floor3.png"));
   		tilesquare[2]=new Tile();
   		tilesquare[2].imagename="/tiles/floor2.png";
   		//tile[2].image=ImageIO.read(getClass().getResourceAsStream("/tiles/floor5.png"));
   		tilesquare[3]=new Tile();
   		tilesquare[3].imagename="/tiles/floor3.png"; 
   		
   		tilesquare[4]=new Tile();
   		tilesquare[4].imagename="/tiles/floor4.png";
   		
   		tilesquare[5]=new Tile();
   		tilesquare[5].imagename="/tiles/floor5.png";
   		
   		tilesquare[6]=new Tile();
   		tilesquare[6].imagename="/tiles/floor6.png";
   		
   		tilesquare[7]=new Tile();
   		tilesquare[7].imagename="/tiles/floor18.png";
   		
   		tilesquare[8]=new Tile();
   		tilesquare[8].imagename="/tiles/floor8.png"; 
   		
   		tilesquare[9]=new Tile();
   		tilesquare[9].imagename="/tiles/floor9.png";
   		
   		tilesquare[10]=new Tile();
   		tilesquare[10].imagename="/tiles/floor10.png";
   		
   		tilesquare[11]=new Tile();
   		tilesquare[11].imagename="/tiles/floor11.png"; 
   		
   		tilesquare[12]=new Tile();
   		tilesquare[12].imagename="/tiles/riaday.png";
   		
   		tilesquare[13]=new Tile();
   		tilesquare[13].imagename="/tiles/riatru.png";
   		
   		tilesquare[14]=new Tile();
   		tilesquare[14].imagename="/tiles/floor14.png"; 
   		
   		tilesquare[15]=new Tile();
   		tilesquare[15].imagename="/tiles/floor15.png";
   		
   		tilesquare[16]=new Tile();
   		tilesquare[16].imagename="/tiles/floor16.png";
   		
   		tilesquare[17]=new Tile();
   		tilesquare[17].imagename="/tiles/floor17.png";
   		
   		tilesquare[18]=new Tile();
   		tilesquare[18].imagename="/tiles/floor7.png";
   		//flat
   		
   		tilesflat[1]=new Tile();
   		tilesflat[1].imagename="/tiles/flat1.png";
   		
   		tilesflat[2]=new Tile();
   		tilesflat[2].imagename="/tiles/flat2.png";
   		
   		tilesflat[3]=new Tile();
   		tilesflat[3].imagename="/tiles/daytruyen1.png";
   		
   		tilesflat[4]=new Tile();
   		tilesflat[4].imagename="/tiles/daytruyen2.png";
   		
   		tilesflat[5]=new Tile();
   		tilesflat[5].imagename="/tiles/daytruyen3.png";
   		
   		tilesflat[6]=new Tile();
   		tilesflat[6].imagename="/tiles/flat.png";
   		// back
   		
   		tilesback[0]=new Tile();
   		tilesback[0].imagename="/backtiles/backtiles1.png";
   		//tile[0].image=ImageIO.read(getClass().getResourceAsStream("/tiles/floor1.png"));
   		tilesback[1]=new Tile();
   		tilesback[1].imagename="/backtiles/backtiles1.png";
   		//tile[1].image=ImageIO.read(getClass().getResourceAsStream("/tiles/floor3.png"));
   		tilesback[2]=new Tile();
   		tilesback[2].imagename="/backtiles/backtiles2.png";
   		//tile[2].image=ImageIO.read(getClass().getResourceAsStream("/tiles/floor5.png"));
   		tilesback[3]=new Tile();
   		tilesback[3].imagename="/backtiles/backtiles3.png"; 
   		
   		tilesback[4]=new Tile();
   		tilesback[4].imagename="/backtiles/backtiles4.png";
   		
   		tilesback[5]=new Tile();
   		tilesback[5].imagename="/backtiles/backtiles5.png";
   		
   		tilesback[6]=new Tile();
   		tilesback[6].imagename="/backtiles/backtiles6.png";
   		
   		tilesback[7]=new Tile();
   		tilesback[7].imagename="/backtiles/backtiles7.png";
   		
   		tilesback[8]=new Tile();
   		tilesback[8].imagename="/backtiles/backtiles8.png"; 
   		
   		tilesback[9]=new Tile();
   		tilesback[9].imagename="/backtiles/backtiles9.png";
   		
   		tilesback[10]=new Tile();
   		tilesback[10].imagename="/backtiles/backtiles10.png";
   		
   		tilesback[11]=new Tile();
   		tilesback[11].imagename="/backtiles/backtiles11.png"; 
   		
   		tilesback[12]=new Tile();
   		tilesback[12].imagename="/backtiles/backtiles12.png";
   		
   		tilesback[13]=new Tile();
   		tilesback[13].imagename="/backtiles/backtiles13.png"; 
   		
   		tilesback[14]=new Tile();
   		tilesback[14].imagename="/backtiles/backtiles14.png";
   		
   		tilesback[15]=new Tile();
   		tilesback[15].imagename="/backtiles/backtiles15.png";
   		
   		tilesback[16]=new Tile();
   		tilesback[16].imagename="/backtiles/backtiles16.png";
   		
   		tilesback[17]=new Tile();
   		tilesback[17].imagename="/backtiles/backtiles17.png";
   		
   		tilesback[18]=new Tile();
   		tilesback[18].imagename="/backtiles/backtiles18.png"; 
   		
   		tilesback[19]=new Tile();
   		tilesback[19].imagename="/backtiles/backtiles19.png";
   		
   		tilesback[20]=new Tile();
   		tilesback[20].imagename="/backtiles/backtiles20.png";
   }
   public void loadMap() {
   	try {
   		InputStream issquare=getClass().getResourceAsStream("/map/map.txt");
   		BufferedReader brsquare=new BufferedReader(new InputStreamReader(issquare));
   		InputStream isflat=getClass().getResourceAsStream("/map/flatmap.txt");
   		BufferedReader brflat=new BufferedReader(new InputStreamReader(isflat));
   		InputStream isback=getClass().getResourceAsStream("/map/backmap.txt");
   		BufferedReader brback=new BufferedReader(new InputStreamReader(isback));
   		int col=0;
   		int row=0;
   		while(col<32&&row<192) {
   			String linesquare=brsquare.readLine();
   			String lineflat=brflat.readLine();
   		    String lineback=brback.readLine();
   			while(col<32) {
   				String numbers[]=linesquare.split(" ");
   				String numbers1[]=lineflat.split(" ");
   				String numbers2[]=lineback.split(" ");
   				int num= Integer.parseInt(numbers[col]);
   				int num1=Integer.parseInt(numbers1[col]);
   				int num2=Integer.parseInt(numbers2[col]);
   				mapTileNumback[col][row]=num2;
   				mapTileNumflat[col][row]=num1;
   				mapTileNumsquare[col][row]=num;
   				col++;
   			}
   			if(col==32) {
   				col=0;
   				row++;
   			}
   		}
   		brsquare.close();
   		brflat.close();
   		brback.close();
   	}catch(Exception e) {
   		
   	}
   }
   public void Render(Graphics g) {
      g.translate(-this.CameraX, -this.CameraY);
      Iterator var3 = this.items.iterator();

      while(var3.hasNext()) {
         Item i = (Item)var3.next();
         i.Render(g);
      }
      this.player.Render(g);
      g.translate(this.CameraX, this.CameraY);
   }
   public void loadtile() {//
   	int col=0;
   	int row=0;
   	int x=0;
   	int y=0;
   	while(col<32&&row<192) {
   		int tileNumsquare=mapTileNumsquare[col][row];
        int tileNumflat=mapTileNumflat[col][row];
        int tileNumback=mapTileNumback[col][row];
   		if(tileNumsquare>0)this.items.add(new Platform(ObjectIDS.Platform, x,748+y-48*190 ,48,48,tilesquare[tileNumsquare].imagename));
   		if(tileNumflat>0)this.items.add(new Platform(ObjectIDS.Platform, x,748-48*190+y ,48,24,tilesflat[tileNumflat].imagename));
   		if(tileNumback>0)this.items.add(new Platform(ObjectIDS.back, x,748-48*190+24+y ,48,48,tilesback[tileNumback].imagename));
   		col++;
   		x+=48;
   		if(col==32) {
   			col=0;
   			x=0;
   			row++;
   			y+=48;
   		}
   	}
   }
   public void tick() {
      if (this.player.y < (double)this.CameraY) {
         this.CameraY -= 800;
      } else if (this.player.y > (double)(this.CameraY + 800)) {
         this.CameraY += 800;
      }

      Iterator var2 = this.items.iterator();

      while(var2.hasNext()) {
         Item i = (Item)var2.next();
         i.tick();
      }
      this.player.tick();
   }
}