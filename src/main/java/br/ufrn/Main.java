package br.ufrn;

public class Main {

    public static void main(String[] args) {

        int capacidadeBanheiro = Integer.parseInt(args[0]);
        BanheiroUnissex banheiroUnissex = BanheiroUnissex.getSingleton(capacidadeBanheiro);

        int i = 0;
        while(i < 5) {
            Runnable homem = new Pessoa(("Homem [" + i + "]"), Sexo.MASCULINO, banheiroUnissex);
            Thread threadH = new Thread(homem);
            threadH.start();

            Runnable mulher = new Pessoa(("Mulher [" + i + "]"), Sexo.FEMININO, banheiroUnissex);
            Thread threadM = new Thread(mulher);
            threadM.start();

            i++;
        }
    }
}
