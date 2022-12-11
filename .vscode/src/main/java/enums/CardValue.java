package main.java.enums;



public enum CardValue {
    ZERO,
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    HEIGHT,
    NINE,
    INVERSE,
    INTERDIT,
    PLUSQAUTRE,
    PLUSDEUX,
    JOKER;

    private final String valeur;

    CardValue(String valeur){
        this.valeur=valeur;
    }

    public String getValeur(){
        return valeur;
    }




}