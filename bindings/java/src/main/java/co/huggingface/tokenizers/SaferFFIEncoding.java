package co.huggingface.tokenizers;

import com.sun.jna.Structure;

@Structure.FieldOrder({"ids","type_ids","tokens","words"})
public class SaferFFIEncoding extends Structure {
    public SaferFFIVecLong ids;

    public SaferFFIVecLong type_ids;

    public SaferFFIVecString tokens;

    public SaferFFIVecLong words;
}
