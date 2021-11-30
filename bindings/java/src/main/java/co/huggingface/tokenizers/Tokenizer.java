package co.huggingface.tokenizers;

import com.sun.jna.Pointer;
import com.sun.jna.PointerType;

public class Tokenizer extends PointerType {

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

    private static class CleanEncoding implements Runnable {
        private Pointer ptr;

        public CleanEncoding(Pointer ptr) {
            this.ptr = ptr;
        }

        @Override
        public void run() {
            SaferFFITokenizersLibrary.INSTANCE.encoding_drop(ptr);
        }
    }

    public Tokenizer(String identifier) {
        var tokenizer = SaferFFITokenizersLibrary.INSTANCE.tokenizer_new();
        this.setPointer(tokenizer);
        SaferFFITokenizersLibrary.cleaner.register(this, new CleanTokenizer(tokenizer));
    }

    public SaferFFIEncoding encode(String input) {
        var encoding = SaferFFITokenizersLibrary.INSTANCE.tokenizer_encode(this.getPointer(), input);
        SaferFFITokenizersLibrary.cleaner.register(encoding, new CleanEncoding(encoding.getPointer()));
        return encoding;
    }
}
