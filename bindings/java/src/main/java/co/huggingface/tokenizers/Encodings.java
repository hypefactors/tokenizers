package co.huggingface.tokenizers;

import co.huggingface.tokenizers.ffi.FFIVec;
import com.sun.jna.Pointer;

public class Encodings {

    private Encoding[] encodings;

    public Encodings(Pointer ptr) {
        assert(ptr != null);

        var ffiVec = new FFIVec(ptr);
        int vecLen = ffiVec.len.intValue();
        this.encodings = new Encoding[vecLen];
        Pointer[] vecPointers = ffiVec.ptr.getPointerArray(0, vecLen);

        for (int i = 0; i < vecLen; i++) {
            encodings[i] = new Encoding(vecPointers[i]);
        }
    }

    public Encoding[] getEncodings(){
        return this.encodings;
    }
}
