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
import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

import org.omegat.util.Preferences;


public class FairseqMachineTranslation extends BaseTranslate implements IMachineTranslation {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(FairseqMachineTranslation.class);

    protected boolean enabled;
    protected FairseqMachineTranslationOptions options;
    private CompletableFuture<FairseqTranslator> setup;

    private FairseqTranslator translator;

    public FairseqMachineTranslation() {
        super();
        translator = null;
        setup = new CompletableFuture<>();

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
    private static final String OPTION_MODEL_FILE = "model_file";
    private static final String OPTION_BPE_FILE = "bpecodes_file";
    private static final String OPTION_SRC_DICT_FILE = "src_dict_file";
    private static final String OPTION_TGT_DICT_FILE = "tgt_dict_file";

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

        setCredential(OPTION_MODEL_FILE, options.getModelFile().toString(), false);
        setCredential(OPTION_BPE_FILE, options.getBpeCodesFile().toString(), false);
        setCredential(OPTION_SRC_DICT_FILE, options.getSourceDictFile().toString(), false);
        setCredential(OPTION_TGT_DICT_FILE, options.getTargetDictFile().toString(), false);
        Preferences.save();

        setup = CompletableFuture.supplyAsync(() -> {
                FairseqTranslator translator = null;
                LOGGER.info("Need to construct a fairseq translator... Starting now.");
                long t0 = System.currentTimeMillis();
                try {
                    translator = FairseqTranslator.fromWhitespaceTokenizerSentencepiece(
                            new File(getCredential(OPTION_SRC_DICT_FILE)),
                            new File(getCredential(OPTION_TGT_DICT_FILE)),
                            new File(getCredential(OPTION_MODEL_FILE)),
                            new File(getCredential(OPTION_BPE_FILE))
                    );
                } catch (IOException e) {
                    LOGGER.info(e.toString());
                }
                LOGGER.info("Finish constructing. Took " + (System.currentTimeMillis() - t0) / 1000 + " seconds.");
                return translator;
        });

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
        LOGGER.info("Trying to translate '" + text + "'.");
        if (translator == null) {
            translator = setup.get();
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
