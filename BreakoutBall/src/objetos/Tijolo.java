package objetos;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Tijolo extends Retangulo{
    
    private int dureza;
    private final Random r = new Random();
    private boolean quebrado;
    
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
                    this.Desenhar(g, Color.red);
                    break;
                case 2:
                    this.Desenhar(g, Color.yellow);
                    break;
                default:
                    this.Desenhar(g, Color.green);
                    break;
            }   
        }
    }

    public int getDureza() {
        return dureza;
    }

    public void setDureza(int dureza) {
        this.dureza = dureza;
    }

    public boolean isQuebrado() {
        return quebrado;
    }

    public void setQuebrado(boolean quebrado) {
        this.quebrado = quebrado;
    }
}