package objetos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Raquete extends Retangulo{
    
    public Raquete(int x, int y, int l, int a){
        super(x, y, l, a);
    }
    
    public void Desenhar(Graphics g){
        g.setColor(Color.BLUE);
        g.fillRect(posX, posY, LARGURA_R, ALTURA_R);
    }
    
    public void MoverEsquerda(Rectangle r){
        retangulo = new Rectangle(posX, posY, LARGURA_R, ALTURA_R);
        if (!retangulo.intersects(r)) {
            posX -= 20;
        }
    }
    
    public void MoverDireita(Rectangle r){
        retangulo = new Rectangle(posX, posY, LARGURA_R, ALTURA_R);
        if (!retangulo.intersects(r)) {
            posX += 20;
        }
    }
    
    public void MoverCima(Rectangle r){
        retangulo = new Rectangle(posX, posY, LARGURA_R, ALTURA_R);
        if (!retangulo.intersects(r)) {
            posY -= 20;
        }
    }
    
    public void MoverBaixo(Rectangle r){
        retangulo = new Rectangle(posX, posY, LARGURA_R, ALTURA_R);
        if (!retangulo.intersects(r)) {
            posY += 20;
        }
    }
}