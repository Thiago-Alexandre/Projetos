package telas;

import breakoutball.GamePlay;

public class TelaJogo  extends Tela{
    
    int LARG = 710;
    int ALT = 723;
    GamePlay game;
    
    public TelaJogo(String nome, boolean novo){
        super(710,723);
        //game = new GamePlay(LARG - 5, ALT - 28, this, nome, novo);
        game = new GamePlay(LARG - 16, ALT - 38, this, nome, novo);
        this.add(game);
    }
}