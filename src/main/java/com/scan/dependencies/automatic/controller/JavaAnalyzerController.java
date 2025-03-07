package com.scan.dependencies.automatic.controller;

import com.scan.dependencies.automatic.model.AnalysisResult;
import com.scan.dependencies.automatic.service.JavaFileAnalyze;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JavaAnalyzerController {

    private final JavaFileAnalyze javaFileAnalyze;

    public JavaAnalyzerController(JavaFileAnalyze javaFileAnalyze) {
        this.javaFileAnalyze = javaFileAnalyze;
    }

    @GetMapping("/analyze")
    public AnalysisResult analyzeFile(@RequestParam String filePath) {
        return javaFileAnalyze.analyzeFileAsJson(filePath);
    }
}
