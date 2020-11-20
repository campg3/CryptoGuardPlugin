package cryptoguard.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.io.File;

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
            Messages.showMessageDialog(e.getProject(), param.getPath(), "Path", Messages.getInformationIcon());
        });

    }
}
