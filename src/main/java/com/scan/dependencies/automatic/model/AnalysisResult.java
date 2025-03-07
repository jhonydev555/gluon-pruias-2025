package com.scan.dependencies.automatic.model;

import java.util.ArrayList;
import java.util.List;

public class AnalysisResult {
    private String packageName;
    private List<String> imports;
    private List<ClassInfo> classes;
    private List<EnumInfo> enums;  // Para enumeraciones

    public AnalysisResult() {
        this.imports = new ArrayList<>();
        this.classes = new ArrayList<>();
        this.enums = new ArrayList<>();
    }

    public String getPackageName() {
        return packageName;
    }
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public List<String> getImports() {
        return imports;
    }
    public void setImports(List<String> imports) {
        this.imports = imports;
    }

    public List<ClassInfo> getClasses() {
        return classes;
    }
    public void setClasses(List<ClassInfo> classes) {
        this.classes = classes;
    }

    public List<EnumInfo> getEnums() {
        return enums;
    }
    public void setEnums(List<EnumInfo> enums) {
        this.enums = enums;
    }
}
