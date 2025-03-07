package com.scan.dependencies.automatic.model;

import java.util.ArrayList;
import java.util.List;

public class MethodInfo {
    private String name;
    private String returnType;
    private String accessLevel;
    private boolean isFinal;
    private boolean isStatic;
    private boolean isAbstract;

    private int startLine;
    private int endLine;
    private String javadoc;

    private List<String> parameters;       // "String param", etc.
    private List<String> annotations;      // @Override, @Transactional...
    private List<String> thrownExceptions; // Excepciones en throws
    private String body;                  // Cuerpo completo
    private int branchingCount;           // if/switch contados
    private List<String> internalCalls;   // Llamadas a otros métodos

    public MethodInfo() {
        this.parameters = new ArrayList<>();
        this.annotations = new ArrayList<>();
        this.thrownExceptions = new ArrayList<>();
        this.internalCalls = new ArrayList<>();
    }

    // Getters y setters...
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getReturnType() {
        return returnType;
    }
    public void setReturnType(String returnType) {
        this.returnType = returnType;
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

    public boolean isStatic() {
        return isStatic;
    }
    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
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

    public List<String> getParameters() {
        return parameters;
    }
    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    public List<String> getAnnotations() {
        return annotations;
    }
    public void setAnnotations(List<String> annotations) {
        this.annotations = annotations;
    }

    public List<String> getThrownExceptions() {
        return thrownExceptions;
    }
    public void setThrownExceptions(List<String> thrownExceptions) {
        this.thrownExceptions = thrownExceptions;
    }

    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }

    public int getBranchingCount() {
        return branchingCount;
    }
    public void setBranchingCount(int branchingCount) {
        this.branchingCount = branchingCount;
    }

    public List<String> getInternalCalls() {
        return internalCalls;
    }
    public void setInternalCalls(List<String> internalCalls) {
        this.internalCalls = internalCalls;
    }
}
