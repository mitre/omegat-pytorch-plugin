package org.mitre.pinball.omegat.pytorch.translator;


public interface PyTorchTranslator {
    String translate(final String sent) throws Exception;
}
