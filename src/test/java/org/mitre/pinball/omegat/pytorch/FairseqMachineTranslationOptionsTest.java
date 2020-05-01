package org.mitre.pinball.omegat.pytorch;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FairseqMachineTranslationOptionsTest {
    @Test
    void testModelDirectoryIsDirectory() throws IOException {
        File dir = Files.createTempDirectory("tmp-").toFile();
        dir.deleteOnExit();

        FairseqMachineTranslationOptions options =
                new FairseqMachineTranslationOptions().setModelFile(dir);

        // Assert the temporary directory is equal to the directory assigned
        assertEquals(options.getModelFile(), dir);
    }

    @Test
    void testModelDirectoryThrowsIfFile() throws IOException {
        // This is explicitly a file
        File file = File.createTempFile("hello", ".tmp");
        file.deleteOnExit();

        assertThrows(() -> new FairseqMachineTranslationOptions().setModelFile(file));
    }

    @Test
    void testModelDirectoryThrowsIfNonexistentDirectory() throws IOException {
        File dir = Files.createTempDirectory("tmp-").toFile();
        dir.deleteOnExit();

        // Delete the temp directory explicitly so we ensure it doesn't exist
        boolean deleted = dir.delete();
        assertTrue(deleted);

        assertThrows(() -> new FairseqMachineTranslationOptions().setModelFile(dir));
    }
}
