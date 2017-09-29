P3LX
====

P3LX is a Processing 3 wrapper library for the [LX](https://github.com/heronarts/LX) lighting engine and the basis of the [LX Studio](http://lx.studio/) application. It allows you to simply embed LX inside a Processing sketch with a rich UI library that makes it easy and painless to render 3D simulations alongside versatile 2D controls.

The simplest way to start an LXStudio project is by downloading this repository and making a copy of the [examples/LXStudioDemo](https://github.com/heronarts/P3LX/tree/master/examples/LXStudioDemo) Processing sketch for your own project.

## Development Environment ##

The recommended IDE for LX is Eclipse, with simple `ant` tasks for command-line build. Create a folder to work in and clone both the LX and P3LX repositories side-by-side.
```
$ mkdir workspace
$ cd workspace
$ git clone https://github.com/heronarts/LX.git
$ git clone https://github.com/heronarts/P3LX.git
```

To build the full project:
```
$ cd P3LX/build
$ ant
```

For quicker build during iterative development, there is a lightweight build task included which does no preprocessing, documentation generation, etc. Note that this task also does no cleanup. If you've removed or renamed files, you'll want to manually nuke the `bin` folder to avoid issues.

```
$ cd P3LX/build
$ ant lightning
$ cp ../bin/P3LX.jar <wherever you need it>
```

To open the project in Eclipse:
```
File | Import...
General > Existing Projects Into Workspace
Select root directory...
```

Go through this process for both the LX and P3LX projects, selecting `workspace/LX` and `workspace/P3LX` as the root directories.

## Licensing Notes ##

LX is made available under the GPLv2 with special linking exceptions that permit the use of [CoreMidi4J](https://github.com/DerekCook/CoreMidi4J) (which improves MIDI support on OSX - thanks Derek!) and [google-gson](https://github.com/google/gson). This means that you are free to distribute a project using LX so long as all the components of your project are open-source and GPL compatible. Specifically, this means ***you may not distribute software using LX if any portion of that software is proprietary closed-source or non-GPL compatible***.

If this licensing is obstructive to your needs or you are unclear as to whether your desired use case is compliant, contact me to discuss licensing options: mark@heronarts.com.

### Contact and Collaboration ###

Building a big cool project? I'm probably interested in hearing about it! Want to solicit some help, request new framework features, or just ask a random question? Drop me a line: mark@heronarts.com
