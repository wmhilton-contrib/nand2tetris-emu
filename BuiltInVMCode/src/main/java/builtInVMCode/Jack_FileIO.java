/********************************************************************************
 * The contents of this file are subject to the GNU General Public License      *
 * (GPL) Version 2 or later (the "License"); you may not use this file except   *
 * in compliance with the License. You may obtain a copy of the License at      *
 * http://www.gnu.org/copyleft/gpl.html                                         *
 *                                                                              *
 * Software distributed under the License is distributed on an "AS IS" basis,   *
 * without warranty of any kind, either expressed or implied. See the License   *
 * for the specific language governing rights and limitations under the         *
 * License.                                                                     *
 *                                                                              *
 * This file was originally developed as part of the software suite that        *
 * supports the book "The Elements of Computing Systems" by Nisan and Schocken, *
 * MIT Press 2005. If you modify the contents of this file, please document and *
 * mark your changes clearly, for the benefit of others.                        *
 ********************************************************************************/

package builtInVMCode;

import java.util.Arrays;
import java.io.File;
import java.nio.file.*;
import Hack.VMEmulator.TerminateVMProgramThrowable;

/**
 * A built-in implementation for the FileIO class of the Jack OS.
 */
public class Jack_FileIO extends JackOSClass {

    public static boolean exists(short _filepath) throws TerminateVMProgramThrowable {
        String filepath = jackStringToJavaStringUsingVM(_filepath);
        return Files.exists(Paths.get(filepath));
    }

    public static boolean isDir(short _filepath) throws TerminateVMProgramThrowable {
        String filepath = jackStringToJavaStringUsingVM(_filepath);
        return Files.isDirectory(Paths.get(filepath));
    }

    public static boolean isFile(short _filepath) throws TerminateVMProgramThrowable {
        String filepath = jackStringToJavaStringUsingVM(_filepath);
        return Files.isRegularFile(Paths.get(filepath));
    }

    public static short read(short _filepath) throws TerminateVMProgramThrowable {
        String filepath = jackStringToJavaStringUsingVM(_filepath);
        String data = "";
        try {
            data = new String(Files.readAllBytes(Paths.get(filepath)));
            data = data.replace("\n", Character.toString((char)NEWLINE_KEY));
        } catch (java.io.IOException e) {
        }
        return javaStringToJackStringUsingVM(data);
    }

    public static short write(short _filepath, short _data) throws TerminateVMProgramThrowable {
        String filepath = jackStringToJavaStringUsingVM(_filepath);
        String data = jackStringToJavaStringUsingVM(_data);
        try {
            data = data.replace(Character.toString((char)NEWLINE_KEY), "\n");

            Files.write(Paths.get(filepath), data.getBytes());
        } catch (java.io.IOException e) {
        }
        return javaStringToJackStringUsingVM(data);
    }

    // Works for both files and directories
    public static boolean rm(short _filepath) throws TerminateVMProgramThrowable {
        String filepath = jackStringToJavaStringUsingVM(_filepath);
        boolean exists = false;
        try {
            exists = Files.deleteIfExists(Paths.get(filepath));
        } catch (java.io.IOException e) {
        }
        return exists;
    }

    public static short listFiles(short _filepath)  throws TerminateVMProgramThrowable {
        String filepath = jackStringToJavaStringUsingVM(_filepath);

        File[] files = new File(filepath).listFiles(File::isFile);
        Arrays.sort(files);

        short arr = callFunction("Array.new", files.length);
        for (int i = 0; i < files.length; i++) {
            short str = javaStringToJackStringUsingVM(files[i].getName());
            writeMemory(arr + i, str);
        }
        return arr;
    }

    public static short listDirs(short _filepath)  throws TerminateVMProgramThrowable {
        String filepath = jackStringToJavaStringUsingVM(_filepath);

        File[] dirs = new File(filepath).listFiles(File::isDirectory);
        Arrays.sort(dirs);

        short arr = callFunction("Array.new", dirs.length);
        for (int i = 0; i < dirs.length; i++) {
            short str = javaStringToJackStringUsingVM(dirs[i].getName());
            writeMemory(arr + i, str);
        }
        return arr;
    }

    public static boolean mkdir(short _filepath) throws TerminateVMProgramThrowable {
        String filepath = jackStringToJavaStringUsingVM(_filepath);
        boolean exists = false;
        File f = new File(filepath);
        return f.mkdir();
    }
}
