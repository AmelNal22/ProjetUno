package main.java.enums;


public enum CardColor {
    ROUGE,
    VERT,
    JAUNE,
    BLEU;

    private final String couleur;

    CardColor(String couleur) {
        this.couleur = couleur;
    }

    public String getCouleur(){
        return couleur;
    }

    

}