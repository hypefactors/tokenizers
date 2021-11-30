package co.huggingface.tokenizers;

import com.sun.jna.*;

public interface SaferFFITokenizersLibrary extends Library {
    SaferFFITokenizersLibrary INSTANCE = (SaferFFITokenizersLibrary) Native.load("safer_ffi_tokenizers", SaferFFITokenizersLibrary.class);

    // handling unsigned ints as size_t
    public static class size_t extends IntegerType {
        public size_t() { this(0); }
        public size_t(long value) { super(Native.SIZE_T_SIZE, value); }
    }

    // getting & dropping tokenizers
    Pointer tokenizer_new();
    void tokenizer_drop(Pointer tokenizer);

    // getting & dropping encoders
    SaferFFIEncoding tokenizer_encode(Pointer tokenizer, String input);
    void encoding_drop(Pointer encoding);
}