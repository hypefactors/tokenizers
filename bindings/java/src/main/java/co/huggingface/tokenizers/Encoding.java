package co.huggingface.tokenizers;

import co.huggingface.tokenizers.ffi.FFIEncoding;
import co.huggingface.tokenizers.ffi.FFIVec;
import com.sun.jna.Pointer;

public class Encoding implements WrapsFFIResultType<FFIEncoding> {

    private FFIEncoding ffiEncoding;

    @Override
    public FFIEncoding ok() {
        return this.ffiEncoding;
    }

    protected Encoding(Pointer pointer) {
        assert(pointer != null);
        this.ffiEncoding = new FFIEncoding(pointer);
    }

    public long[] getIds() {
        FFIVec ids = ok().ids;
        return ids.ptr.getLongArray(0, ids.len.intValue());
    }

    public long[] getTypeIds() {
        FFIVec typeIds = ok().type_ids;
        return typeIds.ptr.getLongArray(0, typeIds.len.intValue());
    }

    public long[] getWordIds() {
        FFIVec wordIds = ok().words;
        return wordIds.ptr.getLongArray(0, wordIds.len.intValue());
    }

    public String[] getTokens() {
        FFIVec tokens = ok().tokens;
        return tokens.ptr.getStringArray(0, tokens.len.intValue());
    }
}
