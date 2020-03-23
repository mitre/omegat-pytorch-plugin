package org.mitre.pinball.omegat.pytorch;

import org.mitre.pinball.omegat.pytorch.PyTorchMachineTranslationOptions;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class PyTorchMachineTranslationOptionsTest {
    @Test
    void testModelDirectoryIsDirectory() throws IOException {
        File dir = Files.createTempDirectory("tmp-").toFile();
        dir.deleteOnExit();

        PyTorchMachineTranslationOptions options =
                new PyTorchMachineTranslationOptions().setModelDirectory(dir);

        // Assert the temporary directory is equal to the directory assigned
        assertEquals(options.getModelDirectory(), dir);
    }

    @Test
    void testModelDirectoryThrowsIfFile() throws IOException {
        // This is explicitly a file
        File file = File.createTempFile("hello", ".tmp");
        file.deleteOnExit();

        assertThrows(() -> new PyTorchMachineTranslationOptions().setModelDirectory(file));
    }

    @Test
    void testModelDirectoryThrowsIfNonexistentDirectory() throws IOException {
        File dir = Files.createTempDirectory("tmp-").toFile();
        dir.deleteOnExit();

        // Delete the temp directory explicitly so we ensure it doesn't exist
        boolean deleted = dir.delete();
        assertTrue(deleted);

        assertThrows(() -> new PyTorchMachineTranslationOptions().setModelDirectory(dir));
    }
}
