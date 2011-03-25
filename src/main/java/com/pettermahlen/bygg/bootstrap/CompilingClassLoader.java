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
import java.util.Arrays;
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
            fileManager.getJavaFileObjectsFromFiles(fileList(name));

        JavaCompiler.CompilationTask task = compiler.getTask(null, null, null, compilerOptions, null, compilationUnits);

        if (!task.call()) {
            throw new RuntimeException("compilation failed");
        }


        try {
            JavaFileObject classFile = fileManager.getJavaFileForOutput(StandardLocation.CLASS_OUTPUT, name, JavaFileObject.Kind.CLASS, null);

            byte[] classData = ByteStreams.toByteArray(classFile.openInputStream());

            return defineClass(name, classData, 0, classData.length);
         } catch (IOException e) {
            throw new ClassNotFoundException("not found", e);
        }
    }

    private Iterable<? extends File> fileList(String name) {
        String fileName = name.replaceAll("\\.", File.separator) + ".java";

        return Arrays.asList(new File(sourcePath, fileName));
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
