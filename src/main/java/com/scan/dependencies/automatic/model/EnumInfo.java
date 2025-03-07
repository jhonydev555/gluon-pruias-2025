package com.scan.dependencies.automatic.model;

import java.util.ArrayList;
import java.util.List;

public class EnumInfo {
    private String name;
    private int startLine;
    private int endLine;
    private String javadoc;
    private List<String> annotations;
    private List<String> constants; // Las literales: "MONDAY", "TUESDAY", etc.

    public EnumInfo() {
        this.annotations = new ArrayList<>();
        this.constants = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getStartLine() {
        return startLine;
    }
    public void setStartLine(int startLine) {
        this.startLine = startLine;
    }

    public int getEndLine() {
        return endLine;
    }
    public void setEndLine(int endLine) {
        this.endLine = endLine;
    }

    public String getJavadoc() {
        return javadoc;
    }
    public void setJavadoc(String javadoc) {
        this.javadoc = javadoc;
    }

    public List<String> getAnnotations() {
        return annotations;
    }
    public void setAnnotations(List<String> annotations) {
        this.annotations = annotations;
    }

    public List<String> getConstants() {
        return constants;
    }
    public void setConstants(List<String> constants) {
        this.constants = constants;
    }
}
