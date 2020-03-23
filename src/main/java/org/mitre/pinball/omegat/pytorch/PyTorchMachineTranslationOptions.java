package org.mitre.pinball.omegat.pytorch;

import java.io.File;

public class PyTorchMachineTranslationOptions {

    private File modelDirectory;

    public PyTorchMachineTranslationOptions setModelDirectory(File modelDirectory) {
        if (!modelDirectory.exists()) {
            String msg = "Model directory " + modelDirectory.toString() + " does not exist";
            throw new ExceptionInInitializerError(msg);
        }
        if (!modelDirectory.isDirectory()) {
            String msg = "Model directory " + modelDirectory.toString() + " is not a directory";
            throw new ExceptionInInitializerError(msg);
        }
        this.modelDirectory = modelDirectory;
        return this;
    }

    public File getModelDirectory() {
        return modelDirectory;
    }

}
