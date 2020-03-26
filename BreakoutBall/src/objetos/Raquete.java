package objetos;

import java.awt.Color;
import java.awt.Graphics;

public class Raquete extends Retangulo{
    
    public Raquete(int x, int y, int l, int a){
        super(x, y, l, a);
    }
    
    public void Desenhar(Graphics g){
        this.Desenhar(g, Color.blue);
    }
    
    public void MoverEsquerda(Retangulo r){
        if (!this.intersects(r)) {
            this.setPosX(this.getPosX() - 20);
        }
    }
    
    public void MoverDireita(Retangulo r){
        if (!this.intersects(r)) {
            this.setPosX(this.getPosX() + 20);
        }
    }
    
    public void MoverCima(Retangulo r){
        if (!this.intersects(r)) {
            this.setPosY(this.getPosY() - 20);
        }
    }
    
    public void MoverBaixo(Retangulo r){
        if (!this.intersects(r)) {
            this.setPosY(this.getPosY() + 20);
        }
    }
}