package co.huggingface.tokenizers;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

@Structure.FieldOrder({"value","error"})
public class SaferFFIOpaqueResult extends Structure {
    public Pointer value;
    public String error;
}