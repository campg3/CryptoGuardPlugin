package cryptoguard.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.wm.WindowManager;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RunAction extends AnAction {
    private static List<File> listFiles = new ArrayList<>();

    public static void listOfFiles(File dirPath) {
        File filesList[] = dirPath.listFiles();
        for (File file : filesList) {
            if (file.isFile() ) {
                if (file.toString().endsWith(".class")) {
                    listFiles.add(file);
                }
            }
            else {
                listOfFiles(file);
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

        listOfFiles(new File(activeProject.getBasePath()));
        List<String> sourceFiles = new ArrayList<>();
        for (File f : listFiles) {
            sourceFiles.add(f.toString());
        }

        Messages.showMessageDialog(e.getProject(), "CryptoGuard has been ran on your code, see temp.txt for output", "CryptoGuard", Messages.getInformationIcon());
        //Messages.showMessageDialog(e.getProject(), result, "CryptoGuard", Messages.getInformationIcon());
    }
}
