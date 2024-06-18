package lib.errors;

public class SemanticError {
    String message;

    public SemanticError() {
    }

    public SemanticError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
