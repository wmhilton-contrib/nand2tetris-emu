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
import java.util.List;
import java.io.File;
import java.nio.file.*;
import Hack.VMEmulator.TerminateVMProgramThrowable;

/**
 * A built-in implementation for the FileReader class of the Jack OS.
 */
public class Jack_FileReader extends JackOSClass {
    private static List<String> lines;
    private static int index;

    public static void open(short _filepath) throws TerminateVMProgramThrowable {
        String filepath = jackStringToJavaStringUsingVM(_filepath);
        try {
            lines = Files.readAllLines(Paths.get(filepath));
        } catch (java.io.IOException e) {
            callFunction("Sys.error", FILE_NOT_FOUND);
        }
        index = 0;
        return;
    }

    public static short lineCount() {
        return (short)lines.size();
    }

    public static boolean hasMoreLines() throws TerminateVMProgramThrowable {
        return index < lines.size();
    }

    public static short readLine() throws TerminateVMProgramThrowable {
        String line = lines.get(index);
        index++;
        return javaStringToJackStringUsingVM(line);
    }
}
