package day21;

import java.util.Map;

public class Monkey {
    private final Operation operation;
    private final String leftMonkeyName;
    private final String rightMonkeyName;
    private final String name;

    private Monkey parent;
    private Monkey leftMonkey;
    private Monkey rightMonkey;
    private Long value;

    public Monkey(final String string)
    {
        name = string.substring(0, 4);

        if (string.length() == 17)
        {
            operation = Operation.of(string.charAt(11));
            leftMonkeyName = string.substring(6, 10);
            rightMonkeyName = string.substring(13);
        }
        else
        {
            operation = null;
            leftMonkeyName = null;
            rightMonkeyName = null;

            value = Long.parseLong(string.substring(6));
        }
    }

    public long getValue()
    {
        if (value == null)
        {
            return operation.on(leftMonkey.getValue(), rightMonkey.getValue());
        }
        return value;
    }

    public void linkMonkeys(final Map<String, Monkey> allMonkeys)
    {
        leftMonkey = allMonkeys.get(leftMonkeyName);
        if (leftMonkey != null)
        {
            leftMonkey.parent = this;
        }
        rightMonkey = allMonkeys.get(rightMonkeyName);
        if (rightMonkey != null)
        {
            rightMonkey.parent = this;
        }
    }

    public String getName()
    {
        return name;
    }

    public long calculateNeededValue()
    {
        if ("root".equals(parent.name))
        {
            return parent.getOtherChild(this).getValue();
        }

        return parent.operation.invert(parent.getOtherChild(this).getValue(),
                parent.calculateNeededValue(),
                isLeftChild());
    }

    private boolean isLeftChild()
    {
        return parent.leftMonkey == this;
    }
    private Monkey getOtherChild(final Monkey monkey)
    {
        return monkey == leftMonkey ? rightMonkey : leftMonkey;
    }
}




