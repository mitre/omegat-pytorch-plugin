package org.mitre.pinball.omegat.pytorch;

import org.mitre.pinball.omegat.pytorch.dialog.PyTorchTranslationOptionDialog;
import org.omegat.core.Core;
import org.omegat.core.machinetranslators.BaseTranslate;
import org.omegat.gui.exttrans.IMachineTranslation;
import org.omegat.util.Language;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.nio.file.Paths;
import org.omegat.util.Preferences;

public class PyTorchMachineTranslation extends BaseTranslate implements IMachineTranslation {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(PyTorchMachineTranslation.class);

    protected boolean enabled;
    protected PyTorchMachineTranslationOptions options;

    public PyTorchMachineTranslation() {
        super();
        boolean created = DEFAULT_MODEL_DIRECTORY.mkdirs();
        if (created) {
            LOGGER.debug("Default model directory " + DEFAULT_MODEL_DIRECTORY.toString() + " created");
        }
        initOptions();
    }

    @Override
    public boolean isConfigurable() {
        return true;
    }


    /**
     * Register plugin into OmegaT.
     */
    public static void loadPlugins() {
        LOGGER.debug("Registering machine translation class " + PyTorchMachineTranslation.class.toString());
        Core.registerMachineTranslationClass(PyTorchMachineTranslation.class);
    }

    /**
     * Unregister plugin.
     * Currently not supported.
     */
    public static void unloadPlugins() {
    }

    /**
     * Preparation for OmegaT Menu.
     */
    private static final String OPTION_ALLOW_PYTORCH_TRANSLATE = "allow_pytorch_translate";
    private static final String OPTION_MODEL_DIRECTORY = "model_directory";

    private static File DEFAULT_MODEL_DIRECTORY = Paths.get(
            System.getProperty("user.home"),
            ".models",
            "pytorch"
            ).toFile();

    private void initOptions() {
        options = new PyTorchMachineTranslationOptions()
             .setModelDirectory(DEFAULT_MODEL_DIRECTORY);
    }

    @Override
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
        Preferences.setPreference(OPTION_ALLOW_PYTORCH_TRANSLATE, enabled);
    }

    @Override
    public void showConfigurationUI(final Window parent) {
        PyTorchTranslationOptionDialog dialog = new PyTorchTranslationOptionDialog(parent);
        dialog.pack();
        dialog.setData(options);
        dialog.setVisible(true);
        if (dialog.isModified(options)) {
            dialog.getData(options);
        }
        setCredential(OPTION_MODEL_DIRECTORY, options.getModelDirectory().toString(), false);
        Preferences.save();
    }

    @Override
    protected String getPreferenceName() {
        return OPTION_ALLOW_PYTORCH_TRANSLATE;
    }

    @Override
    public String getName() {
        return "PyTorch local translation";
    }

    @Override
    protected String translate(Language sLang, Language tLang, String text) throws Exception {
        return null;
    }

    /**
     * Return machine translation result.
     *
     * {@link IMachineTranslation()#getTranslation}
     * @param sLang source language.
     * @param tLang target language.
     * @param text source text.
     * @return translated text.
     * @throws Exception when error happened.
     */
    public String getTranslation(final Language sLang, final Language tLang, final String text)
            throws Exception {
        if (enabled) {
            // No-op for now
            String result = translate(sLang, tLang, text);
            if (result != null) {
                putToCache(sLang, tLang, text, result);
            }
            return result;
        } else {
            return null;
        }
    }
}
