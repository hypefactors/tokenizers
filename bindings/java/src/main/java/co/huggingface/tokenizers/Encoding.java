package co.huggingface.tokenizers;

import com.sun.jna.Pointer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Long> getIds() {
        SaferFFIVec ids = ffiEncoding.ids;
        long[] idsArray =  ids.ptr.getLongArray(0, ids.len.intValue());
        return Arrays.stream(idsArray).boxed().collect(Collectors.toList());
    }

    public List<Long> getTypeIds() {
        SaferFFIVec typeIds = ffiEncoding.type_ids;
        long[] typeIdsArray = typeIds.ptr.getLongArray(0, typeIds.len.intValue());
        return Arrays.stream(typeIdsArray).boxed().collect(Collectors.toList());
    }

    public List<Long> getWordIds() {
        SaferFFIVec wordIds = ffiEncoding.words;
        long[] wordIdsArray = wordIds.ptr.getLongArray(0, wordIds.len.intValue());
        return Arrays.stream(wordIdsArray).boxed().collect(Collectors.toList());
    }

    public List<String> getTokens() {
        SaferFFIVec tokens = ffiEncoding.tokens;
        String[] tokensArray = tokens.ptr.getStringArray(0, tokens.len.intValue());
        return Arrays.asList(tokensArray);
    }
}
