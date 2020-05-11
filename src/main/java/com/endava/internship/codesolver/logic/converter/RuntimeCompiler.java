package com.endava.internship.codesolver.logic.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import javax.tools.DiagnosticCollector;
import javax.tools.Diagnostic;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.FileObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class RuntimeCompiler {

    private static final Logger logger = LoggerFactory.getLogger(RuntimeCompiler.class);

    private final JavaCompiler javaCompiler;

    private final Map<String, byte[]> classData;

    private final MapClassLoader mapClassLoader;

    private final ClassDataFileManager classDataFileManager;

    private final List<JavaFileObject> compilationUnits;

    private String logs = "";

    protected RuntimeCompiler() {
        this.javaCompiler = ToolProvider.getSystemJavaCompiler();
        if (javaCompiler == null) {
            throw new NullPointerException(
                    "No JavaCompiler found. Make sure to run this with "
                            + "a JDK, and not only with a JRE");
        }
        this.classData = new LinkedHashMap<>();
        this.mapClassLoader = new MapClassLoader();
        this.classDataFileManager =
                new ClassDataFileManager(
                        javaCompiler.getStandardFileManager(null, null, null));
        this.compilationUnits = new ArrayList<>();
    }

    public String getLogs() {
        return logs;
    }

    protected void addClass(String className, String code) {
        String javaFileName = className + ".java";
        JavaFileObject javaFileObject =
                new MemoryJavaSourceFileObject(javaFileName, code);
        compilationUnits.add(javaFileObject);
    }

    boolean compile() {
        DiagnosticCollector<JavaFileObject> diagnosticsCollector =
                new DiagnosticCollector<>();
        JavaCompiler.CompilationTask task =
                javaCompiler.getTask(null, classDataFileManager,
                        diagnosticsCollector, null, null,
                        compilationUnits);
        boolean success = task.call();
        compilationUnits.clear();
        for (Diagnostic<?> diagnostic : diagnosticsCollector.getDiagnostics()) {
            String errorKind = diagnostic.getKind().toString();
            String errorMessage = diagnostic.getMessage(null);
            String errorLineNumber = String.valueOf(diagnostic.getLineNumber());
            String errorSource = diagnostic.getSource().toString();
            logger.error(errorKind + ": " + errorMessage + "\nLine " + errorLineNumber + " of " + errorSource);
            if (errorKind.equals("ERROR")) {
                logs += errorKind + ": " + errorMessage + "\n";
            }
        }
        return success;
    }

    protected Class<?> getCompiledClass(String className) {
        return mapClassLoader.findClass(className);
    }

    private static final class MemoryJavaSourceFileObject extends
            SimpleJavaFileObject {

        private final String code;

        private MemoryJavaSourceFileObject(String fileName, String code) {
            super(URI.create("string:///" + fileName), Kind.SOURCE);
            this.code = code;
        }


        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return code;
        }

    }

    private class MapClassLoader extends ClassLoader {


        public Class<?> findClass(String name) {
            byte[] b = classData.get(name);
            return defineClass(name, b, 0, b.length);
        }

    }

    private class MemoryJavaClassFileObject extends SimpleJavaFileObject {

        private final String className;

        private MemoryJavaClassFileObject(String className) {
            super(URI.create("string:///" + className + ".class"),
                    Kind.CLASS);
            this.className = className;
        }


        public OutputStream openOutputStream() {
            return new ClassDataOutputStream(className);
        }

    }

    private class ClassDataFileManager extends
            ForwardingJavaFileManager<StandardJavaFileManager> {

        private ClassDataFileManager(
                StandardJavaFileManager standardJavaFileManager) {
            super(standardJavaFileManager);
        }


        public JavaFileObject getJavaFileForOutput(final Location location,
                                                   final String className, JavaFileObject.Kind kind, FileObject sibling) {
            return new MemoryJavaClassFileObject(className);
        }

    }

    private class ClassDataOutputStream extends OutputStream {

        private final String className;

        private final ByteArrayOutputStream baos;

        private ClassDataOutputStream(String className) {
            this.className = className;
            this.baos = new ByteArrayOutputStream();
        }


        public void write(int b) {
            baos.write(b);
        }


        public void close() throws IOException {
            classData.put(className, baos.toByteArray());
            super.close();
        }

    }

}
