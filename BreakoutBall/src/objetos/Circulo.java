package objetos;

import java.awt.Color;
import java.awt.Graphics;

public class Circulo extends Retangulo{
    
    private int speedX;
    private int speedY;
    private final int larguraT;
    private final int alturaT;

    public Circulo(int x, int y, int l, int a){
        super(x,y,20,20);
        speedX = 1;
        speedY = 1;
        larguraT = l;
        alturaT = a;
    }
    
    public Circulo(int x, int y, int l, int a, int sX, int sY){
        super(x,y,20,20);
        speedX = sX;
        speedY = sY;
        larguraT = l;
        alturaT = a;
    }
    
    public void Desenhar(Graphics g){
        g.setColor(Color.CYAN);
        g.fillOval(this.getPosX(),this.getPosY(),20,20);
    }
    
    public boolean TestarColisao(Retangulo r){
        
        this.AtualizarPontos();
        r.AtualizarPontos();

        if (
               (this.getPontoA().x == r.getPontoC().x && this.getPontoA().y == r.getPontoC().y)
            || (this.getPontoD().x == r.getPontoB().x && this.getPontoD().y == r.getPontoB().y)
            || (this.getPontoC().x == r.getPontoA().x && this.getPontoC().y == r.getPontoA().y)
            || (this.getPontoB().x == r.getPontoD().x && this.getPontoB().y == r.getPontoD().y)
        ){
            speedX = -speedX;
            speedY = -speedY;
            return true;
        }
        if (
               (this.getPontoA().x == r.getPontoD().x && (this.getPontoA().y < r.getPontoC().y && this.getPontoA().y > r.getPontoD().y))
            || (this.getPontoB().x == r.getPontoC().x && (this.getPontoB().y < r.getPontoC().y && this.getPontoB().y > r.getPontoD().y))
            || (this.getPontoD().x == r.getPontoA().x && (this.getPontoD().y < r.getPontoB().y && this.getPontoD().y > r.getPontoA().y))
            || (this.getPontoC().x == r.getPontoB().x && (this.getPontoC().y < r.getPontoB().y && this.getPontoC().y > r.getPontoA().y))
        ){
            speedX = -speedX;
            return true;
        } else if (
               (this.getPontoB().y == r.getPontoA().y && (this.getPontoB().x < r.getPontoD().x && this.getPontoB().x > r.getPontoA().x))
            || (this.getPontoC().y == r.getPontoD().y && (this.getPontoC().x < r.getPontoD().x && this.getPontoC().x > r.getPontoA().x))
            || (this.getPontoA().y == r.getPontoB().y && (this.getPontoA().x < r.getPontoC().x && this.getPontoA().x > r.getPontoB().x))
            || (this.getPontoD().y == r.getPontoC().y && (this.getPontoD().x < r.getPontoC().x && this.getPontoD().x > r.getPontoB().x))
        ){
            speedY = -speedY;
            return true;
        } else{
            return false;
        }
    }
    
    public boolean TestarColisao(){
        if (
            this.getPosX() < 0 || this.getPosY() < 0 
            || this.getPosX() + this.getLARGURA_R() > larguraT
            || this.getPosY() + this.getALTURA_R() > alturaT
        ){
            return true;
        } else{
            return false;
        }
    }
    
    public void Mover(){
        this.setPosX(this.getPosX() + speedX);
        this.setPosY(this.getPosY() + speedY);
    }

    public int getSpeedX() {
        return speedX;
    }

    public int getSpeedY() {
        return speedY;
    }
}