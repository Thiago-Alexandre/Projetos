package objetos;

import java.awt.Color;
import java.awt.Graphics;

public class Quina extends Retangulo{
    
    public Quina(int x, int y, int l, int a){
        super(x, y, l, a);
    }
    
    public void Desenhar(Graphics g){
        g.setColor(Color.gray);
        g.fillRect(super.posX, super.posY, super.LARGURA_R, super.ALTURA_R);
    }
}