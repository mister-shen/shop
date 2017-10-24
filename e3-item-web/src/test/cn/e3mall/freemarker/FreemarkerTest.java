package cn.e3mall.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.*;

/**
 * @author shenrs
 * @Description freeMarker测试
 * @create 2017-10-23 11:19
 **/
public class FreemarkerTest   {

    /**
     * @Description 生成一个模板文件
     * @author shenrs
     * @create 2017/10/23 11:21
     */
    @Test
    public void testFreeMarker() throws Exception{
        //1.创建一个模板文件
        //2.创建一个Configuration对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        //3.设置保存模板的文件路径
        configuration.setDirectoryForTemplateLoading(new File("E:/projects/myProject/shop/e3-item-web/src/main/webapp/WEB-INF/ftl"));
        //4.模板文件的编码格式
        configuration.setDefaultEncoding("utf-8");
        //5.加载一个模板文件，创建一个模板对象
        Template template = configuration.getTemplate("hello.ftl");
        //6.创建一个数据集
        Map map = new HashMap();
        map.put("hello", "hello freemarker!");
        //7.创建一个writer对象，指定输出文件的路径及文件名
        Writer writer = new FileWriter("C:/Users/Administrator/Desktop/hello.txt");
        //8.生成静态页面
        template.process(map, writer);
        //9.关闭流
        writer.close();
    }

    /**
     * @Description freemarker的语法学习
     * @author shenrs
     * @create 2017/10/23 15:55
     */
    @Test
    public void testObjFreeMarker() throws Exception{
        //1.创建一个模板文件（studeng.ftl）
        //2.创建一个Configuration对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        //3.设置保存模板的文件路径
        configuration.setDirectoryForTemplateLoading(new File("E:/projects/myProject/shop/e3-item-web/src/main/webapp/WEB-INF/ftl"));
        //4.模板文件的编码格式
        configuration.setDefaultEncoding("utf-8");
        //5.加载一个模板文件，创建一个模板对象
        Template template = configuration.getTemplate("student.ftl");
        //6.创建一个数据集
        Map data = new HashMap();
        data.put("hello", "hello freemarker!");

        Student stu = new Student(1,null, 18, new Date(), "武侯区科园南路");
        data.put("stu", stu);

        List<Student> stuList = new ArrayList<>();
        stuList.add(new Student(1,null, 18, new Date(), "武侯区科园南路"));
        stuList.add(new Student(2,"张三02", 16, new Date(), "武侯区科园南路"));
        stuList.add(new Student(3,"张三03", 12, new Date(), "武侯区科园南路"));
        stuList.add(new Student(4,"张三04", 21, new Date(), "武侯区科园南路"));
        stuList.add(new Student(5,"张三05", 22, new Date(), "武侯区科园南路"));
        stuList.add(new Student(6,"张三06", 23, new Date(), "武侯区科园南路"));
        stuList.add(new Student(7,"张三07", 25, new Date(), "武侯区科园南路"));
        stuList.add(new Student(8,"张三08", 23, new Date(), "武侯区科园南路"));
        stuList.add(new Student(9,"张三09", 18, new Date(), "武侯区科园南路"));
        stuList.add(new Student(10,"张三10", 19, new Date(), "武侯区科园南路"));
        data.put("stuList", stuList);

        //7.创建一个writer对象，指定输出文件的路径及文件名
        Writer writer = new FileWriter("C:/Users/Administrator/Desktop/student.html");
        //8.生成静态页面
        template.process(data, writer);
        //9.关闭流
        writer.close();
    }

}
