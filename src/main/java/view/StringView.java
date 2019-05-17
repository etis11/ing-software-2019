package view;

/**
 * A String view is a string the read from something and returns the string read.
 */
public interface StringView {
    /**
     * return a string from the input file
     * @return the string given by a input source, such as the stdin.
     */
    public String getInput();
}
