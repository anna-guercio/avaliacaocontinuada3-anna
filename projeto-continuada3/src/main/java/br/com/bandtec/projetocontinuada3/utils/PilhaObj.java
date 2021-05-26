package br.com.bandtec.projetocontinuada3.utils;

public class PilhaObj<T> {

    // Atributos
    protected int topo;
    protected T[] pilha;

    public PilhaObj(int capacidade) {
        pilha = (T[]) new Object[capacidade];
        topo = -1;
    }

    // Métodos
    public Boolean isEmpty() {
        return topo == -1;
    }

    public Boolean isFull() {
        return (this.pilha.length - 1) == topo;
    }

    public void push(T info) {
        if (!isFull()) {
            pilha[++topo] = info;
        }
    }

    public T pop() {
        if (!isEmpty()) {
            return this.pilha[topo--];
        }
        return null;
    }

    public T peek() {
        if (!isEmpty()) {
            return this.pilha[topo];
        }
        return null;
    }

    public int length() {
        return this.pilha.length;
    }

    public T get(int indice) {
        if (!isEmpty()) {
            return this.pilha[indice];
        }
        return null;
    }

    public void exibe() {
        if (!isEmpty()) {
            for (int i = topo; i >= 0; i--) {
                System.out.println(pilha[i]);
            }
        } else {
            System.out.println("Pilha vazia");
        }
    }
}
