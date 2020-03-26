package objetos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Circulo extends Retangulo{
    
    public int speedX;
    public int speedY;
    public final int larguraT;
    public final int alturaT;

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
        g.fillOval(super.posX,super.posY,20,20);
    }
    
    public boolean TestarColisao(Retangulo r){
        super.retangulo = new Rectangle(super.posX, super.posY, super.LARGURA_R, super.ALTURA_R);
        r.retangulo = new Rectangle(r.posX, r.posY, r.LARGURA_R, r.ALTURA_R);
        this.pontoA = new Point(this.posX, this.posY);
        this.pontoB = new Point(this.posX, this.posY + this.ALTURA_R);
        this.pontoC = new Point(this.posX + this.LARGURA_R, this.posY + this.ALTURA_R);
        this.pontoD = new Point(this.posX + this.LARGURA_R, this.posY);

        r.pontoA = new Point(r.posX, r.posY);
        r.pontoB = new Point(r.posX, r.posY + r.ALTURA_R);
        r.pontoC = new Point(r.posX + r.LARGURA_R, r.posY + r.ALTURA_R);
        r.pontoD = new Point(r.posX + r.LARGURA_R, r.posY);

        if (
               (this.pontoA.x == r.pontoC.x && this.pontoA.y == r.pontoC.y)
            || (this.pontoD.x == r.pontoB.x && this.pontoD.y == r.pontoB.y)
            || (this.pontoC.x == r.pontoA.x && this.pontoC.y == r.pontoA.y)
            || (this.pontoB.x == r.pontoD.x && this.pontoB.y == r.pontoD.y)
        ){
            speedX = -speedX;
            speedY = -speedY;
            return true;
        }
        if (
               (this.pontoA.x == r.pontoD.x && (this.pontoA.y < r.pontoC.y && this.pontoA.y > r.pontoD.y))
            || (this.pontoB.x == r.pontoC.x && (this.pontoB.y < r.pontoC.y && this.pontoB.y > r.pontoD.y))
            || (this.pontoD.x == r.pontoA.x && (this.pontoD.y < r.pontoB.y && this.pontoD.y > r.pontoA.y))
            || (this.pontoC.x == r.pontoB.x && (this.pontoC.y < r.pontoB.y && this.pontoC.y > r.pontoA.y))
        ){
            speedX = -speedX;
            return true;
        } else if (
               (this.pontoB.y == r.pontoA.y && (this.pontoB.x < r.pontoD.x && this.pontoB.x > r.pontoA.x))
            || (this.pontoC.y == r.pontoD.y && (this.pontoC.x < r.pontoD.x && this.pontoC.x > r.pontoA.x))
            || (this.pontoA.y == r.pontoB.y && (this.pontoA.x < r.pontoC.x && this.pontoA.x > r.pontoB.x))
            || (this.pontoD.y == r.pontoC.y && (this.pontoD.x < r.pontoC.x && this.pontoD.x > r.pontoB.x))
        ){
            speedY = -speedY;
            return true;
        } else{
            return false;
        }
    }
    
    public boolean TestarColisao(){
        if (
               this.posX < 0 || this.posY < 0 
            || this.posX + this.LARGURA_R > larguraT || this.posY + this.ALTURA_R > alturaT
        ){
            return true;
        } else{
            return false;
        }
    }
    
    public void Mover(){
        super.posX += speedX;
        super.posY += speedY;
    }

    public int getSpeedX() {
        return speedX;
    }

    public int getSpeedY() {
        return speedY;
    }
}
