package objetos;

import java.awt.Point;
import java.awt.Rectangle;

public class Retangulo{
    
    public int posX;
    public int posY;
    public final int LARGURA_R;
    public final int ALTURA_R;
    public Rectangle retangulo;
    public Point pontoA;
    public Point pontoB;
    public Point pontoC;
    public Point pontoD;
    
    public Retangulo(int x, int y, int l, int a){
        LARGURA_R = l;
        ALTURA_R = a;
        posX = x;
        posY = y;
    }
}