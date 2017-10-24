package cn.e3mall.freemarker;

import java.util.Date;

/**
 * @author shenrs
 * @Description 学生属性对象信息
 * @create 2017-10-23 15:52
 **/
public class Student {

    private long id;
    private String name;
    private int age;
    private Date date;
    private String addr;

    public Student(long id, String name, int age, Date date, String addr) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.date = date;
        this.addr = addr;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
