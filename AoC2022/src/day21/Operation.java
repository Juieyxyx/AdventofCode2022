package day21;

public enum Operation
{
    ADD,
    SUBTRACT,
    MULTIPLY,
    DIVIDE;

    public static Operation of(final char operation)
    {
        switch (operation)
        {
            case '+': return ADD;
            case '-': return SUBTRACT;
            case '*': return MULTIPLY;
            case '/': return DIVIDE;
            default: throw new IllegalArgumentException();
        }
    }

    public long on(final long x, final long y)
    {
        switch (this)
        {
            case ADD: return x + y;
            case SUBTRACT: return x - y;
            case MULTIPLY: return x * y;
            case DIVIDE: return x / y;
            default: throw new IllegalArgumentException();
        }
    }

    public long invert(final long input, final long output, final boolean answerWasLeft)
    {
        switch (this)
        {
            case ADD: return output - input;
            case MULTIPLY: return output / input;
            case SUBTRACT: return answerWasLeft ? input + output : input - output;
            case DIVIDE: return answerWasLeft ? input * output : input / output;
            default: throw new IllegalArgumentException();
        }
    }
}
