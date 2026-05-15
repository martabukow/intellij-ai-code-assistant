package com.example.aicodeassistant;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.Messages;

public class ExplainCodeAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {

        Editor editor = e.getData(CommonDataKeys.EDITOR);
        if (editor == null) return;

        String selectedText = editor.getSelectionModel().getSelectedText();
        if (selectedText == null || selectedText.isEmpty()) {
            Messages.showInfoMessage("No code selected", "AI Assistant");
            return;
        }

        String fileName = e.getData(CommonDataKeys.VIRTUAL_FILE) != null
                ? e.getData(CommonDataKeys.VIRTUAL_FILE).getName()
                : "Unknown file";

        int startOffset = editor.getSelectionModel().getSelectionStart();
        int endOffset = editor.getSelectionModel().getSelectionEnd();

        int documentLength = editor.getDocument().getTextLength();

        int contextStart = Math.max(0, startOffset - 500);
        int contextEnd = Math.min(documentLength, endOffset + 500);

        String surroundingCode = editor.getDocument().getText()
                .substring(contextStart, contextEnd);

        String context = """
        File: %s
        
        --- Surrounding code ---
        %s
        
        --- Selected code ---
        %s
        """.formatted(fileName, surroundingCode, selectedText);

        String response = OpenAIService.explain(context);

        Messages.showMessageDialog(
                e.getProject(),
                response,
                "AI Explanation",
                Messages.getInformationIcon()
        );
    }
}