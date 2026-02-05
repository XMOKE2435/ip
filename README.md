# xmoke.Xmoke project template

This is a masterpiece project for course CS2103DE. It's named after the CS god, XMOKE. Given below are instructions on how to use it.

## Setting up in IntelliJ

Prerequisites: JDK 21.

1. Open IntelliJ (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project first)
1. Open the project into IntelliJ as follows:
   1. Click `Open`.
   1. Select the project directory, and click `OK`.
   1. If there are any further prompts, accept the defaults.
1. Configure the project to use **JDK 21** (not other versions) as explained in [here](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk).<br>
   In the same dialog, set the **Project language level** field to the `SDK default` option.
1. After that, locate the `src/main/java/xmoke.Xmoke.java` file, right-click it, and choose `Run xmoke.Xmoke.main()` (if the code editor is showing compile errors, try restarting the IDE). If the setup is correct, you should see something like the below as the output:
   ```
Hello from
__  __ __  __  ___  _  __ _____
\ \/ /|  \/  |/ _ \| |/ /| ____|
 \  / | |\/| | | | | ' / |  _|
 /  \ | |  | | |_| | . \ | |___
/_/\_\|_|  |_|\___/|_|\_\|_____|
   ```

**Warning:** Keep the `src\main\java` folder as the root folder for Java files (i.e., don't rename those folders or move Java files to another folder outside of this folder path), as this is the default location some tools (e.g., Gradle) expect to find Java files.

## Running Tests

The project comes with a set of test scripts for different operating systems:

- **MacOS/Linux**: Run the shell script
  ```bash
  cd text-ui-test
  ./runtest.sh
  ```

- **Windows**: Run the batch script
  ```batch
  cd text-ui-test
  runtest.bat
  ```

These scripts will compile the source files, run the tests, and compare the output against the expected output.
