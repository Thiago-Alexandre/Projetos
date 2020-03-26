package objetos;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Tijolo extends Retangulo{
    
    public int dureza;
    private final Random r = new Random();
    public boolean quebrado;
    
    public Tijolo(int x, int y, int l, int a){
        super(x, y, l, a);
        dureza = r.nextInt(3) + 1;
        quebrado = false;
    }
    
    public Tijolo(int x, int y, int l, int a, int dureza){
        super(x, y, l, a);
        this.dureza = dureza;
        if (dureza == 0) {
            quebrado = true;
        } else{
            quebrado = false;
        }
    }
    
    public void Desenhar(Graphics g){
        if (!quebrado) {
            switch (dureza) {
                case 1:
                    g.setColor(Color.red);
                    break;
                case 2:
                    g.setColor(Color.yellow);
                    break;
                default:
                    g.setColor(Color.green);
                    break;
            }
            g.fillRect(super.posX, super.posY, super.LARGURA_R, super.ALTURA_R);    
        }
    }
}