package co.huggingface.tokenizers;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

@Structure.FieldOrder({"ptr","len","cap"})
public class SaferFFIVec extends Structure {
    public Pointer ptr;
    public SaferFFITokenizersLibrary.size_t len, cap;

    public SaferFFIVec() {

    }

    public SaferFFIVec(Pointer p) {
        super(p);
        read();
    }

}