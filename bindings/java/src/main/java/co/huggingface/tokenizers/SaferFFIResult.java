package co.huggingface.tokenizers;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

@Structure.FieldOrder({"value","error"})
public abstract class SaferFFIResult extends Structure {
    public Pointer value;
    public String error;

    public abstract void drop();
}
