package com.esgi.drawnshare.core.control;

import com.esgi.ModuleAnnotations.model.InitView;
import com.esgi.ModuleAnnotations.model.PluginInfo;
import com.esgi.ModuleAnnotations.model.PluginView;
import com.esgi.drawnshare.core.view_controller.CoreController;
import custo.java.nio.Directory;
import custo.javax.module.model.Module;
import custo.javax.module.model.ModuleFactory;
import custo.javax.module.model.error.ModuleFormatException;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;

/**
 * Created by linneya on 01/07/16.
 */
public class ModuleProcessor {
    private final Path path = Paths.get("./modules");
    private CoreController controller;
    private final ModuleFactory factory = new ModuleFactory();
    private final Directory moduleDir = this.createDir();

    private Directory createDir() {
        try {
            if (Files.exists(path)) {
                if (Files.isDirectory(path)) {
                    return new Directory(path);
                } else {
                    throw new IOException(String.format("%s already exist and is note a directory", path));
                }
            }
            return Directory.mkdirs(path.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ModuleProcessor (CoreController controller) throws IOException {
        this.controller = controller;
        this.determineLinkedRules();
        this.init();
    }

    public ModuleFactory getFactory() {
        return factory;
    }

    private void init() throws IOException {
        moduleDir.getSubPaths().forEach(path -> {
            try {
                System.out.println(path);
                factory.register(new Module(path));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });
    }

    private void determineLinkedRules() {
        factory.setOnRegister(event -> {
            Module module = event.getSource();
            module.newLoader();
            Enumeration<JarEntry> entries = module.getJarFile().entries();
            ArrayList<Package> packages = new ArrayList<>();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.toString().endsWith(".class")) {
                    try {
                        Class<?> clazz =  Class.forName(entry.toString().replace('/', '.').substring(0, entry.toString().length() - ".class".length()), false, module.getLoader());
                        packages.add(clazz.getPackage());
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            //Recherche du package d'information
            packages.forEach(packy -> {
                if (packy.isAnnotationPresent(PluginInfo.class)) {
                    PluginInfo annotation = packy.getAnnotation(PluginInfo.class);
                    module.getProperties().put("author", annotation.author());
                    module.getProperties().put("title", annotation.title());
                    module.getProperties().put("titleShort", annotation.titleShort());
                    module.getProperties().put("creationDate", annotation.creationDate());
                    module.getProperties().put("version", annotation.version());
                    System.out.println(module.getProperties());
                }
            });
            module.getLoader().close();
        });

        factory.setOnLoad(event -> {
            Module module = event.getSource();
            module.newLoader();
            Enumeration<JarEntry> entries = module.getJarFile().entries();
            /*Class<? extends Runnable> classLauncher = null;*/
            Class<? extends Runnable> classLauncher = null;
            PluginView pluginView = null;
            Class<?> toLaunch = null;
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.toString().endsWith(".class")) {
                    Class<?> clazz = null;
                    try {
                        clazz = Class.forName(entry.toString().replace('/', '.').substring(0, entry.toString().length() - ".class".length()), false, module.getLoader());
                        if (clazz.isAnnotationPresent(PluginView.class)) {
                            pluginView = clazz.getAnnotation(PluginView.class);
                            toLaunch = clazz;
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (toLaunch == null) {
                try {
                    throw new ModuleFormatException(String.format("%s is not a valid module for the Core", module.getJarFile().getName()));
                } catch (ModuleFormatException e) {
                    e.printStackTrace();
                }
            }
            try {
                for (URL url:
                module.getLoader().getURLs()) {
                    System.out.println(url);
                }
                System.out.println("-------------------------");
                /*classLauncher = toLaunch.asSubclass(Runnable.class);
                Constructor<? extends Runnable> classConstructor = classLauncher.getConstructor();
                Runnable toRun = classConstructor.newInstance();
                toRun.run();*/
                for (Method method: toLaunch.getMethods()) {
                    System.out.println(method.getName());
                    if (method.isAnnotationPresent(InitView.class)) {
                        System.out.println("found");
                        switch(pluginView.viewType()) {
                            case CENTER:
                                this.controller.setCenterPane((Pane) method.invoke(toLaunch.newInstance()), null);
                                break;
                            case LEFT:

                                this.controller.setLeftPane((Pane) method.invoke(toLaunch.newInstance()), null);
                                break;
                            case RIGHT:
                                this.controller.setRightPane((Pane) method.invoke(toLaunch.newInstance()), null);
                                break;
                            default:
                                break;
                        }
                    }
                }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

        });

        moduleDir.addDirectoryListener((key, events) -> events.forEach(event -> {
            if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                try {
                    factory.register(new Module((Path) event.context()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }));
    }

}
