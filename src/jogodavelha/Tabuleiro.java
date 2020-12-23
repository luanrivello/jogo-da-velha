package jogodavelha;

import java.io.Serializable;
import java.util.Objects;

public class Tabuleiro implements Serializable{
    String nome;
    private char[][] tabuleiro;
    private char proximo;
    private int p1;
    private int p2;
    private int jogadas;
    private int partidas;
    private final int opponent;
    /*
    0- jogador
    1- facil
    2- medio
    3- dificil
    */
    private final String j1;
    private final String j2;
    private boolean VezComputador;
    
    public Tabuleiro(String j1, String j2, int op) {
        this.nome = "";
        this.tabuleiro = new char[3][3];
        this.proximo = 'X';
        this.p1 = 0;
        this.p2 = 0;
        this.jogadas = 0;
        this.partidas = 0;
        this.j1 = j1;
        this.j2 = j2;
        this.opponent = op;
        this.VezComputador = false;
        
    }
    
    public Tabuleiro(String j1, String j2, int op, boolean VezComputador) {
        this.nome = "";
        this.tabuleiro = new char[3][3];
        this.proximo = 'X';
        this.p1 = 0;
        this.p2 = 0;
        this.jogadas = 0;
        this.partidas = 0;
        this.j1 = j1;
        this.j2 = j2;
        this.opponent = op;
        this.VezComputador = VezComputador;
        
    }

    public boolean jogar(int lin, int col){
        
        if(!(lin < 0 || lin > 2 || col < 0 || col > 2)){
            
            if(tabuleiro[lin][col] == 0){
                this.tabuleiro[lin][col] = proximo;

                if(proximo == 'X'){
                    this.proximo = 'O';
                }else{
                    this.proximo = 'X';
                }
                
                this.jogadas = this.jogadas + 1;
                return true;
                
            }else{
                return false;
            }
 
        }else{
            return false;
        }

    }

    public boolean checaVitoria(){
        char aux;
        int i,j;
        
        //Coluna
        for(j=0 ; j < 3 ; j++){
            aux = tabuleiro[0][j];
            
            if(aux != 0 && aux == tabuleiro[1][j] && aux == tabuleiro[2][j]){
                return true;
            }
        }
        
        //Linha
        for(i=0 ; i < 3 ; i++){
            aux = tabuleiro[i][0];
            
            if(aux != 0 && aux == tabuleiro[i][1] && aux == tabuleiro[i][2]){
                return true;
            }
        }
        
        //Diagonal
        if(tabuleiro[0][0] != 0 &&  tabuleiro[0][0] == tabuleiro[1][1] && tabuleiro[1][1] == tabuleiro[2][2]){
            return true;
            
        }else if(tabuleiro[0][2] != 0 && tabuleiro[0][2] == tabuleiro[1][1] && tabuleiro[1][1] == tabuleiro[2][0]){
            return true;
            
        }
        
        return false;
        
    }

    public void pontuar(){
        if(proximo == 'O'){
            this.p1 ++;
        }else{
            this.p2++;
        }
    }
    
    public void zerarPontos(){
        this.p1 = 0;
        this.p2 = 0;
    }
    
    //Get e Set
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
    public int getP1() {
        return this.p1;
    }

    public int getP2() {
        return this.p2;
    }

    public int getJogadas() {
        return jogadas;
    }

    public char getProximo() {
        return proximo;
    }

    public String getJ1() {
        return j1;
    }

    public String getJ2() {
        return j2;
    }

    public char[][] getTabuleiro() {
        return tabuleiro;
    }

    public int getOpponent() {
        return opponent;
    }

    public boolean isVezComputador() {
        return VezComputador;
    }
    
    public void passarVez(){
        this.VezComputador = !this.VezComputador;
    }

    public int getPartidas() {
        return partidas;
    }

    public void setPartidas(int partidas) {
        this.partidas = partidas;
    }

    //Impressao do tabuleiro
    public void print(){
        int i,j;
        Character aux; 
        
        for(i = 0; i<3 ; i++){
            
            //Linha
            for(j = 0; j<2 ; j++){
                
                aux = tabuleiro[i][j];
                
                if(aux != 0){
                    System.out.print("  " + tabuleiro[i][j]);
                }else{
                    System.out.print("   ");
                }
                
                System.out.print("  ┃");
                
            }
            
            System.out.println(" " + tabuleiro[i][j] + " ");
            
            //Divisor
            if(i != 2){
                for(j = 0; j<2 ; j++){

                    System.out.print("━━━━━╋");

                }

                System.out.println("━━━━━");
            }
        }

    }
    
    public void limparTabuleiro(){
        this.tabuleiro = new char[3][3];
        this.jogadas = 0;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tabuleiro other = (Tabuleiro) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        return true;
    }
    
}