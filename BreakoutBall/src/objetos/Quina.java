package objetos;

import java.awt.Color;
import java.awt.Graphics;

public class Quina extends Retangulo{
    
    public Quina(int x, int y, int l, int a){
        super(x, y, l, a);
    }
    
    public void Desenhar(Graphics g){
        this.Desenhar(g, Color.gray);
    }
}