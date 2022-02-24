package com.basic.admin.common;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.basic.admin.model.BaseEntity;

import java.util.Collections;
import java.util.Scanner;

/**
 * @author admin
 */
public class MyBatisPlusGenerator {
    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入" + tip + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StrUtil.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");

        FastAutoGenerator.create("jdbc:postgresql://47.106.8.60:5432/honi", "postgres", "honi20220108")
                .globalConfig(builder -> {
                    builder.author("baomidou") // 设置作者
                            .disableOpenDir()
//                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(projectPath + "/src/test/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.chengyu.honi") // 设置父包名
                            .moduleName(scanner("父包模块名")) // 设置父包模块名
                            .entity("model.entity")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, projectPath + "/src/main/resources/mapper/")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(scanner("表名，多个英文逗号分割").split(","))
                            .entityBuilder()
                            .superClass(BaseEntity.class)
                            .enableLombok()
                            .enableTableFieldAnnotation()
                            .idType(IdType.ASSIGN_UUID)
                            .addSuperEntityColumns("id", "tenant_id", "revision", "created_by_id", "created_by", "created_time", "updated_by_id", "updated_by", "updated_time")
                            .mapperBuilder()
                            .enableMapperAnnotation()
                    ;
                })
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }
}
