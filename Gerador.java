/*
 * Gerador.java
 *
 * Gera coordenadas aleatorias dentro do retangulo especificado por
 * (X1, Y1), (X2, Y1), (X2, Y2) e (X1, Y2).
 * Esse retangulo corresponde a regiao aproximada da floresta
 * amazonica em coordenadas de latitude e longitude.
 *
 * Compilar:
 * $ javac Gerador.java
 *
 * Rodar:
 * $ java Gerador
 * $ java Gerador [numero de coordenadas] [tipo]
 *
 * Exemplo de uso
 * $ java Gerador 5000 2
 * 
 * Usa o numero 15 como semente para o gerador de numeros aleatorios
 * e gera 5000 coordenadas. TODO
 * 
 * Um arquivo .csv sera gerado com as coordenadas
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

    Gerador(int tamanho, int tipo) {
        this.tamanho = tamanho;
        this.tipo = tipo;
    }

    // Getters & Setters
    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    public int getTamanho() {
        return this.tamanho;
    }
    public int getTipo() {
        return this.tipo;
    }

/*
    public static void main(String[] args) {

        Random rand;
        int n = 100;
        if (args.length == 1) {
            // defender de entradas invalidas TODO
            rand = new Random(Integer.parseInt(args[0]));
        }
        else if (args.length == 2) {
            rand = new Random(Integer.parseInt(args[0]));
            n = Integer.parseInt(args[1]);
        }
        else {
            rand = new Random();
        }

        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
            fw = new FileWriter("coordenadas.csv");
            bw = new BufferedWriter(fw);

            bw.write("LATITUDE, LONGITUDE,\n");

            for (int i = 0; i < n; i++) {
                bw.write(geraLat(rand) +
                 ", " + geraLong(rand) + ",\n");
            }

            System.out.println("Arquivo escrito");
        } 
        catch (IOException e) {
            e.printStackTrace();
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
            }
        }


    }*/ // fim main

    //TEST ONLY - Essa clase não tera main
    public static void main(String[] args) {
        Gerador g = new Gerador();

        Imagem[] v = g.geraVetorSemiOrdenado(19);
        for (Imagem i : v) {
            System.out.println(i);
        }

    }

    // Metodos
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
        for (int i = 0; i < n/5; i++) { //20% dos valores estao embaralhados
            int j = r.nextInt(n);
            int k = r.nextInt(n);
            System.out.println(j +" "+ k);
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