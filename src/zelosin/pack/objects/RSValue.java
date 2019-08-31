package zelosin.pack.objects;

public class RSValue {
    private String denomination;
    private int value;

    public RSValue(String denomination, int value) {
        this.denomination = denomination;
        this.value = value;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
