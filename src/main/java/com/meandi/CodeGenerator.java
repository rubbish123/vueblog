package com.meandi;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
public class CodeGenerator {
    public static void main(String[] args) {
        // 需要构建代码生成器对象
        AutoGenerator mpg = new AutoGenerator();
        // 配置策略

        // 1.全局配置
        GlobalConfig config = new GlobalConfig();
        // 获取当前路径
        String ProjectPath = System.getProperty("user.dir");
        // 设置代码输出路径
        config.setOutputDir(ProjectPath+"/src/main/java");
        config.setAuthor("Meandi");
        // 是否打开资源管理器
        config.setOpen(false);
        // 是否覆盖之前的
        config.setFileOverride(true);
        // 去除Service的I前缀
        config.setServiceName("%sService");
        config.setIdType(IdType.ID_WORKER);
        // 设置日期
        config.setDateType(DateType.ONLY_DATE);
        config.setSwagger2(true);

        mpg.setGlobalConfig(config);

        // 2.设置数据源
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/vueblog?useSSL=false&useUnicode=true&serverTimezone=GMT%2B8&characterEncoding=utf8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("831143");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        // 3.包的配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.meandi");
        pc.setEntity("pojo");
        pc.setMapper("mapper");
        pc.setService("service");
        pc.setController("controller");

        mpg.setPackageInfo(pc);

        // 4.策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude("user","blog"); // 设置要映射哪些表
        strategy.setNaming(NamingStrategy.underline_to_camel); // 下划线转驼峰
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true); // 自动lombok
        //strategy.setLogicDeleteFieldName("deleted"); // 逻辑删除字段

        // 自动填充配置
        TableFill createTime = new TableFill("create_time", FieldFill.INSERT);
        TableFill updateTime = new TableFill("last_login", FieldFill.INSERT_UPDATE);
        ArrayList<TableFill> tableFills = new ArrayList<>();
        tableFills.add(createTime);
        tableFills.add(updateTime);
        strategy.setTableFillList(tableFills);
        // 乐观锁配置
        //strategy.setVersionFieldName("version");

        strategy.setRestControllerStyle(true);
        strategy.setControllerMappingHyphenStyle(true);


        mpg.setStrategy(strategy);

        mpg.execute(); // 执行
    }
}
