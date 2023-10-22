package com.hysea.hyseaappapi.constant;

public class FunctionConstant {

    public static String BODY = "import org.apache.ibatis.annotations.Mapper;\n"
            + "import java.util.List;\n"
            + "import com.baomidou.mybatisplus.core.metadata.IPage;\n"
            + "import com.baomidou.mybatisplus.core.mapper.BaseMapper;\n"
            + "import org.apache.ibatis.annotations.Param;\n"
            + "import com.hysea.hyseaappapi.entity.po.#{tableName};\n\n"
            + "@Mapper\n"
            + "public interface #{tableName}Mapper extends BaseMapper<#{tableName}> {\n\n"
            + "#{$contain$}"
            + "}\n";

    public static String TABLE_NAME = "#{tableName}";

    public static String METHOD_LINE = "#{returnValue} #{id}(#{object});\n\n";

    public static String RETURN_VALUE = "#{returnValue}";

    public static String ID = "#{id}";

    public static String OBJECT = "#{object}";

    public static String CONTAIN = "#{$contain$}";

}
