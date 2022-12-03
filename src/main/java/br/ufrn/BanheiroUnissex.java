package br.ufrn;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BanheiroUnissex {
    private static BanheiroUnissex banheiroUnissex;
    private static int capacidade;
    private final ArrayList<Pessoa> pessoas;
    private final Lock lock = new ReentrantLock();
    private Sexo sexoOcupado;

    public BanheiroUnissex(int capacidade) {
        BanheiroUnissex.capacidade = capacidade;
        this.sexoOcupado = Sexo.NENHUM;
        this.pessoas = new ArrayList<>();
    }

    public static BanheiroUnissex getSingleton(int capacidade) {
        if(banheiroUnissex == null)
            banheiroUnissex = new BanheiroUnissex(capacidade);
        return banheiroUnissex;
    }

    public void addUser(Pessoa pessoa) {
        this.lock.lock();
        try {
            if (pessoas.isEmpty())
                sexoOcupado = pessoa.getSexo();
            if (this.pessoas.size() < capacidade  && !this.pessoas.contains(pessoa) && getSexoOcupado().equals(pessoa.getSexo())) {
                pessoas.add(pessoa);
                System.out.println(" Entrou --> " + pessoa.getNome());
            }
        } finally {
            System.out.println(this);
            this.lock.unlock();
        }
    }

    public void removeUser(Pessoa pessoa) {
        this.lock.lock();
        try {
            if (!pessoas.isEmpty()) {
                if (pessoas.remove(pessoa))
                    System.out.println(" Saiu <-- " + pessoa.getNome());
                if (pessoas.isEmpty())
                    this.sexoOcupado = Sexo.NENHUM;
            }
        } finally {
            System.out.println(this);
            this.lock.unlock();
        }
    }

    public int numeroPessoasAguardando() {
        int contador = 0;
        for (Pessoa p: this.pessoas) {
            if(p.isNeedBathroom())
                contador++;
        }
        return contador;
    }

    public Sexo getSexoOcupado() { return sexoOcupado; }

    public boolean isInBanheiro(Pessoa pessoa) {
        return this.pessoas.contains(pessoa);
    }

    public boolean isFull() {
        return capacidade == this.pessoas.size();
    }

    @Override
    public String toString() {
        return "\n--------| BANHEIRO UNISSEX |---------\n" +
                 "|     > PESSOAS UTILIZANDO: " + this.pessoas.size() + "\n" +
                 "|     > VAGAS DISPONIVEIS: " + (capacidade - this.pessoas.size()) + "\n" +
                 "|     > PESSOAS AGUARDANDO: " + numeroPessoasAguardando() + "\n" +
                 "|     > CAPACIDADE MAXIMA: " + capacidade + "\n" +
                 "|     > SEXO OCUPADO: " + this.sexoOcupado + "\n" +
                 "_____________________________________\n";
    }
}
