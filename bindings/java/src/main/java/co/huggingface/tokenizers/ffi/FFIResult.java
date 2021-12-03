package co.huggingface.tokenizers.ffi;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

@Structure.FieldOrder({"value","error"})
public abstract class FFIResult extends Structure {
    public Pointer value;
    public String error;

    public abstract void drop();

    public static class Tokenizer extends FFIResult {
        @Override
        public void drop() {
            FFILibrary.INSTANCE.tokenizer_drop(this.getPointer());
        }
    }

    public static class Encoding extends FFIResult {
        @Override
        public void drop() {
            FFILibrary.INSTANCE.encoding_drop(this.getPointer());
        }
    }

    public static class Encodings extends FFIResult {
        @Override
        public void drop() {
            FFILibrary.INSTANCE.encodings_drop(this.getPointer());
        }
    }
}
