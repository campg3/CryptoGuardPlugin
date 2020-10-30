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
import java.util.ArrayList;
import java.util.List;
import java.io.File;

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
        Project[] projects = ProjectManager.getInstance().getOpenProjects();
        Project activeProject = null;
        for (Project project : projects) {
            Window window = WindowManager.getInstance().suggestParentWindow(project);
            if (window != null && window.isActive()) {
                activeProject = project;
            }
        }

        listOfFiles(new File(activeProject.getBasePath()), ".class");
        ArrayList<String> sourceFiles = new ArrayList<>();
        for (File f : listFiles) {
            sourceFiles.add(f.toString());
        }
        listFiles.clear();

        listOfFiles(new File(activeProject.getBasePath()), ".jar");
        ArrayList<String> depFiles = new ArrayList<>();
        String test = "";
        for (File f : listFiles) {
            depFiles.add(f.toString());
            test = test + f.getName() + " ";
        }
        listFiles.clear();

        File outputFile = new File(activeProject.getBasePath(), "tmp/_cryptoguard.json");
        try {
            //String fileOut = Base.entryPoint(sourceFiles, depFiles, outputFile.getAbsolutePath(), null, 2);
        } catch (ExceptionHandler exceptionHandler) {
            exceptionHandler.printStackTrace();
        }
        //Messages.showMessageDialog(e.getProject(), "CryptoGuard has been ran on your code, see temp.txt for output", "CryptoGuard", Messages.getInformationIcon());
        Messages.showMessageDialog(e.getProject(), outputFile.getAbsolutePath(), "CryptoGuard", Messages.getInformationIcon());
    }
}
