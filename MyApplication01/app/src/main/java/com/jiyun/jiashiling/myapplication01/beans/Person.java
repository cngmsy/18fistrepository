package com.jiyun.jiashiling.myapplication01.beans;

/**
 * Created by admin on 2018/1/16.
 */

public class Person {
    //姓名
    private String name;
    //拼音
    private String pinyin;
    //拼音首字母
    private String headerWord;


    public Person(String name, String pinyin, String headerWord) {
        this.name = name;
        this.pinyin = pinyin;
        this.headerWord = headerWord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getHeaderWord() {
        return headerWord;
    }

    public void setHeaderWord(String headerWord) {
        this.headerWord = headerWord;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", pinyin='" + pinyin + '\'' +
                ", headerWord='" + headerWord + '\'' +
                '}';
    }
}
