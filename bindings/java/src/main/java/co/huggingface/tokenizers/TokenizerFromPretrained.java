package co.huggingface.tokenizers;

import co.huggingface.tokenizers.ffi.FFILibrary;
import com.sun.jna.*;

public class TokenizerFromPretrained implements WrapsFFIResultType<Pointer> {
    private Pointer pointer;

    private TokenizerFromPretrained(Pointer pointer) {
        assert(pointer != null);
        this.pointer = pointer;
    }

    public static Result<TokenizerFromPretrained> create(String identifier) {
        var ffiResult = FFILibrary.INSTANCE.tokenizer_from_pretrained(identifier);
        var wrapper = ffiResult.value == null ? null : new TokenizerFromPretrained(ffiResult.value);
        var result = new Result<TokenizerFromPretrained>(ffiResult, wrapper);

        return result;
    }

    @Override
    public Pointer ok() {
        return pointer;
    }

    public Result<Encoding> encode(String input, Boolean addSpecialTokens) {
        var ffiResult = FFILibrary.INSTANCE.encode_from_str(this.ok(), input, addSpecialTokens ? 1 : 0);
        var wrapper = ffiResult.value == null ? null : new Encoding(ffiResult.value);
        var result = new Result<Encoding>(ffiResult, wrapper);

        return result;
    }

//    public Encoding encode(List<String> input, Boolean addSpecialTokens) {
//        FFIVec stringArray = new FFIVec();
//        stringArray.ptr = new StringArray(input.toArray(new String[0]));
//        stringArray.len = new FFILibrary.size_t(input.size());
//        stringArray.cap = new FFILibrary.size_t(input.size());
//        var ffiEncoding = FFILibrary.INSTANCE.encode_from_vec_str(this.ok(), stringArray, addSpecialTokens ? 1 : 0);
//        return new Encoding(ffiEncoding);
//    }

//    public Encoding[] encode_batch(List<String> input, Boolean addSpecialTokens) {
//        FFIVec vec = new FFIVec();
//        vec.ptr = new StringArray(input.toArray(new String[0]));
//        vec.len = new FFILibrary.size_t(input.size());
//        vec.cap = new FFILibrary.size_t(input.size());
//        var result =  FFILibrary.INSTANCE.encode_batch(this.ok(), vec, addSpecialTokens ? 1 : 0);
//        var encodingArray =  new EncodingVec(result);
//        return encodingArray.getEncodings();
//    }
}
