package fr.pantheonsorbonne.miage.enums;

public enum CardValue {
  ZERO("0"),
  ONE("1"),
  TWO("2"),
  THREE("3"),
  FOUR("4"),
  FIVE("5"),
  SIX("6"),
  SEVEN("7"),
  HEIGHT("8"),
  NINE("9"),
  INVERSE("inverse"),
  INTERDIT("interdit"),
  PLUSDEUX("+2"),
  PLUSQAUTRE("+4"),
  JOKER("joker");

  private String stringRepresentation;
  private final String valeur;

  CardValue(String valeur) {
    this.valeur = valeur;
  }

  public String getValeur() {
    return this.valeur;
  }

  public static CardValue valueOfStr(String str) {
    for (CardValue value : CardValue.values()) {
        if (str.equals(value.getStringRepresentation())) {
            return value;
        }
    }

    throw new RuntimeException("failed to parse value");

}

public String getStringRepresentation() {
    return stringRepresentation;
}

}