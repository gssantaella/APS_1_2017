/*
 *
 * Classe de interacao com o usuario
 *
 */

import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Arrays;

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
        "\n--- Fim ---\n"
    };
    private Scanner scan = new Scanner(System.in);


    // MAIN
    public static void main(String[] args) {

        Main m = new Main();

        for (boolean sair = false; sair != true; ) {
            char c = '0';

            while (!m.existeEm(m.opcao, c)) {
                m.geraMenu();
                c = m.validaEntrada(c);
            }
            //System.out.println("> "+c);

            switch(c) {
                case '1':
                    m.mostraFrase(8);
                    break;
                case '2':
                case '3':
                case '4':
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
    public void geraMenu() {
        for (int i = 0; i <= 6; i++)
            mostraFrase(i);
    }

    //TODO - unificar ou separar ambos valida entrada
    public char validaEntrada(char c) {
        //char c = '0';
        try {
            c = scan.next("[1-5sSnNqQ]").charAt(0);
        } catch (Exception e) {
            mostraFrase(7);
            scan.nextLine();
        }
        //System.out.println("> "+c);
        return c;
    }
    public int validaEntrada(int c) {
        //int c = 0;
        try {
            c = scan.nextInt();
        } catch (Exception e) {
            System.out.println("Opcao invalida");
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

} //Fim classe Main