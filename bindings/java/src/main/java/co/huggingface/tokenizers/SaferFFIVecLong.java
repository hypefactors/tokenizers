package co.huggingface.tokenizers;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

@Structure.FieldOrder({"ptr","len","cap"})
public class SaferFFIVecLong extends Structure {
    public Pointer ptr;
    public SaferFFITokenizersLibrary.size_t len, cap;
    
    public long[] getArray() {
        return ptr.getLongArray(0, len.intValue());
    } 
}
