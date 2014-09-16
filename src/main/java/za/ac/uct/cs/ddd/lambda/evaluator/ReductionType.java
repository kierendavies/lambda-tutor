package za.ac.uct.cs.ddd.lambda.evaluator;

public enum ReductionType {
    NONE, ALPHA, ALPHA_CA, BETA, ETA;
    // ALPHA_CA is internal only, to signal an alpha conversion to avoid variable capture

    @Override
    public String toString() {
        switch (this) {
            case ALPHA:
            case ALPHA_CA:
                return "\u03b1";
            case BETA:
                return "\u03b2";
            case ETA:
                return "\u03b7";
            default:
                return " ";
        }
    }
}
