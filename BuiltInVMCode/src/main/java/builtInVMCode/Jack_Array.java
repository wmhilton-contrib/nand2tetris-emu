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

import Hack.VMEmulator.TerminateVMProgramThrowable;

/**
 * A built-in implementation for the Array class of the Jack OS.
 */

@SuppressWarnings("UnusedDeclaration")
public class Jack_Array extends JackOSClass {

	public static short NEW(short size) throws TerminateVMProgramThrowable {
		if (size <= 0)
			callFunction("Sys.error", ARRAY_NEW_NONPOSITIVE_SIZE);
		short arr = callFunction("Memory.alloc", size + 1);
		writeMemory(arr, size);
		return (short)(arr + 1);
	}

	public static void dispose(short arr) throws TerminateVMProgramThrowable {
		callFunction("Memory.deAlloc", arr - 1);
	}

	public static short length(short arr) throws TerminateVMProgramThrowable {
			return readMemory(arr - 1);
	}

}
