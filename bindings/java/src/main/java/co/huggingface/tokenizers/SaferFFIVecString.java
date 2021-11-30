package co.huggingface.tokenizers;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

@Structure.FieldOrder({"ptr","len","cap"})
public class SaferFFIVecString extends Structure {
    public Pointer ptr;
    public SaferFFITokenizersLibrary.size_t len, cap;

    public String[] getArray() {
        return ptr.getStringArray(0, len.intValue());
    }
}
