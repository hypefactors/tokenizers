package co.huggingface.tokenizers;

import com.sun.jna.*;
import com.sun.jna.ptr.PointerByReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TokenizerFromPretrained extends PointerType {

    // according to https://techinplanet.com/java-9-cleaner-cleaner-cleanable-objects/,
    // it is wise to keep the cleaner runnables as a static class
    private static class CleanTokenizer implements Runnable {
        private Pointer ptr;

        public CleanTokenizer(Pointer ptr) {
            this.ptr = ptr;
        }

        @Override
        public void run() {
            SaferFFITokenizersLibrary.INSTANCE.tokenizer_drop(ptr);
        }
    }

    private Pointer ffiTokenizer;

    public TokenizerFromPretrained(String identifier) throws ExceptionInInitializerError {
        var result = SaferFFITokenizersLibrary.INSTANCE.tokenizer_from_pretrained(identifier);
        if (result.error != null || result.value == null) {
            var message = result.error == null ? "unknown init error" : result.error;
            throw new ExceptionInInitializerError(message);
        }

        var resultPtr = result.getPointer();
        this.ffiTokenizer = result.value;
        this.setPointer(resultPtr);
        SaferFFITokenizersLibrary.cleaner.register(this, new CleanTokenizer(resultPtr));
    }

    public Encoding encode(String input, Boolean addSpecialTokens) {
        var ffiEncoding = SaferFFITokenizersLibrary.INSTANCE.encode_from_str(this.ffiTokenizer, input, addSpecialTokens ? 1 : 0);
        return new Encoding(ffiEncoding);
    }

    public Encoding encode(List<String> input, Boolean addSpecialTokens) {
        SaferFFIVec stringArray = new SaferFFIVec();
        stringArray.ptr = new StringArray(input.toArray(new String[0]));
        stringArray.len = new SaferFFITokenizersLibrary.size_t(input.size());
        stringArray.cap = new SaferFFITokenizersLibrary.size_t(input.size());
        var ffiEncoding = SaferFFITokenizersLibrary.INSTANCE.encode_from_vec_str(this.ffiTokenizer, stringArray, addSpecialTokens ? 1 : 0);
        return new Encoding(ffiEncoding);
    }

    public Encoding[] encode_batch(List<String> input, Boolean addSpecialTokens) {
        SaferFFIVec vec = new SaferFFIVec();
        vec.ptr = new StringArray(input.toArray(new String[0]));
        vec.len = new SaferFFITokenizersLibrary.size_t(input.size());
        vec.cap = new SaferFFITokenizersLibrary.size_t(input.size());
        var result =  SaferFFITokenizersLibrary.INSTANCE.encode_batch(this.ffiTokenizer, vec, addSpecialTokens ? 1 : 0);
        var encodingArray =  new EncodingVec(result);
        return encodingArray.getEncodings();
    }

    public  Encoding[] getEncodings(Pointer ptr, int len){
        Pointer[] parray = ptr.getPointerArray(0, len);
        Encoding[] encodings = new Encoding[len];

        for (int i = 0; i < len; i++) {
            Pointer pp = parray[i];
            SaferFFIEncoding ffiEncoding = new SaferFFIEncoding(pp);
            encodings[i] = new Encoding(ffiEncoding);
        }

        return encodings;
    }

}
