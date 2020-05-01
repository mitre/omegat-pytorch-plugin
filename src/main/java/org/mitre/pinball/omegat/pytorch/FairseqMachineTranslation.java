package org.mitre.pinball.omegat.pytorch;

import org.mitre.pinball.omegat.pytorch.dialog.FairseqTranslationOptionDialog;
import org.mitre.pinball.omegat.pytorch.translator.FairseqTranslator;
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


public class FairseqMachineTranslation extends BaseTranslate implements IMachineTranslation {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(FairseqMachineTranslation.class);

    protected boolean enabled;
    protected FairseqMachineTranslationOptions options;

    private FairseqTranslator translator;

    public FairseqMachineTranslation() {
        super();
        translator = null;
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
        LOGGER.debug("Registering machine translation class " + FairseqMachineTranslation.class.toString());
        Core.registerMachineTranslationClass(FairseqMachineTranslation.class);
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
    private static final String OPTION_ALLOW_PYTORCH_TRANSLATE = "allow_fairseq_translate";
    private static final String OPTION_MODEL_DIRECTORY = "model_directory";

    public static File DEFAULT_MODEL_DIRECTORY = Paths.get(
            System.getProperty("user.home"),
            ".models",
            "fairseq"
            ).toFile();

    private void initOptions() {
        options = new FairseqMachineTranslationOptions();
    }

    @Override
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
        Preferences.setPreference(OPTION_ALLOW_PYTORCH_TRANSLATE, enabled);
    }

    @Override
    public void showConfigurationUI(final Window parent) {
        FairseqTranslationOptionDialog dialog = new FairseqTranslationOptionDialog(parent);
        dialog.pack();
        dialog.setData(options);
        dialog.setVisible(true);
        if (dialog.isModified(options)) {
            dialog.getData(options);
        }

        setCredential(OPTION_MODEL_DIRECTORY, options.getModelFile().toString(), false);
        Preferences.save();
    }

    @Override
    protected String getPreferenceName() {
        return OPTION_ALLOW_PYTORCH_TRANSLATE;
    }

    @Override
    public String getName() {
        return "Fairseq local translation";
    }

    @Override
    protected String translate(Language sLang, Language tLang, String text) throws Exception {
        if (translator == null) {
            translator = FairseqTranslator.fromWhitespaceTokenizerFastBPE(
                    options.getSourceDictFile(),
                    options.getTargetDictFile(),
                    options.getModelFile(),
                    options.getBpeCodesFile()
            );
        }

        return translator.translate(text);
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
