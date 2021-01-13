package org.mitre.pinball.omegat.pytorch;

import org.scalatest._
import flatspec._
import matchers._

import java.io.File
import java.io.IOException
import java.nio.file.Files

class FairseqMachineTranslationOptionsSpec  extends AnyFlatSpec with should.Matchers {

    "Fairseq Translation Options" should "work when passed a file" in {
        val file = File.createTempFile("tmp-", ".txt", Files.createTempDirectory("tmp-").toFile())
        file.deleteOnExit()

        val options = new FairseqMachineTranslationOptions().setModelFile(file)

        // Assert the temporary directory is equal to the directory assigned
        options.getModelFile() shouldBe file

    }


}
