# Functional Programming - TP 0: Setting up Visual Studio Code and Scala

This page provides a step-by-step guide to install Scala 3 and/or Visual Studio Code (VSCode) on [Kapps'Ul](https://kappsul.su.univ-lorraine.fr/) or on your computer, as well a some useful plugins. We *strongly* recommend an installation on your own computer. 

*Warning:* It's essential to ensure that the paths to your projects do not contain spaces, special characters, or non-English letters. This is especially relevant on Windows, where historically, issues arose with usernames containing spaces or special characters.

## Tool installation

### Kapps'Ul 

You can use Kapps'UL, a virtual environment provided by the FST, to use a VSCode instance with Scala and the relevant plugins installed. Your data will be stored on this platform, you will not be able to access them on your computer. 

*Warning:* if you want to use this environment outside of the faculty's network, you will have to use a [VPN](https://numerique.univ-lorraine.fr/catalogue-des-services/reseau-distant-vpn). 

* Go to [Kapps'Ul](https://kappsul.su.univ-lorraine.fr/) and log in with your University's credentials
* Click on *Installer une application*
* Search for *Scala* and click on the tile
* Click on *Deployer*
* Give a name to your instance (for example, *Programmation Fonctionnelle*)
* Wait until the app is deployed
* In the installation notice, under the section *IDE en ligne (VSCode)*, on the link after *nom DNS* in a new tab.
* You will reach another login page. Past the password given under the *secrets* section on the previous page.
* You can now set up your virtual instance of VSCode! Please jump to the [Scala Installation part](#scala-installation) below.

### Computer Installation

#### VSCode Installation

* Open your favorite web browser and go to this webpage: https://code.visualstudio.com/download
* Download the executable corresponding to your OS and follow the instructions.

#### Scala Installation

We recommend that you use VSCode to do the course exercises. To be able to use Scala on the command line, or in interactive mode, or to use other text editors for writing Scala code, follow those steps to install Scala and the Scala build tool `sbt`:

* Open your favorite web browser and go to this webpage: [https://docs.scala-lang.org/getting-started/index.html](https://docs.scala-lang.org/getting-started/index.html)
* Follow the instructions related to your OS to install Scala version 3
* You may need to restart your terminal, log out, or reboot in order for the changes to take effect
* You should have the commands `scala` and `sbt` available now.

#### Scala Extension for VSCode

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
* In VSCode, choose `File - Open Folder` and select the `https://github.com/jcailler/programmation-fonctionnelle-ul/TP/TP0/hello-world` directory.
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

When you compiled it, new files appears. Do not pay too much attention to them for now, you are going to work with `Main.scala`, at least at the beginning. 

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
