package com.hysea.hyseaappapi.constant;

public class EntityConstant {

    public static String ATTRIBUTE_LINE = "\tprivate #{type} #{attributeName};";

    public static String NOTES_LINE = "\n\t/**" +
            "\n\t * #{note}" +
            "\n\t */";

    public static String CLASS_CONTENT = "package #{package};\n\n"
            + "import java.util.Date;\n"
            + "import java.util.List;\n"
            + "import java.io.Serializable;\n"
            + "import lombok.*;\n\n"
            + "/**\n"
            + " * @author hysea\n"
            + " */\n"
            + "@Data\n"
            + "public class #{className} implements Serializable {\n\n"
            + "#{content}\n"
            + "}";

    public static String INNER_CLASS_CONTENT = "@Data\n"
            + "private class #{className} {\n\n"
            + "#{content}\n"
            + "}";

    public static String TYPE = "#{type}";

    public static String ATTRIBUTE_NAME = "#{attributeName}";

    public static String CLASS_NAME = "#{className}";

    public static String CONTENT = "#{content}";

    public static String PACKAGE = "#{package}";

    public static String NOTE = "#{note}";
}
