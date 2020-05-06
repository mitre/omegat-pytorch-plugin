package org.mitre.pinball.omegat.pytorch.dialog;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.mitre.pinball.omegat.pytorch.FairseqMachineTranslation;
import org.mitre.pinball.omegat.pytorch.FairseqMachineTranslationOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;

public class FairseqTranslationOptionDialog extends JDialog {
    private JButton selectModelFileButton;
    private JButton selectTargetDictionaryFileButton;
    private JFileChooser chooser;
    private JPanel contentPane;
    private JButton cancelButton;
    private JButton selectBPECodesFileButton;
    private JButton selectSourceDictionaryFileButton;
    private JButton okButton;
    private JLabel modelFileLabel;
    private JLabel bpeCodesFillelabel;
    private JLabel sourceDictFileLabel;
    private JLabel targetDictFileLabel;

    private File defaultDir;
    private File modelFile;
    private File bpeCodesFile;
    private File sourceDictFile;
    private File targetDictFile;

    private boolean updated;

    private static final File DEFAULT_FILECHOOSER_DIR = FairseqMachineTranslation.DEFAULT_MODEL_DIRECTORY;

    private static final Logger LOGGER = LoggerFactory
            .getLogger(FairseqTranslationOptionDialog.class);

    private JFileChooser initJFileChooser(final File dir) {
        JFileChooser chooser = new JFileChooser(dir);
        // Disable multiple directories
        chooser.setMultiSelectionEnabled(false);
        // Only allow directories to be selected.
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        return chooser;
    }

    public FairseqTranslationOptionDialog(Window parent) {
        updated = false;
        setContentPane(contentPane);
        setModal(true);
        defaultDir = DEFAULT_FILECHOOSER_DIR;
        getRootPane().setDefaultButton(selectModelFileButton);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        chooser = initJFileChooser(defaultDir);

        selectModelFileButton.addActionListener(e -> {
            int ret = chooser.showOpenDialog(parent);
            if (ret == JFileChooser.APPROVE_OPTION) {
                modelFile = chooser.getSelectedFile();
                modelFileLabel.setText(modelFile.toString());
                updated = true;
            }
        });
        selectBPECodesFileButton.addActionListener(e -> {
            int ret = chooser.showOpenDialog(parent);
            if (ret == JFileChooser.APPROVE_OPTION) {
                bpeCodesFile = chooser.getSelectedFile();
                bpeCodesFillelabel.setText(bpeCodesFile.toString());
                updated = true;
            }
        });
        selectSourceDictionaryFileButton.addActionListener(e -> {
            int ret = chooser.showOpenDialog(parent);
            if (ret == JFileChooser.APPROVE_OPTION) {
                sourceDictFile = chooser.getSelectedFile();
                sourceDictFileLabel.setText(sourceDictFile.toString());
                updated = true;
            }
        });
        selectTargetDictionaryFileButton.addActionListener(e -> {
            int ret = chooser.showOpenDialog(parent);
            if (ret == JFileChooser.APPROVE_OPTION) {
                targetDictFile = chooser.getSelectedFile();
                targetDictFileLabel.setText(targetDictFile.toString());
                updated = true;
            }
        });
        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        cancelButton.addActionListener(e -> onCancel());
        okButton.addActionListener(e -> onOk());

    }

    public void setData(final FairseqMachineTranslationOptions options) {
        modelFile = options.getModelFile();
        bpeCodesFile = options.getBpeCodesFile();
        sourceDictFile = options.getSourceDictFile();
        targetDictFile = options.getTargetDictFile();
    }

    public void getData(final FairseqMachineTranslationOptions options) {
        options.setModelFile(modelFile);
        options.setBpeCodesFile(bpeCodesFile);
        options.setSourceDictFile(sourceDictFile);
        options.setTargetDictFile(targetDictFile);
    }

    public boolean isModified(final FairseqMachineTranslationOptions options) {
        if (!updated) {
            return false;
        }

        if (modelFile != null && bpeCodesFile != null && sourceDictFile != null && targetDictFile != null) {
            return !modelFile.equals(options.getModelFile()) &&
                    !bpeCodesFile.equals(options.getBpeCodesFile()) &&
                    !sourceDictFile.equals(options.getSourceDictFile()) &&
                    !targetDictFile.equals(options.getTargetDictFile());
        } else {
            return options.getModelFile() != null &&
                    options.getBpeCodesFile() != null &&
                    options.getSourceDictFile() != null &&
                    options.getTargetDictFile() != null;
        }
    }


    private void onCancel() {
        dispose();
    }

    private void onOk() {
        updated = true;
        dispose();
    }

    private void createUIComponents() {

    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(16, 11, new Insets(0, 0, 0, 0), -1, -1));
        selectBPECodesFileButton = new JButton();
        selectBPECodesFileButton.setText("Select BPE Codes File");
        contentPane.add(selectBPECodesFileButton, new GridConstraints(5, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        selectSourceDictionaryFileButton = new JButton();
        selectSourceDictionaryFileButton.setText("Select Source Dictionary File");
        contentPane.add(selectSourceDictionaryFileButton, new GridConstraints(8, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        contentPane.add(spacer1, new GridConstraints(13, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        contentPane.add(spacer2, new GridConstraints(4, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        contentPane.add(spacer3, new GridConstraints(7, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        contentPane.add(spacer4, new GridConstraints(12, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        contentPane.add(spacer5, new GridConstraints(10, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        contentPane.add(spacer6, new GridConstraints(14, 10, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer7 = new Spacer();
        contentPane.add(spacer7, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        selectTargetDictionaryFileButton = new JButton();
        selectTargetDictionaryFileButton.setText("Select Target Dictionary File");
        contentPane.add(selectTargetDictionaryFileButton, new GridConstraints(11, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cancelButton = new JButton();
        cancelButton.setText("Cancel");
        contentPane.add(cancelButton, new GridConstraints(14, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        okButton = new JButton();
        okButton.setText("OK");
        contentPane.add(okButton, new GridConstraints(14, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer8 = new Spacer();
        contentPane.add(spacer8, new GridConstraints(14, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer9 = new Spacer();
        contentPane.add(spacer9, new GridConstraints(14, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer10 = new Spacer();
        contentPane.add(spacer10, new GridConstraints(14, 9, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer11 = new Spacer();
        contentPane.add(spacer11, new GridConstraints(14, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer12 = new Spacer();
        contentPane.add(spacer12, new GridConstraints(14, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer13 = new Spacer();
        contentPane.add(spacer13, new GridConstraints(14, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer14 = new Spacer();
        contentPane.add(spacer14, new GridConstraints(15, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer15 = new Spacer();
        contentPane.add(spacer15, new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer16 = new Spacer();
        contentPane.add(spacer16, new GridConstraints(6, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer17 = new Spacer();
        contentPane.add(spacer17, new GridConstraints(9, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        modelFileLabel = new JLabel();
        modelFileLabel.setHorizontalTextPosition(2);
        modelFileLabel.setText("");
        contentPane.add(modelFileLabel, new GridConstraints(2, 6, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        bpeCodesFillelabel = new JLabel();
        bpeCodesFillelabel.setText("");
        contentPane.add(bpeCodesFillelabel, new GridConstraints(5, 6, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        sourceDictFileLabel = new JLabel();
        sourceDictFileLabel.setText("");
        contentPane.add(sourceDictFileLabel, new GridConstraints(8, 6, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        targetDictFileLabel = new JLabel();
        targetDictFileLabel.setText("");
        contentPane.add(targetDictFileLabel, new GridConstraints(11, 6, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer18 = new Spacer();
        contentPane.add(spacer18, new GridConstraints(15, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        selectModelFileButton = new JButton();
        selectModelFileButton.setText("Select Model File");
        contentPane.add(selectModelFileButton, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}
