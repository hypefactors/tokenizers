package co.huggingface.tokenizers.ffi;

import com.sun.jna.Structure;

@Structure.FieldOrder({"ids","type_ids","tokens","words"})
public class FFIEncoding extends Structure {
    public FFIVec ids, type_ids, tokens, words;
}
