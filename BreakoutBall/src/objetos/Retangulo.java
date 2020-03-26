package objetos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Retangulo extends Rectangle{
    
    private int posX;
    private int posY;
    private final int LARGURA_R;
    private final int ALTURA_R;
    private Point pontoA;
    private Point pontoB;
    private Point pontoC;
    private Point pontoD;
    
    public Retangulo(int x, int y, int l, int a){
        super(x,y,l,a);
        LARGURA_R = l;
        ALTURA_R = a;
        posX = x;
        posY = y;
    }
    
    public void AtualizarPontos(){
        pontoA = new Point(posX, posY);
        pontoB = new Point(posX, posY + ALTURA_R);
        pontoC = new Point(posX + LARGURA_R, posY + ALTURA_R);
        pontoD = new Point(posX + LARGURA_R, posY);
    };

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public Point getPontoA() {
        return pontoA;
    }

    public void setPontoA(Point pontoA) {
        this.pontoA = pontoA;
    }

    public Point getPontoB() {
        return pontoB;
    }

    public void setPontoB(Point pontoB) {
        this.pontoB = pontoB;
    }

    public Point getPontoC() {
        return pontoC;
    }

    public void setPontoC(Point pontoC) {
        this.pontoC = pontoC;
    }

    public Point getPontoD() {
        return pontoD;
    }

    public void setPontoD(Point pontoD) {
        this.pontoD = pontoD;
    }

    public int getLARGURA_R() {
        return LARGURA_R;
    }

    public int getALTURA_R() {
        return ALTURA_R;
    }
    
    public void Desenhar(Graphics g, Color c){
        g.setColor(c);
        g.fillRect(posX, posY, LARGURA_R, ALTURA_R);
    }
}