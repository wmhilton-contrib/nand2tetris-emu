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

import java.io.File;
import java.io.FileWriter;
import Hack.VMEmulator.TerminateVMProgramThrowable;

/**
 * A built-in implementation for the FileWriter class of the Jack OS.
 */
public class Jack_FileWriter extends JackOSClass {
    private static FileWriter file;

    public static void open(short _filepath) throws TerminateVMProgramThrowable {
        String filepath = jackStringToJavaStringUsingVM(_filepath);
        try {
            file = new FileWriter(new File(filepath));
        } catch (java.io.IOException e) {
            callFunction("Sys.error", FILE_NOT_FOUND);
        }
        return;
    }

    public static void writeLine(short text) throws TerminateVMProgramThrowable {
        String line = jackStringToJavaStringUsingVM(text);
        try {
            file.write(line + System.getProperty("line.separator"));
        } catch (java.io.IOException e) {
            callFunction("Sys.error", FILE_IO_ERROR);
        }
        return;
    }

    public static void close() throws TerminateVMProgramThrowable {
        try {
            file.close();
        } catch (java.io.IOException e) {
            callFunction("Sys.error", FILE_IO_ERROR);
        }
        return;
    }
}
