package breakoutball;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import objetos.*;
import telas.*;

public class GamePlay extends JPanel implements KeyListener{
    
    private Quina[] quina;
    private Circulo bola;
    private Raquete[] raquete;
    private Tijolo[] tijolo;
    private final int LARGURA;
    private final int ALTURA;
    private boolean enterOK = false;
    private boolean movimentoOK = false;
    private boolean colidiu = false;
    private Thread automacaoJogo;
    private Thread desenharQuadros;
    private int pontos;
    public int time = 9;
    private final Random r = new Random();
    BufferedImage img;
    TelaJogo tela;
    private RandomAccessFile raf;
    private final String arquivo;
    boolean if1 = true;
    boolean if2 = true;
    boolean if3 = true;
    
    public GamePlay(int largura, int altura, TelaJogo tela, String nome, boolean novo){
        LARGURA = largura;
        ALTURA = altura;
        this.tela = tela;
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        this.arquivo = nome + ".txt";
        quina = new Quina[8];
        quina[0] = new Quina(0,0,80,16);//Quina Horizontal Esquerda Superior
        quina[1] = new Quina(LARGURA - 80,0,80,16);//Quina Horizontal Direita Superior
        quina[2] = new Quina(0,0,16,80);//Quina Vertical Esquerda Superior
        quina[3] = new Quina(0,ALTURA - 80,16,80);//Quina Vertical Esquerda Inferior
        quina[4] = new Quina(LARGURA - 16,0,16,80);//Quina Vertical Direita Superior
        quina[5] = new Quina(LARGURA - 16,ALTURA - 80,16,80);//Quina Vertical Direita Inferior
        quina[6] = new Quina(0,ALTURA - 16,80,16);//Quina Horizontal Esquerda Inferior
        quina[7] = new Quina(LARGURA - 80,ALTURA - 16,80,16);//Quina Horizontal Direita Inferior
        raquete = new Raquete[4];
        tijolo = new Tijolo[81];
        if (novo) {
            pontos = 0;
            raquete[0] = new Raquete(0,(ALTURA/2 - 50),16,100);//Raquete Vertical Esquerda
            raquete[1] = new Raquete(LARGURA - 16,(ALTURA/2 - 50),16,100);//Raquete Vertical Direita
            raquete[2] = new Raquete((LARGURA/2 - 50),0,100,16);//Raquete Horizontal Superior
            raquete[3] = new Raquete((LARGURA/2 - 50),ALTURA - 16,100,16);//Raquete Horizontal Inferior
            bola = new Circulo(r.nextInt(300) + 200, 500,LARGURA, ALTURA);
            int t = 0;
            for (int y = 203; y < 497; y += 33) {
                for (int x = 203; x < 497; x += 33) {
                   tijolo[t] = new Tijolo(x,y,30,30);
                   t++;
                }
            }
        } else{
            try {
                raf = new RandomAccessFile(arquivo, "r");
                int t = 0;
                for (int y = 203; y < 497; y += 33) {
                    for (int x = 203; x < 497; x += 33) {
                       tijolo[t] = new Tijolo(x,y,30,30, raf.readByte());
                       //System.out.print("T" + t + " " + tijolo[t].dureza);
                       t++;
                    }
                }
                //System.out.println("");
                raquete[0] = new Raquete(raf.readInt(),raf.readInt(),16,100);//Raquete Vertical Esquerda
                raquete[1] = new Raquete(raf.readInt(),raf.readInt(),16,100);//Raquete Vertical Direita
                raquete[2] = new Raquete(raf.readInt(),raf.readInt(),100,16);//Raquete Horizontal Superior
                raquete[3] = new Raquete(raf.readInt(),raf.readInt(),100,16);//Raquete Horizontal Inferior
                /*
                System.out.println("R" + 0 + " X" + raquete[0].posX + "/Y" + raquete[0].posY);
                System.out.println("R" + 1 + " X" + raquete[1].posX + "/Y" + raquete[1].posY);
                System.out.println("R" + 2 + " X" + raquete[2].posX + "/Y" + raquete[2].posY);
                System.out.println("R" + 3 + " X" + raquete[3].posX + "/Y" + raquete[3].posY);
                */
                bola = new Circulo(raf.readInt(), raf.readInt(),LARGURA, ALTURA, raf.readByte(), raf.readByte());
                //System.out.println("Bx" + bola.posX + "/By" + bola.posY + " Sx" + bola.getSpeedX() + " Sy" + bola.getSpeedY());
                pontos = raf.readInt();
                System.out.println("Pontos:" + pontos);
                raf.close();
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao encontrar o arquivo! " + ex);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao ler o arquivo! " + ex);
            }
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!enterOK){
                enterOK = true;
                movimentoOK = true;
                automacaoJogo = new Thread(run1);
                automacaoJogo.start();
                desenharQuadros = new Thread(run2);
                desenharQuadros.start();
            } else{
                movimentoOK = false;
                enterOK = false;
                automacaoJogo.stop();
                desenharQuadros.stop();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (enterOK) {
                raquete[2].MoverEsquerda(quina[0]);
                raquete[3].MoverEsquerda(quina[6]);
            }
        }else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (enterOK) {
                raquete[2].MoverDireita(quina[1]);
                raquete[3].MoverDireita(quina[7]);    
            }
        }else if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
            if (enterOK) {
                raquete[0].MoverCima(quina[2]);
                raquete[1].MoverCima(quina[4]);    
            }
        }else if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (enterOK) {
                raquete[0].MoverBaixo(quina[3]);
                raquete[1].MoverBaixo(quina[5]);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            movimentoOK = false;
            automacaoJogo.stop();
            desenharQuadros.stop();
            JOptionPane.showMessageDialog(null, "Pontuação: " + pontos);
            TelaFinal fim = new TelaFinal(pontos);
            fim.setVisible(true);
            tela.dispose();
        }
        if (e.getKeyCode() == KeyEvent.VK_G) {
            movimentoOK = false;
            automacaoJogo.stop();
            desenharQuadros.stop();
            Salvar();
            enterOK = true;
            movimentoOK = true;
            automacaoJogo = new Thread(run1);
            automacaoJogo.start();
            desenharQuadros = new Thread(run2);
            desenharQuadros.start();
        }
    }
    
    public void Desenhar(){
        Graphics g = this.getGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, LARGURA, ALTURA);
        for (int i = 0; i < quina.length; i++) {
            quina[i].Desenhar(g);
        }
        for (int i = 0; i < raquete.length; i++) {
            raquete[i].Desenhar(g);
        }
        for (int i = 0; i < tijolo.length; i++) {
            tijolo[i].Desenhar(g);
        }
        bola.Desenhar(g);
    }
    
    Runnable run1 = new Runnable() {
        @Override
        public void run() {
            while(movimentoOK){
                try {
                    Thread.sleep(time);
                    for (int i = 0; i < quina.length; i++) {
                        
                        if (bola.TestarColisao(quina[i])) {
                            colidiu = true;
                            break;
                        } else{
                            colidiu = false;
                        }
                    }
                    if (!colidiu) {
                        for (int i = 0; i < raquete.length; i++) {
                            if (bola.TestarColisao(raquete[i])) {
                                colidiu = true;
                                break;
                            } else{
                                colidiu = false;
                            }
                        }
                        if (!colidiu) {
                            for (int i = 0; i < tijolo.length; i++) {
                                if (!tijolo[i].isQuebrado()) {
                                    if (bola.TestarColisao(tijolo[i])) {
                                        tijolo[i].setDureza(tijolo[i].getDureza() - 1);
                                        if (tijolo[i].getDureza() < 1) {
                                            tijolo[i].setQuebrado(true);
                                        }
                                        pontos += 5;
                                        break;
                                    } else{
                                        colidiu = false;
                                    }    
                                }
                            }    
                        }    
                    }
                    bola.Mover();
                    if (CalcularTijolosQuebrados() == 40 && if1) {
                        time--;
                        if1 = false;
                    }
                    if (CalcularTijolosQuebrados() < 40 && CalcularTijolosQuebrados() == 20 && if2){
                        time--;
                        if2 = false;
                    }
                    if (CalcularTijolosQuebrados() < 20 && CalcularTijolosQuebrados() == 10 && if3){
                        time--;
                        if3 = false;
                    }
                    FimGame();
                } catch (InterruptedException ex) {
                    System.out.println("RUN GUY, RUN!!!!!!!");
                }
            }
        }
    };
    
    Runnable run2 = new Runnable() {
        @Override
        public void run() {
            while(movimentoOK){
                try {
                    Thread.sleep(50);
                    Desenhar();
                } catch (InterruptedException ex) {
                    System.out.println("RUN GUY, RUN!!!!!!!");
                }
            }
        }
    };
    
    public void FimGame(){
        if (bola.TestarColisao()) {
            movimentoOK = false;
            automacaoJogo.stop();
            desenharQuadros.stop();
        } else{
            if (CalcularTijolosQuebrados() == 81) {
                movimentoOK = false;
                automacaoJogo.stop();
                desenharQuadros.stop();
            }
        }
    }
    
    public int CalcularTijolosQuebrados(){
        int quebrados = 0;
        for (int i = 0; i < tijolo.length; i++) {
            if (tijolo[i].isQuebrado()) {
                quebrados += 1;
            }
        }
        return quebrados;
    }
    
    public void Salvar() {
        try {
            raf = new RandomAccessFile(arquivo, "rw");
            for (int i = 0; i < tijolo.length; i++){
                raf.writeByte(tijolo[i].getDureza());
            }
            for (int i = 0; i < raquete.length; i++) {
                raf.writeInt(raquete[i].getPosX());
                raf.writeInt(raquete[i].getPosY());
            }
            raf.writeInt(bola.getPosX());
            raf.writeInt(bola.getPosY());
            raf.writeByte(bola.getSpeedX());
            raf.writeByte(bola.getSpeedY());
            raf.writeInt(pontos);
            System.out.println("Pontos:" + pontos);
            raf.close();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao encontrar o arquivo! " + ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar no arquivo! " + ex);
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void keyReleased(KeyEvent e) {}
    
    @Override  
    public void paintComponent(Graphics g) {  
        super.paintComponent(g);        
        Graphics gr = (Graphics2D)g.create(); 
        try{
            img =  ImageIO.read(new File("Regras.jpg"));
        } catch(Exception e){
            System.out.println("Erro na imagem...");
        }
        gr.drawImage(img, 0, 0,this.getWidth(),this.getHeight(),this);  
        gr.dispose();     
    }
}