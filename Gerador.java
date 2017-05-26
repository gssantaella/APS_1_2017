/*
 * Gerador.java
 *
 * Gera coordenadas aleatorias dentro do retangulo especificado por
 * (X1, Y1), (X2, Y1), (X2, Y2) e (X1, Y2).
 * Esse retangulo corresponde a regiao aproximada da floresta
 * amazonica em coordenadas de latitude e longitude.
 *
 *
 */

import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Gerador {

    // Atributos
    private static final double X1 = -76.0;
    private static final double Y1 =   2.0;
    private static final double X2 = -56.0;
    private static final double Y2 =  -8.0;

    private static final double longLimit =  X1-X2;
    private static final double latLimit =  Y1-Y2;

    private int tamanho;
    private int tipo;


    // Construtores
    Gerador() {}

    // Metodos
    
    /*
     * Cria uma string concatenando duas coordenadas
     */
    private String geraCoord(Random rand) {
        DecimalFormat decimalFormat;
        String la, lo;

        double latitude = (100*rand.nextDouble() % latLimit) + Y2 ;
        double longitude = (-100*rand.nextDouble() % longLimit) + X2 ;

        if (latitude >= 0) {
            decimalFormat = new DecimalFormat("000.0000");
        }
        else {
            decimalFormat = new DecimalFormat("00.0000");
        }
        la = decimalFormat.format(latitude);
        if (longitude >= 0) {
            decimalFormat = new DecimalFormat("000.0000");
        }
        else {
            decimalFormat = new DecimalFormat("00.0000");
        }
        lo = decimalFormat.format(longitude);

        return la + lo;
    }

    private Imagem[] geraVetorAleatorio(int n) {
        Imagem[] v = new Imagem[n];
        Random r = new Random();

        for (int i = 0; i < n; i++) {
            v[i] = new Imagem(geraCoord(r));
        }
        return v;
    }

    private Imagem[] geraVetorInvertido(int n) {
        Imagem[] v = new Imagem[n];
        Random r = new Random();

        for (int i = 0; i < n; i++) {
            v[i] = new Imagem(geraCoord(r));
        }
        Arrays.sort(v, Collections.reverseOrder());
        return v;
    }

    private Imagem[] geraVetorSemiOrdenado(int n) {
        Imagem[] v = new Imagem[n];
        Random r = new Random();

        for (int i = 0; i < n; i++) {
            v[i] = new Imagem(geraCoord(r));
        }
        Arrays.sort(v);

        Imagem aux = new Imagem();
        //20% dos valores serao embaralhados
        for (int i = 0; i < n/5; i++) { 
            int j = r.nextInt(n);
            int k = r.nextInt(n);

            aux.setCoord(v[j].getCoord());
            v[j].setCoord(v[k].getCoord());
            v[k].setCoord(aux.getCoord());
        }

        return v;
    }

    private Imagem[] geraVetorRepetido(int n) {
        Imagem[] v = new Imagem[n];
        Random r = new Random();

        for (int i = 0; i < n; i++) {
            v[i] = new Imagem(geraCoord(r));
            if (i+4 < n) {
                v[++i] = new Imagem(v[i-1].getCoord());
                v[++i] = new Imagem(v[i-1].getCoord());
                v[++i] = new Imagem(v[i-1].getCoord());
                v[++i] = new Imagem(v[i-1].getCoord());
            }
        }
        return v;
    }

    public Imagem[] geraVetorDeCoord(char tipo, int n) {
        switch(tipo) {
            case '1':
                return geraVetorAleatorio(n);
            case '2':
                return geraVetorInvertido(n);
            case '3':
                return geraVetorSemiOrdenado(n);
            default:
                return geraVetorRepetido(n);
        }
    }


} // fim Gerador