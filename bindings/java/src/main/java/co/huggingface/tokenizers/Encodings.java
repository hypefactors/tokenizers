package co.huggingface.tokenizers;

import co.huggingface.tokenizers.ffi.FFIVec;
import com.sun.jna.Pointer;

public class Encodings implements WrapsFFIResultType<FFIVec> {

    private FFIVec ffiVec;

    public Encodings(Pointer ptr) {
        assert(ptr != null);
        this.ffiVec = new FFIVec(ptr);
    }

    public  Encoding[] getEncodings(){
        Pointer ptr = this.ffiVec.ptr;
        int len = ffiVec.len.intValue();
        Pointer[] parray = ptr.getPointerArray(0, len);
        Encoding[] encodings = new Encoding[len];

        for (int i = 0; i < len; i++) {
            Pointer pp = parray[i];
            encodings[i] = new Encoding(pp);
        }

        return encodings;
    }

    @Override
    public FFIVec ok() {
        return this.ffiVec;
    }
}
