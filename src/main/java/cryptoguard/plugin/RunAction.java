package cryptoguard.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.wm.WindowManager;
import cryptoguard.source.Base;
import frontEnd.Interface.outputRouting.ExceptionHandler;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.Scanner;

public class RunAction extends AnAction {
    private static List<File> listFiles = new ArrayList<>();

    public static void listOfFiles(File dirPath, String extension) {
        File filesList[] = dirPath.listFiles();
        for (File file : filesList) {
            if (file.isFile() ) {
                if (file.toString().endsWith(extension)) {
                    listFiles.add(file);
                }
            }
            else {
                listOfFiles(file, extension);
            }
        }
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        // get the active project
        Project[] projects = ProjectManager.getInstance().getOpenProjects();
        Project activeProject = null;
        for (Project project : projects) {
            Window window = WindowManager.getInstance().suggestParentWindow(project);
            if (window != null && window.isActive()) {
                activeProject = project;
            }
        }

        // ge the source files
        listOfFiles(new File(activeProject.getBasePath()), ".class");
        ArrayList<String> sourceFiles = new ArrayList<>();
        for (File f : listFiles) {
            sourceFiles.add(f.toString());
        }
        listFiles.clear();

        // get the in-directory dependencies
        listOfFiles(new File(activeProject.getBasePath()), ".jar");
        ArrayList<String> depFiles = new ArrayList<>();
        String test = "";
        for (File f : listFiles) {
            depFiles.add(f.toString());
            test = test + f.getName() + " ";
        }
        listFiles.clear();

        // create the outputFile
        File outputFile = new File(activeProject.getBasePath(), "./tmp");
        if (!outputFile.exists()){
            outputFile.mkdirs();
        }
        boolean correctlyRan = true;
        // call cryptoguard
        try {
            String fileOut = Base.entryPoint(sourceFiles, depFiles, null, null, 2);
            if (fileOut == null) {
                File list[] = (new File(activeProject.getBasePath())).listFiles();
                for (File f : list) {
                    if (f.isFile() && f.getName().startsWith("_CryptoGuard")) {
                        f.renameTo(new File ("tmp/" + f.getName()));
                    }
                }
            }
        } catch (ExceptionHandler exceptionHandler) {
            Messages.showMessageDialog(e.getProject(), "ERROR", "CryptoGuard", Messages.getInformationIcon());
            correctlyRan = false;
            exceptionHandler.printStackTrace();
        }

        // show the output that points to the file
        //Messages.showMessageDialog(e.getProject(), "CryptoGuard has been ran on your code, see temp.txt for output", "CryptoGuard", Messages.getInformationIcon());
        if (correctlyRan) {
            Messages.showMessageDialog(e.getProject(), "CryptoGuard has been executed on your code. See the output file in the following directory: \n" + outputFile.getPath(), "CryptoGuard", Messages.getInformationIcon());
        }
    }
}