# OmegaT PyTorch plugin

## Motivation

This repo serves to enable translators using OmegaT to add plugin support for TorchScript models. Currently it supports scripted fairseq models by way [jFairseq](https://github.com/mitre/jfairseq).

## Build

The plugin can be built using `./gradlew jar`. Once this is done, the resulting artifact `./build/libs/omegat-pytorch-plugin-1.0-SNAPSHOT.jar` will need to be moved to the OmegaT plugins directory.

## Test

Tests can be run by issuing `./gradlew test`.

## Authors

- [Elijah Rippeth](mailto:erippeth@mitre.org)
