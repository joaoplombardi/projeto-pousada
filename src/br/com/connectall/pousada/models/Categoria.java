package br.com.connectall.pousada.models;

public enum Categoria {
    VIP("VIP"), APARTAMENTO("APARTAMENTO");
    private String value;

    Categoria(String value){
        this.value = value;
    }


    @Override
    public String toString() {
        return value;
    }
}
