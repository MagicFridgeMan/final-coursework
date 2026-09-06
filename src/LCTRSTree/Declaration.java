package LCTRSTree;

public class Declaration {
    private final boolean isPrivate;
    private final String name;
    private final int sorts;

    public Declaration(boolean isPrivate, String name, int sorts) {
        this.isPrivate = isPrivate;
        this.name = name;
        this.sorts = sorts;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public String getName() {
        return name;
    }

    public int getSorts() {
        return sorts;
    }
}
