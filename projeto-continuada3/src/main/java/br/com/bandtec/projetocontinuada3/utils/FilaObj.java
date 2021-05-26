package br.com.bandtec.projetocontinuada3.utils;

// Representa uma fila de objetos de tipo genérico
public class FilaObj<T> {

    private int tamanho;    // tamanho da fila
    private T[] fila;    // vetor que representa a fila

    // construtor - recebe a capacidade da fila (tamanho total do vetor)
    public FilaObj(int capacidade) {
        tamanho = 0;                          // inicializa tamanho com zero
        fila = (T[]) new Object[capacidade];    // cria o vetor que representa a fila
    }

    // Método isEmpty() - retorna true se a fila está vazia e false caso contrário
    public boolean isEmpty() {
        return tamanho == 0;
    }

    // Método isFull() - retorna true se a fila está cheia e false caso contrário
    public boolean isFull() {
        return tamanho == fila.length;
    }

    // Método insert - recebe uma info para ser inserida na fila
    public void insert(T info) {
        if (isFull()) {                            // Se fila está cheia
            System.out.println("Fila cheia");    // exibe mensagem
        }
        else {
            // senão, insere info na fila, índice tamanho, e incrementa tamanho
            fila[tamanho++] = info;
        }
    }

    // Método peek - retorna o primeiro da fila, sem removê-lo
    public T peek() {
        return fila[0];
    }

    // Método poll - retorna o primeiro da fila, removendo-o da fila
    public T poll() {
        T primeiro = peek();     // Salva o primeiro da fila numa variável

        if (!isEmpty()) {   // Se a fila não estiver vazia

            // Faz a "fila andar", deslocando todos os elementos para a esquerda
            for (int i = 0; i < tamanho - 1; i++) {
                fila[i] = fila[i + 1];
            }

            // "Anula" o último elemento da fila
            fila[tamanho - 1] = null;

            // Decrementa tamanho
            tamanho--;
        }

        // Retorna elemento que era o primeiro da fila
        return primeiro;
    }

    // Exibe os elementos da fila
    public void exibe() {
        if (isEmpty()) {
            System.out.println("Fila vazia");
        } else {
            for (int i = 0; i < tamanho; i++) {
                System.out.println(fila[i]);
            }
        }
    }

}
