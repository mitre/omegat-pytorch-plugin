# OmegaT PyTorch plugin

## Motivation

This repo serves to enable translators using OmegaT to add plugin support for TorchScript models. Currently it supports scripted fairseq models by way [jFairseq](https://github.com/mitre/jfairseq).

## Build

The plugin can be built using `sbt clean compile assembly`. Once this is done, the resulting artifact `./build/libs/omegat-pytorch-plugin-1.0-SNAPSHOT.jar` will need to be moved to the OmegaT plugins directory.

## Running OmegaT with the plugin

Because TorchScript support relies on native code a la libtorch, the OmegaT jar will need to be invoked with a java option indicating the location to the libtorch bundle:

```sh
% java -jar -Djava.library.path=$LIBTORCH_HOME /path/to/OmegaT.jar
```

where `$LIBTORCH_HOME` is `/path/to/libtorch/`. Note: libtorch is platform-dependent, so be sure to download the appropriate libtorch for the operating system on which OmegaT will be running.

In addition, because of differences in native library loading on Windows, the path will need to be updated to include `$LIBTORCH_HOME/lib/` so all native dependencies can be located by library loader. 

Currently Windows support is only ensured for PyTorch 1.7.1 nightlies as of 2 December 2020 and above, though non-Windows support is guaranteed for 1.5+.

## Test

Tests can be run by issuing `sbt test`.

## Authors

- [Elijah Rippeth](mailto:erippeth@mitre.org)
