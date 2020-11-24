package cryptoguard.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import frontEnd.Interface.outputRouting.ExceptionHandler;
import frontEnd.MessagingSystem.routing.structure.Default.Issue;
import frontEnd.MessagingSystem.routing.structure.Default.Report;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
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
                ViewActionUI.main(e.getProject().getName(), r.getIssues());
            } catch (ExceptionHandler exceptionHandler) {
                exceptionHandler.printStackTrace();
            }

        });

    }
}
