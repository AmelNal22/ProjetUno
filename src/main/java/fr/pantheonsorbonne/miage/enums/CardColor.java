package fr.pantheonsorbonne.miage.enums;

public enum CardColor {
    ROUGE("R"),
    VERT("V"),
    JAUNE("J"),
    BLEU("B");

    private final String couleur;
    private String stringRepresentation;

    CardColor(String couleur) {
        this.couleur = couleur;
    }

    public String getCouleur(){
        return this.couleur;
    }

    public static CardColor valueOfStr(String str) {
        for (CardColor color : CardColor.values()) {
            if (str.equals(color.getStringRepresentation())) {
                return color;
            }
        }
    
        throw new RuntimeException("failed to parse value");
    
    }
    
    public String getStringRepresentation() {
        return stringRepresentation;
    }
    
    }
