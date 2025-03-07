package com.scan.dependencies.automatic.service;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.scan.dependencies.automatic.model.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class JavaFileAnalyze {

    public AnalysisResult analyzeFileAsJson(String filePath) {
        AnalysisResult result = new AnalysisResult();

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.err.println("El archivo no existe en la ruta especificada.");
                return result;  // Devuelve vacío
            }

            // Parseamos el archivo .java
            CompilationUnit cu = StaticJavaParser.parse(file);

            // 1. Package
            cu.getPackageDeclaration().ifPresent(pkg ->
                    result.setPackageName(pkg.getNameAsString())
            );

            // 2. Imports
            cu.getImports().forEach(importDecl -> {
                String importStr = importDecl.toString().trim();
                result.getImports().add(importStr);
            });

            // 3. Enums
            List<EnumDeclaration> enumDeclarations = cu.findAll(EnumDeclaration.class);
            for (EnumDeclaration enumDecl : enumDeclarations) {
                EnumInfo enumInfo = new EnumInfo();
                enumInfo.setName(enumDecl.getNameAsString());

                enumDecl.getRange().ifPresent(range -> {
                    enumInfo.setStartLine(range.begin.line);
                    enumInfo.setEndLine(range.end.line);
                });

                enumDecl.getJavadocComment().ifPresent(c ->
                        enumInfo.setJavadoc(c.getContent().trim())
                );

                // Anotaciones en el enum
                enumDecl.getAnnotations().forEach(a ->
                        enumInfo.getAnnotations().add(a.toString())
                );

                // Constantes del enum
                enumDecl.getEntries().forEach(entry ->
                        enumInfo.getConstants().add(entry.getNameAsString())
                );

                result.getEnums().add(enumInfo);
            }

            // 4. Clases / Interfaces
            List<ClassOrInterfaceDeclaration> classes = cu.findAll(ClassOrInterfaceDeclaration.class);
            for (ClassOrInterfaceDeclaration clazz : classes) {
                ClassInfo classInfo = new ClassInfo();
                classInfo.setClassName(clazz.getNameAsString());
                classInfo.setAccessLevel(clazz.getAccessSpecifier().asString());
                classInfo.setFinal(clazz.isFinal());
                classInfo.setAbstract(clazz.isAbstract());

                clazz.getRange().ifPresent(range -> {
                    classInfo.setStartLine(range.begin.line);
                    classInfo.setEndLine(range.end.line);
                });

                // Javadoc de la clase
                clazz.getJavadocComment().ifPresent(c ->
                        classInfo.setJavadoc(c.getContent().trim())
                );

                // Anotaciones
                for (AnnotationExpr annotation : clazz.getAnnotations()) {
                    classInfo.getAnnotations().add(annotation.toString());
                }

                // Herencia e interfaces
                clazz.getExtendedTypes().forEach(extended ->
                        classInfo.getExtendedClasses().add(extended.getNameAsString())
                );
                clazz.getImplementedTypes().forEach(impl ->
                        classInfo.getImplementedInterfaces().add(impl.getNameAsString())
                );

                // 4.1. Campos
                for (FieldDeclaration field : clazz.getFields()) {
                    field.getVariables().forEach(var -> {
                        FieldInfo fieldInfo = new FieldInfo(var.getNameAsString(), var.getType().asString());
                        // Modificadores
                        fieldInfo.setAccessLevel(field.getAccessSpecifier().asString());
                        fieldInfo.setStatic(field.hasModifier(Modifier.Keyword.STATIC));
                        fieldInfo.setFinal(field.hasModifier(Modifier.Keyword.FINAL));

                        // Anotaciones en el campo
                        field.getAnnotations().forEach(a ->
                                fieldInfo.getAnnotations().add(a.toString())
                        );

                        classInfo.getFields().add(fieldInfo);
                    });
                }

                // 4.2. Constructores
                for (ConstructorDeclaration constructor : clazz.getConstructors()) {
                    ConstructorInfo constructorInfo = new ConstructorInfo();
                    constructorInfo.setName(constructor.getNameAsString());
                    constructorInfo.setAccessLevel(constructor.getAccessSpecifier().asString());
                    constructorInfo.setFinal(constructor.hasModifier(Modifier.Keyword.FINAL));
                    constructorInfo.setStatic(constructor.hasModifier(Modifier.Keyword.STATIC));
                    constructorInfo.setAbstract(constructor.hasModifier(Modifier.Keyword.ABSTRACT));

                    constructor.getRange().ifPresent(range -> {
                        constructorInfo.setStartLine(range.begin.line);
                        constructorInfo.setEndLine(range.end.line);
                    });

                    constructor.getJavadocComment().ifPresent(c ->
                            constructorInfo.setJavadoc(c.getContent().trim())
                    );

                    // Parámetros
                    constructor.getParameters().forEach(param -> {
                        String paramStr = param.getType().asString() + " " + param.getNameAsString();
                        constructorInfo.getParameters().add(paramStr);
                    });

                    // Anotaciones
                    constructor.getAnnotations().forEach(a ->
                            constructorInfo.getAnnotations().add(a.toString())
                    );

                    // Excepciones declaradas (throws)
                    constructor.getThrownExceptions().forEach(thrownType ->
                            constructorInfo.getThrownExceptions().add(thrownType.toString())
                    );

                    // Cuerpo (BlockStmt) -> no es Optional
                    BlockStmt body = constructor.getBody();
                    if (body != null) {
                        constructorInfo.setBody(body.toString());

                        // If / switch
                        List<IfStmt> ifStmts = body.getChildNodesByType(IfStmt.class);
                        List<SwitchStmt> switchStmts = body.getChildNodesByType(SwitchStmt.class);
                        constructorInfo.setBranchingCount(ifStmts.size() + switchStmts.size());

                        // Llamadas internas
                        List<MethodCallExpr> calls = body.getChildNodesByType(MethodCallExpr.class);
                        for (MethodCallExpr call : calls) {
                            constructorInfo.getInternalCalls().add(call.toString());
                        }
                    }

                    classInfo.getConstructors().add(constructorInfo);
                }

                // 4.3. Métodos
                for (MethodDeclaration method : clazz.getMethods()) {
                    MethodInfo methodInfo = new MethodInfo();
                    methodInfo.setName(method.getNameAsString());
                    methodInfo.setReturnType(method.getType().asString());
                    methodInfo.setAccessLevel(method.getAccessSpecifier().asString());
                    methodInfo.setFinal(method.hasModifier(Modifier.Keyword.FINAL));
                    methodInfo.setStatic(method.hasModifier(Modifier.Keyword.STATIC));
                    methodInfo.setAbstract(method.isAbstract());

                    method.getRange().ifPresent(range -> {
                        methodInfo.setStartLine(range.begin.line);
                        methodInfo.setEndLine(range.end.line);
                    });

                    method.getJavadocComment().ifPresent(c ->
                            methodInfo.setJavadoc(c.getContent().trim())
                    );

                    method.getParameters().forEach(param -> {
                        String paramStr = param.getType().asString() + " " + param.getNameAsString();
                        methodInfo.getParameters().add(paramStr);
                    });

                    method.getAnnotations().forEach(a ->
                            methodInfo.getAnnotations().add(a.toString())
                    );

                    method.getThrownExceptions().forEach(thrown ->
                            methodInfo.getThrownExceptions().add(thrown.toString())
                    );

                    // Cuerpo (Optional<BlockStmt>)
                    BlockStmt body = method.getBody().orElse(null);
                    if (body != null) {
                        methodInfo.setBody(body.toString());

                        // If / switch
                        List<IfStmt> ifStmts = body.getChildNodesByType(IfStmt.class);
                        List<SwitchStmt> switchStmts = body.getChildNodesByType(SwitchStmt.class);
                        methodInfo.setBranchingCount(ifStmts.size() + switchStmts.size());

                        // Llamadas internas
                        List<MethodCallExpr> calls = body.getChildNodesByType(MethodCallExpr.class);
                        for (MethodCallExpr call : calls) {
                            methodInfo.getInternalCalls().add(call.toString());
                        }
                    }

                    classInfo.getMethods().add(methodInfo);
                }

                result.getClasses().add(classInfo);
            }

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }

        return result;
    }
}
