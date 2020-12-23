package jogodavelha;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.security.SecureRandom;
import java.util.ArrayList;

public class JogoDaVelha{
    //Input
    private static final Scanner IN = new Scanner(System.in);
    
    //Local Para Salvar Os Jogos
    private static final String  SAVE = "/media/jodah/Storage/Codes/java/Pratica/JogoDaVelhaSave/saves.obj";
    
    public static void main(String[] args) throws IOException, ClassNotFoundException{
        int resp;
        
        do{
                    
            do{
                System.out.println("JOGO DA VELHA");
                System.out.println("1- Novo Jogo\n"
                                + "2- Continuar Jogo\n"
                                + "3- Sair");
                resp = IN.nextInt();
            }while(resp < 1 || resp > 3);
            
            switch(resp){
                
                //Novo Jogo
                case 1:
                    do{
                        System.out.println("1- Um jogador\n"
                                        + "2- Dois Jogadores");
                        resp = IN.nextInt();
                    }while(resp < 1 || resp > 2);
                    
                    
                    if(resp == 1){
                        umJogador();                    
                    }else{
                        doisJogadores();
                    }

                    break;

                //Continuar Jogo    
                case 2: continuarJogo();
                    break;
            }
            
        }while(resp != 3);
                
    }
    
    //Um Jogador
    public static void umJogador() throws IOException, ClassNotFoundException{
        int resp;
        String j1;
        String j2;
        
        do{
            System.out.println("1- Facil\n"
                            + "2- Medio\n"
                            + "3- Impossivel");
            resp = IN.nextInt();
        }while(resp < 1 || resp > 3);
        
        //Sorteio
        SecureRandom random = new SecureRandom();
        boolean vezComputador;
        int sorteio = random.nextInt(2) + 1;
        
        System.out.println("=============================");
        
        if(sorteio == 1){
            j1 = "Voce";
            j2 = "Computador";
            vezComputador = false;
        }else{
            j1 = "Computador";
            j2 = "Voce";
            vezComputador = true;
        }
        
        System.out.println("X- " + j1 + "\n"
                         + "O- " + j2);
        
        Tabuleiro tab;
        switch(resp){
            //Facil
            case 1:
                tab = new Tabuleiro(j1,j2,1,vezComputador);
                jogarC(tab);
                break;
            //Medio
            case 2: 
                tab = new Tabuleiro(j1,j2,2,vezComputador);
                jogarC(tab);
                break;
            //Dificil
            case 3:
                tab = new Tabuleiro(j1,j2,3,vezComputador);
                jogarC(tab);
                break;
        }
                    
    }
    
    //Dois Jogadores
    public static void doisJogadores() throws IOException, ClassNotFoundException{
        String j1, j2;
        
        //Nomeacao
        System.out.println("Jogador 1: ");
        j1 = IN.next();

        System.out.println("Jogador 2: ");
        j2 = IN.next();
        
        System.out.println("");
        
        
        //Sorteio
        SecureRandom random = new SecureRandom();
        int sorteio = random.nextInt(2) + 1;
        
        if(sorteio == 1){
            System.out.println("X- " + j1 + "\n"
                             + "O- " + j2);
        }else{
            System.out.println("X- " + j2 + "\n"
                             + "O- " + j1);
        }
        
        //Iniciar Jogo
        Tabuleiro tab = new Tabuleiro(j1,j2,0);
        jogarJ(tab);
        
    }
    
    //Jogar Contra Jogador
    private static void jogarJ(Tabuleiro tab) throws IOException, ClassNotFoundException{
        int lin, col;
        boolean valido;
        
        //Placar Geral (melhor de 3)
        while(tab.getP1() < 2 && tab.getP2() < 2 && tab.getPartidas() < 3){
        
            System.out.println("\n" + tab.getProximo() + " Joga Primeiro" + "\n");
            //Jogo Atual
            while(!tab.checaVitoria() && tab.getJogadas() < 9){
                tab.print();

                System.out.println("Ditigite 0-0 para salvar");
                System.out.println("Posicao: ");
                System.out.print("Coluna --> ");
                col = IN.nextInt();
                
                System.out.print("Linha -->  ");
                lin = IN.nextInt();
                
                System.out.println("");
                
                valido = tab.jogar(lin-1, col-1);
                
                if(lin == 0 && col ==0){
                    salvarTabuleiro(tab);
                    return;
                }else if(!valido){
                    System.out.println("\n*Posicao Invalida*\n");
                }

            }
            
            System.out.println("");
            tab.print();
            
            //Contador de partidas
            tab.setPartidas(tab.getPartidas() + 1);
            
            //Potuacao
            printarVencedor(tab);
            
            //Recomecar
            tab.limparTabuleiro();
        
        }
        
        //Printar Resultado
        printarResultado(tab);
        
        //Remover Save
        if(!tab.getNome().equals("")){
            removerTabuleiro(tab);
        }
        
        
    }
    
    //Jogar Contra Computador
    private static void jogarC(Tabuleiro tab) throws IOException, ClassNotFoundException{
        int lin, col;
        boolean valido;
        
        //Placar Geral (melhor de 3)
        while(tab.getP1() < 2 && tab.getP2() < 2 && tab.getPartidas() < 3){
        
            System.out.println("\n" + tab.getProximo() + " Joga Primeiro" + "\n");
            
            //Jogo Atual
            while(!tab.checaVitoria() && tab.getJogadas() < 9){
                
                //Computador Joga
                if(tab.isVezComputador()){
                    computadorJogar(tab);
                    tab.passarVez();
                    
                }
                
                tab.print();
                
                //Jogador so joga se o computador nao tiver vencido
                if(!tab.checaVitoria() && tab.getJogadas() < 9){
                    //Jogador Joga
                    System.out.println("Ditigite 0-0 para salvar");
                    System.out.println("Posicao: ");
                    System.out.print("Coluna --> ");
                    col = IN.nextInt();

                    System.out.print("Linha -->  ");
                    lin = IN.nextInt();

                    System.out.println("");

                    valido = tab.jogar(lin-1, col-1);

                    if(lin == 0 && col ==0){
                        salvarTabuleiro(tab);
                        return;
                    }else if(!valido){
                        System.out.println("\n*Posicao Invalida*\n");
                    }else{
                        tab.passarVez();
                    }
                    
                }
            }
            
            System.out.println("");
            tab.print();
            
            //Contador de partidas
            tab.setPartidas(tab.getPartidas() + 1);
            
            //Potuacao
            printarVencedor(tab);
            
            //Recomecar
            tab.limparTabuleiro();
            
        }
        
        //Printar Resultado
        printarResultado(tab);
        
        //Remover Save
        if(!tab.getNome().equals("")){
            removerTabuleiro(tab);
        }
        
    }
    
    //Imprime o vencedor da partida
    private static void printarVencedor(Tabuleiro tab){
        if(tab.checaVitoria()){
            
            tab.pontuar();
            if(tab.getProximo() == 'O'){
                System.out.println("\n->" + tab.getJ1() + " venceu a partida!<-\n");
            }else{
                System.out.println("\n->" + tab.getJ2() + " venceu a partida!<-\n");
            }
        }else{
            System.out.println("\n->Velha!<-\n");
        }
        
        System.out.println("-------------------");
        System.out.println("      " + tab.getP1() + " - " + tab.getP2());
        System.out.println("-------------------");
        
    }
    
    //Imprime o resultado da partida
    private static void printarResultado(Tabuleiro tab){
        
        if(tab.getP1() > tab.getP2()){
            vencedorAjustado(tab.getJ1());
            System.out.println("");
            
        }else if(tab.getP2() > tab.getP1()){
            vencedorAjustado(tab.getJ2());
            System.out.println("");
        }else{
            System.out.println("-------------");
            System.out.println("-> Empate! <-");
            System.out.println("-------------");
            System.out.println("");
        }
        
    }
     
    //Imprime o vencedor do jogo
    private static void vencedorAjustado(String jg){
        int i;
        String div = "";
        
        for(i = 0 ; i < jg.length()*2 + 32 ; i++){
            div = div + "-";
        }
        
        System.out.println(div);
        System.out.println("  -> " + jg + " venceu o jogo! <-");
        System.out.println(div);
        
    }
    
    private static void computadorJogar(Tabuleiro tab){
        int aux = tab.getOpponent();
        
        switch(aux){
            case 1:
                //Facil
                computadorFacil(tab);
                break;
            case 2:
                //Medio
                computadorMedio(tab);
                break;
            case 3:
                //Dificil
                computadorDificil(tab);
                break;
        }

    }
    
    private static void computadorFacil(Tabuleiro tab){
        SecureRandom jogada = new SecureRandom();
        boolean valido;
        
        //Jogar aleatoriamente
        do{
            valido = tab.jogar(jogada.nextInt(3), jogada.nextInt(3));
        }while(!valido);
        
    }
    
    private static void computadorMedio(Tabuleiro tabuleiro){ 
        boolean aux;
        
        //Checar se pode vencer
        aux = computadorVencer(tabuleiro);
        
        //Checar Se precisa Bloquear
        if(!aux){
            aux = computadorBloquear(tabuleiro);
        }
        
        //Jogada aleatoria
        if(!aux){
            computadorFacil(tabuleiro);
        }    
        
    }
    
    //Nao recomendo tentar entender
    private static void computadorDificil(Tabuleiro tab){
        char[][] matriz = tab.getTabuleiro();
        boolean venceu,bloqueou;
        char aux, aux2;
        int jogadas = tab.getJogadas();
        
        
        if(tab.getJ1().equals("Computador")){
            aux2 = 'X';
            aux = 'O';
        }else{
            aux2 = 'O';
            aux = 'X';
        }
        
        venceu = computadorVencer(tab);
        
        if(!venceu){
            //Ataque
            if(jogadas%2 == 0){
                
                switch (jogadas) {
                    //Turno 1
                    case 0:
                        tab.jogar(0, 0);
                        break;
                        
                    //Turno 3
                    case 2:
                        if(matriz[1][1] == aux){
                            tab.jogar(2,2);
                            
                        }else if(matriz[0][1] == aux || matriz[0][2] == aux){
                            tab.jogar(1, 0);
                            
                        }else if(matriz[1][0] == aux || matriz[2][0] == aux){
                            tab.jogar(0, 1);
                            
                        }else{
                            tab.jogar(0, 2);
                            
                        }   
                        
                        break;
                        
                    //Turno 5
                    case 4:
                        if(matriz[1][1] == aux){
                            bloqueou = computadorBloquear(tab);
                            
                            if(!bloqueou){
                                if(matriz[0][2] == 0){
                                    tab.jogar(0, 2);
                                }else{
                                    tab.jogar(2, 0);
                                }
                            }
                            
                        }else if(matriz[2][2] == aux){
                            tab.jogar(2,0);
                        }else{
                            tab.jogar(1,1);
                        }   
  
                        break;

                    //Resto
                    default:
                        computadorMedio(tab);
                        break;
                }
                
            //Defesa
            }else{
                
                switch (jogadas) {
                    //Turno 2
                    case 1:
                        if(matriz[1][1] == 0){
                            tab.jogar(1, 1);
                        }else{
                            tab.jogar(0, 0);
                        }  
                        
                        break;
                    //Turno 4
                    case 3:
                        if(matriz[0][2] == aux && matriz[2][0] == aux || matriz[0][0] == aux && matriz[2][2] == aux){
                            tab.jogar(0, 1);
                        }else if(matriz[2][2] == aux){
                            tab.jogar(0,2);
                        }else{
                            computadorMedio(tab);
                        }   
                        
                        break;
                    //Resto
                    default:
                        computadorMedio(tab);
                        break;
                }

            }
        }
    }
    
    //Verifica se possui alguma jogada vencedora
    private static boolean computadorVencer(Tabuleiro tabuleiro){
        //Matriz dos chars
        char[][] matriz = tabuleiro.getTabuleiro();
        char aux;
        int i,j;
        
        if(tabuleiro.getJ1().equals("Computador")){
            aux = 'X';
        }else{
            aux = 'O';
        }
        
        //Coluna
        for(j=0 ; j < 3 ; j++){

            if(matriz[1][j] == aux && matriz[1][j] == matriz[2][j] && matriz[0][j] == 0){
                tabuleiro.jogar(0, j);
                return true;
            }else if(matriz[0][j] == aux && matriz[0][j] == matriz[2][j] && matriz[1][j] == 0){
                tabuleiro.jogar(1, j);
                return true;
            }else if(matriz[0][j] == aux && matriz[0][j] == matriz[1][j] && matriz[2][j] == 0){
                tabuleiro.jogar(2, j);
                return true;
            }
            
        }
        
        //Linha
        for(i=0 ; i < 3 ; i++){
            
            if(matriz[i][1] == aux && matriz[i][1] == matriz[i][2] && matriz[i][0] == 0){
                tabuleiro.jogar(i, 0);
                return true;
            }else if(matriz[i][0] == aux && matriz[i][0] == matriz[i][2] && matriz[i][1] == 0){
                tabuleiro.jogar(i, 1);
                return true;
            }else if(matriz[i][0] == aux && matriz[i][0] == matriz[i][1] && matriz[i][2] == 0){
                tabuleiro.jogar(i, 2);
                return true;
            }
            
        }
        
        //Diagonal EsqDir 
        if(matriz[1][1] == aux && matriz[1][1] == matriz[2][2] && matriz[0][0] == 0){
            tabuleiro.jogar(0, 0);
            return true;
        }else if(matriz[0][0] == aux && matriz[0][0] == matriz[2][2] && matriz[1][1] == 0){            
            tabuleiro.jogar(1, 1);
            return true;
        }else if(matriz[0][0] == aux && matriz[0][0] == matriz[1][1] && matriz[2][2] == 0){
            tabuleiro.jogar(2, 2);
            return true;
        }
        
        //Diagonal DirEsq
        if(matriz[1][1] == aux && matriz[1][1] == matriz[2][0] && matriz[0][2] == 0){
            tabuleiro.jogar(0, 2);
            return true;
        }else if(matriz[0][2] == aux && matriz[0][2] == matriz[2][0] && matriz[1][1] == 0){            
            tabuleiro.jogar(1, 1);
            return true;
        }else if(matriz[0][2] == aux && matriz[0][2] == matriz[1][1] && matriz[2][0] == 0){
            tabuleiro.jogar(2, 0);
            return true;
        }
        
        //Nenhuma jogada vencedora
        return false;
        
    }
    
    //Verfica se precisa bloque o oponente de vencer
    private static boolean computadorBloquear(Tabuleiro tabuleiro){
        char[][] matriz = tabuleiro.getTabuleiro();
        char aux;
        int i,j;
        
        //Coluna
        for(j=0 ; j < 3 ; j++){

            if(matriz[1][j] != 0 && matriz[1][j] == matriz[2][j] && matriz[0][j] == 0){
                tabuleiro.jogar(0, j);
                return true;
            }else if(matriz[0][j] != 0 && matriz[0][j] == matriz[2][j] && matriz[1][j] == 0){
                tabuleiro.jogar(1, j);
                return true;
            }else if(matriz[0][j] != 0 && matriz[0][j] == matriz[1][j] && matriz[2][j] == 0){
                tabuleiro.jogar(2, j);
                return true;
            }
            
        }
        
        //Linha
        for(i=0 ; i < 3 ; i++){
            
            if(matriz[i][1] != 0 && matriz[i][1] == matriz[i][2] && matriz[i][0] == 0){
                tabuleiro.jogar(i, 0);
                return true;
            }else if(matriz[i][0] != 0 && matriz[i][0] == matriz[i][2] && matriz[i][1] == 0){
                tabuleiro.jogar(i, 1);
                return true;
            }else if(matriz[i][0] != 0 && matriz[i][0] == matriz[i][1] && matriz[i][2] == 0){
                tabuleiro.jogar(i, 2);
                return true;
            }
            
        }
        
        //Diagonal EsqDir 
        if(matriz[1][1] != 0 && matriz[1][1] == matriz[2][2] && matriz[0][0] == 0){
            tabuleiro.jogar(0, 0);
            return true;
        }else if(matriz[0][0] != 0 && matriz[0][0] == matriz[2][2] && matriz[1][1] == 0){            
            tabuleiro.jogar(1, 1);
            return true;
        }else if(matriz[0][0] != 0 && matriz[0][0] == matriz[1][1] && matriz[2][2] == 0){
            tabuleiro.jogar(2, 2);
            return true;
        }
        
        //Diagonal DirEsq
        if(matriz[1][1] != 0 && matriz[1][1] == matriz[2][0] && matriz[0][2] == 0){
            tabuleiro.jogar(0, 2);
            return true;
        }else if(matriz[0][2] != 0 && matriz[0][2] == matriz[2][0] && matriz[1][1] == 0){            
            tabuleiro.jogar(1, 1);
            return true;
        }else if(matriz[0][2] != 0 && matriz[0][2] == matriz[1][1] && matriz[2][0] == 0){
            tabuleiro.jogar(2, 0);
            return true;
        }
        
        //Nenhuma posicao a ser bloqueada
        return false;
        
    }
    
    //Continuar jogo salvo
    private static void continuarJogo() throws IOException, ClassNotFoundException{
        File arq = new File(SAVE);

        if(arq.exists()){
            ArrayList<Tabuleiro> saves = carregarSaves();
            
            //Existe jogo salvo
            if(saves.size() > 0){
                int i, resp2;
                Tabuleiro tab;

                //Printar jogos salvos
                System.out.println("Jogos salvos:");
                for(i = 0 ; i < saves.size() ; i++){
                    System.out.println((i+1) + "- " + saves.get(i).getNome());
                }
                System.out.println("0- Sair");
                
                //Escolher save  
                System.out.println("Escolha um jogo: ");
                resp2 = IN.nextInt();

                while(resp2 < 0 || resp2 > saves.size()){
                    System.out.println("*Posicao Invalida*");
                    System.out.println("Escolha um save: ");
                    resp2 = IN.nextInt();
                }
                
                if(resp2 != 0){
                    tab = saves.get(resp2-1);
                
                    //Resume jogo
                    if(tab.getOpponent() == 0){
                        jogarJ(tab);
                    }else{
                        jogarC(tab);
                    }
                    
                }
                
                return;
                
            }
        }
        
        System.out.println("\n*Nenhum Jogo Salvo Encontrado*\n");
        
    }
    
    //Carregar Jogo
    private static ArrayList<Tabuleiro> carregarSaves() throws IOException, ClassNotFoundException{
        FileInputStream fin = new FileInputStream(SAVE);
        ObjectInputStream oin = new ObjectInputStream(fin);
        
        ArrayList<Tabuleiro> saves = (ArrayList<Tabuleiro>) oin.readObject();
        
        oin.close();
        fin.close();
        
        return saves;

    }
    
    //Salver Jogo
    private static void salvarTabuleiro(Tabuleiro tab) throws IOException, ClassNotFoundException{
        File arq = new File(SAVE);
        ArrayList<Tabuleiro> saves;
        String nome;
        
        if(tab.getNome().equals("")){
            System.out.println("Nome do save: ");
            nome = IN.next();

            tab.setNome(nome);
        }
        
        if(!arq.exists()){
            arq.createNewFile();
            
            saves = new ArrayList();
            
            FileOutputStream fos = new FileOutputStream(arq);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(saves);

            oos.close();
            fos.close();
        }
        
        //Carregar Lista De Saves
        FileInputStream fin = new FileInputStream(SAVE);
        ObjectInputStream oin = new ObjectInputStream(fin);
        
        saves = (ArrayList<Tabuleiro>) oin.readObject(); 
        
        //Adiciona Novo Save
        if(saves.contains(tab)){
            saves.remove(tab);
        }
        
        saves.add(tab);
        
        //Salvar Lista De Saves
        FileOutputStream fos = new FileOutputStream(arq);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        
        oos.writeObject(saves);

        oos.close();
        fos.close();
        
    }
    
    private static void removerTabuleiro(Tabuleiro tab) throws IOException, ClassNotFoundException{
        File arq = new File(SAVE);
        
        if(arq.exists()){
            //Carregar Lista De Saves
            FileInputStream fin = new FileInputStream(SAVE);
            ObjectInputStream oin = new ObjectInputStream(fin);

            ArrayList<Tabuleiro> saves = (ArrayList<Tabuleiro>) oin.readObject(); 

            //Adiciona Novo Save
            saves.remove(tab);

            //Salvar Lista De Saves
            FileOutputStream fos = new FileOutputStream(arq);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(saves);

            oos.close();
            fos.close();
        }
        
    }
    
}