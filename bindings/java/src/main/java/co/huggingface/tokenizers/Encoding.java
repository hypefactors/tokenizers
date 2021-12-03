package co.huggingface.tokenizers;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class Encoding {

    private SaferFFIEncoding ffiEncoding;

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

    private void setFFIEncoding(SaferFFIEncoding ffiEncoding){
        this.ffiEncoding = ffiEncoding;
    }

    public Encoding(SaferFFIEncoding ffiEncoding) {
        this.setFFIEncoding(ffiEncoding);
        SaferFFITokenizersLibrary.cleaner.register(this, new CleanEncoding(ffiEncoding.getPointer()));
    }

    public long[] getIds() {
        SaferFFIVec ids = ffiEncoding.ids;
        return ids.ptr.getLongArray(0, ids.len.intValue());
    }

    public long[] getTypeIds() {
        SaferFFIVec typeIds = ffiEncoding.type_ids;
        return typeIds.ptr.getLongArray(0, typeIds.len.intValue());
    }

    public long[] getWordIds() {
        SaferFFIVec wordIds = ffiEncoding.words;
        return wordIds.ptr.getLongArray(0, wordIds.len.intValue());
    }

    public String[] getTokens() {
        SaferFFIVec tokens = ffiEncoding.tokens;
        return tokens.ptr.getStringArray(0, tokens.len.intValue());
    }
}
