/**
 * Copyright (C) 2004 - 2009 Shopzilla, Inc. 
 * All rights reserved. Unauthorized disclosure or distribution is prohibited.
 */

package com.pettermahlen.bygg.bootstrap;

import com.google.common.io.ByteStreams;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: document this class!
 *
 * @author Petter Måhlén
 * @since 25/03/2011
 */
public class CompilingClassLoader extends ClassLoader {
    private final URLClassLoader parent;
    private final String sourcePath;


    public CompilingClassLoader(URLClassLoader parent, String sourcePath) {
        this.parent = parent;
        this.sourcePath = sourcePath;
    }

    @Override
    protected Class findClass(String name) throws ClassNotFoundException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        Iterable<String> compilerOptions = compilerOptions();

        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> compilationUnits =
            fileManager.getJavaFileObjectsFromFiles(fileList());

        JavaCompiler.CompilationTask task = compiler.getTask(null, null, null, compilerOptions, null, compilationUnits);

        if (!task.call()) {
            throw new RuntimeException("compilation failed");
        }


        try {
            fileManager.close();

            fileManager = compiler.getStandardFileManager(null, null, null);

            JavaFileObject classFile = fileManager.getJavaFileForOutput(StandardLocation.CLASS_OUTPUT, name, JavaFileObject.Kind.CLASS, compilationUnits.iterator().next());

            byte[] classData = ByteStreams.toByteArray(classFile.openInputStream());

            return defineClass(name, classData, 0, classData.length);
         } catch (IOException e) {
            throw new ClassNotFoundException("not found", e);
        }
    }

    private Iterable<? extends File> fileList() throws ClassNotFoundException {
        File sourceRoot = new File(sourcePath);

        if (!sourceRoot.isDirectory()) {
            throw new ClassNotFoundException("Unable to load class since source directory not found: " + sourceRoot.getAbsolutePath());
        }

        List<File> result = new ArrayList<File>();

        addSourceFiles(sourceRoot, result);

        return result;
    }

    private void addSourceFiles(File directory, List<File> result) {
        for (File file : directory.listFiles()) {
            if (file.getName().endsWith(JavaFileObject.Kind.SOURCE.extension)) {
                result.add(file);
            }
            else if (file.isDirectory()) {
                addSourceFiles(file, result);
            }
        }
    }

    private Iterable<String> compilerOptions() {
        List<String> result = new ArrayList<String>(2);

        result.add("-classpath");
        result.add(classpathString());

        return result;
    }

    private String classpathString() {
        StringBuilder builder = new StringBuilder();

        for (URL url : parent.getURLs()) {
            builder.append(url.getFile()).append(File.pathSeparator);
        }

        return builder.toString();
    }

}
