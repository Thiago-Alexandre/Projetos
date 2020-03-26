package telas;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Fundo extends JPanel{
    private BufferedImage img = null;
    private final String imagem;
    
    public Fundo(String img){
        imagem = img;
    }
    
    @Override  
    public void paintComponent(Graphics g) {  
        super.paintComponent(g);        
        Graphics gr = (Graphics2D)g.create(); 
        try{
            this.img =  ImageIO.read(new File(imagem));
        } catch(Exception e){
            System.out.println("Erro na imagem...");
        }
        gr.drawImage(img, 0, 0,this.getWidth(),this.getHeight(),this);  
        gr.dispose();     
    } 
}
