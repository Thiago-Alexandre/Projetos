package telas;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import javax.swing.JOptionPane;

public class TelaInicial extends Tela implements KeyListener{
    
    TelaJogo jogo;
    Fundo fundo;
    
    public TelaInicial(){
        super(400,400);
        this.addKeyListener(this);
        fundo = new Fundo("BreakoutBall.jpg");
        this.add(fundo);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_N) {
            jogo = new TelaJogo(JOptionPane.showInputDialog("Digite o nome do novo jogador:"), true);
            jogo.setVisible(true);
            this.setVisible(false);
            this.dispose();
        }
        if (e.getKeyCode() == KeyEvent.VK_C) {
            String arquivo = JOptionPane.showInputDialog("Digite o nome do jogador salvo:");
            try {
                RandomAccessFile raf = new RandomAccessFile(arquivo + ".txt", "r");
                raf.close();
                jogo = new TelaJogo(arquivo, false);
                jogo.setVisible(true);
                this.setVisible(false);
                this.dispose();
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao encontrar o arquivo! " + ex);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao ler o arquivo! " + ex);
            }
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}    
}