package com.scan.dependencies.automatic.model;

import java.util.ArrayList;
import java.util.List;

public class ClassInfo {
    private String className;
    private String accessLevel;        // public, private, protected, etc.
    private boolean isFinal;
    private boolean isAbstract;
    private int startLine;
    private int endLine;
    private String javadoc;           // Javadoc/comentario de la clase, si deseas
    private List<String> annotations; // Anotaciones en la clase

    // Herencia e interfaces
    private List<String> extendedClasses;
    private List<String> implementedInterfaces;

    private List<FieldInfo> fields;
    private List<MethodInfo> methods;
    private List<ConstructorInfo> constructors;

    public ClassInfo() {
        this.annotations = new ArrayList<>();
        this.extendedClasses = new ArrayList<>();
        this.implementedInterfaces = new ArrayList<>();
        this.fields = new ArrayList<>();
        this.methods = new ArrayList<>();
        this.constructors = new ArrayList<>();
    }

    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    public String getAccessLevel() {
        return accessLevel;
    }
    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public boolean isFinal() {
        return isFinal;
    }
    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    public boolean isAbstract() {
        return isAbstract;
    }
    public void setAbstract(boolean anAbstract) {
        isAbstract = anAbstract;
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

    public List<String> getExtendedClasses() {
        return extendedClasses;
    }
    public void setExtendedClasses(List<String> extendedClasses) {
        this.extendedClasses = extendedClasses;
    }

    public List<String> getImplementedInterfaces() {
        return implementedInterfaces;
    }
    public void setImplementedInterfaces(List<String> implementedInterfaces) {
        this.implementedInterfaces = implementedInterfaces;
    }

    public List<FieldInfo> getFields() {
        return fields;
    }
    public void setFields(List<FieldInfo> fields) {
        this.fields = fields;
    }

    public List<MethodInfo> getMethods() {
        return methods;
    }
    public void setMethods(List<MethodInfo> methods) {
        this.methods = methods;
    }

    public List<ConstructorInfo> getConstructors() {
        return constructors;
    }
    public void setConstructors(List<ConstructorInfo> constructors) {
        this.constructors = constructors;
    }
}
