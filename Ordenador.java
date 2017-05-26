/*
 * Ordenador.java
 *
 * Classe que contem os algoritmos de ordenação
 * Decebe vetor de imagem desordenado e o ordena de forma crescente
 *
 */

class Ordenador {

    Ordenador () { }

//-----------------------------------------------------
// HEAPSORT
//-----------------------------------------------------
    // usando max heap

    public long heapsort(Imagem[] v) {

        long startTime = System.nanoTime();
        heap(v);
        long endTime = System.nanoTime();

        long duration = (endTime - startTime);

        return duration;
    }

    private void heap(Imagem[] v) {

        int n = v.length;

        // Constroi heap (rearranja o array)
        for (int i = n/2 - 1; i >= 0; i--) {
            fazHeap(v, n, i);
        }

        // Extrai elementos da heap de um em um
        for (int i = n-1; i >= 0; i--) {
            Imagem aux = v[0];
            v[0] = v[i];
            v[i] = aux; //talvez problema aqui &

        // faz max heap na heap reduzida
            fazHeap(v, i, 0);
        }
        
    }
 
    private void fazHeap(Imagem[] v, int n, int i) {
        int maior = i;
        int e = 2*i + 1;
        int d = 2*i + 2;

        // Se filha esquerda e' maior que raiz
        if (e < n && v[e].compareTo(v[maior]) >= 1 ) {
            maior = e;
        }
        // Se filha direita e' maior que maior ate' agora
        if (d < n && v[d].compareTo(v[maior]) >= 1) {
            maior = d;
        }
        // se maior nao e' raiz
        if (maior != i) {
            Imagem troca = v[i];
            v[i] = v[maior];
            v[maior] = troca; // pode dar problema &
            fazHeap(v, n, maior);
        }
    }

//-----------------------------------------------------
// MERGESORT
//-----------------------------------------------------

    public long mergesort(Imagem[] v) {

        long startTime = System.nanoTime();
        ordena(v, 0, v.length-1);
        long endTime = System.nanoTime();

        long duration = (endTime - startTime);
        
        return duration;
    }

    private void merge(Imagem[] v, int e, int m, int d)
    {

        // Encontra tamanho dos subvetores a serem fundidos
        int n1 = m - e + 1;
        int n2 = d - m;
 
        // Cria arrays temporarios
        Imagem E[] = new Imagem [n1];
        Imagem D[] = new Imagem [n2];
 
        for (int i = 0; i < n1; i++) {
            E[i] = v[e + i];
        }
        for (int j = 0; j < n2; j++) {
            D[j] = v[m + 1 + j];
        }
 
 
        //Fundir arrays temporarios
 
        int i = 0, j = 0;
 
        int k = e;
        while (i < n1 && j < n2)
        {
            if (E[i].compareTo(D[j]) <= 0) 
            {
                v[k] = E[i];
                i++;
            }
            else
            {
                v[k] = D[j];
                j++;
            }
            k++;
        }
 
        // Copiar elementos de E[] que estiverem sobrando, se necessario
        while (i < n1)
        {
            v[k] = E[i];
            i++;
            k++;
        }
 
        // Copiar elementos de D[] que estiverem sobrando, se necessario
        while (j < n2)
        {
            v[k] = D[j];
            j++;
            k++;
        }
    }
 
    // Main function that sorts v[l..r] using
    // merge()
    private void ordena(Imagem[] v, int e, int d)
    {
        if (e < d)
        {
            // Find the middle point
            int m = (e+d)/2;
 
            // Sort first and second halves
            ordena(v, e, m);
            ordena(v , m+1, d);
 
            // Merge the sorted halves
            merge(v, e, m, d);
        }
    }

//-----------------------------------------------------
// QUICKSORT
//-----------------------------------------------------
    public long quicksort(Imagem[] v) {

        long startTime = System.nanoTime();
        quickSort(v, 0, v.length-1);
        long endTime = System.nanoTime();

        long duration = (endTime - startTime);

        return duration;
    }

    private void troca(Imagem[] v,int i,int j)
    {
        Imagem t = v[i];
        v[i] = v[j];
        v[j] = t;
    }
 
    private int partition (Imagem[] v, int l, int h)
    {
        Imagem x = v[h];
        int i = (l - 1);
 
        for (int j = l; j <= h- 1; j++)
        {
            if (v[j].compareTo(x) <= 0)
            {
                i++;
                // troca v[i] e v[j]
                troca(v,i,j);
            }
        }
        // troca v[i+1] e v[h]
        troca(v,i+1,h);
        return (i + 1);
    }
 
    // Quicksort iterativo, o recursivo tem um overhead 
    // grande demais quando o vetor e' invertido
    private void quickSort(Imagem[] v, int l, int h)
    {
        // cria pilha auxiliar
        int stack[] = new int[h-l+1];
 
        // inicializa o topo da pilha
        int top = -1;
 
        stack[++top] = l;
        stack[++top] = h;
 
        // retira elementos ate' pilha estar vazia
        while (top >= 0)
        {
            // pop h e l
            h = stack[top--];
            l = stack[top--];
 
            // coloca pivo na posicao
            int p = partition(v, l, h);
 
            if ( p-1 > l )
            {
                stack[ ++top ] = l;
                stack[ ++top ] = p - 1;
            }

            if ( p+1 < h )
            {
                stack[ ++top ] = p + 1;
                stack[ ++top ] = h;
            }
       }
    }
}