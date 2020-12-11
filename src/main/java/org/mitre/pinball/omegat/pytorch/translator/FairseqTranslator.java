package org.mitre.pinball.omegat.pytorch.translator;

import org.mitre.jfairseq.encoder.Encoder;
import org.mitre.jfairseq.encoder.FastBPEEncoder;
import org.mitre.jfairseq.encoder.SentencepieceEncoder;
import org.mitre.jfairseq.tokenizer.Tokenizer;
import org.mitre.jfairseq.tokenizer.WhitespaceTokenizer;

import java.io.File;
import java.io.IOException;


public class FairseqTranslator implements PyTorchTranslator {

    final private org.mitre.jfairseq.FairseqTranslator translator;

    public FairseqTranslator(final File sourceVocab, final File targetVocab, final File modelFile,
                             final Encoder encoder, final Tokenizer tokenizer) throws IOException {
        this.translator = new org.mitre.jfairseq.FairseqTranslator(sourceVocab, targetVocab, modelFile, encoder, tokenizer);
    }

    public static FairseqTranslator fromWhitespaceTokenizerFastBPE(final File sourceVocab, final File targetVocab, final File modelFile,
                             final File bpeCodes) throws IOException {
        return new FairseqTranslator(sourceVocab, targetVocab, modelFile, new FastBPEEncoder(bpeCodes), new WhitespaceTokenizer());
    }

    public static FairseqTranslator fromWhitespaceTokenizerSentencepiece(final File sourceVocab, final File targetVocab, final File modelFile,
                                                                   final File spmModel) throws IOException {
        return new FairseqTranslator(sourceVocab, targetVocab, modelFile, new SentencepieceEncoder(spmModel), new WhitespaceTokenizer());
    }

    @Override
    public String translate(String sent) throws Exception {
        return translator.translate(sent);
    }
}
