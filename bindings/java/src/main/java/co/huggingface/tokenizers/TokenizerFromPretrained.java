package co.huggingface.tokenizers;

import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
import com.sun.jna.StringArray;
import com.sun.jna.ptr.PointerByReference;

import java.util.List;

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

    public TokenizerFromPretrained(String identifier) {
        var tokenizer = SaferFFITokenizersLibrary.INSTANCE.tokenizer_from_pretrained(identifier);
        this.setPointer(tokenizer);
        SaferFFITokenizersLibrary.cleaner.register(this, new CleanTokenizer(tokenizer));
    }

    public Encoding encode(String input, Boolean addSpecialTokens) {
        var ffiEncoding = SaferFFITokenizersLibrary.INSTANCE.encode_from_str(this.getPointer(), input, addSpecialTokens ? 1 : 0);
        return new Encoding(ffiEncoding);
    }

    public Encoding encode(List<String> input, Boolean addSpecialTokens) {
        StringArray sarray = new StringArray(input.toArray(new String[0]));
        Pointer p = sarray.getPointer(0);
        SaferFFIVec slice = new SaferFFIVec();
        slice.ptr = p;
        slice.len = new SaferFFITokenizersLibrary.size_t(input.size());
        var ffiEncoding = SaferFFITokenizersLibrary.INSTANCE.encode_from_vec_str(this.getPointer(), slice, addSpecialTokens ? 1 : 0);
        return new Encoding(ffiEncoding);
    }
}
