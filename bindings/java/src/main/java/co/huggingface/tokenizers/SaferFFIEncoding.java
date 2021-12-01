package co.huggingface.tokenizers;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

@Structure.FieldOrder({"ids","type_ids","tokens","words"})
public class SaferFFIEncoding extends Structure {
    public SaferFFIVec ids, type_ids, tokens, words;

    public SaferFFIEncoding(Pointer p) {
        super(p);
        read();
    }
}
