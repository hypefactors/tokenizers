package co.huggingface.tokenizers;

import com.sun.jna.Pointer;

public class EncodingVec {

    private SaferFFIVec ffiVec;


    private static class CleanEncodingVec implements Runnable {
        private SaferFFIVec ffiVec;

        public CleanEncodingVec(SaferFFIVec ptr) {
            this.ffiVec = ptr;
        }

        @Override
        public void run() {
            SaferFFITokenizersLibrary.INSTANCE.vec_encoding_drop(ffiVec);
        }
    }

    private void setFFIVec(SaferFFIVec ffiVec){
        this.ffiVec = ffiVec;
    }

    public EncodingVec(SaferFFIVec ffiVec) {
        this.setFFIVec(ffiVec);
        SaferFFITokenizersLibrary.cleaner.register(this, new CleanEncodingVec(ffiVec));
    }

    public  Encoding[] getEncodings(){
        Pointer ptr = this.ffiVec.ptr;
        int len = ffiVec.len.intValue();
        Pointer[] parray = ptr.getPointerArray(0, len);
        Encoding[] encodings = new Encoding[len];

        for (int i = 0; i < len; i++) {
            Pointer pp = parray[i];
            SaferFFIEncoding ffiEncoding = new SaferFFIEncoding(pp);
            encodings[i] = new Encoding(ffiEncoding);
        }

        return encodings;
    }

}
