package co.huggingface.tokenizers;

import co.huggingface.tokenizers.ffi.FFILibrary;
import co.huggingface.tokenizers.ffi.FFIEncoding;
import co.huggingface.tokenizers.ffi.FFIVec;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class EncodingVec {

    private FFIVec ffiVec;

    private void setFFIVec(FFIVec ffiVec){
        this.ffiVec = ffiVec;
    }

    public EncodingVec(FFIVec ffiVec) {
        this.setFFIVec(ffiVec);
        //This is causing memory issues - don't know why
        //SaferFFITokenizersLibrary.cleaner.register(this, new CleanEncodingVec(ffiVec));
    }

    public  Encoding[] getEncodings(){
        Pointer ptr = this.ffiVec.ptr;
        int len = ffiVec.len.intValue();
        Pointer[] parray = ptr.getPointerArray(0, len);
        Encoding[] encodings = new Encoding[len];

        for (int i = 0; i < len; i++) {
            Pointer pp = parray[i];
            FFIEncoding ffiEncoding = Structure.newInstance(FFIEncoding.class, pp);
            encodings[i] = new Encoding(ffiEncoding);
        }

        return encodings;
    }

}
