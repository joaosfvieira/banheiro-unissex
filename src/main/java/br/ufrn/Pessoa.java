package br.ufrn;

public class Pessoa implements Runnable {

    private String nome;
    private Sexo sexo;
    private final BanheiroUnissex banheiroUnissex;
    private boolean canLeave;
    private boolean needBathroom;

    public Pessoa(String nome, Sexo sexo, BanheiroUnissex banheiroUnissex) {
        this.nome = nome;
        this.sexo = sexo;
        this.banheiroUnissex = banheiroUnissex;
        this.canLeave = false;
        this.needBathroom = true;
    }

    @Override
    public void run() {
        while (this.needBathroom) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if ((this.banheiroUnissex.getSexoOcupado().equals(this.getSexo()) || this.banheiroUnissex.getSexoOcupado().equals(Sexo.NENHUM)) && !this.banheiroUnissex.isFull() && !this.banheiroUnissex.isInBanheiro(this))
                this.usarBanheiro();
            if (this.canLeave)
                this.sairBanheiro();
        }
    }

    public void usarBanheiro() {
        this.banheiroUnissex.addUser(this);
        if (this.banheiroUnissex.isInBanheiro(this)) {
            try {
                Thread.sleep((int) ((Math.random() * 10000 - 5000)) + 5000);
                this.canLeave = true;
                this.needBathroom = false;
            } catch (InterruptedException ex) {
                System.out.println("First catch useBathroom - Interrupted Exception");
            }
        }
    }

    public void sairBanheiro() {
        this.banheiroUnissex.removeUser(this);
        this.canLeave = false;
        this.needBathroom = false;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public BanheiroUnissex getBanheiroUnissex() {
        return banheiroUnissex;
    }

    public boolean isCanLeave() {
        return canLeave;
    }

    public void setCanLeave(boolean canLeave) {
        this.canLeave = canLeave;
    }

    public boolean isNeedBathroom() {
        return needBathroom;
    }

    public void setNeedBathroom(boolean needBathroom) {
        this.needBathroom = needBathroom;
    }
}
