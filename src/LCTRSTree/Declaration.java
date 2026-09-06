package LCTRSTree;

/**
 * The class for storing information about a declaration of an LCTRS node for CORA program. All of its fields
 * can be access by using the public getter methods.
 */

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
