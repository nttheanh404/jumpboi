package objects;

//import java.awt.Color;
//import java.awt.Graphics;
//
//public class Platform extends Item {
//   public int x;
//   public int y;
//   public int width;
//   public int height;
//   public Color c;
//
//   public Platform(byte ID, int x, int y, int width, int height, Color c) {
//      super(ID);
//      this.c = c;
//      this.x = x;
//      this.y = y;
//      this.width = width;
//      this.height = height;
//   }
//
//   public void Render(Graphics g) {
//      g.setColor(Color.blue);
//      g.fillRect(this.x, this.y, this.width, this.height);
//   }
//
//   public void tick() {
//   }
//}
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Platform extends Item {
    public int x;
    public int y;
    public int width;
    public int height;
    public Image image; // Thêm thuộc tính hình ảnh

    public Platform(byte ID, int x, int y, int width, int height, String imagePath) {
        super(ID);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        
        // Load hình ảnh từ tệp
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Render(Graphics g) {
        // Vẽ hình ảnh thay vì hình chữ nhật
        g.drawImage(this.image, this.x, this.y, this.width, this.height, null);
    }

    @Override
    public void tick() {
        // Cập nhật trạng thái của mục nếu cần
    }
}

