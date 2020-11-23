package cryptoguard.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import frontEnd.Interface.outputRouting.ExceptionHandler;
import frontEnd.MessagingSystem.routing.structure.Default.Issue;
import frontEnd.MessagingSystem.routing.structure.Default.Report;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ViewAction extends AnAction {

    private List<Issue> issuesList = new ArrayList<>();

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        issuesList.clear();
        FileChooserDescriptor fileChooser = new FileChooserDescriptor(
                true,
                true,
                false,
                false,
                false,
                false
        );

        fileChooser.setTitle("CryptoGuard Summary File Chooser");
        fileChooser.setDescription("Choose a CryptoGuard output file to summarize");

        FileChooser.chooseFile(fileChooser, e.getProject(),null, (param) -> {
            //Messages.showMessageDialog(e.getProject(), param.getPath(), "Path", Messages.getInformationIcon());
            File file = new File(param.getPath());
            try {
                Report r = Report.deserialize(new File(param.getPath()));
                issuesList = r.getIssues();
                String str = "Project name: " + e.getProject().getName() +
                        "\nNumber of crypto errors: " + Integer.toString(r.getIssues().size()) + "\n";
                for (Issue i : r.getIssues()) {
                    str = str + i.getMessage() + "\n";
                }
                Messages.showMessageDialog(
                        e.getProject(),
                        str,
                        "Path",
                        Messages.getInformationIcon()
                );
            } catch (ExceptionHandler exceptionHandler) {
                exceptionHandler.printStackTrace();
            }

        });

    }
}
