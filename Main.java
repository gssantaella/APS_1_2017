/*
 * Main.java
 *
 * Classe de interacao com o usuario
 *
 */

import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

class Main {
    
    // criar vetor com as escolhas possiveis
    // fica mais facil de ver se a opcao existe
    private char[] opcao = {'1', '2', '3', '4', '5'};
    private String[] frases = {
        "\n\n--- Ordenar Imagens de Satelite ---\n",
        "\n\t1) Gerar arquivo de imagens",
        "\n\t2) Ler arquivo e ordenar",
        "\n\t3) Gerar imagens internamente e ordenar",
        "\n\t4) Testes automaticos",
        "\n\t5) Sair do programa",
        "\nEscolha: ",
        "\n!!! Opcao invalida !!!",
        "\nQuantidade de imagens a serem criadas: ",
        "\nTipo de vetor a ser criado: ",
        "\n\t1) Aleatorio",
        "\n\t2) Invertido",
        "\n\t3) Semi ordenado",
        "\n\t4) Repetido",
        "\n\t5) Voltar para menu inicial",
        "\nNome do arquivo: ",
        "\nArquivo criado",
        "\n!!! Arquivo nao existe !!!",
        "\nDigite o nome do arquivo ou 'Q' para voltar ao menu inicial",
        "\nArquivo encontrado",
        "\nRealizando ordenacao",
        "\nHeapsort",
        "\nMergesort",
        "\nQuicksort",
        "\n--- Fim ---\n",
        "\nSalvar arquivo\n",
    };
    private Scanner scan = new Scanner(System.in);
    private Gerador g = new Gerador();
    private Ordenador o = new Ordenador();
    private Imagem[] v;
    

    // MAIN
    public static void main(String[] args) {

        Main m = new Main();     

        for (boolean sair = false; sair != true; ) {
            char c = '0';

            while (!m.existeEm(m.opcao, c)) {
                m.geraMenu(0,6);
                c = m.validaEntrada(c);
            }

            switch(c) {
                case '1':
                    m.opcao1();
                    break;
                case '2':
                    m.opcao2();
                    break;
                case '3':
                    m.opcao3();
                    break;
                case '4':
                    m.opcao4();
                    break;
                case '5':
                    sair = true;
                    m.mostraFrase(24);
                    break;
                default:
                    break;
            }

        }

    } //fim main


    // METODOS

    public void mostraFrase(int i) {
        System.out.print(frases[i]);
    }

    public void geraMenu(int j, int k) {
        for (int i = j; i <= k; i++)
            mostraFrase(i);
    }

    public char validaEntrada(char c) {

        try {
            c = scan.next("[1-5sSnNqQ]").charAt(0);
        } catch (Exception e) {
            mostraFrase(7);
            scan.nextLine();
        }

        return c;
    }

    public int validaEntrada(int c) {

        try {
            c = scan.nextInt();
        } catch (Exception e) {
            mostraFrase(7);
            scan.nextLine();
        }

        return c;
    }

    public boolean existeEm(char[] v, char c) {
        for (char i : v) {
            if (i == c)
                return true;
        }
        return false;
    }

    public void escreveArquivo(String fn, Imagem[] v) {
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
            fw = new FileWriter(fn);
            bw = new BufferedWriter(fw);

            for (Imagem i : v) {
                bw.write(i.getCoord() + "\n");
            }

            mostraFrase(16);
        } 
        catch (IOException e) {
            e.printStackTrace();
            mostraFrase(17);
        } 
        finally {
            try {
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
                mostraFrase(17);
            }
        }
    }

    public List<String> leArquivo(String fn) {
        BufferedReader br = null;
        FileReader fr = null;
        List<String> s = new ArrayList<>();

        try {
            fr = new FileReader(fn);
            br = new BufferedReader(fr);
        
            String linha;

            while((linha = br.readLine()) != null) {
                s.add(linha);
            }
        }

        catch (IOException ex) {
            ex.printStackTrace();
            mostraFrase(17);
        }

        finally {
            try {
                if (br != null)
                    br.close();

                if (fr != null)
                    fr.close();
            } 
            catch (IOException ex) {
                ex.printStackTrace();
                mostraFrase(17);
            }

        }
        return s;
    }

    public void montaVetor(String fn) {

        List<String> s = leArquivo(fn);

        v = new Imagem[s.size()];
            int i = 0;
            for (String aux : s) {
                v[i++] = new Imagem(aux);
            }
    }

    public void opcao1() {
        int num = 0;
        char tipo = '0';

        //Pede numero de imagens
        while(num <= 0) {
            mostraFrase(8);
            num = validaEntrada(num);
        }

        //Pede tipo de vetor
        while (!existeEm(opcao, tipo)) {
            geraMenu(9,14);
            mostraFrase(6);
            tipo = validaEntrada(tipo);
        }
        if (tipo == '5') return;

        //gera vetor de Imagem
        v = g.geraVetorDeCoord(tipo, num);

        //Pede nome de arquivo
        mostraFrase(25);
        mostraFrase(15);
        scan.nextLine();
        String fn = scan.nextLine(); 

        escreveArquivo(fn, v);

        //Volta pro menu inicial

    } //Fim opcao1

    public void opcao2() {
        long tempo;

        //Pede nome de arquivo
        mostraFrase(15);
        scan.nextLine();
        String fn = scan.nextLine(); 

        //Confere se arquivo existe, le, monta o vetor
        // e faz ordenacao
        montaVetor(fn);
        tempo = o.heapsort(v);
        System.out.println("\nHeapsort:  " + tempo/1000000000.0 + "s");
        escreveArquivo("a.out", v);

        montaVetor(fn);
        tempo = o.mergesort(v);
        System.out.println("\nMergesort: " + tempo/1000000000.0 + "s");
        escreveArquivo("b.out", v);

        montaVetor(fn);
        tempo = o.quicksort(v);
        System.out.println("\nQuicksort: " + tempo/1000000000.0 + "s");
        escreveArquivo("c.out", v);

        //Volta pro menu inicial

    } //Fim opcao2

    public void opcao3() {
        int num = 0;
        char tipo = '0';
        long tempo;

        //Pede numero de imagens
        while(num <= 0) {
            mostraFrase(8);
            num = validaEntrada(num);
        }

        //Pede tipo de vetor
        while (!existeEm(opcao, tipo)) {
            geraMenu(9,14);
            mostraFrase(6);
            tipo = validaEntrada(tipo);
        }
        if (tipo == '5') return;

        //gera vetor de Imagem
        v = g.geraVetorDeCoord(tipo, num);
        Imagem[] aux;
        
        //Copia vetor e faz sort
        aux = new Imagem[v.length];
        for (int i = 0; i < v.length; i++) {
            aux[i] = new Imagem(v[i].getCoord());
        }
        tempo = o.heapsort(aux);
        System.out.println("\nHeapsort:  " + tempo/1000000000.0 + "s");
        escreveArquivo("a2.out", aux);

        aux = new Imagem[v.length];
        for (int i = 0; i < v.length; i++) {
            aux[i] = new Imagem(v[i].getCoord());
        }
        tempo = o.mergesort(aux);
        System.out.println("\nMergesort: " + tempo/1000000000.0 + "s");
        escreveArquivo("b2.out", aux);

        aux = new Imagem[v.length];
        for (int i = 0; i < v.length; i++) {
            aux[i] = new Imagem(v[i].getCoord());
        }
        tempo = o.quicksort(aux);
        System.out.println("\nQuicksort: " + tempo/1000000000.0 + "s");
        escreveArquivo("c2.out", aux);

        //Volta pro menu inicial

    } //Fim opcao3

    public void opcao4() {

        int n = 100;

        System.out.println("\n\nSerao criados " 
            + n + " vetores de cada tamanho e de cada tipo "
            + "para encontrar o tempo medio de cada algoritmo");

        //Testes com Heapsort
        testeHeapsort(n);

        //Testes com Mergesort
        testeMergesort(n);

        //Testes com Quicksort
        testeQuicksort(n);
    } //Fim opcao4

    public void testeHeapsort(int n) {

        int tam = 10000;
        
        System.out.println("\n-- HEAPSORT --");
        System.out.println("\nVetores aleatorios");
        System.out.println("| Tamanho | Tempo medio   |");
        long tempo = 0;
        for (int k = 100; k <= tam; k *= 10) {
            for (int i = 0; i < n; i++) {
                v = g.geraVetorDeCoord('1', k);
                tempo += o.heapsort(v);
            }
            System.out.printf("| %7d | %,.9f s |\n", k, (tempo/n)/1000000000.0);
        }
        System.out.println("\nVetores invertidos");
        System.out.println("| Tamanho | Tempo medio   |");
        tempo = 0;
        for (int k = 100; k <= tam; k *= 10) {
            for (int i = 0; i < n; i++) {
                v = g.geraVetorDeCoord('2', k);
                tempo += o.heapsort(v);
            }
            System.out.printf("| %7d | %,.9f s |\n", k, (tempo/n)/1000000000.0);
        }
        System.out.println("\nVetores semiordenados");
        System.out.println("| Tamanho | Tempo medio   |");
        tempo = 0;
        for (int k = 100; k <= tam; k *= 10) {
            for (int i = 0; i < n; i++) {
                v = g.geraVetorDeCoord('3', k);
                tempo += o.heapsort(v);
            }
            System.out.printf("| %7d | %,.9f s |\n", k, (tempo/n)/1000000000.0);
        }
        System.out.println("\nVetores repetidos");
        System.out.println("| Tamanho | Tempo medio   |");
        tempo = 0;
        for (int k = 100; k <= tam; k *= 10) {
            for (int i = 0; i < n; i++) {
                v = g.geraVetorDeCoord('4', k);
                tempo += o.heapsort(v);
            }
            System.out.printf("| %7d | %,.9f s |\n", k, (tempo/n)/1000000000.0);
        }

    } // testeHeapsort

    public void testeMergesort(int n) {

        int tam = 10000;
        
        System.out.println("\n-- MERGESORT --");
        System.out.println("\nVetores aleatorios");
        System.out.println("| Tamanho | Tempo medio   |");
        long tempo = 0;
        for (int k = 100; k <= tam; k *= 10) {
            for (int i = 0; i < n; i++) {
                v = g.geraVetorDeCoord('1', k);
                tempo += o.mergesort(v);
            }
            System.out.printf("| %7d | %,.9f s |\n", k, (tempo/n)/1000000000.0);
        }
        System.out.println("\nVetores invertidos");
        System.out.println("| Tamanho | Tempo medio   |");
        tempo = 0;
        for (int k = 100; k <= tam; k *= 10) {
            for (int i = 0; i < n; i++) {
                v = g.geraVetorDeCoord('2', k);
                tempo += o.mergesort(v);
            }
            System.out.printf("| %7d | %,.9f s |\n", k, (tempo/n)/1000000000.0);
        }
        System.out.println("\nVetores semiordenados");
        System.out.println("| Tamanho | Tempo medio   |");
        tempo = 0;
        for (int k = 100; k <= tam; k *= 10) {
            for (int i = 0; i < n; i++) {
                v = g.geraVetorDeCoord('3', k);
                tempo += o.mergesort(v);
            }
            System.out.printf("| %7d | %,.9f s |\n", k, (tempo/n)/1000000000.0);
        }
        System.out.println("\nVetores repetidos");
        System.out.println("| Tamanho | Tempo medio   |");
        tempo = 0;
        for (int k = 100; k <= tam; k *= 10) {
            for (int i = 0; i < n; i++) {
                v = g.geraVetorDeCoord('4', k);
                tempo += o.mergesort(v);
            }
            System.out.printf("| %7d | %,.9f s |\n", k, (tempo/n)/1000000000.0);
        }
    } // fim testeMergesort

    public void testeQuicksort(int n) {

        int tam = 10000;
        
        System.out.println("\n-- QUICKSORT --");
        System.out.println("\nVetores aleatorios");
        System.out.println("| Tamanho | Tempo medio   |");
        long tempo = 0;
        for (int k = 100; k <= tam; k *= 10) {
            for (int i = 0; i < n; i++) {
                v = g.geraVetorDeCoord('1', k);
                tempo += o.quicksort(v);
            }
            System.out.printf("| %7d | %,.9f s |\n", k, (tempo/n)/1000000000.0);
        }
        System.out.println("\nVetores invertidos");
        System.out.println("| Tamanho | Tempo medio   |");
        tempo = 0;
        for (int k = 100; k <= tam; k *= 10) { //Quicksort e' n^2
            for (int i = 0; i < n; i++) {
                v = g.geraVetorDeCoord('2', k);
                tempo += o.quicksort(v);
            }
            System.out.printf("| %7d | %,.9f s |\n", k, (tempo/n)/1000000000.0);
        }
        System.out.println("\nVetores semiordenados");
        System.out.println("| Tamanho | Tempo medio   |");
        tempo = 0;
        for (int k = 100; k <= tam; k *= 10) {
            for (int i = 0; i < n; i++) {
                v = g.geraVetorDeCoord('3', k);
                tempo += o.quicksort(v);
            }
            System.out.printf("| %7d | %,.9f s |\n", k, (tempo/n)/1000000000.0);
        }
        System.out.println("\nVetores repetidos");
        System.out.println("| Tamanho | Tempo medio   |");
        tempo = 0;
        for (int k = 100; k <= tam; k *= 10) {
            for (int i = 0; i < n; i++) {
                v = g.geraVetorDeCoord('4', k);
                tempo += o.quicksort(v);
            }
            System.out.printf("| %7d | %,.9f s |\n", k, (tempo/n)/1000000000.0);
        }
    } //fim testeQuicksort

} //Fim classe Main