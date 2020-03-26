package telas;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TelaFinal extends Tela implements KeyListener{
    
    Fundo fundo;
    
    public TelaFinal(int pontos){
        super(400,400);
        this.addKeyListener(this);
        fundo = new Fundo("BreakoutBall2.jpg");
        this.add(fundo);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_S) {
            TelaInicial tela = new TelaInicial();
            tela.setVisible(true);
            this.dispose();
        }
        if (e.getKeyCode() == KeyEvent.VK_N) {
            System.out.println("Sair!");
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}