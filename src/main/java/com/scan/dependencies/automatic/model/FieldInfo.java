package com.scan.dependencies.automatic.model;

import java.util.ArrayList;
import java.util.List;

public class FieldInfo {
    private String name;
    private String type;
    private String accessLevel;   // public, private...
    private boolean isStatic;
    private boolean isFinal;
    private List<String> annotations;

    public FieldInfo() {
        this.annotations = new ArrayList<>();
    }

    public FieldInfo(String name, String type) {
        this();
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getAccessLevel() {
        return accessLevel;
    }
    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public boolean isStatic() {
        return isStatic;
    }
    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }

    public boolean isFinal() {
        return isFinal;
    }
    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    public List<String> getAnnotations() {
        return annotations;
    }
    public void setAnnotations(List<String> annotations) {
        this.annotations = annotations;
    }
}
