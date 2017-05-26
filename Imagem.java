/*
 * Imagem.java
 *
 * Uma imagem é definida por uma latitude e longitude.
 * Para facilitar a ordenacao, uma string esta sendo usada,
 * nela estao concatenados os valores da latitude e longitude.
 * 
 * A string sera no formato: '-00.0000-00.0000'
 * Sendo o sinal '-' trocado por '0' quando o numero for positivo.
 *
 */

class Imagem implements Comparable<Imagem> {
    
    // Atributos
    private String coordenada;

    // Construtores
    Imagem() { }

    Imagem(String coordenada) {
        this.coordenada = coordenada;
    }


    // Getters e Setters
    public String getCoord() {
        return this.coordenada;
    }

    public void setCoord(String coordenada) {
        this.coordenada = coordenada;
    }


    // Metodos
    public String toString() {
        return "(" + this.coordenada.substring(0, 8) 
            + ", " + this.coordenada.substring(8,16) + ")";
    }

    // Explicar TODO
    public int compareTo(Imagem outraImagem) {
        if (this.coordenada.startsWith("-")
            && outraImagem.coordenada.startsWith("-")) {
            return -1*(this.coordenada.compareTo(outraImagem.coordenada));
        }
        return this.coordenada.compareTo(outraImagem.coordenada);
    }

} //Fim Imagem