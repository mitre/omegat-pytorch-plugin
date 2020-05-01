package org.mitre.pinball.omegat.pytorch;

import java.io.File;

public class FairseqMachineTranslationOptions {

    private File modelFile;
    private File bpeCodesFile;
    private File targetDictFile;
    private File sourceDictFile;


    private void validateFile(final File file, final String fileDescriptor) {
        if (!file.exists()) {
            String msg = fileDescriptor + " file " + file.toString() + " does not exist";
            throw new ExceptionInInitializerError(msg);
        }
        if (!file.isFile()) {
            String msg = fileDescriptor + " file " + file.toString() + " is not a file";
            throw new ExceptionInInitializerError(msg);
        }
    }

    public FairseqMachineTranslationOptions setBpeCodesFile(File bpeCodesFile) {
        validateFile(bpeCodesFile, "BPE Codes");
        this.bpeCodesFile = bpeCodesFile;
        return this;
    }

    public FairseqMachineTranslationOptions setSourceDictFile(File sourceDictFile) {
        validateFile(sourceDictFile, "Source dictionary");
        this.sourceDictFile = sourceDictFile;
        return this;
    }

    public FairseqMachineTranslationOptions setTargetDictFile(File targetDictFile) {
        validateFile(targetDictFile, "Target dictionary");
        this.targetDictFile = targetDictFile;
        return this;
    }

    public FairseqMachineTranslationOptions setModelFile(File modelFile) {
        validateFile(modelFile, "Model");
        this.modelFile = modelFile;
        return this;
    }

    public File getModelFile() {
        return modelFile;
    }

    public File getTargetDictFile() {
        return targetDictFile;
    }

    public File getSourceDictFile() {
        return sourceDictFile;
    }

    public File getBpeCodesFile() {
        return bpeCodesFile;
    }


}
