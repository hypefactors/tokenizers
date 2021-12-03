package co.huggingface.tokenizers;

import com.sun.jna.*;
import java.util.List;

public class TokenizerFromPretrained extends PointerType {

    private Pointer ffiTokenizer;

    public TokenizerFromPretrained(String identifier) throws ExceptionInInitializerError {
        var ffiResult = SaferFFITokenizersLibrary.INSTANCE.tokenizer_from_pretrained(identifier);
        var result = new Result<SaferFFIResultTokenizer>(ffiResult);
        if (result.error() != null) {
            throw new ExceptionInInitializerError(result.error());
        }

        this.ffiTokenizer = result.getPointer();
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

}
