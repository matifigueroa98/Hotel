package model;

public enum ERoomType { // valores en dolares (USD)

    INDIVIDUAL(15),
    DOUBLE(20),
    KING(50);

    private final Integer USD;

    private ERoomType(Integer USD) {
        this.USD = USD;
    }

    public Integer getUSD() {
        return USD;
    }
}
