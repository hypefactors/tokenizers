package co.huggingface.tokenizers;

import com.sun.jna.*;

import java.lang.ref.Cleaner;

public interface SaferFFITokenizersLibrary extends Library {
    SaferFFITokenizersLibrary INSTANCE = (SaferFFITokenizersLibrary) Native.load("safer_ffi_tokenizers", SaferFFITokenizersLibrary.class);

    // to automatically free memory on the Rust side when GC'ed on JVM
    static final Cleaner cleaner = Cleaner.create();

    // handling unsigned ints as size_t
    public static class size_t extends IntegerType {
        public size_t() { this(0); }
        public size_t(long value) { super(Native.SIZE_T_SIZE, value); }
    }

    // getting & dropping tokenizers
    SaferFFIResultTokenizer tokenizer_from_pretrained(String identifier);
    void tokenizer_drop(Pointer tokenizerResult);

    // getting & dropping encoders
    SaferFFIEncoding encode_from_str(Pointer tokenizer, String input, int addSpecialTokens);
    SaferFFIEncoding encode_from_vec_str(Pointer tokenizer, SaferFFIVec slice, int addSpecialTokens);
    SaferFFIVec encode_batch(Pointer tokenizer, SaferFFIVec slice, int addSpecialTokens);

    void encoding_drop(Pointer encoding);
    void vec_encoding_drop(SaferFFIVec vector);
}