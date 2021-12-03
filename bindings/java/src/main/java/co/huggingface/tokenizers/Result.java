package co.huggingface.tokenizers;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class Result<T extends Structure> {

    private SaferFFIResult ffiResult;

    // according to https://techinplanet.com/java-9-cleaner-cleaner-cleanable-objects/,
    // it is wise to keep the cleaner runnables as a static class
    private static class CleanResult implements Runnable {
        private SaferFFIResult result;

        public CleanResult(SaferFFIResult result) {
            this.result = result;
        }

        @Override
        public void run() { result.drop(); }
    }

    public Result(SaferFFIResult ffiResult) {
        this.ffiResult = ffiResult;
        SaferFFITokenizersLibrary.cleaner.register(this, new CleanResult(ffiResult));
    }

    public T ok(Class<T> type) {
        if (ffiResult.value != null) {
            return Structure.newInstance(type, ffiResult.value);
        }

        return null;
    }

    public Pointer getPointer() {
        return ffiResult.value;
    }

    public String error() { return ffiResult.error; }

    public boolean hasError() { return ffiResult.error != null; }

    public boolean hasValue() { return ffiResult.value != null; }
}
