package co.huggingface.tokenizers;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class SaferFFIVecEncoding extends Structure {
    public Pointer ptr;
    public SaferFFITokenizersLibrary.size_t len, cap;

//    public SaferFFIEncoding[] getEncodings() {
//
//    }
}
