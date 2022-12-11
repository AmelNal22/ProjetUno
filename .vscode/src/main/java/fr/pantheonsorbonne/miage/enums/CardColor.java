package fr.pantheonsorbonne.miage.enums;

public enum CardColor {
    RED("R"),
    GREEN("G"),
    YELLOW("Y"),
    BLUE("B");

    private final String couleur;

    CardColor(String color) {
        this.couleur = color;
    }

    public String getCouleur() {
        return this.couleur;
    }
}