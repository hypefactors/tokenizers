package co.huggingface.tokenizers;

import co.huggingface.tokenizers.ffi.FFIEncoding;
import co.huggingface.tokenizers.ffi.FFIVec;

public class Encoding  {

    private FFIEncoding ffiEncoding;

    private void setFFIEncoding(FFIEncoding ffiEncoding){
        this.ffiEncoding = ffiEncoding;
    }

    public Encoding(FFIEncoding ffiEncoding) {
        this.setFFIEncoding(ffiEncoding);
//        FFILibrary.cleaner.register(this, new CleanEncoding(ffiEncoding.getPointer()));
    }

    public long[] getIds() {
        FFIVec ids = ffiEncoding.ids;
        return ids.ptr.getLongArray(0, ids.len.intValue());
    }

    public long[] getTypeIds() {
        FFIVec typeIds = ffiEncoding.type_ids;
        return typeIds.ptr.getLongArray(0, typeIds.len.intValue());
    }

    public long[] getWordIds() {
        FFIVec wordIds = ffiEncoding.words;
        return wordIds.ptr.getLongArray(0, wordIds.len.intValue());
    }

    public String[] getTokens() {
        FFIVec tokens = ffiEncoding.tokens;
        return tokens.ptr.getStringArray(0, tokens.len.intValue());
    }
}
