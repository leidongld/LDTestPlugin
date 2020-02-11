package com.example.leidong.ldplugin.extensions

class LDExtension {
    String author
    String time
    String content


    @Override
    public String toString() {
        return "LDExtension{" +
                "author='" + author + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}