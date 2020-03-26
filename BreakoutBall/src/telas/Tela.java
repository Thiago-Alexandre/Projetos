package telas;

import javax.swing.JFrame;

public class Tela extends JFrame{
    
    private final int LARGURA;
    private final int ALTURA;
    
    public Tela(int largura, int altura){
        super("BreakOut Ball");
        LARGURA = largura;
        ALTURA = altura;
        this.setSize(LARGURA, ALTURA);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}