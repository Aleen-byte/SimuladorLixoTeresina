package src.main.java.com.simulador.structures;

public class Fila<T> {
    private static class No<T> {
        T dado;
        No<T> proximo;

        No(T dado) {
            this.dado = dado;
            this.proximo = null;
        }
    }

    private No<T> inicio;
    private No<T> fim;
    private int tamanho;

    public Fila() {
        inicio = null;
        fim = null;
        tamanho = 0;
    }

    public void enfileirar(T elemento) {
        No<T> novoNo = new No<>(elemento);
        if (estaVazia()) {
            inicio = novoNo;
        } else {
            fim.proximo = novoNo;
        }
        fim = novoNo;
        tamanho++;
    }

    public T desenfileirar() {
        if (estaVazia()) {
            throw new IllegalStateException("Fila vazia");
        }
        T dado = inicio.dado;
        inicio = inicio.proximo;
        if (inicio == null) {
            fim = null;
        }
        tamanho--;
        return dado;
    }

    public T primeiro() {
        if (estaVazia()) {
            throw new IllegalStateException("Fila vazia");
        }
        return inicio.dado;
    }

    public boolean estaVazia() {
        return inicio == null;
    }

    public int tamanho() {
        return tamanho;
    }
}
