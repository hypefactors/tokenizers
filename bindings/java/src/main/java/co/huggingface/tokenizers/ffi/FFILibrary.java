package co.huggingface.tokenizers.ffi;

import com.sun.jna.*;

public interface FFILibrary extends Library {
    FFILibrary INSTANCE = (FFILibrary) Native.load("safer_ffi_tokenizers", FFILibrary.class);

    // handling unsigned ints as size_t
    public static class size_t extends IntegerType {
        public size_t() { this(0); }
        public size_t(long value) { super(Native.SIZE_T_SIZE, value); }
    }

    // getting & dropping tokenizers
    FFIResult.Tokenizer tokenizer_from_pretrained(String identifier);
    void tokenizer_drop(Pointer tokenizerResult);

    // getting & dropping encoders
    FFIEncoding encode_from_str(Pointer tokenizer, String input, int addSpecialTokens);
    FFIEncoding encode_from_vec_str(Pointer tokenizer, FFIVec slice, int addSpecialTokens);
    FFIVec encode_batch(Pointer tokenizer, FFIVec slice, int addSpecialTokens);

    void encoding_drop(Pointer encoding);
    void vec_encoding_drop(FFIVec vector);
}