# Functional Programming - TP 0: Setting up Visual Studio Code and Scala

This page provides a step-by-step guide to install Scala 3 and/or Visual Studio Code (VSCode) on the FST's computer or on your own computer, as well a some useful plugins. We recommend an installation on your own computer. 

*Warning:* It's essential to ensure that the paths to your projects do not contain spaces, special characters, or non-English letters. This is especially relevant on Windows, where historically, issues arose with usernames containing spaces or special characters.

## Tool installation

### VSCode Installation

* Open your favorite web browser and go to this webpage: https://code.visualstudio.com/download
* Download the executable corresponding to your OS and follow the instructions.

### Scala Installation

We recommend that you use VSCode to do the course exercises. To be able to use Scala on the command line, or in interactive mode, or to use other text editors for writing Scala code, follow those steps to install Scala and the Scala build tool `sbt`:

* Open your favorite web browser and go to this webpage: [https://docs.scala-lang.org/getting-started/install-scala.html](https://docs.scala-lang.org/getting-started/install-scala.html)
* Follow the instructions related to your OS to install Scala version 3
* You may need to restart your terminal, log out, or reboot in order for the changes to take effect
* You should have the commands `scala` and `sbt` available now.

### Scala Extension for VSCode

The extension "Scala (Metals)" adds functionality to work with Scala to VSCode, and is needed if you want to solve the exercises with VSCode. To install this extension:

* Change to the "Extensions" pane and search for "Metals"
* In command line: https://marketplace.visualstudio.com/items?itemName=scalameta.metals


## Scala Hello World: Your First Project in Scala

For the following steps to work, you need to have followed one of the [two previous installation methods above](#tool-installation).

### Using VSCode, from this github repository

This repository contains a simple hello-world project that you can use to get started. 

* You first need to create a local copy of the repository contents on your computer. There are several ways to do this:
    - If you have `git` installed on your computer, you can *clone* the repository using the command `git clone https://github.com/jcailler/programmation-fonctionnelle-ul.git`, which will copy over all files to a sub-directory of your current directory.
    - The same can be done directly in VSCode: select `File - New Window` and the command `Clone Git Repository ...`. Provide the URL `https://github.com/jcailler/programmation-fonctionnelle-ul.git`
    - In your web browser you can download the repository contents by clicking on the green `<code>` button, selecting `Download ZIP`, and then unpacking the downloaded zip file.
* In VSCode, choose `File - Open Folder` and select the `https://github.com/jcailler/programmation-fonctionnelle-ul/TP0/hello-world` directory.
* You will be able to see the different downloaded files in the `Explorer` pane, in particular the `build.sbt` file containing the Scala build configuration, and the sources `src/main/scala/Main.scala`.
* To compile and run this project, you need to import the build settings into VSCode. Scala Metals might by itself offer to `Import build`, which you can agree to. Otherwise, change to the Metals pane (the "m" icon on the left side) and select the `Build Command` &rarr; `Import build`.
* Once the project has been compiled, you can execute the program by selecting the `Main.scala` file and clicking on `run` or `debug`, occurring above the program code.

*Warning:* VSCode can take some time to detect and download Metals for the first time. Go to the *Metals* extension on the left and wait a bit. 

### On the command line, by setting up a new project using sbt

* Create a folder `programmation-fonctionnelle`
* Go into this folder: `cd programmation-fonctionnelle`
* Run `sbt new scala/scala3.g8`
* Give a name to your project (for example, `hello-world`)
* Go into your project: `cd hello-world`
* Eventually VSCode will ask you to import the build, please click “Import build”
* Run `sbt`
* Type `run`

⚠️ You need to be in the folder `programmation-fonctionnelle/hello-world` to be able to use `sbt`!

Congrats, you have successfully compiled your first program in Scala! Feel free to explore the project to get familiar with the syntax and the file hierarchy. 


## What Are Those Files?
Here is the initial project that you have downloaded: 
```
- hello-world
    - project (sbt uses this for its own files)
        - build.properties
    - build.sbt (sbt's build definition file)
    - src
        - main
            - scala (all of your Scala code goes here)
                - Main.scala (Entry point of the program) <-- this is all we need for now
        - test
            - scala
                - MySuite.scala (unit tests for this project)
```


#### src

In this folder, all code you are writing is stored. It contains the main folder and the test folder.

  1. /main
    
  This folder contains all files with the actual code of your project. At the start, there is only the ```Main.scala``` file. The file named "Main" is usually the starting point when you run your code. As you might have noticed from earlier exercises, the amount of ```.scala``` files in this folder can increase quite rapidly, so if needed sort your files in fittingly named folders for better oversight. 

  2. /test
    
  This folder contains all files with automated tests for your project. At the start, it only contains a short test that always succeeds. You have probably seen and used such tests in earlier exercises. Later in this exercise you will learn to write these tests yourself (if you haven't written some yourself already). You do not need to write all tests into one file, you can spread them over multiple separate files.

#### target

Generated files (compiled classes, packaged jars, managed files, caches, and documentation) will be written to the target directory by default. You can take a look at all included files, but you will probably not need to change any of these files right now.

#### build.sbt

```scala
val scala3Version = "3.4.0"
```

As part of your build definition, you specify the Scala version to be used in your build. sbt will take care of downloading and installing this version of the compiler. This allows people with different system configurations to build the same projects with consistent results.

```scala
lazy val root = project
  .in(file("."))
  .settings(
    name := "hello-world",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test
  )

```

In the second part, there are various other settings for your project, including the name of the project folder, the build version or any library dependencies. For a detailed explaination, read the [sbt documentation](https://www.scala-sbt.org/1.x/docs/Basic-Def.html)

#### project

build.sbt conceals how sbt really works. sbt builds are defined with Scala code. That code, itself, has to be built. What better way than with sbt?

The project directory is another build inside your build, which knows how to build your build. Your build definition is an sbt project.

This topic is rather complex but you can read about what it is and how to use it in the [sbt documentation](https://www.scala-sbt.org/1.x/docs/Organizing-Build.html)

#### README.md

A README is a Markdown file that usually encapsulates important information about the provided code. README files typically include information on:

- What the project does
- Why the project is useful
- How users can get started with the project
- Where users can get help with your project
- Who maintains and contributes to the project

When taking a look at the README file you generated in the beginning, you can read about all these topics.

If you have any important information about your code which should not be part of the code itself as a comment, do not hesitate to write everything into the README file. Using the Markdown format, you have various very easy options to modify, structure or format your text. A quick oversight over all functions of Markdown offers the [cheatsheet](https://www.markdownguide.org/cheat-sheet/). If you need a preview of how your README will look, click the *Open Preview to the Side* button (book with magnifying glasses) on the top right.

## Exercises Management for this Course
* We will provide a single repository for all the exercises and projects of this course, with the following hierarchy:  
```
- programmation-fonctionnelle-ul
    - TP
        - TP0
        - TP1
        - ...
    - Projects
        - ...
```

However, since VSCode can struggle to manage multiple Scala project at the same time, we recommend you to work with only one project at a time. In addition, this repository will be updated every week. You can pull the update (but be sure to save your changes before) or download the new version every time. 

* When we ask you to type and/or run a specific command (for instance, `git clone myProject`), we can directly give you the command or write it that way: ```$ git clone myProject```. The `$` symbol is called the prompt and is not part of the command. Writing a project is a convention used to indicate that the following command should be executed in a terminal.

## Next Steps

You can now start with the first exercise of the course: [TP1](../TP1)
