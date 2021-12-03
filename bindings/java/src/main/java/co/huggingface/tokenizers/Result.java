package co.huggingface.tokenizers;

import co.huggingface.tokenizers.ffi.FFIResult;

import java.lang.ref.Cleaner;

public class Result<T extends WrapsFFIResultType> {

    private FFIResult ffiResult;

    private T value;

    // according to https://techinplanet.com/java-9-cleaner-cleaner-cleanable-objects/,
    // it is wise to keep the cleaner runnables as a static class
    // to automatically free memory on the Rust side when GC'ed on JVM
    private static final Cleaner cleaner = Cleaner.create();

    private static class CleanResult implements Runnable {
        private FFIResult result;

        public CleanResult(FFIResult result) {
            this.result = result;
        }

        @Override
        public void run() {
            result.drop();
        }
    }

    public Result(FFIResult ffiResult, T value) {
        this.ffiResult = ffiResult;
        this.value = value;
        cleaner.register(this, new CleanResult(ffiResult));
    }

    public T value() {
        return this.value;
    }

    public String error() {
        return ffiResult.error;
    }

    public boolean isError() {
        return ffiResult.error != null;
    }

    public boolean hasValue() {
        return ffiResult.value != null;
    }
}
