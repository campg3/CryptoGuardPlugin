package cryptoguard.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class HelpAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        HelpActionUI.main(null);
    }
}

