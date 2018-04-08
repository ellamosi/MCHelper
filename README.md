# MCHelper
An Android TV service for basic MusicCast integration

Right now it's meant for personal use and cannot really be used without changing the hardcoded behaviors and IPs.

## Background

I have an Sony TV that runs Android TV, a nVidia Shield TV and a Yamaha R-N803 network receiver. I've been looking into ways to make most of the scenarios work with a single remote with minimal input. This includes turning on the amplifier when the TV is on, automatically switching to the right input and properly controlling the volume through it.

In the end I came up with this Android app meant to be installed on the TV instead, that monitors for screen and volume events to trigger actions through the MusicCast API documented in [this document](http://habitech.s3.amazonaws.com/PDFs/YAM/MusicCast/Yamaha%20MusicCast%20HTTP%20simplified%20API%20for%20ControlSystems.pdf).

## Features

- *Volume Up/Down*: Turns on the amp, sets the volume (uses a logarithmic volume level mapping for more straightforward control). The volume bar can still be shown on screen if the option for external speakers is set, as long as the audio out is set to headphones (does not affect the level of the optical output).
- *Screen On*: Turns on the amp, sets it to the TV Input
- *Screen Off*: Turns off the amp only if the current input is the TV Input
