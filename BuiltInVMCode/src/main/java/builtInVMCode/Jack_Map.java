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

import java.util.Map;
import java.util.HashMap;
import Hack.VMEmulator.TerminateVMProgramThrowable;

/**
 * A built-in implementation for the FileReader class of the Jack OS.
 */
public class Jack_Map extends JackOSClass {
    private static Map<Short, Map> maps = new HashMap();
    private static short counter = 0;

    public static short NEW() throws TerminateVMProgramThrowable {
        counter++;
        Map map = new HashMap<String, Short>();
        maps.put(counter, map);

        short self = callFunction("Memory.alloc", 1);
        writeMemory(self, counter);
        return self;
    }

    public static void put(short self, short _key, short val) throws TerminateVMProgramThrowable {
        short index = readMemory(self);
        Map map = maps.get(index);

        String key = jackStringToJavaStringUsingVM(_key);
        map.put(key, val);
        return;
    }

    public static short get(short self, short _key) throws TerminateVMProgramThrowable {
        short index = readMemory(self);
        Map map = maps.get(index);

        String key = jackStringToJavaStringUsingVM(_key);
        return (short)map.get(key);
    }

    public static void dispose(short self) throws TerminateVMProgramThrowable {
        callFunction("Memory.deAlloc", self);

        short index = readMemory(self);
        maps.remove(index);
        return;
    }

}
