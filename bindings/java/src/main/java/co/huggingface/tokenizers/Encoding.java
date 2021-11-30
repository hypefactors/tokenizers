package co.huggingface.tokenizers;

import com.sun.jna.Pointer;

public class Encoding {

    SaferFFIEncoding ffiEncoding;

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

    public Encoding(SaferFFIEncoding ffiEncoding) {
        this.ffiEncoding = ffiEncoding;
        SaferFFITokenizersLibrary.cleaner.register(this, new CleanEncoding(ffiEncoding.getPointer()));
    }

    public long[] getIds() {
        var ids = ffiEncoding.ids;
        return ids.ptr.getLongArray(0, ids.len.intValue());
    }

    public long[] getTypeIds() {
        var typeIds = ffiEncoding.type_ids;
        return typeIds.ptr.getLongArray(0, typeIds.len.intValue());
    }

    public long[] getWordIds() {
        var wordIds = ffiEncoding.words;
        return wordIds.ptr.getLongArray(0, wordIds.len.intValue());
    }

    public String[] getTokens() {
        var tokens = ffiEncoding.tokens;
        return tokens.ptr.getStringArray(0, tokens.len.intValue());
    }
}
