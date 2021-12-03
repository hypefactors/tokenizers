package co.huggingface.tokenizers;

import co.huggingface.tokenizers.ffi.FFIEncoding;
import com.sun.jna.Pointer;

public class Encoding implements WrapsFFIResultType {

    private long[] ids;
    private long[] typeIds;
    private long[] wordIds;
    private String[] tokens;

    protected Encoding(Pointer pointer) {
        assert(pointer != null);
        var ffiEncoding = new FFIEncoding(pointer);
        this.ids = ffiEncoding.ids.ptr.getLongArray(0, ffiEncoding.ids.len.intValue());
        this.typeIds = ffiEncoding.type_ids.ptr.getLongArray(0, ffiEncoding.type_ids.len.intValue());
        this.wordIds = ffiEncoding.words.ptr.getLongArray(0, ffiEncoding.words.len.intValue());
        this.tokens = ffiEncoding.tokens.ptr.getStringArray(0, ffiEncoding.tokens.len.intValue());
    }

    public long[] getIds() {
        return this.ids;
    }

    public long[] getTypeIds() {
        return this.typeIds;
    }

    public long[] getWordIds() {
        return this.wordIds;
    }

    public String[] getTokens() {
        return this.tokens;
    }
}
